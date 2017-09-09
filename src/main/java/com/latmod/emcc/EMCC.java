package com.latmod.emcc;

import com.latmod.emcc.api.ToolInfusion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", acceptedMinecraftVersions = "[1.12,)", dependencies = "required-after:ftbl;after:Baubles")
public class EMCC
{
	public static final String MOD_ID = "emc_condenser";

	@Mod.Instance(MOD_ID)
	public static EMCC INST;

	@SidedProxy(clientSide = "com.latmod.emc_condenser.client.EMCCClient", serverSide = "latmod.emc_condenser.EMCCCommon")
	public static EMCCCommon PROXY;

	public static final CreativeTabs TAB = new CreativeTabs(MOD_ID)
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(EMCCItems.EMC_BATTERY);
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		EMCCConfig.load();

		EMCCItems.preInit();
		mod.onPostLoaded();
		ToolInfusion.initAll();

		tab.addIcon(new ItemStack(EMCCItems.EMC_BATTERY, 1, 1));
		PROXY.preInit();
	}
}