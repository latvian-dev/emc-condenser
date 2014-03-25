package mods.lm.emcc;
import java.util.*;

import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import mods.lm.core.*;

public class EMCCRecipes
{
	private static final ArrayList<IRecipe> recipesLoaded = new ArrayList<IRecipe>();
	private static final ArrayList<List<Integer>> furnaceRecipesLoaded = new ArrayList<List<Integer>>();
	
	public static final String UUS = "ununseptium";
	public static final String UUS_BLOCK = "blockUnunseptium";
	
	public static ItemStack UUS_BLOCK_ITEM;
	public static ItemStack UUS_ITEM;
	
	private static boolean hasLoadedOnce = false;
	
	@SuppressWarnings("all")
	public static final void load()
	{
		LatCore.addOreDictionary("ingotIron", new ItemStack(Item.ingotIron));
		LatCore.addOreDictionary("ingotGold", new ItemStack(Item.ingotGold));
		LatCore.addOreDictionary("slimeball", new ItemStack(Item.slimeBall));
		LatCore.addOreDictionary("itemTear", new ItemStack(Item.ghastTear));
		
		if(hasLoadedOnce)
		{
			try
			{
				CraftingManager.getInstance().getRecipeList().removeAll(recipesLoaded);
				recipesLoaded.clear();
			}
			catch(Exception e)
			{ e.printStackTrace(); EMCC.logger.warning("Failed to reset Crafting Recipes"); }
			
			try
			{
				for(int i = 0; i < furnaceRecipesLoaded.size(); i++)
				FurnaceRecipes.smelting().getMetaSmeltingList().remove(furnaceRecipesLoaded.get(i));
				furnaceRecipesLoaded.clear();
			}
			catch(Exception e)
			{ e.printStackTrace(); EMCC.logger.warning("Failed to reset Furnace Recipes"); }
		}
		
		for(int i = 0; i < EMCC.blocks.size(); i++) EMCC.blocks.get(i).loadRecipes();
		for(int i = 0; i < EMCC.items.size(); i++) EMCC.items.get(i).loadRecipes();
		
		hasLoadedOnce = true;
		
		EMCC.logger.info("Loaded " + (recipesLoaded.size() + furnaceRecipesLoaded.size()) + " reloadable recipes (Shaped, Shapeless & Smelting) from Alchemy mod");
	}
	
	public static final ItemStack siz(ItemStack is, int s)
	{ ItemStack is1 = is.copy(); is1.stackSize = s; return is1; }

	public static final void addRecipe(ItemStack out, Object... in)
	{ recipesLoaded.add(LatCore.addRecipe(out, in)); }
	
	public static final void addShapelessRecipe(ItemStack out, Object... in)
	{ recipesLoaded.add(LatCore.addShapelessRecipe(out, in)); }
	
	public static final void add3x3Recipe(ItemStack out, ItemStack in, boolean back)
	{
		ItemStack out9 = out.copy(); out9.stackSize = 9;
		addShapelessRecipe(out9, in);
		if(back) addRecipe(in, "EEE", "EEE", "EEE", Character.valueOf('E'), out);
	}
	
	public static final void addOre(String s, ItemStack is)
	{ LatCore.addOreDictionary(s, is); }

	public static final void addSmelting(ItemStack in, ItemStack out, float... xp)
	{ LatCore.addSmeltingRecipe(out, in, (xp != null && xp.length == 1) ? xp[0] : 0F);
	furnaceRecipesLoaded.add(Arrays.asList(in.itemID, in.getItemDamage())); }
}