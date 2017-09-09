package com.latmod.emcc.item;

import com.latmod.emcc.EMCCConfig;
import com.latmod.emcc.api.EnumToolType;
import com.latmod.emcc.api.IEmcTool;
import com.latmod.emcc.api.ToolInfusion;
import com.latmod.emcc.item.ItemEmcStorage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
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
		double d = EMCCConfig.tools.tool_emc_per_damage;

		if (emc >= d)
		{
			if (!simulate)
			{
				setStoredEmc(is, emc - d);
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, IBlockState state, Block b, BlockPos pos, EntityLivingBase el)
	{
		if (b.getBlockHardness(state, w, pos) != 0D)
		{
			damageItem(is, false);
		}

		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack is1, ItemStack is2)
	{
		return false;
	}

	@Override
	public boolean isItemTool(ItemStack is)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs c, List l)
	{
		ItemStack is0 = new ItemStack(this);
		setStoredEmc(is0, 0);
		l.add(is0);

		ItemStack is1 = new ItemStack(this);
		setStoredEmc(is1, getMaxStoredEmc(is1));
		l.add(is1);
	}

	@Override
	public void loadRecipes()
	{
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		return is;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		return (isEffective(state) && damageItem(is, true)) ? efficiencyOnProperMaterial : 1F;
	}

	@Override
	public int getInfusionLevel(ItemStack is, ToolInfusion t)
	{
		return EnchantmentHelper.getEnchantmentLevel(t.getEnchantment(getToolType(is)).effectId, is);
	}

	public boolean hasInfusion(ItemStack is, ToolInfusion t)
	{
		return getInfusionLevel(is, t) > 0;
	}

	@SuppressWarnings("all")
	public void setInfusionLevel(ItemStack is, ToolInfusion t, int lvl)
	{
		Map m = EnchantmentHelper.getEnchantments(is);
		m.put(t.getEnchantment(getToolType(is)).effectId, lvl);
		EnchantmentHelper.setEnchantments(m, is);
	}

	@Override
	public boolean canDischargeEmc(ItemStack is)
	{
		return false;
	}

	@Override
	public double getMaxStoredEmc(ItemStack is)
	{
		return Short.MAX_VALUE;
	}

	@Override
	public double getEmcTrasferLimit(ItemStack is)
	{
		return 4096D;
	}

	@Override
	public EnumToolType getToolType(ItemStack is)
	{
		return EnumToolType.TOOL;
	}

	@Override
	public boolean showDurabilityBar(ItemStack is)
	{
		return getStoredEmc(is) != getMaxStoredEmc(is);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack is)
	{
		return 1F - getStoredEmc(is) / getMaxStoredEmc(is);
	}

	@Override
	public int getItemEnchantability(ItemStack is)
	{
		return 0;
	}

	@Override
	public boolean isRepairable()
	{
		return false;
	}

	@Override
	public boolean canHarvestBlock(Block b, ItemStack is)
	{
		return isEffective(b);
	}

	public static boolean isEffective(Block b, List<Block> bl, List<Material> ml)
	{
		return bl.contains(b) || ml.contains(b.getMaterial());
	}
}