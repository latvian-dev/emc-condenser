package com.latmod.emc_condenser.item;

import com.feed_the_beast.ftbl.lib.item.ItemBase;
import com.latmod.emc_condenser.EMCC;

public class ItemEMCC extends ItemBase
{
	public ItemEMCC(String s)
	{
		super(EMCC.MOD_ID, s);
		setCreativeTab(EMCC.TAB);
	}
}