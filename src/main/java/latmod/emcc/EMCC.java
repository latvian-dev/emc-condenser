package latmod.emcc;
import latmod.core.*;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:LatCoreMC;required-after:EE3")
public class EMCC
{
	protected static final String MOD_ID = "EMC_Condenser";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.core.LMProxy")
	public static LMProxy proxy;
	
	public static CreativeTabs tab = null;
	
	public static LMMod mod;
	public static EMCCBlacklist blacklist;
	
	private static boolean hasEE3 = false;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(MOD_ID, new EMCCConfig(e), EMCCRecipes.instance);
		blacklist = new EMCCBlacklist(e);
		
		hasEE3 = LatCoreMC.isModInstalled("EE3");
		
		EMCCItems.preInit();
		mod.onPostLoaded();
		
		ToolInfusion.initAll();
		
		tab = mod.createTab("tab", new ItemStack(EMCCItems.i_emc_battery, 1, 1));
		
		proxy.preInit(e);
	}
	
	public static final boolean hasEE3()
	{ return hasEE3; }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		ToolInfusion.initAll();
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		ItemMaterialsEMCC.loadEE3Items();
		
		mod.loadRecipes();
		proxy.postInit(e);
	}
	
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		
		if(hasEE3())
		{
			com.pahimar.ee3.api.EnergyValue e = com.pahimar.ee3.exchange.EnergyValueRegistry.getInstance().getEnergyValue(is);
			return (e == null) ? 0F : e.getEnergyValue();
		}
		else
		{
			return 0F;
		}
	}
}