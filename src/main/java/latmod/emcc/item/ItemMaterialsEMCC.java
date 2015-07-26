package latmod.emcc.item;
import latmod.emcc.EMCC;
import latmod.ftbu.core.LMMod;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.item.*;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public class ItemMaterialsEMCC extends ItemMaterialsLM
{
	public static MaterialItem ITEM_UUS;
	public static MaterialItem MINIUM_STAR;
	public static MaterialItem INGOT_UUS;
	
	public ItemMaterialsEMCC(String s)
	{ super(s); }
	
	public void onPostLoaded()
	{
		add(ITEM_UUS = new MaterialItem(0, "item_uus")
		{
			public void onPostLoaded()
			{ ODItems.add("itemUUS", stack); }
		});
		
		add(MINIUM_STAR = new MaterialItem(1, "minium_star")
		{
			public void onPostLoaded()
			{ ODItems.add("miniumStar", stack); }
		});
		
		add(INGOT_UUS = new MaterialItem(2, "ingot_uus")
		{
			public void onPostLoaded()
			{ ODItems.add("ingotUUS", stack); }
		});
		
		super.onPostLoaded();
	}
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}