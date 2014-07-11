package latmod.emcc;
import com.pahimar.ee3.lib.*;
import net.minecraft.item.*;
import latmod.core.LatCore;
import latmod.emcc.block.*;
import latmod.emcc.item.*;
import latmod.emcc.item.tools.*;

public class EMCCItems
{
	public static BlockEMCCBlocks b_blocks;
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
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public static void load()
	{
		LatCore.addOreDictionary("ingotIron", new ItemStack(Item.ingotIron));
		LatCore.addOreDictionary("ingotGold", new ItemStack(Item.ingotGold));
		LatCore.addOreDictionary("slimeball", new ItemStack(Item.slimeBall));
		LatCore.addOreDictionary("itemTear", new ItemStack(Item.ghastTear));
		
		DUST_VERDANT = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 1);
		DUST_AZURE = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 2);
		DUST_MINIUM = new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 3);
	}
}