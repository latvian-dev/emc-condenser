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
		"uus",
		"miniumStar"
	};
	
	@SideOnly(Side.CLIENT)
	public Icon[] icons;
	
	public ItemMaterials(String s)
	{
		super(s);
		addAllDamages(names.length);
		
		EMCCRecipes.UUS_ITEM = new ItemStack(this, 1, 0);
		EMCCRecipes.MINIUM_STAR = new ItemStack(this, 1, 1);
	}
	
	public void loadRecipes()
	{
		EMCCRecipes.addShapelessRecipe(EMCCRecipes.UUS_ITEM,
				EMCCRecipes.DUST_VERDANT,
				EMCCRecipes.DUST_AZURE,
				EMCCRecipes.DUST_MINIUM,
				Item.redstone,
				Item.glowstone,
				Item.netherQuartz,
				Item.ingotIron,
				Item.ingotGold,
				Item.diamond);
		
		if(EMCCConfig.infuseMiniumStar)
		EMCCRecipes.addInfusing(EMCCRecipes.MINIUM_STAR, new ItemStack(Item.netherStar), EMCCRecipes.siz(EMCCRecipes.DUST_MINIUM, 8));
		else EMCCRecipes.addRecipe(EMCCRecipes.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), new ItemStack(ItemIds.ALCHEMICAL_DUST, 1, 3),
				Character.valueOf('S'), Item.netherStar );
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCCFinals.getItemName(names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		icons = new Icon[names.length];
		for(int i = 0; i < icons.length; i++)
		icons[i] = ir.registerIcon(EMCCFinals.ASSETS + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int m, int r)
	{ return icons[m]; }
	
	public int getRenderPasses(int m)
	{ return (m == 1) ? 2 : 1; }
}