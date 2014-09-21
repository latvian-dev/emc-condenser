package latmod.emcc.block;
import latmod.core.*;
import latmod.core.mod.tile.TileLM;
import latmod.emcc.*;
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
	public IIcon topIcon;
	
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
		
		if(EMCC.mod.config().recipes.condenserRecipeDifficulty == 1) is = new ItemStack(Items.nether_star);
		else if(EMCC.mod.config().recipes.condenserRecipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.mod.recipes().addRecipe(new ItemStack(this), "OBO", "OSO", "OIO",
				'O', ODItems.OBSIDIAN,
				'I', is,
				'B', EMCCItems.i_black_hole_band,
				'S', new ItemStack(ModItems.stoneMinium, 1, LatCoreMC.ANY));
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "condSide");
		topIcon = ir.registerIcon(mod.assets + "condTop");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ 
		if(s == LatCoreMC.TOP) return topIcon;
		return blockIcon;
	}
}