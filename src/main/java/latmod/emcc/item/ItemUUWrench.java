package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.ForgeDirection;

public class ItemUUWrench extends ItemToolEMCC
{
	private static final String NBT_KEY = "WrenchData";
	
	@SideOnly(Side.CLIENT)
	public Icon icon_full;
	
	public ItemUUWrench(int id, String s)
	{
		super(id, s);
		setMaxDamage(32);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enablePick)
			EMCC.recipes.addRecipe(new ItemStack(this), "UBU", " S ", " S ",
					Character.valueOf('U'), EMCCItems.UU_BLOCK,
					Character.valueOf('S'), EMCCItems.STICK,
					Character.valueOf('B'), new ItemStack(EMCCItems.i_emc_storage, 1, LatCore.ANY));
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enableWrench; }
	
	public int getItemEnchantability()
	{ return 15; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		itemIcon = ir.registerIcon(mod.assets + "wrench");
		icon_full = ir.registerIcon(mod.assets + "wrench_full");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack is, int r)
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
					TileEntity te = w.getBlockTileEntity(x, y, z);
					
					if(te != null && te instanceof IEmcWrenchable)
					{
						IEmcWrenchable wr = (IEmcWrenchable)te;
						
						if(wr.canWrench(ep))
						{
							tag = new NBTTagCompound();
							wr.writeToWrench(tag);
							
							if(!is.hasTagCompound()) is.stackTagCompound = new NBTTagCompound();
							tag.setInteger("PlaceID", w.getBlockId(x, y, z));
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
						
						w.setBlock(x, y, z, placeId);
						w.setBlockMetadataWithNotify(x, y, z, placeMeta, 3);
						
						TileEntity te = w.getBlockTileEntity(x, y, z);
						
						if(te != null && te instanceof IEmcWrenchable)
						{
							IEmcWrenchable wr = (IEmcWrenchable)te;
							
							Block.blocksList[placeId].onBlockPlacedBy(w, x, y, z, ep, ep.getHeldItem());
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
}