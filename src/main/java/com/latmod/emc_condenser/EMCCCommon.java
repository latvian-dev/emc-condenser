package com.latmod.emc_condenser;

import net.minecraftforge.oredict.OreDictionary;

public class EMCCCommon
{
	public void onPreInit()
	{
		EMCCConfig.sync();
	}

	public void onPostInit()
	{
		OreDictionary.registerOre("itemUUS", EMCCItems.UUS_ITEM);
		OreDictionary.registerOre("ingotUUS", EMCCItems.UUS_INGOT);
		OreDictionary.registerOre("blockUUS", EMCCItems.UUS_BLOCK);
		OreDictionary.registerOre("crystalStar", EMCCItems.CRYSTAL_STAR);
	}
}