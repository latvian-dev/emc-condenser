package com.latmod.emc_condenser.client;

import com.latmod.emc_condenser.EMCCCommon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	@Override
	public void onPreInit()
	{
	}

	@Override
	public void onPostInit()
	{
		//ClientUtils.MC.getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xFF0000, EMCCItems.UUS_ITEM);
		//ClientUtils.MC.getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xFF0000, EMCCItems.UUS_INGOT);
	}
}