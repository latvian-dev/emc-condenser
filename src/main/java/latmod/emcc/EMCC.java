package latmod.emcc;
import org.apache.logging.log4j.*;

import com.pahimar.ee3.api.EnergyValue;
import com.pahimar.ee3.exchange.EnergyValueRegistry;
import com.pahimar.ee3.recipe.*;

import latmod.core.*;
import latmod.core.base.*;
import latmod.core.base.recipes.*;
import latmod.emcc.blacklist.*;
import latmod.emcc.block.*;
import latmod.emcc.customemc.*;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;
import latmod.emcc.net.EMCCNetHandler;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = EMCC.MOD_NAME, version = EMCC.VERSION, dependencies = "required-after:latcore;required-after:EE3")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { EMCC.MOD_ID }, packetHandler = EMCCNetHandler.class)
public class EMCC
{
	public static final String MOD_ID = "emcc";
	public static final String MOD_NAME = "EMC Condenser Mod";
	public static final String VERSION = "1.3.1";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy; // EMCCClient
	
	public static CreativeTabs tab = null;
	
	public static final Logger logger = LogManager.getLogger("EMC_Cond");
	
	public static LMMod mod;
	public static EMCCConfig config;
	public static LMRecipes recipes;
	public static EMCCBlacklist blacklist;
	public static EMCCCustomEMC customEMC;
	
	public static final ToolMaterial toolMaterial = EnumHelper.addToolMaterial("ununseptium", ToolMaterial.EMERALD.getHarvestLevel() + 1, 512, ToolMaterial.EMERALD.getEfficiencyOnProperMaterial(), 7F, 20);
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(MOD_ID);
		config = new EMCCConfig(e);
		blacklist = new EMCCBlacklist(e);
		customEMC = new EMCCCustomEMC(e);
		recipes = new LMRecipes(false);
		
		mod.addBlock(EMCCItems.b_uu_block = new BlockUUBlock("uub"));
		
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
	
	public static void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ RecipesAludel.getInstance().addRecipe(out, in, with); }
}