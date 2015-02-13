package latmod.emcc;

import latmod.core.recipes.LMRecipes;
import net.minecraft.item.ItemStack;

public class EMCCRecipes extends LMRecipes
{
	public static final EMCCRecipes instance = new EMCCRecipes();

	public void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{
		if(EMCC.hasEE3())
			com.pahimar.ee3.recipe.RecipesAludel.getInstance().addRecipe(out, in, with);
	}
}