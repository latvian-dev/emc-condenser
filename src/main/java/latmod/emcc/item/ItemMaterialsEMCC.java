package latmod.emcc.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.core.recipes.LMRecipes;
import latmod.emcc.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
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
		
		LatCoreMC.addOreDictionary("itemUUS", ITEM_UUS = new ItemStack(this, 1, 0));
		LatCoreMC.addOreDictionary("miniumStar", MINIUM_STAR = new ItemStack(this, 1, 1));
		LatCoreMC.addOreDictionary("nuggetEmerald", NUGGET_EMERALD = new ItemStack(this, 1, 2));
		LatCoreMC.addOreDictionary("ingotUUS", INGOT_UUS = new ItemStack(this, 1, 3));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(ITEM_UUS, "MRM", "VSA", "MGM",
				'M', EMCC.DUST_MINIUM,
				'V', ODItems.EMERALD,
				'A', ODItems.DIAMOND,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'S', Blocks.stone);
		
		{
			int in = EMCCConfig.Recipes.infusedUUIngots;
			if(in > 0) EMCCRecipes.instance.addInfusing(
					LMRecipes.size(INGOT_UUS, in),
					new ItemStack(Items.iron_ingot, in),
					ITEM_UUS);
		}
		
		{
			int in = EMCCConfig.Recipes.infusedUUBlocks;
			if(in > 0) EMCCRecipes.instance.addInfusing(
					new ItemStack(EMCCItems.b_uu_block, in),
					new ItemStack(Blocks.obsidian, in),
					ITEM_UUS);
		}
		
		if(EMCCConfig.Recipes.infuseMiniumStar)
			EMCCRecipes.instance.addInfusing(MINIUM_STAR, new ItemStack(Items.nether_star), LMRecipes.size(EMCC.DUST_MINIUM, 8));
		else mod.recipes.addRecipe(MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), EMCC.DUST_MINIUM,
				Character.valueOf('S'), Items.nether_star);
		
		if(EMCCConfig.Recipes.miniumToNetherStar == 1)
			EMCCRecipes.instance.addInfusing(new ItemStack(Items.nether_star), MINIUM_STAR, new ItemStack(Items.glowstone_dust));
		else if(EMCCConfig.Recipes.miniumToNetherStar == 2)
			mod.recipes.addSmelting(new ItemStack(Items.nether_star), MINIUM_STAR);
		
		mod.recipes.addItemBlockRecipe(NUGGET_EMERALD, new ItemStack(Items.emerald), true);
		
		if(EMCCConfig.Recipes.infuseEnchBottle)
			EMCCRecipes.instance.addInfusing(new ItemStack(Items.experience_bottle), new ItemStack(Items.potionitem, 1, 32), NUGGET_EMERALD);
	}
}