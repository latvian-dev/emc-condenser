package com.latmod.emc_condenser.api;

import net.minecraft.item.ItemStack;

/**
 * @author LatvianModder
 */
public interface ICustomEMCItem
{
	default boolean hasCustomEMC(ItemStack stack)
	{
		return true;
	}

	int getCustomEMC(ItemStack stack);
}