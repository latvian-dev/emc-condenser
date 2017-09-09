package com.latmod.emcc.item;

import com.google.common.collect.Multimap;
import com.latmod.emcc.api.EnumToolType;
import com.latmod.emcc.api.ToolInfusion;
import com.latmod.emcc.config.EMCCConfigTools;
import com.latmod.emcc.item.ItemMaterialsEMCC;
import ftb.lib.api.item.ODItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSword extends ItemToolEMCC // ItemSword
{
	public ItemUUSword(String s)
	{
		super(s);
	}

	@Override
	public void loadRecipes()
	{
		if (EMCCConfigTools.sword.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "U", "U", "S", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if (!w.isRemote && hasInfusion(is, ToolInfusion.FIRE) && ep.isBurning())
		{
			ep.extinguish();
		}

		return is;
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		damageItem(is, false);
		return true;
	}

	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{
		return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.KNOCKBACK, ToolInfusion.FIRE);
	}

	@Override
	public EnumToolType getToolType(ItemStack is)
	{
		return EnumToolType.SWORD;
	}

	@SuppressWarnings("all")
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = super.getAttributeModifiers(is);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", damageItem(is, true) ? 9D : 1D, 0));
		return multimap;
	}

	@Override
	public boolean isEffective(Block b)
	{
		return b.getMaterial() == Material.web;
	}
}