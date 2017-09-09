package com.latmod.emcc.api;

import net.minecraft.item.ItemStack;

public interface IEmcStorageItem
{
	boolean canChargeEmc(ItemStack is);

	boolean canDischargeEmc(ItemStack is);

	double getStoredEmc(ItemStack is);

	void setStoredEmc(ItemStack is, double emc);

	double getMaxStoredEmc(ItemStack is);

	double getEmcTrasferLimit(ItemStack is);
}