package mods.lm_emc_cond.item;
import mods.lm_emc_cond.*;
import net.minecraft.item.*;

public class ItemUUS extends ItemAlchemy
{
	public ItemUUS(String s)
	{
		super(s);
		AlchemyRecipes.UUS_ITEM = new ItemStack(this, 1, 0);
		addAllDamages(new int[] { 0 });
	}
	
	public void loadRecipes()
	{
		AlchemyRecipes.addOre(AlchemyRecipes.UUS, AlchemyRecipes.UUS_ITEM);
		
		AlchemyRecipes.addShapelessRecipe(AlchemyRecipes.siz(AlchemyRecipes.UUS_ITEM, 2),
				Item.redstone,
				Item.glowstone,
				Item.gunpowder,
				Item.coal,
				Item.ingotIron,
				Item.ingotGold,
				Item.enderPearl,
				Item.diamond,
				Item.emerald);
	}
}