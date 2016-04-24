package latmod.emcc.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.LMMod;
import ftb.lib.api.item.ItemMaterialsLM;
import ftb.lib.api.item.MaterialItem;
import ftb.lib.api.item.ODItems;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class ItemMaterialsEMCC extends ItemMaterialsLM
{
	public static MaterialItem ITEM_UUS;
	public static MaterialItem MINIUM_STAR;
	public static MaterialItem INGOT_UUS;
	
	public ItemMaterialsEMCC(String s)
	{ super(s); }
	
	@Override
	public void onPostLoaded()
	{
		add(ITEM_UUS = new MaterialItem(this, 0, "item_uus")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("itemUUS", getStack()); }
			
			@Override
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(), "MRM", "VSV", "MGM", 'M', ODItems.DIAMOND, 'V', ODItems.EMERALD, 'R', ODItems.REDSTONE, 'G', ODItems.GLOWSTONE, 'S', Blocks.stone);
			}
		});
		
		add(MINIUM_STAR = new MaterialItem(this, 1, "minium_star")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("miniumStar", getStack()); }
			
			@Override
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(), "MMM", "MSM", "MMM", Character.valueOf('M'), ODItems.DIAMOND, Character.valueOf('S'), Items.nether_star);
			}
		});
		
		add(INGOT_UUS = new MaterialItem(this, 2, "ingot_uus")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("ingotUUS", getStack()); }
			
			@Override
			public void loadRecipes()
			{
				EMCC.mod.recipes.addRecipe(getStack(8), "III", "IUI", "III", 'I', ODItems.IRON, 'U', ItemMaterialsEMCC.ITEM_UUS);
			}
		});
		
		super.onPostLoaded();
	}
	
	@Override
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}