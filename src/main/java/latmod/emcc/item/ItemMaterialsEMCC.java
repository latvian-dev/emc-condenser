package latmod.emcc.item;

import cpw.mods.fml.relauncher.*;
import ftb.lib.LMMod;
import ftb.lib.api.item.*;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;

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
			
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(), "MRM", "VSV", "MGM", 'M', ODItems.DIAMOND, 'V', ODItems.EMERALD, 'R', ODItems.REDSTONE, 'G', ODItems.GLOWSTONE, 'S', Blocks.stone);
			}
		});
		
		add(MINIUM_STAR = new MaterialItem(this, 1, "minium_star")
		{
			public void onPostLoaded()
			{ ODItems.add("miniumStar", getStack()); }
			
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(), "MMM", "MSM", "MMM", Character.valueOf('M'), ODItems.DIAMOND, Character.valueOf('S'), Items.nether_star);
			}
		});
		
		add(INGOT_UUS = new MaterialItem(this, 2, "ingot_uus")
		{
			public void onPostLoaded()
			{ ODItems.add("ingotUUS", getStack()); }
			
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(8), "III", "IUI", "III", 'I', ODItems.IRON, 'U', ItemMaterialsEMCC.ITEM_UUS);
			}
		});
		
		super.onPostLoaded();
	}
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}