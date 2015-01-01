package latmod.emcc;
import latmod.core.*;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.blacklist.EMCCBlacklist;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.core.LMProxy")
	public static LMProxy proxy;
	
	public static CreativeTabs tab = null;
	
	public static LMMod mod;
	public static EMCCBlacklist blacklist;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(MOD_ID, new EMCCConfig(e), EMCCRecipes.instance);
		blacklist = new EMCCBlacklist(e);
		
		EMCCItems.preInit();
		mod.onPostLoaded();
		
		ToolInfusion.initAll();
		
		tab = mod.createTab("tab", new ItemStack(EMCCItems.i_emc_battery, 1, 1));
		
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		ToolInfusion.initAll();
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		DUST_VERDANT = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 3);
		
		mod.loadRecipes();
		proxy.postInit(e);
	}
	
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		EnergyValue e = EnergyValueRegistry.getInstance().getEnergyValue(is);
		return (e == null) ? 0F : e.getEnergyValue();
	}
}