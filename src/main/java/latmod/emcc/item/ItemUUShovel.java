package latmod.emcc.item;
import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class ItemUUShovel extends ItemToolEMCC
{
	public ItemUUShovel(int id, String s)
	{
		super(id, s, ItemSpade.blocksEffectiveAgainst);
		
		LatCore.addTool(this, EnumToolClass.SHOVEL, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableShovel)
			EMCC.recipes.addRecipe(new ItemStack(this), "U", "S", "S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean canHarvestBlock(Block b)
	{ return b == Block.snow ? true : b == Block.blockSnow; }
}