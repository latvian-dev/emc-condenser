package latmod.emcc;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.emc.EMCHandler;
import latmod.ftbu.core.*;
import latmod.ftbu.core.api.FTBUReloadableRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:FTBU;after:EE3")
public class EMCC
{
	protected static final String MOD_ID = "EMC_Condenser";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.client.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy;
	
	public static CreativeTabs tab = null;
	
	@LMMod.Instance(MOD_ID)
	public static LMMod mod;
	public static EMCCBlacklist blacklist;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		LMMod.init(this, new EMCCConfig(e), null);
		blacklist = new EMCCBlacklist(e);
		
		LatCoreMC.BusType.FORGE.register(EMCCEventHandler.instance);
		LatCoreMC.BusType.LATMOD.register(EMCCEventHandler.instance);
		FTBUReloadableRegistry.add(EMCCEventHandler.instance);
		
		EMCHandler.init(e);
		
		EMCCItems.preInit();
		mod.onPostLoaded();
		
		ToolInfusion.initAll();
		
		tab = mod.createTab("tab", new ItemStack(EMCCItems.i_emc_battery, 1, 1));
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
		EMCHandler.instance().loadRecipes();
	}
}