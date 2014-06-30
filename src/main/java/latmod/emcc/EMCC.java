package latmod.emcc;
import java.util.logging.*;

import com.pahimar.ee3.emc.*;
import com.pahimar.ee3.recipe.RecipesAludel;

import latmod.core.*;
import latmod.core.base.LMMod;
import latmod.core.base.recipes.LMRecipes;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;

@Mod(modid = EMCC.MOD_ID, name = EMCC.MOD_NAME, version = EMCC.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { EMCC.MOD_ID }, packetHandler = EMCCNetHandler.class)
public class EMCC
{
	public static final String MOD_ID = "emcc";
	public static final String MOD_NAME = "EMC Condenser Mod";
	public static final String VERSION = "1.3.0";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy; // LMEClient
	
	public static CreativeTabs tab = null;
	
	public static Logger logger = Logger.getLogger("EMC_Cond");
	
	public static LMMod mod;
	public static EMCCConfig config;
	public static EMCCBlacklist blacklist;
	public static LMRecipes recipes;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger.setParent(FMLLog.getLogger());
		
		mod = new LMMod(MOD_ID);
		config = new EMCCConfig(e);
		blacklist = new EMCCBlacklist(e);
		recipes = new LMRecipes(false);
		
		mod.addBlock(EMCCItems.b_machines = new BlockMachines(config.ids.b_machines, "machines"));
		
		mod.addItem(EMCCItems.i_mat = new ItemMaterials(config.ids.i_materials, "materials"));
		mod.addItem(EMCCItems.i_uuBattery = new ItemBattery(config.ids.i_uuBattery, "uuBattery"));
		
		mod.onPostLoaded();
		
		tab = LatCore.createTab(mod.assets + "tab", EMCCItems.UU_ITEM);
		
		LatCore.addGuiHandler(inst, proxy);
		
		proxy.preInit();
		config.save();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		EMCCItems.load();
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
		proxy.postInit();
	}
	
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		EmcValue e = EmcRegistry.getInstance().getEmcValue(is);
		return (e == null) ? 0F : e.getValue();
	}
	
	public static void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ RecipesAludel.getInstance().addRecipe(out, in, with); }
}