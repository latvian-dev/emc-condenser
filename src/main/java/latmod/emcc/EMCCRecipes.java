package latmod.emcc;

import latmod.core.base.recipes.LMRecipes;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.recipe.RecipesAludel;

public class EMCCRecipes extends LMRecipes
{
	public EMCCRecipes()
	{ super(false); }
	
	public void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ RecipesAludel.getInstance().addRecipe(out, in, with); }
}