package com.latmod.emcc.api;

import com.feed_the_beast.ftbl.lib.NameMap;
import com.latmod.emcc.EMCCConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.function.IntSupplier;

public enum ToolInfusion implements IStringSerializable
{
	FIRE("fire", 3, () -> EMCCConfig.enchanting.fire),
	FORTUNE("fortune", 5, () -> EMCCConfig.enchanting.fortune),
	UNBREAKING("unbreaking", 10, () -> EMCCConfig.enchanting.unbreaking),
	SHARPNESS("sharpness", 10, () -> EMCCConfig.enchanting.sharpness),
	KNOCKBACK("knockback", 5, () -> EMCCConfig.enchanting.knockback),
	SILK_TOUCH("silk_touch", 1, () -> EMCCConfig.enchanting.silk_touch),
	INFINITY("infinity", 1, () -> EMCCConfig.enchanting.infinity);

	public static final NameMap<ToolInfusion> NAME_MAP = NameMap.create(UNBREAKING, values());

	public final int ID;
	private final String name;
	public final int maxLevel;
	public final IntSupplier requiredLevel;
	public Ingredient ingredient;

	ToolInfusion(String s, int max, IntSupplier level)
	{
		ID = ordinal();
		name = s;
		maxLevel = max;
		requiredLevel = level;
		ingredient = Ingredient.EMPTY;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public static void initAll()
	{
		FIRE.ingredient = Ingredient.fromItem(Items.BLAZE_ROD);
		FORTUNE.ingredient = new OreIngredient("ingotGold");
		UNBREAKING.ingredient = new OreIngredient("obsidian");
		SILK_TOUCH.ingredient = new OreIngredient("string");
		SHARPNESS.ingredient = new OreIngredient("ingotIron");
		KNOCKBACK.ingredient = new OreIngredient("piston");
		INFINITY.ingredient = new OreIngredient("gemDiamond");
	}

	@Nullable
	public Enchantment getEnchantment(EnumToolType t)
	{
		switch (this)
		{
			case FIRE:
				if (t == EnumToolType.BOW)
				{
					return Enchantments.FLAME;
				}
				else if (t == EnumToolType.SWORD)
				{
					return Enchantments.FIRE_ASPECT;
				}
				break;
			case FORTUNE:
				if (t == EnumToolType.TOOL)
				{
					return Enchantments.FORTUNE;
				}
				else if (t == EnumToolType.SWORD)
				{
					return Enchantments.LOOTING;
				}
				break;
			case UNBREAKING:
				return Enchantments.UNBREAKING;
			case SILK_TOUCH:
				if (t == EnumToolType.TOOL)
				{
					return Enchantments.SILK_TOUCH;
				}
			case SHARPNESS:
				if (t == EnumToolType.TOOL)
				{
					return Enchantments.EFFICIENCY;
				}
				else if (t == EnumToolType.SWORD)
				{
					return Enchantments.SHARPNESS;
				}
				return Enchantments.POWER;
			case KNOCKBACK:
				if (t == EnumToolType.SWORD)
				{
					return Enchantments.KNOCKBACK;
				}
				return Enchantments.PUNCH;
			case INFINITY:
				if (t == EnumToolType.BOW)
				{
					return Enchantments.INFINITY;
				}
		}

		return null;
	}

	public boolean is(ToolInfusion... t)
	{
		for (ToolInfusion t1 : t)
		{
			if (t1 == this)
			{
				return true;
			}
		}
		return false;
	}

	@Nullable
	public static ToolInfusion get(EnumToolType type, Enchantment e)
	{
		for (ToolInfusion t : NAME_MAP)
		{
			if (t.getEnchantment(type) == e)
			{
				return t;
			}
		}
		return null;
	}

	@Nullable
	public static ToolInfusion get(String s)
	{
		if (s.isEmpty())
		{
			return null;
		}

		for (ToolInfusion t : NAME_MAP)
		{
			if (t.name.equals(s))
			{
				return t;
			}
		}

		return null;
	}

	@Nullable
	public static ToolInfusion get(ItemStack is)
	{
		for (ToolInfusion t : NAME_MAP)
		{
			if (t.ingredient.apply(is))
			{
				return t;
			}
		}

		return null;
	}
}