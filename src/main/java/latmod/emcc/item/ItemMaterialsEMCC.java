package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import ftb.lib.item.ODItems;
import latmod.emcc.EMCC;
import latmod.ftbu.item.*;
import latmod.ftbu.util.LMMod;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMaterialsEMCC extends ItemMaterialsLM
{
	public static MaterialItem ITEM_UUS;
	public static MaterialItem MINIUM_STAR;
	public static MaterialItem INGOT_UUS;
	
	public ItemMaterialsEMCC(String s)
	{ super(s); }
	
	public void onPostLoaded()
	{
		add(ITEM_UUS = new MaterialItem(this, 0, "item_uus")
		{
			public void onPostLoaded()
			{ ODItems.add("itemUUS", getStack()); }
		});
		
		add(MINIUM_STAR = new MaterialItem(this, 1, "minium_star")
		{
			public void onPostLoaded()
			{ ODItems.add("miniumStar", getStack()); }
		});
		
		add(INGOT_UUS = new MaterialItem(this, 2, "ingot_uus")
		{
			public void onPostLoaded()
			{ ODItems.add("ingotUUS", getStack()); }
		});
		
		super.onPostLoaded();
	}
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}