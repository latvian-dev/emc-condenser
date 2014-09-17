package latmod.emcc.item;
import latmod.core.ODItems;
import latmod.core.mod.recipes.LMRecipes;
import latmod.emcc.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public class ItemMaterials extends ItemEMCC
{
	public static final String[] names =
	{
		"itemUUS",
		"miniumStar",
		"nuggetEmerald",
		"ingotUUS"
	};
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemMaterials(String s)
	{
		super(s);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		EMCC.mod.recipes().addOre("itemUUS", EMCCItems.ITEM_UUS = new ItemStack(this, 1, 0));
		EMCCItems.MINIUM_STAR = new ItemStack(this, 1, 1);
		EMCC.mod.recipes().addOre("nuggetEmerald", EMCCItems.NUGGET_EMERALD = new ItemStack(this, 1, 2));
		EMCC.mod.recipes().addOre("ingotUUS", EMCCItems.INGOT_UUS = new ItemStack(this, 1, 3));
	}
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
	}
	
	public void loadRecipes()
	{
		/*EMCC.mod.recipes().addShapelessRecipe(EMCCItems.ITEM_UUS,
				EMCCItems.DUST_VERDANT,
				EMCCItems.DUST_AZURE,
				EMCCItems.DUST_MINIUM,
				ODItems.REDSTONE,
				ODItems.GLOWSTONE,
				ODItems.QUARTZ,
				ODItems.IRON,
				ODItems.GOLD,
				ODItems.DIAMOND);*/
		
		EMCC.mod.recipes().addRecipe(EMCCItems.ITEM_UUS, "MRM", "VSA", "MGM",
				'M', EMCCItems.DUST_MINIUM,
				'V', ODItems.EMERALD,
				'A', ODItems.DIAMOND,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'S', Blocks.stone);
		
		{
			int in = EMCC.mod.config().recipes.infusedUUIngots;
			if(in > 0) EMCC.mod.recipes().addInfusing(
					LMRecipes.size(EMCCItems.INGOT_UUS, in),
					new ItemStack(Items.iron_ingot, in),
					EMCCItems.ITEM_UUS);
		}
		
		{
			int in = EMCC.mod.config().recipes.infusedUUBlocks;
			if(in > 0) EMCC.mod.recipes().addInfusing(
					LMRecipes.size(EMCCItems.BLOCK_UUS, in),
					new ItemStack(Blocks.obsidian, in),
					EMCCItems.ITEM_UUS);
		}
		
		if(EMCC.mod.config().recipes.infuseMiniumStar)
		EMCC.mod.recipes().addInfusing(EMCCItems.MINIUM_STAR, new ItemStack(Items.nether_star), LMRecipes.size(EMCCItems.DUST_MINIUM, 8));
		else EMCC.mod.recipes().addRecipe(EMCCItems.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), EMCCItems.DUST_MINIUM,
				Character.valueOf('S'), Items.nether_star);
		
		if(EMCC.mod.config().recipes.miniumToNetherStar == 1)
			EMCC.mod.recipes().addInfusing(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR, new ItemStack(Items.glowstone_dust));
		else if(EMCC.mod.config().recipes.miniumToNetherStar == 2)
			EMCC.mod.recipes().addSmelting(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR);
		
		EMCC.mod.recipes().addItemBlockRecipe(EMCCItems.NUGGET_EMERALD, new ItemStack(Items.emerald), true);
		
		if(EMCC.mod.config().recipes.infuseEnchBottle)
			EMCC.mod.recipes().addInfusing(new ItemStack(Items.experience_bottle), new ItemStack(Items.potionitem, 1, 32), EMCCItems.NUGGET_EMERALD);
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return mod.getItemName(names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < icons.length; i++)
		icons[i] = ir.registerIcon(mod.assets + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int m, int r)
	{ return icons[m]; }
}