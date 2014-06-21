package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class ItemMaterials extends ItemEMCC
{
	public static final String[] names =
	{
		"uus",
		"miniumStar"
	};
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemMaterials(String s)
	{
		super(s);
		
		EMCCItems.UNUNSEPTIUM = new ItemStack(this, 1, 0);
		EMCCItems.MINIUM_STAR = new ItemStack(this, 1, 1);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
	}
	
	public void loadRecipes()
	{
		EMCC.recipes.addShapelessRecipe(EMCCItems.UNUNSEPTIUM,
				EMCCItems.DUST_VERDANT,
				EMCCItems.DUST_AZURE,
				EMCCItems.DUST_MINIUM,
				Items.redstone,
				Items.glowstone_dust,
				Items.quartz,
				Items.iron_ingot,
				Items.gold_ingot,
				Items.diamond);
		
		if(EMCC.config.general.infuseMiniumStar)
		EMCCItems.addInfusing(EMCCItems.MINIUM_STAR, new ItemStack(Items.nether_star), EMCC.recipes.size(EMCCItems.DUST_MINIUM, 8));
		else EMCC.recipes.addRecipe(EMCCItems.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), EMCCItems.DUST_MINIUM,
				Character.valueOf('S'), Items.nether_star );
		
		if(EMCC.config.general.miniumToNetherStar == 1)
			EMCCItems.addInfusing(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR, new ItemStack(Items.glowstone_dust));
		else if(EMCC.config.general.miniumToNetherStar == 2)
			EMCC.recipes.addSmelting(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR);
		
		if(EMCC.config.general.infuseNameTag)
			EMCCItems.addInfusing(new ItemStack(Items.name_tag), EMCC.recipes.size(new ItemStack(Items.paper), 4), new ItemStack(Items.slime_ball));
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < icons.length; i++)
		icons[i] = ir.registerIcon(EMCC.mod.assets + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int m, int r)
	{ return icons[m]; }
	
	public int getRenderPasses(int m)
	{ return (m == 1) ? 2 : 1; }
}