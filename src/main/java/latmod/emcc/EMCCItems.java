package latmod.emcc;
import com.pahimar.ee3.lib.ItemIds;
import net.minecraft.item.*;
import latmod.core.LatCore;
import latmod.emcc.block.*;
import latmod.emcc.item.*;

public class EMCCItems
{
	public static BlockMachines b_machines;
	public static ItemMaterials i_mat;
	public static ItemBattery i_uuBattery;
	
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