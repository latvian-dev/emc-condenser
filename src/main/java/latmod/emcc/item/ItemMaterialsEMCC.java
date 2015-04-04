package latmod.emcc.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemMaterialsEMCC extends ItemMaterials
{
	public static ItemStack ITEM_UUS;
	public static ItemStack MINIUM_STAR;
	public static ItemStack NUGGET_EMERALD;
	public static ItemStack INGOT_UUS;
	
	public ItemMaterialsEMCC(String s)
	{ super(s); }
	
	public String[] getNames()
	{
		return new String[]
		{
			"itemUUS",
			"miniumStar",
			"nuggetEmerald",
			"ingotUUS"
		};
	}
	
	public String getPrefix()
	{ return null; }
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		ODItems.add("itemUUS", ITEM_UUS = new ItemStack(this, 1, 0));
		ODItems.add("miniumStar", MINIUM_STAR = new ItemStack(this, 1, 1));
		ODItems.add("nuggetEmerald", NUGGET_EMERALD = new ItemStack(this, 1, 2));
		ODItems.add("ingotUUS", INGOT_UUS = new ItemStack(this, 1, 3));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addItemBlockRecipe(new ItemStack(Items.emerald), ItemMaterialsEMCC.NUGGET_EMERALD, true, false);
	}
}