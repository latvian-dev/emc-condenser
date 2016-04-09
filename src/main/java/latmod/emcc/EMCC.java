package latmod.emcc;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import ftb.lib.*;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.config.EMCCConfig;
import latmod.emcc.emc.EMCHandler;
import net.minecraft.item.ItemStack;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:FTBU;after:EE3;after:Baubles")
public class EMCC
{
	protected static final String MOD_ID = "EMC_Condenser";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.client.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy;
	
	public static CreativeTabLM tab;
	
	public static LMMod mod;
	public static EMCCBlacklist blacklist;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(EMCC.MOD_ID);
		tab = new CreativeTabLM("emcc").setMod(mod);
		
		EMCCConfig.load();
		blacklist = new EMCCBlacklist();
		
		EventBusHelper.register(EMCCEventHandler.instance);
		EMCHandler.init();
		EMCCItems.preInit();
		mod.onPostLoaded();
		ToolInfusion.initAll();
		
		tab.addIcon(new ItemStack(EMCCItems.i_emc_battery, 1, 1));
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		EMCHandler.instance().modInited();
		ToolInfusion.initAll();
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
	}
}