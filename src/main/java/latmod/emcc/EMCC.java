package latmod.emcc;
import java.util.logging.*;
import com.pahimar.ee3.emc.*;
import com.pahimar.ee3.recipe.*;
import latmod.core.*;
import latmod.core.base.*;
import latmod.core.base.recipes.*;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import latmod.emcc.net.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
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
	public static EMCCCommon proxy; // EMCCClient
	
	public static CreativeTabs tab = null;
	
	public static Logger logger = Logger.getLogger("EMC_Cond");
	
	public static LMMod mod;
	public static EMCCConfig config;
	public static LMRecipes recipes;
	public static EMCCBlacklist blacklist;
	public static EMCCCustomEMC customEMC;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger.setParent(FMLLog.getLogger());
		
		mod = new LMMod(MOD_ID);
		config = new EMCCConfig(e);
		blacklist = new EMCCBlacklist(e);
		customEMC = new EMCCCustomEMC(e);
		recipes = new LMRecipes(false);
		
		mod.addBlock(EMCCItems.b_blocks = new BlockEMCCBlocks(config.ids.b_blocks, "blocks"));
		
		mod.addItem(EMCCItems.i_mat = new ItemMaterials(config.ids.i_materials, "materials"));
		mod.addItem(EMCCItems.i_emc_storage = new ItemEmcStorage(config.ids.i_emcStorage, "emcStorage"));
		
		mod.addItem(EMCCItems.i_wrench = new ItemUUWrench(config.ids.i_wrench, "wrench"));
		mod.addItem(EMCCItems.i_sword = new ItemUUSword(config.ids.i_sword, "sword"));
		mod.addItem(EMCCItems.i_pick = new ItemUUPick(config.ids.i_pick, "pick"));
		mod.addItem(EMCCItems.i_shovel = new ItemUUShovel(config.ids.i_shovel, "shovel"));
		mod.addItem(EMCCItems.i_axe = new ItemUUAxe(config.ids.i_axe, "axe"));
		mod.addItem(EMCCItems.i_hoe = new ItemUUHoe(config.ids.i_hoe, "hoe"));
		mod.addItem(EMCCItems.i_smasher = new ItemUUSmasher(config.ids.i_smasher, "smasher"));
		
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
		customEMC.registerCustomEmcValues();
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