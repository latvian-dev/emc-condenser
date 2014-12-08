package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class ItemUUWrench extends ItemToolEMCC
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
		if(EMCCConfig.Tools.enableWrench)
			mod.recipes.addRecipe(new ItemStack(this), "UBU", " S ", " S ",
					'U', EMCCItems.b_uu_block,
					'S', ODItems.STICK,
					'B', EMCCItems.i_emc_battery);
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
	{ return EMCCConfig.Tools.toolEmcPerDamage; }
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING); }
	
	public boolean isEffective(Block b)
	{ return false; }
}