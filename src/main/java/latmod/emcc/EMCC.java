package latmod.emcc;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ftb.lib.CreativeTabLM;
import ftb.lib.EventBusHelper;
import ftb.lib.LMMod;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfig;
import net.minecraft.item.ItemStack;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:FTBU;after:EE3;after:Baubles")
public class EMCC
{
	protected static final String MOD_ID = "EMCC";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.client.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy;
	
	public static CreativeTabLM tab;
	
	public static LMMod mod;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(EMCC.MOD_ID);
		tab = new CreativeTabLM("emcc").setMod(mod);
		
		EMCCConfig.load();
		
		EventBusHelper.register(EMCCEventHandler.instance);
		EMCCItems.preInit();
		mod.onPostLoaded();
		ToolInfusion.initAll();
		
		tab.addIcon(new ItemStack(EMCCItems.i_emc_battery, 1, 1));
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
	}
}