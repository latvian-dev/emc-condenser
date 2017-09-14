package com.latmod.emc_condenser.util;

import net.minecraft.item.crafting.Ingredient;

/**
 * @author LatvianModder
 */
public class EMCValue
{
	public static EMCValue EMPTY = new EMCValue(Ingredient.EMPTY, 0);

	public final Ingredient ingredient;
	public final int value;

	public EMCValue(Ingredient i, int v)
	{
		ingredient = i;
		value = v;
	}
}