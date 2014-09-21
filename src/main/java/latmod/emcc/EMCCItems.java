package latmod.emcc;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;
import net.minecraft.item.ItemStack;

public class EMCCItems
{
	public static BlockUUBlock b_uu_block;
	public static BlockCondenser b_condenser;
	public static BlockInfuser b_infuser;
	
	public static ItemMaterials i_mat;
	public static ItemEmcBattery i_emc_battery;
	public static ItemLifeRing i_life_ring;
	public static ItemBlackHoleBand i_black_hole_band;
	
	public static ItemUUWrench i_wrench;
	public static ItemUUSword i_sword;
	public static ItemUUPick i_pick;
	public static ItemUUShovel i_shovel;
	public static ItemUUAxe i_axe;
	public static ItemUUHoe i_hoe;
	public static ItemUUSmasher i_smasher;
	public static ItemUUBow i_bow;
	
	public static ItemStack ITEM_UUS;
	public static ItemStack MINIUM_STAR;
	public static ItemStack NUGGET_EMERALD;
	public static ItemStack INGOT_UUS;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public static void preInit()
	{
		EMCC.mod.addBlock(b_uu_block = new BlockUUBlock("uub"));
		EMCC.mod.addBlock(b_condenser = new BlockCondenser("condenser"));
		EMCC.mod.addBlock(b_infuser = new BlockInfuser("infuser"));
		
		EMCC.mod.addItem(i_mat = new ItemMaterials("materials"));
		EMCC.mod.addItem(i_emc_battery = new ItemEmcBattery("battery"));
		EMCC.mod.addItem(i_life_ring = new ItemLifeRing("lifeRing"));
		EMCC.mod.addItem(i_black_hole_band = new ItemBlackHoleBand("blackHoleBand"));
		
		EMCC.mod.addItem(i_wrench = new ItemUUWrench("wrench"));
		EMCC.mod.addItem(i_sword = new ItemUUSword("sword"));
		EMCC.mod.addItem(i_pick = new ItemUUPick("pick"));
		EMCC.mod.addItem(i_shovel = new ItemUUShovel("shovel"));
		EMCC.mod.addItem(i_axe = new ItemUUAxe("axe"));
		EMCC.mod.addItem(i_hoe = new ItemUUHoe("hoe"));
		EMCC.mod.addItem(i_smasher = new ItemUUSmasher("smasher"));
		EMCC.mod.addItem(i_bow = new ItemUUBow("bow"));
	}
	
	public static void load()
	{
		DUST_VERDANT = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 3);
	}
}