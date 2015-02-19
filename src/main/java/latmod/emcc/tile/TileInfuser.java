package latmod.emcc.tile;
import latmod.core.*;
import latmod.core.mod.LC;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.emcc.EMCCConfig;
import latmod.emcc.api.*;
import latmod.emcc.item.tools.ItemToolEMCC;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileInfuser extends TileLM
{
	public ItemStack itemInfusing = null;
	public int timer = 0;
	
	public void spawnPart()
	{
		double px = ParticleHelper.rand.nextFloat();
		double py = ParticleHelper.rand.nextFloat();
		double pz = ParticleHelper.rand.nextFloat();
		LC.proxy.spawnDust(worldObj, xCoord + px, yCoord + py + 1, zCoord + pz, 0xFF33FFFF);
	}
	
	@SuppressWarnings("unchecked")
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(timer == 0)
		{
			if(isServer())
			{
				timer = EMCCConfig.General.ticksToInfuse;
				
				FastList<EntityItem> itemList = new FastList<EntityItem>();
				itemList.addAll(worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(0D, 1D, 0D, 1D, 2D, 1D).getOffsetBoundingBox(xCoord, yCoord, zCoord)));
				itemInfusing = infuse(itemList);
			}
			
			for(int i = 0; i < 10; i++)
				spawnPart();
			
			markDirty();
		}
		
		return true;
	}
	
	private ItemStack infuse(FastList<EntityItem> itemList)
	{
		for(int i = 0; i < itemList.size(); i++)
		{
			EntityItem eiI = itemList.get(i);
			ItemStack itemI = eiI.getEntityItem();
			
			if(itemI != null && itemI.stackSize > 0)
			{
				if(itemI.getItem() instanceof IEmcTool)
				{
					IEmcTool tool = (IEmcTool)itemI.getItem();
					
					for(int j = 0; j < itemList.size(); j++)
					{
						if(j != i)
						{
							EntityItem eiJ = itemList.get(j);
							ItemStack itemJ = eiJ.getEntityItem();
							
							ToolInfusion ti = ToolInfusion.get(itemJ.getItem());
							
							if(ti != null && tool.canEnchantWith(itemI, ti))
							{
								int addedLvls = (itemJ.stackSize / ti.requiredSize);
								if(addedLvls > 0) addedLvls = Math.min(addedLvls, ti.maxLevel - ItemToolEMCC.getInfusionLevel(itemI, ti));
								
								if(addedLvls > 0 && itemJ.stackSize >= addedLvls * ti.requiredSize)
								{
									ItemStack is1 = eiJ.getEntityItem().copy();
									is1.stackSize -= addedLvls * ti.requiredSize;
									if(is1.stackSize == 0) eiJ.setDead();
									else eiJ.setEntityItemStack(is1);
									
									ItemStack itemI1 = itemI.copy();
									ItemToolEMCC.setInfusionLevel(itemI1, ti, addedLvls + ItemToolEMCC.getInfusionLevel(itemI, ti));
									eiI.setDead();
									return itemI1;
								}
							}
						}
					}
				}
			}
		}
		
		return null;
	}
	
	public void onUpdate()
	{
		if(timer > 0)
		{
			timer--;
			
			if(timer == 0)
			{
				if(itemInfusing != null)
				{
					for(int i = 0; i < 100; i++)
						spawnPart();
					
					if(isServer())
					{
						InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 1.5D, zCoord + 0.5D, 0D, 0.1D, 0D, itemInfusing, 20);
						itemInfusing = null;
						markDirty();
					}
				}
			}
			else spawnPart();
		}
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		timer = tag.getShort("Timer");
		itemInfusing = InvUtils.loadStack(tag, "");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setShort("Timer", (short)timer);
		InvUtils.saveStack(tag, "ItemInfusing", itemInfusing);
	}
}