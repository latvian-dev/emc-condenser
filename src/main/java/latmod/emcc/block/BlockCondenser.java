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
		
		EMCC.mod.addTile(TileCondenser.class, "condenser");
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		EMCCItems.CONDENSER = new ItemStack(this);
	}

	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCondenser(); }
	
	public void loadRecipes()
	{
		ItemStack is = EMCCItems.UU_BLOCK;
		
		if(EMCC.config.recipes.condenserRecipeDifficulty == 1) is = new ItemStack(Items.nether_star);
		else if(EMCC.config.recipes.condenserRecipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.recipes.addRecipe(EMCCItems.CONDENSER, "OBO", "OSO", "OIO",
				'O', ODItems.OBSIDIAN,
				'I', is,
				'B', new ItemStack(EMCCItems.i_emc_storage, 1, 4),
				'S', new ItemStack(ModItems.stoneMinium, 1, LatCore.ANY));
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
		if(s == LatCore.TOP) return topIcon;
		return blockIcon;
	}
}