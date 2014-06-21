package latmod.emcc;
import com.pahimar.ee3.exchange.*;
import latmod.core.*;
import latmod.core.base.LMMod;
import latmod.core.base.LMRecipes;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = EMCC.MOD_ID, name = EMCC.MOD_NAME, version = EMCC.VERSION)
public class EMCC
{
	protected static final String MOD_ID = "emcc";
	protected static final String MOD_NAME = "EMC Condenser Mod";
	protected static final String VERSION = "1.2.0";
	
	@Mod.Instance(EMCC.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = "latmod.emcc.EMCCClient", serverSide = "latmod.emcc.EMCCCommon")
	public static EMCCCommon proxy; // LMEClient
	
	public static CreativeTabs tab = null;
	
	public static LMMod mod;
	public static EMCCConfig config;
	public static LMRecipes recipes;
	public static EMCCBlacklist blacklist;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(EMCC.MOD_ID);
		config = new EMCCConfig(e);
		recipes = new LMRecipes(false);
		
		blacklist = new EMCCBlacklist();
		blacklist.preInit(e);
		
		mod.addBlock(EMCCItems.b_blocks = new BlockEMCCBlocks("blocks"));
		
		mod.addItem(EMCCItems.i_uus = new ItemMaterials("uus"));
		mod.addItem(EMCCItems.i_battery = new ItemBattery("uusBattery"));
		
		mod.onPostLoaded();
		
		tab = LatCore.createTab(mod.assets + "tab", EMCCItems.UNUNSEPTIUM);
		
		//NetworkRegistry.instance().registerChannel(new EMCCNetHandler(), EMCCFinals.MOD_ID);
		
		proxy.preInit();
		config.save();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		EMCCItems.loadItems();
		mod.loadRecipes();
		proxy.postInit();
	}
	
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		EnergyValue e = EnergyValueRegistry.getInstance().getEnergyValue(is);
		return (e == null) ? 0F : e.getValue();
	}
}