package mods.lm_emc_cond;
import java.util.*;
import java.util.logging.*;
import mods.lm_core.*;
import mods.lm_emc_cond.block.*;
import mods.lm_emc_cond.item.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;

@Mod(modid = AlchemyFinals.MOD_ID, name = AlchemyFinals.MOD_NAME, version = AlchemyFinals.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Alchemy
{
	@Mod.Instance(AlchemyFinals.MOD_ID)
	public static Alchemy inst;
	
	@SidedProxy(clientSide = AlchemyFinals.SIDE_CLIENT, serverSide = AlchemyFinals.SIDE_SERVER)
	public static AlchemyCommon proxy; // LMEClient
	
	public static ArrayList<ItemAlchemy> items = new ArrayList<ItemAlchemy>();
	public static ArrayList<BlockAlchemy> blocks = new ArrayList<BlockAlchemy>();
	
	public static CreativeTabs tab = null;
	
	public static Logger logger = Logger.getLogger("Alchemy");
	
	public static BlockMachines b_machines;
	public static ItemUUS i_uus;
	public static ItemBattery i_battery;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger.setParent(FMLLog.getLogger());
		
		AlchemyConfig.load(e);
		
		addBlock(b_machines = new BlockMachines("machines"));
		
		addItem(i_uus = new ItemUUS("uus"));
		addItem(i_battery = new ItemBattery("uusBattery"));
		
		tab = LatCore.createTab(AlchemyFinals.ASSETS + "tab", new ItemStack(b_machines, 1, 3));
		
		LatCore.addPacketHandler(new AlchemyNetHandler(), AlchemyFinals.MOD_ID);
		
		proxy.preInit();
		AlchemyConfig.save();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		AlchemyRecipes.load();
		proxy.postInit();
	}
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent e)
	{ e.registerServerCommand(new AlchemyCommand()); }
	
	public void addItem(ItemAlchemy i)
	{ LatCore.addItem(i, AlchemyFinals.MOD_ID + '.' + i.itemName, AlchemyFinals.MOD_ID); items.add(i); }

	public void addBlock(BlockAlchemy b, Class<? extends ItemBlockAlchemy> c)
	{ LatCore.addBlock(b, c, AlchemyFinals.MOD_ID + '.' + b.blockName, AlchemyFinals.MOD_ID); blocks.add(b); }
	
	public void addBlock(BlockAlchemy b)
	{ addBlock(b, ItemBlockAlchemy.class); }

	public void addTile(Class<? extends TileAlchemy> c, String s)
	{ LatCore.addTileEntity(c, AlchemyFinals.MOD_ID + '.' + s); }
}