package latmod.emcc.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.emcc.*;
import latmod.emcc.client.render.world.RenderCondenser;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.pahimar.ee3.init.ModItems;

import cpw.mods.fml.relauncher.*;

public class BlockCondenser extends BlockEMCC
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_top_empty, icon_top_glow, icon_side_empty, icon_side_glow;
	
	public BlockCondenser(String s)
	{
		super(s, Material.rock);
		isBlockContainer = true;
		
		mod.addTile(TileCondenser.class, s);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCondenser(); }
	
	public void loadRecipes()
	{
		ItemStack is = new ItemStack(EMCCItems.b_uu_block);
		
		if(EMCCConfig.Recipes.condenserRecipeDifficulty == 1) is = new ItemStack(Items.nether_star);
		else if(EMCCConfig.Recipes.condenserRecipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.mod.recipes.addRecipe(new ItemStack(this), "OBO", "OSO", "OIO",
				'O', ODItems.OBSIDIAN,
				'I', is,
				'B', EMCCItems.i_black_hole_band,
				'S', new ItemStack(ModItems.stoneMinium, 1, LatCoreMC.ANY));
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_top_empty = ir.registerIcon(mod.assets + "condTopEmpty");
		icon_top_glow = ir.registerIcon(mod.assets + "condTopGlow");
		icon_side_empty = ir.registerIcon(mod.assets + "condSideEmpty");
		icon_side_glow = ir.registerIcon(mod.assets + "condSideGlow");
		blockIcon = icon_side_empty;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCondenser.instance.getRenderId(); }
	
	public boolean renderAsNormalBlock()
	{ return false; }
}