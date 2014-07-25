package latmod.emcc.item.tools;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import latmod.core.base.IItemLM;
import latmod.emcc.EMCC;
import latmod.emcc.EMCCItems;
import latmod.emcc.api.*;

public class ItemToolEMCC extends ItemTool implements IItemLM, IEmcTool, IEffectiveTool
{
	public final String itemName;
	
	@SideOnly(Side.CLIENT)
	public IIcon blazingIcon;
	public final boolean isBlazing;
	
	@SideOnly(Side.CLIENT)
	public IIcon areaIcon;
	public final boolean isArea;
	
	public Set<Block> effectiveBlocks;
	
	public ItemToolEMCC(String s, Set<Block> b, boolean blaze, boolean area)
	{
		super(4F, EMCC.toolMaterial, b);
		setUnlocalizedName(s);
		itemName = s;
		effectiveBlocks = b;
		setFull3D();
		
		isBlazing = blaze;
		isArea = area;
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public Item getItem()
	{ return this; }

	public String getItemID()
	{ return itemName; }

	public final void onPostLoaded() { }
	
	public void loadRecipes()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{ return true; }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{ return is; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/def/" + itemName);
		if(isBlazing) blazingIcon = ir.registerIcon(EMCC.mod.assets + "tools/blazing/" + itemName);
		if(isArea) areaIcon = ir.registerIcon(EMCC.mod.assets + "tools/area/" + itemName);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{
		if(isBlazing && ItemToolEMCC.isBlazing(is))
			return blazingIcon;
		if(isArea && ItemToolEMCC.isArea(is))
			return areaIcon;
		return itemIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(itemName); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return isEffective(b) ? efficiencyOnProperMaterial : 1F; }
	
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b); }
	
	public static boolean isEffectiveAgainst(Material m, Material... materials)
	{ for(Material m1 : materials) { if(m1 == m) return true; } return false; }
	
	public static boolean isBlazing(ItemStack is)
	{ return EMCC.config.recipes.toolBlazingInfusion > 0 && EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, is) > 0; }
	
	public static boolean isArea(ItemStack is)
	{ return EMCC.config.recipes.toolAreaInfusion > 0 && is.hasTagCompound() && is.stackTagCompound.getBoolean("AreaMode"); }
	
	public static ItemStack setBlazing(ItemStack is)
	{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(Enchantment.fireAspect.effectId, 1);
		EnchantmentHelper.setEnchantments(map, is);
		return is;
	}
	
	public static ItemStack setArea(ItemStack is)
	{
		if(!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setBoolean("AreaMode", true);
		return is;
	}

	public static void addBlazingRecipe(ItemStack is)
	{ if(EMCC.config.recipes.toolBlazingInfusion > 0) EMCC.addInfusing(setBlazing(is.copy()), is.copy(), new ItemStack(Items.blaze_rod, EMCC.config.recipes.toolBlazingInfusion)); }
	
	public static void addAreaRecipe(ItemStack is)
	{ if(EMCC.config.recipes.toolAreaInfusion > 0) EMCC.addInfusing(setArea(is.copy()), is.copy(), EMCC.recipes.size(EMCCItems.UU_BLOCK, EMCC.config.recipes.toolAreaInfusion)); }
}