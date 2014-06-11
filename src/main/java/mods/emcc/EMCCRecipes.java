package mods.emcc;
import java.util.*;

import com.pahimar.ee3.lib.ItemIds;
import com.pahimar.ee3.recipe.RecipesAludel;

import latmod.core.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;

public class EMCCRecipes
{
	public static ItemStack UUS_BLOCK;
	public static ItemStack UUS_ITEM;
	public static ItemStack MINIUM_STAR;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	private static boolean hasLoadedOnce = false;
	
	@SuppressWarnings("all")
	public static final void load()
	{
		LatCore.addOreDictionary("ingotIron", new ItemStack(Item.ingotIron));
		LatCore.addOreDictionary("ingotGold", new ItemStack(Item.ingotGold));
		LatCore.addOreDictionary("slimeball", new ItemStack(Item.slimeBall));
		LatCore.addOreDictionary("itemTear", new ItemStack(Item.ghastTear));
		
		DUST_VERDANT = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 1);
		DUST_AZURE = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 2);
		DUST_MINIUM = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 3);
		
		for(int i = 0; i < EMCC.blocks.size(); i++) EMCC.blocks.get(i).loadRecipes();
		for(int i = 0; i < EMCC.items.size(); i++) EMCC.items.get(i).loadRecipes();
	}
	
	public static final ItemStack siz(ItemStack is, int s)
	{ ItemStack is1 = is.copy(); is1.stackSize = s; return is1; }

	public static final void addRecipe(ItemStack out, Object... in)
	{ LatCore.addRecipe(out, in); }
	
	public static final void addShapelessRecipe(ItemStack out, Object... in)
	{ LatCore.addShapelessRecipe(out, in); }
	
	public static final void add3x3Recipe(ItemStack out, ItemStack in, boolean back)
	{
		ItemStack out9 = out.copy(); out9.stackSize = 9;
		addShapelessRecipe(out9, in);
		if(back) addRecipe(in, "EEE", "EEE", "EEE", Character.valueOf('E'), out);
	}
	
	public static final void addOre(String s, ItemStack is)
	{ LatCore.addOreDictionary(s, is); }

	public static final void addSmelting(ItemStack out, ItemStack in, float... xp)
	{ LatCore.addSmeltingRecipe(out, in, (xp != null && xp.length == 1) ? xp[0] : 0F); }
	
	public static final void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ RecipesAludel.getInstance().addRecipe(out, in, with); }
}