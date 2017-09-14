package com.latmod.emc_condenser.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagInt;

public interface IEmcStorageItem
{
	default void setActive(ItemStack stack, boolean active)
	{
		if (active)
		{
			stack.setTagInfo("Active", new NBTTagByte((byte) 1));
		}
		else
		{
			if (stack.hasTagCompound())
			{
				stack.getTagCompound().removeTag("Active");

				if (stack.getTagCompound().hasNoTags())
				{
					stack.setTagCompound(null);
				}
			}
		}
	}

	default boolean isActive(ItemStack stack)
	{
		return stack.hasTagCompound() && stack.getTagCompound().getBoolean("Active");
	}

	default boolean canChargeEmc(ItemStack stack)
	{
		return true;
	}

	default boolean canDischargeEmc(ItemStack stack)
	{
		return false;
	}

	default void setStoredEmc(ItemStack stack, int emc)
	{
		if (emc <= 0)
		{
			if (stack.hasTagCompound())
			{
				stack.getTagCompound().removeTag("EMC");

				if (stack.getTagCompound().hasNoTags())
				{
					stack.setTagCompound(null);
				}
			}
		}
		else
		{
			stack.setTagInfo("EMC", new NBTTagInt(emc));
		}
	}

	default int getStoredEmc(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger("EMC") : 0;
	}

	int getMaxStoredEmc(ItemStack stack);

	int getEmcTransferLimit(ItemStack stack);
}