package mods.lm_emc_cond.item;
import com.pahimar.ee3.lib.ItemIds;

import mods.lm_emc_cond.*;
import net.minecraft.item.*;

public class ItemUUS extends ItemAlchemy
{
	public ItemUUS(String s)
	{
		super(s);
		AlchemyRecipes.UUS_ITEM = new ItemStack(this, 1, 0);
		addAllDamages(1);
	}
	
	public void loadRecipes()
	{
		AlchemyRecipes.addOre(AlchemyRecipes.UUS, AlchemyRecipes.UUS_ITEM);
		
		AlchemyRecipes.addShapelessRecipe(AlchemyRecipes.UUS_ITEM,
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