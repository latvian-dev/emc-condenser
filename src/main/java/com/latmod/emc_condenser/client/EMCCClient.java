package com.latmod.emc_condenser.client;

import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.latmod.emc_condenser.EMCCCommon;
import com.latmod.emc_condenser.EMCCItems;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class EMCCClient extends EMCCCommon
{
	@Override
	public void onPreInit()
	{
		super.onPreInit();
	}

	@Override
	public void onPostInit()
	{
		super.onPostInit();

		ClientUtils.MC.getItemColors().registerItemColorHandler((stack, tintIndex) ->
		{
			if (tintIndex == 1)
			{
				return Color.HSBtoRGB((Minecraft.getSystemTime() % 10000L) * 0.0001F, 0.6F, 0.7F);
			}

			return 0xFFFFFF;
		}, EMCCItems.UUS_ITEM);

		ClientUtils.MC.getItemColors().registerItemColorHandler((stack, tintIndex) -> Color.HSBtoRGB((Minecraft.getSystemTime() % 10000L) * 0.0001F, 0.7F, 0.7F), EMCCItems.UUS_INGOT);
	}
}