package latmod.emcc;
import latmod.core.*;
import latmod.core.mod.LMMod;
import latmod.emcc.blacklist.EMCCBlacklist;
import latmod.emcc.block.*;
import latmod.emcc.customemc.EMCCCustomEMC;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;
import latmod.emcc.net.EMCCNetHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

import org.apache.logging.log4j.*;

import com.pahimar.ee3.api.EnergyValue;
import com.pahimar.ee3.exchange.EnergyValueRegistry;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MODID, name = "EMC Condenser Mod", version = EMCC.MODVERSION, dependencies = "required-after:LatCoreMC;required-after:EE3")
public class EMCC
{
	protected static final String MODID = "EMCC";
	protected static final String MODVERSION = "1.3.2";
	
	public static final String getModID()
	{ return MODID; }
	
	@Mod.Instance(EMCC.MODID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy;
	
	public static CreativeTabs tab = null;
	
	public static final Logger logger = LogManager.getLogger("EMC_Cond");
	
	public static LMMod mod;
	public static EMCCConfig config;
	public static EMCCRecipes recipes;
	public static EMCCBlacklist blacklist;
	public static EMCCCustomEMC customEMC;
	
	public static final ToolMaterial toolMaterial = EnumHelper.addToolMaterial("ununseptium", ToolMaterial.EMERALD.getHarvestLevel() + 1, 512, ToolMaterial.EMERALD.getEfficiencyOnProperMaterial(), 7F, 20);
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(MODID);
		config = new EMCCConfig(e);
		blacklist = new EMCCBlacklist(e);
		customEMC = new EMCCCustomEMC(e);
		recipes = new EMCCRecipes();
		
		mod.addBlock(EMCCItems.b_uu_block = new BlockUUBlock("uub"));
		mod.addBlock(EMCCItems.b_condenser = new BlockCondenser("condenser"));
		
		mod.addItem(EMCCItems.i_mat = new ItemMaterials("materials"));
		mod.addItem(EMCCItems.i_emc_storage = new ItemEmcStorage("emcStorage"));
		
		mod.addItem(EMCCItems.i_wrench = new ItemUUWrench("wrench"));
		
		mod.addItem(EMCCItems.i_sword = new ItemUUSword("sword"));
		mod.addItem(EMCCItems.i_pick = new ItemUUPick("pick"));
		mod.addItem(EMCCItems.i_shovel = new ItemUUShovel("shovel"));
		mod.addItem(EMCCItems.i_axe = new ItemUUAxe("axe"));
		mod.addItem(EMCCItems.i_hoe = new ItemUUHoe("hoe"));
		mod.addItem(EMCCItems.i_smasher = new ItemUUSmasher("smasher"));
		
		mod.onPostLoaded();
		
		tab = LatCore.createTab(mod.assets + "tab", new ItemStack(EMCCItems.i_emc_storage, 1, 1));
		
		LatCore.addGuiHandler(inst, proxy);
		MinecraftForge.EVENT_BUS.register(new EMCCEventHandler());
		
		proxy.preInit();
		config.save();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		EMCCItems.load();
		customEMC.initRegNameItems();
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		EMCCNetHandler.init();
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