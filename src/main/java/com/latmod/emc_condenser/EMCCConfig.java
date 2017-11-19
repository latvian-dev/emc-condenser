package com.latmod.emc_condenser;

import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EMCC.MOD_ID)
@Config(modid = EMCC.MOD_ID, category = "")
public class EMCCConfig
{
	@Config.LangKey(GuiLang.LANG_GENERAL)
	public static final General general = new General();

	public static final Constructor constructor = new Constructor();
	public static final Destructor destructor = new Destructor();

	public static class General
	{
		@Config.RangeDouble(min = 0, max = 100)
		public double uu_block_enchant_power = 3;
	}

	public static class Constructor
	{
		@Config.RangeInt(min = 1)
		public int speed = 1;
	}

	public static class Destructor
	{
		@Config.RangeInt(min = 0)
		public int cooldown = 5;

		@Config.RangeInt(min = 1000)
		public int max_emc = 1000000;
	}

	public static void sync()
	{
		ConfigManager.sync(EMCC.MOD_ID, Config.Type.INSTANCE);
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(EMCC.MOD_ID))
		{
			sync();
		}
	}
}