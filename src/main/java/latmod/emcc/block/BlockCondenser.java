package latmod.emcc.block;

import cpw.mods.fml.relauncher.*;
import ftb.lib.item.ODItems;
import latmod.emcc.*;
import latmod.emcc.client.render.world.RenderCondenser;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.tile.TileLM;
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
		isBlockContainer = true;
		setResistance(100000F);
		
		getMod().addTile(TileCondenser.class, s);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCondenser(); }
	
	public void loadRecipes()
	{
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_condenser), "OBO", "ASA", "OIO", 'O', EMCCItems.b_uu_block, 'I', ItemMaterialsEMCC.MINIUM_STAR, 'B', EMCCItems.i_black_hole_band, 'S', Blocks.diamond_block, 'A', ODItems.OBSIDIAN);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_top_empty = ir.registerIcon(getMod().assets + "cond_top_empty");
		icon_top_glow = ir.registerIcon(getMod().assets + "cond_top_glow");
		icon_side_empty = ir.registerIcon(getMod().assets + "cond_side_empty");
		icon_side_glow = ir.registerIcon(getMod().assets + "cond_side_glow");
		blockIcon = icon_side_empty;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCondenser.instance.getRenderId(); }
	
	public boolean renderAsNormalBlock()
	{ return false; }
}