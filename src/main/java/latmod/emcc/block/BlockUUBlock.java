package latmod.emcc.block;
import latmod.core.ODItems;
import latmod.core.mod.tile.TileLM;
import latmod.emcc.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockUUBlock extends BlockEMCC
{
	public BlockUUBlock(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		EMCCItems.BLOCK_UUS = new ItemStack(this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public float getEnchantPowerBonus(World w, int x, int y, int z)
	{ return (float)EMCC.mod.config().general.ununblockEnchantPower; }
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().recipes.infuseUUBlock)
		EMCC.mod.recipes().addInfusing(EMCCItems.BLOCK_UUS, EMCCItems.ITEM_UUS, new ItemStack(Blocks.obsidian, 8));
		else EMCC.mod.recipes().addRecipe(EMCCItems.BLOCK_UUS, "OOO", "OUO", "OOO",
			'U', EMCCItems.ITEM_UUS,
			'O', ODItems.OBSIDIAN);
	}
}