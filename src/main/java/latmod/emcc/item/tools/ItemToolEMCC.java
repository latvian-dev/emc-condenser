package latmod.emcc.item.tools;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.EMCC;
import latmod.emcc.api.*;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemEmcStorage;
import latmod.lib.FastList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Map;

public abstract class ItemToolEMCC extends ItemEmcStorage implements IEmcTool
{
	public static final float efficiencyOnProperMaterial = 9F;
	
	public ItemToolEMCC(String s)
	{
		super(s);
		setFull3D();
		setHasSubtypes(false);
		setMaxDamage(0);
	}
	
	public boolean damageItem(ItemStack is, boolean simulate)
	{
		double emc = getStoredEmc(is);
		double d = EMCCConfigTools.tool_emc_per_damage.get();
		
		if(emc >= d)
		{
			if(!simulate) setStoredEmc(is, emc - d);
			return true;
		}
		
		return false;
	}
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block b, int x, int y, int z, EntityLivingBase el)
    { if(b.getBlockHardness(w, x, y, z) != 0D) damageItem(is, false); return true; }
	
	public boolean getIsRepairable(ItemStack is1, ItemStack is2)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public final void onPostLoaded()
	{
		ItemStack is0 = new ItemStack(this);
		setStoredEmc(is0, 0);
		itemsAdded.add(is0);
		
		ItemStack is1 = new ItemStack(this);
		setStoredEmc(is1, getMaxStoredEmc(is1));
		itemsAdded.add(is1);
	}
	
	public void loadRecipes()
	{
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{ return is; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/" + itemName); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{ return itemIcon; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(itemName); }
	
	public float getDigSpeed(ItemStack is, Block b, int meta)
	{ return (isEffective(b) && damageItem(is, true)) ? efficiencyOnProperMaterial : 1F; }
	
	public int getInfusionLevel(ItemStack is, ToolInfusion t)
	{ return EnchantmentHelper.getEnchantmentLevel(t.getEnchantment(getToolType(is)).effectId, is); }
	
	public boolean hasInfusion(ItemStack is, ToolInfusion t)
	{ return getInfusionLevel(is, t) > 0; }
	
	@SuppressWarnings("all")
	public void setInfusionLevel(ItemStack is, ToolInfusion t, int lvl)
	{
		Map m = EnchantmentHelper.getEnchantments(is);
		m.put(t.getEnchantment(getToolType(is)).effectId, lvl);
		EnchantmentHelper.setEnchantments(m, is);
	}
	
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	public double getMaxStoredEmc(ItemStack is)
	{ return Short.MAX_VALUE; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return 4096D; }
	
	public EnumToolType getToolType(ItemStack is)
	{ return EnumToolType.TOOL; }
	
	public boolean showDurabilityBar(ItemStack is)
	{ return getStoredEmc(is) != getMaxStoredEmc(is); }
	
	public double getDurabilityForDisplay(ItemStack is)
	{ return 1F - getStoredEmc(is) / getMaxStoredEmc(is); }
	
	public int getItemEnchantability(ItemStack is)
	{ return 0; }
	
	public boolean isRepairable()
	{ return false; }
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return isEffective(b); }
	
	public static boolean isEffective(Block b, FastList<Block> bl, FastList<Material> ml)
	{ return bl.contains(b) || ml.contains(b.getMaterial()); }
}