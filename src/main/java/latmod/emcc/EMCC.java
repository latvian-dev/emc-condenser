package latmod.emcc;
import latmod.core.LMMod;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.customemc.EMCCCustomEMC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.pahimar.ee3.api.EnergyValue;
import com.pahimar.ee3.exchange.EnergyValueRegistry;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = "EMC Condenser", version = "@VERSION@", dependencies = "required-after:LatCoreMC;required-after:EE3")
public class EMCC
{
	protected static final String MOD_ID = "EMC_Condenser";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy;
	
	public static CreativeTabs tab = null;
	
	public static LMMod<EMCCConfig, EMCCRecipes> mod;
	public static EMCCBlacklist blacklist;
	public static EMCCCustomEMC customEMC;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod<EMCCConfig, EMCCRecipes>(MOD_ID, new EMCCConfig(e), new EMCCRecipes());
		blacklist = new EMCCBlacklist(e);
		customEMC = new EMCCCustomEMC(e);
		
		EMCCItems.preInit();
		mod.onPostLoaded();
		
		ToolInfusion.initAll();
		
		tab = mod.createTab("tab", new ItemStack(EMCCItems.i_emc_battery, 1, 1));
		
		MinecraftForge.EVENT_BUS.register(new EMCCEventHandler());
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		EMCCItems.load();
		ToolInfusion.initAll();
		customEMC.initRegNameItems();
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		customEMC.postInitRegNameItems();
		mod.loadRecipes();
		proxy.postInit();
	}
	
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		EnergyValue e = EnergyValueRegistry.getInstance().getEnergyValue(is);
		return (e == null) ? 0F : e.getEnergyValue();
	}
}