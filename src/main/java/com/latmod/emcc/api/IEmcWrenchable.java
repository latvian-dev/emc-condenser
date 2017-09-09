package com.latmod.emcc.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IEmcWrenchable
{
	boolean canWrench(EntityPlayer ep);

	void readFromWrench(NBTTagCompound tag);

	void writeToWrench(NBTTagCompound tag);

	void onWrenched(EntityPlayer ep, ItemStack is);

	ItemStack getBlockToPlace();
}