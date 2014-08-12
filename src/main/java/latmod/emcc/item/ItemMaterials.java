package latmod.emcc.item;
import latmod.emcc.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public class ItemMaterials extends ItemEMCC
{
	public static final String[] names =
	{
		"uu",
		"miniumStar",
		"nuggetEmerald"
	};
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemMaterials(String s)
	{
		super(s);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		EMCCItems.UU_ITEM = new ItemStack(this, 1, 0);
		EMCCItems.MINIUM_STAR = new ItemStack(this, 1, 1);
		EMCC.recipes.addOre("nuggetEmerald", EMCCItems.NUGGET_EMERALD = new ItemStack(this, 1, 2));
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
				Items.redstone,
				Items.glowstone_dust,
				Items.quartz,
				Items.iron_ingot,
				Items.gold_ingot,
				Items.diamond);
		
		if(EMCC.config.recipes.infuseMiniumStar)
		EMCC.recipes.addInfusing(EMCCItems.MINIUM_STAR, new ItemStack(Items.nether_star), EMCC.recipes.size(EMCCItems.DUST_MINIUM, 8));
		else EMCC.recipes.addRecipe(EMCCItems.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), EMCCItems.DUST_MINIUM,
				Character.valueOf('S'), Items.nether_star);
		
		if(EMCC.config.recipes.miniumToNetherStar == 1)
			EMCC.recipes.addInfusing(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR, new ItemStack(Items.glowstone_dust));
		else if(EMCC.config.recipes.miniumToNetherStar == 2)
			EMCC.recipes.addSmelting(new ItemStack(Items.nether_star), EMCCItems.MINIUM_STAR);
		
		EMCC.recipes.addItemBlockRecipe(EMCCItems.NUGGET_EMERALD, new ItemStack(Items.emerald), true);
		
		if(EMCC.config.recipes.infuseEnchBottle)
			EMCC.recipes.addInfusing(new ItemStack(Items.experience_bottle), new ItemStack(Items.potionitem, 1, 32), EMCCItems.NUGGET_EMERALD);
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