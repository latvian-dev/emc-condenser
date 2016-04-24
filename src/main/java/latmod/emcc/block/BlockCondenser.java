package latmod.emcc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.ODItems;
import ftb.lib.api.tile.TileLM;
import latmod.emcc.EMCC;
import latmod.emcc.EMCCItems;
import latmod.emcc.client.render.world.RenderCondenser;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCondenser extends BlockEMCC
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_top_empty, icon_top_glow, icon_side_empty, icon_side_glow;
	
	public BlockCondenser(String s)
	{
		super(s, Material.rock);
		setResistance(100000F);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileLM createTileEntity(World w, int m)
	{ return new TileCondenser(); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileCondenser.class, blockName);
	}
	
	@Override
	public void loadRecipes()
	{
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_condenser), "OBO", "ASA", "OIO", 'O', EMCCItems.b_uu_block, 'I', ItemMaterialsEMCC.MINIUM_STAR, 'B', EMCCItems.i_black_hole_band, 'S', Blocks.diamond_block, 'A', ODItems.OBSIDIAN);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_top_empty = ir.registerIcon(getMod().lowerCaseModID + ":cond_top_empty");
		icon_top_glow = ir.registerIcon(getMod().lowerCaseModID + ":cond_top_glow");
		icon_side_empty = ir.registerIcon(getMod().lowerCaseModID + ":cond_side_empty");
		icon_side_glow = ir.registerIcon(getMod().lowerCaseModID + ":cond_side_glow");
		blockIcon = icon_side_empty;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCondenser.instance.getRenderId(); }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
}