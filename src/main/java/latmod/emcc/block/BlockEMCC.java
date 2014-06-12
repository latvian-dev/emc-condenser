package latmod.emcc.block;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.Icon;
import net.minecraft.world.*;
import net.minecraftforge.common.ForgeDirection;

public class BlockEMCC extends BlockContainer
{
	public final String blockName;
	public ArrayList<ItemStack> blocksAdded = new ArrayList<ItemStack>();
	
	public BlockEMCC(String s, Material m)
	{
		super(EMCCConfig.getBlockID(s), m);
		blockName = s;
		setHardness(2F);
		setResistance(3F);
		setUnlocalizedName(EMCCFinals.getBlockName(s));
		isBlockContainer = false;
	}

	public void onPostLoaded() { }

	public Block setHardness(float f)
	{ return super.setHardness(f); }

	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }

	public final TileEntity createNewTileEntity(World world)
	{ return null; }

	public TileEntity createTileEntity(World world, int m)
	{ return null; }

	public int damageDropped(int i)
	{ return i; }

	public boolean hasTileEntity(int m)
	{ return isBlockContainer; }

	public String getUnlocalizedName(int m)
	{ return EMCCFinals.getBlockName(blockName); }
	
	public void addAllDamages(int until)
	{
		for(int i = 0; i < until; i++)
		blocksAdded.add(new ItemStack(this, 1, i));
	}
	
	public void addAllDamages(int[] dmg)
	{
		for(int i = 0; i < dmg.length; i++)
		blocksAdded.add(new ItemStack(this, 1, dmg[i]));
	}
	
	public void addAllDamages(Integer[] dmg)
	{
		for(int i = 0; i < dmg.length; i++)
		blocksAdded.add(new ItemStack(this, 1, dmg[i]));
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int j, CreativeTabs c, List l)
	{ l.addAll(blocksAdded); }

	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase el, ItemStack is)
	{
		if(isBlockContainer && el instanceof EntityPlayer)
		{
			TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
			if(tile != null) tile.onPlacedBy((EntityPlayer)el, is);
		}
	}

	public void onPostBlockPlaced(World w, int x, int y, int z, int s)
	{
		if(isBlockContainer)
		{
			TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
			if(tile != null) tile.onPostPlaced(s);
		}
	}
	
	public float getPlayerRelativeBlockHardness(EntityPlayer ep, World w, int x, int y, int z)
	{
		TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
		if(tile != null && tile.isIndestructible(ep)) return -1F;
		return super.getPlayerRelativeBlockHardness(ep, w, x, y, z);
	}
	
	public float getExplosionResistance(Entity e, World w, int x, int y, int z, double ex, double ey, double ez)
	{
		TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
		if(tile != null && tile.isIndestructible(null)) return 1000000F;
		return super.getExplosionResistance(e, w, x, y, z, ex, ey, ez);
	}

	public int getMobilityFlag()
	{ return 2; }

	public ArrayList<ItemStack> getBlockDropped(World w, int x, int y, int z, int m, int f)
	{
		if(!isBlockContainer || !dropSpecialBlock()) return super.getBlockDropped(w, x, y, z, m, f);
		TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
		if(tile != null)
		{
			ArrayList<ItemStack> al = new ArrayList<ItemStack>();
			tile.addDropItems(al, blockID, w.getBlockMetadata(x, y, z));
			return al;
		}

		return super.getBlockDropped(w, x, y, z, m, f);
	}

	public void breakBlock(World w, int x, int y, int z, int i, int j)
	{
		if(!w.isRemote && isBlockContainer)
		{ TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
		if(tile != null) tile.onBroken(); }
		super.breakBlock(w, x, y, z, i, j);
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		if(!isBlockContainer) return false;
		TileEMCC tile = (TileEMCC) w.getBlockTileEntity(x, y, z);
		return (tile != null) ? tile.onRightClick(ep, ep.getCurrentEquippedItem(), s, x1, y1, z1) : false;
	}

	public boolean dropSpecialBlock()
	{ return false; }

	public ItemStack create(Object... args)
	{ return new ItemStack(this); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{ blockIcon = ir.registerIcon(EMCCFinals.ASSETS + blockName); }

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int s, int m)
	{ return blockIcon; }

	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iba, int x, int y, int z, int s)
	{ return getIcon(s, iba.getBlockMetadata(x, y, z)); }

	public boolean rotateBlock(World w, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z)
	{ return ForgeDirection.VALID_DIRECTIONS; }

	public boolean onBlockEventReceived(World w, int x, int y, int z, int eventID, int param)
	{
		TileEMCC t = (TileEMCC) w.getBlockTileEntity(x, y, z);
		if(t != null) return t.receiveClientEvent(eventID, param);
		return false;
	}
	
	public void loadRecipes()
	{
	}
}