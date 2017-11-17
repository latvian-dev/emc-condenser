package com.latmod.emc_condenser.item;

import net.minecraft.item.ItemStack;

/**
 * @author LatvianModder
 */
public class ItemBalancedClay extends ItemEMCC
{
	public ItemBalancedClay(String s)
	{
		super(s);
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}