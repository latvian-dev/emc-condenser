package mods.lm_emc_cond;
import java.util.*;
import java.util.logging.*;
import mods.lm.core.*;
import mods.lm_emc_cond.block.*;
import mods.lm_emc_cond.item.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;

@Mod(modid = EMCCFinals.MOD_ID, name = EMCCFinals.MOD_NAME, version = EMCCFinals.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EMCC
{
	@Mod.Instance(EMCCFinals.MOD_ID)
	public static EMCC inst;
	
	@SidedProxy(clientSide = EMCCFinals.SIDE_CLIENT, serverSide = EMCCFinals.SIDE_SERVER)
	public static EMCCCommon proxy; // LMEClient
	
	public static ArrayList<ItemEMCC> items = new ArrayList<ItemEMCC>();
	public static ArrayList<BlockEMCC> blocks = new ArrayList<BlockEMCC>();
	
	public static CreativeTabs tab = null;
	
	public static Logger logger = Logger.getLogger("Alchemy");
	
	public static BlockMachines b_machines;
	public static ItemUUS i_uus;
	public static ItemBattery i_battery;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger.setParent(FMLLog.getLogger());
		
		EMCCConfig.load(e);
		
		addBlock(b_machines = new BlockMachines("machines"));
		
		addItem(i_uus = new ItemUUS("uus"));
		addItem(i_battery = new ItemBattery("uusBattery"));
		
		tab = LatCore.createTab(EMCCFinals.ASSETS + "tab", new ItemStack(b_machines, 1, 3));
		
		LatCore.addPacketHandler(new EMCCNetHandler(), EMCCFinals.MOD_ID);
		
		proxy.preInit();
		EMCCConfig.save();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		EMCCRecipes.load();
		proxy.postInit();
	}
	
	public void addItem(ItemEMCC i)
	{ LatCore.addItem(i, EMCCFinals.MOD_ID + '.' + i.itemName, EMCCFinals.MOD_ID); items.add(i); }

	public void addBlock(BlockEMCC b, Class<? extends ItemBlockAlchemy> c)
	{ LatCore.addBlock(b, c, EMCCFinals.MOD_ID + '.' + b.blockName, EMCCFinals.MOD_ID); blocks.add(b); }
	
	public void addBlock(BlockEMCC b)
	{ addBlock(b, ItemBlockAlchemy.class); }

	public void addTile(Class<? extends TileAlchemy> c, String s)
	{ LatCore.addTileEntity(c, EMCCFinals.MOD_ID + '.' + s); }
}