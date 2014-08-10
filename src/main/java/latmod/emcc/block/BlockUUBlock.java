package latmod.emcc.block;
import latmod.core.ODItems;
import latmod.core.base.TileLM;
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
		
		EMCCItems.UU_BLOCK = new ItemStack(this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public float getEnchantPowerBonus(World w, int x, int y, int z)
	{ return (float)EMCC.config.general.ununblockEnchantPower; }
	
	public void loadRecipes()
	{
		if(EMCC.config.recipes.infuseUUBlock)
		EMCC.recipes.addInfusing(EMCCItems.UU_BLOCK, EMCCItems.UU_ITEM, new ItemStack(Blocks.obsidian, 8));
		else EMCC.recipes.addRecipe(EMCCItems.UU_BLOCK, "OOO", "OUO", "OOO",
			'U', EMCCItems.UU_ITEM,
			'O', ODItems.OBSIDIAN);
	}
}