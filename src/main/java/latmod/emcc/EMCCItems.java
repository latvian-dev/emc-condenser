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
		b_uu_block = new BlockUUBlock("uub").register();
		b_condenser = new BlockCondenser("condenser").register();
		b_infuser = new BlockInfuser("infuser").register();
		
		i_mat = new ItemMaterials("materials").register();
		i_emc_battery = new ItemEmcBattery("battery").register();
		i_life_ring = new ItemLifeRing("lifeRing").register();
		i_black_hole_band = new ItemBlackHoleBand("blackHoleBand").register();
		
		i_wrench = new ItemUUWrench("wrench").register();
		i_sword = new ItemUUSword("sword").register();
		i_pick = new ItemUUPick("pick").register();
		i_shovel = new ItemUUShovel("shovel").register();
		i_axe = new ItemUUAxe("axe").register();
		i_hoe = new ItemUUHoe("hoe").register();
		i_smasher = new ItemUUSmasher("smasher").register();
		i_bow = new ItemUUBow("bow").register();
	}
	
	public static void load()
	{
		DUST_VERDANT = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 3);
	}
}