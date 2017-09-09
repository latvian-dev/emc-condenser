package com.latmod.emcc.api;

import net.minecraft.item.ItemStack;

public interface IEmcTool extends IEmcStorageItem
{
	boolean canEnchantWith(ItemStack is, ToolInfusion t);

	int getInfusionLevel(ItemStack is, ToolInfusion t);

	void setInfusionLevel(ItemStack is, ToolInfusion t, int lvl);

	EnumToolType getToolType(ItemStack is);
}