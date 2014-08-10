package latmod.emcc;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.init.ModItems;

public class EMCCItems
{
	public static BlockUUBlock b_uu_block;
	public static BlockCondenser b_condenser;
	public static ItemMaterials i_mat;
	public static ItemEmcStorage i_emc_storage;
	
	public static ItemUUWrench i_wrench;
	public static ItemUUSword i_sword;
	public static ItemUUPick i_pick;
	public static ItemUUShovel i_shovel;
	public static ItemUUAxe i_axe;
	public static ItemUUHoe i_hoe;
	public static ItemUUSmasher i_smasher;
	
	public static ItemStack UU_BLOCK;
	public static ItemStack CONDENSER;
	
	public static ItemStack UU_ITEM;
	public static ItemStack MINIUM_STAR;
	public static ItemStack NUGGET_EMERALD;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public static void load()
	{
		DUST_VERDANT = new ItemStack(ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(ModItems.alchemicalDust, 1, 3);
	}
}