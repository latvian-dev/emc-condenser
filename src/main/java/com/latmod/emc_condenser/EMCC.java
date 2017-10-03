package com.latmod.emc_condenser;

import com.feed_the_beast.ftbl.lib.internal.FTBLibFinals;
import com.latmod.emc_condenser.item.ItemEmcBattery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EMCC.MOD_ID, name = EMCC.MOD_NAME, version = EMCC.VERSION, acceptedMinecraftVersions = "[1.12,)", dependencies = "required-after:" + FTBLibFinals.MOD_ID + ";after:" + FTBLibFinals.BAUBLES)
public class EMCC
{
	public static final String MOD_ID = "emc_condenser";
	public static final String MOD_NAME = "EMC Condenser";
	public static final String VERSION = "@VERSION@";

	@Mod.Instance(MOD_ID)
	public static EMCC INST;

	@SidedProxy(clientSide = "com.latmod.emc_condenser.client.EMCCClient", serverSide = "com.latmod.emc_condenser.EMCCCommon")
	public static EMCCCommon PROXY;

	public static final CreativeTabs TAB = new CreativeTabs(MOD_ID)
	{
		@Override
		public ItemStack getTabIconItem()
		{
			ItemStack stack = new ItemStack(EMCCItems.BATTERY);
			((ItemEmcBattery) EMCCItems.BATTERY).setActive(stack, true);
			return stack;
		}
	};

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		PROXY.onPreInit();
	}

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		PROXY.onPostInit();
	}
}