package mods.lm.emcc;
import java.util.*;
import latmod.core.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;

public class EMCCRecipes
{
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

	public static final void addSmelting(ItemStack in, ItemStack out, float... xp)
	{ LatCore.addSmeltingRecipe(out, in, (xp != null && xp.length == 1) ? xp[0] : 0F); }
}