package mods.lm.emcc.item;
import com.pahimar.ee3.lib.ItemIds;

import mods.lm.emcc.*;
import net.minecraft.item.*;

public class ItemUUS extends ItemEMCC
{
	public ItemUUS(String s)
	{
		super(s);
		EMCCRecipes.UUS_ITEM = new ItemStack(this, 1, 0);
		addAllDamages(1);
	}
	
	public void loadRecipes()
	{
		EMCCRecipes.addOre(EMCCRecipes.UUS, EMCCRecipes.UUS_ITEM);
		
		EMCCRecipes.addShapelessRecipe(EMCCRecipes.UUS_ITEM,
				new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 1),
				new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 2),
				new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 3),
				Item.redstone,
				Item.glowstone,
				Item.netherQuartz,
				Item.ingotIron,
				Item.ingotGold,
				Item.diamond);
	}
}