package latmod.emcc;
import com.pahimar.ee3.init.ModItems;
import net.minecraft.init.*;
import net.minecraft.item.*;
import latmod.core.*;
import latmod.emcc.block.*;
import latmod.emcc.item.*;

public class EMCCItems
{
	public static BlockEMCCBlocks b_blocks;
	public static ItemMaterials i_uus;
	public static ItemBattery i_battery;
	
	public static ItemStack UNUNSEPTIUM_BLOCK;
	public static ItemStack CONDENSER;
	
	public static ItemStack UNUNSEPTIUM;
	public static ItemStack BATTERY;
	public static ItemStack MINIUM_STAR;
	
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public static void loadItems()
	{
		LatCore.addOreDictionary("ingotIron", new ItemStack(Items.iron_ingot));
		LatCore.addOreDictionary("ingotGold", new ItemStack(Items.gold_ingot));
		LatCore.addOreDictionary("slimeball", new ItemStack(Items.slime_ball));
		LatCore.addOreDictionary("itemTear", new ItemStack(Items.ghast_tear));
		
		DUST_VERDANT = new ItemStack(ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(ModItems.alchemicalDust, 1, 3);
	}
	
	public static final void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ } //FIXME: RecipesAludel.getInstance().addRecipe(out, in, with); }
}