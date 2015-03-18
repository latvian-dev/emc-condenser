package latmod.emcc;
import latmod.core.*;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.emc.EMCHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:LatCoreMC;after:EE3")
public class EMCC
{
	protected static final String MOD_ID = "EMC_Condenser";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.client.EMCCClient", serverSide = "latmod.core.LMProxy")
	public static LMProxy proxy;
	
	public static CreativeTabs tab = null;
	
	public static LMMod mod;
	public static EMCCBlacklist blacklist;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(e, new EMCCConfig(e), null);
		blacklist = new EMCCBlacklist(e);
		
		LatCoreMC.addEventHandler(EMCCEventHandler.instance, true, false, true);
		
		EMCHandler.init(e);
		
		EMCCItems.preInit();
		mod.onPostLoaded();
		
		ToolInfusion.initAll();
		
		tab = mod.createTab("tab", new ItemStack(EMCCItems.i_emc_battery, 1, 1));
		
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		EMCHandler.instance().modInited();
		ToolInfusion.initAll();
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
		EMCHandler.instance().loadRecipes();
		proxy.postInit(e);
	}
}