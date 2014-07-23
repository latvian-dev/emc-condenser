package latmod.emcc.block;
import com.pahimar.ee3.init.*;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.base.*;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

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
		if(s == TileLM.UP) return topIcon;
		return blockIcon;
	}
}