package latmod.emcc.item.tools;
import cpw.mods.fml.relauncher.*;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.*;
import latmod.emcc.item.ItemEMCC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUUWrench extends ItemEMCC implements IEmcTool
{
	private static final String NBT_KEY = "WrenchData";
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_full;
	
	public ItemUUWrench(String s)
	{
		super(s);
		setMaxDamage(32);
		setMaxStackSize(1);
		setFull3D();
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableWrench)
			EMCC.recipes.addRecipe(new ItemStack(this), "UBU", " S ", " S ",
					'U', EMCCItems.UU_BLOCK,
					'S', ODItems.STICK,
					'B', new ItemStack(EMCCItems.i_emc_storage, 1, 0));
	}
	
	public int getItemEnchantability()
	{ return 15; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/wrench");
		icon_full = ir.registerIcon(EMCC.mod.assets + "tools/wrench_full");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{
		if(is.stackTagCompound != null && is.stackTagCompound.hasKey(NBT_KEY))
			return icon_full; return itemIcon;
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
	{
		if (!ep.canPlayerEdit(x, y, z, side, is)) return false;
		
		if(ep.isSneaking())
		{
			if(!w.isRemote)
			{
				NBTTagCompound tag = (NBTTagCompound)(is.hasTagCompound() ? is.stackTagCompound.getTag(NBT_KEY) : null);
				
				if(tag == null)
				{
					TileEntity te = w.getTileEntity(x, y, z);
					
					if(te != null && te instanceof IEmcWrenchable)
					{
						IEmcWrenchable wr = (IEmcWrenchable)te;
						
						if(wr.canWrench(ep))
						{
							tag = new NBTTagCompound();
							wr.writeToWrench(tag);
							
							if(!is.hasTagCompound()) is.stackTagCompound = new NBTTagCompound();
							tag.setInteger("PlaceID", Item.getIdFromItem(Item.getItemFromBlock(w.getBlock(x, y, z))));
							tag.setShort("PlaceMetadata", (short)w.getBlockMetadata(x, y, z));
							is.stackTagCompound.setTag(NBT_KEY, tag);
							
							wr.onWrenched(ep, is);
							w.setBlockToAir(x, y, z);
						}
					}
				}
				else
				{
					x += ForgeDirection.VALID_DIRECTIONS[side].offsetX;
					y += ForgeDirection.VALID_DIRECTIONS[side].offsetY;
					z += ForgeDirection.VALID_DIRECTIONS[side].offsetZ;
					
					if(w.isAirBlock(x, y, z))
					{
						int placeId = tag.getInteger("PlaceID");
						int placeMeta = tag.getShort("PlaceMetadata");
						
						Block b = Block.getBlockById(placeId);
						
						w.setBlock(x, y, z, b);
						w.setBlockMetadataWithNotify(x, y, z, placeMeta, 3);
						
						TileEntity te = w.getTileEntity(x, y, z);
						
						if(te != null && te instanceof IEmcWrenchable)
						{
							IEmcWrenchable wr = (IEmcWrenchable)te;
							
							b.onBlockPlacedBy(w, x, y, z, ep, ep.getHeldItem());
							wr.readFromWrench(tag);
							
							is.stackTagCompound.removeTag(NBT_KEY);
							is.damageItem(1, ep);
						}
					}
				}
			}
			
			return true;
		}
		
		return false;
	}

	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
}