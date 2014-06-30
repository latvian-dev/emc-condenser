package latmod.emcc.item;
import com.pahimar.ee3.lib.ItemIds;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.*;
import net.minecraft.util.Icon;

public class ItemMaterials extends ItemEMCC
{
	public static final String[] names =
	{
		"uu",
		"miniumStar"
	};
	
	@SideOnly(Side.CLIENT)
	public Icon[] icons;
	
	public ItemMaterials(int id, String s)
	{
		super(id, s);
		
		EMCCItems.UU_ITEM = new ItemStack(this, 1, 0);
		EMCCItems.MINIUM_STAR = new ItemStack(this, 1, 1);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
	}
	
	public void loadRecipes()
	{
		EMCC.recipes.addShapelessRecipe(EMCCItems.UU_ITEM,
				EMCCItems.DUST_VERDANT,
				EMCCItems.DUST_AZURE,
				EMCCItems.DUST_MINIUM,
				Item.redstone,
				Item.glowstone,
				Item.netherQuartz,
				Item.ingotIron,
				Item.ingotGold,
				Item.diamond);
		
		if(EMCC.config.recipes.infuseMiniumStar)
		EMCC.addInfusing(EMCCItems.MINIUM_STAR, new ItemStack(Item.netherStar), EMCC.recipes.size(EMCCItems.DUST_MINIUM, 8));
		else EMCC.recipes.addRecipe(EMCCItems.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 3),
				Character.valueOf('S'), Item.netherStar );
		
		if(EMCC.config.recipes.miniumToNetherStar == 1)
			EMCC.addInfusing(new ItemStack(Item.netherStar), EMCCItems.MINIUM_STAR, new ItemStack(Item.glowstone));
		else if(EMCC.config.recipes.miniumToNetherStar == 2)
			EMCC.recipes.addSmelting(new ItemStack(Item.netherStar), EMCCItems.MINIUM_STAR);
		
		if(EMCC.config.recipes.infuseNameTag)
			EMCC.addInfusing(new ItemStack(Item.nameTag), EMCC.recipes.size(new ItemStack(Item.paper), 4), new ItemStack(Item.slimeBall));
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return mod.getItemName(names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		icons = new Icon[names.length];
		for(int i = 0; i < icons.length; i++)
		icons[i] = ir.registerIcon(mod.assets + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int m, int r)
	{ return icons[m]; }
	
	public int getRenderPasses(int m)
	{ return (m == 1) ? 2 : 1; }
}