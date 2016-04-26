package latmod.emcc.api;

import latmod.emcc.config.EMCCConfigEnchanting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum ToolInfusion
{
	FIRE("fire", 3),
	FORTUNE("fortune", 5),
	UNBREAKING("unbreaking", 10),
	SHARPNESS("sharpness", 10),
	KNOCKBACK("knockback", 5),
	SILKTOUCH("silkTouch", 1),
	INFINITY("infinity", 1),;
	public static final ToolInfusion[] VALUES = values();
	
	public final int ID;
	public final String name;
	public final int maxLevel;
	public Item item;
	public int requiredLevel;
	
	ToolInfusion(String s, int max)
	{
		ID = ordinal();
		name = s;
		maxLevel = max;
		init(null, 0);
	}
	
	public static void initAll()
	{
		//EMCCConfig
		
		FIRE.init(Items.blaze_rod, EMCCConfigEnchanting.fire.getAsInt());
		FORTUNE.init(Items.gold_ingot, EMCCConfigEnchanting.fortune.getAsInt());
		UNBREAKING.init(Item.getItemFromBlock(Blocks.obsidian), EMCCConfigEnchanting.unbreaking.getAsInt());
		SILKTOUCH.init(Items.string, EMCCConfigEnchanting.silk_touch.getAsInt());
		SHARPNESS.init(Items.iron_ingot, EMCCConfigEnchanting.sharpness.getAsInt());
		KNOCKBACK.init(Item.getItemFromBlock(Blocks.piston), EMCCConfigEnchanting.knockback.getAsInt());
		INFINITY.init(Items.diamond, EMCCConfigEnchanting.infinity.getAsInt());
	}
	
	public Enchantment getEnchantment(EnumToolType t)
	{
		if(this == FIRE)
		{
			if(t == EnumToolType.BOW) return Enchantment.flame;
			else if(t == EnumToolType.SWORD) return Enchantment.fireAspect;
		}
		else if(this == FORTUNE)
		{
			if(t == EnumToolType.TOOL) return Enchantment.fortune;
			if(t == EnumToolType.SWORD) return Enchantment.looting;
			return Enchantment.fireAspect;
		}
		else if(this == UNBREAKING)
		{
			return Enchantment.unbreaking;
		}
		else if(this == SHARPNESS)
		{
			if(t == EnumToolType.TOOL) return Enchantment.efficiency;
			if(t == EnumToolType.SWORD) return Enchantment.sharpness;
			return Enchantment.power;
		}
		else if(this == KNOCKBACK)
		{
			if(t == EnumToolType.SWORD) return Enchantment.knockback;
			return Enchantment.punch;
		}
		else if(this == SILKTOUCH)
		{
			if(t == EnumToolType.TOOL) return Enchantment.silkTouch;
		}
		else if(this == INFINITY)
		{
			if(t == EnumToolType.BOW) return Enchantment.infinity;
		}
		
		return null;
	}
	
	public void init(Item it, int i)
	{
		item = it;
		requiredLevel = i;
	}
	
	public boolean is(ToolInfusion... t)
	{
		for(ToolInfusion t1 : t)
			if(t1 == this) return true;
		return false;
	}
	
	public static ToolInfusion get(EnumToolType type, Enchantment e)
	{
		for(ToolInfusion t : VALUES)
			if(t.getEnchantment(type) == e) return t;
		return null;
	}
	
	public static ToolInfusion get(String s)
	{
		if(s == null || s.length() == 0) return null;
		for(ToolInfusion t : VALUES)
			if(t.name.equals(s)) return t;
		return null;
	}
	
	public static ToolInfusion get(ItemStack is)
	{
		Item i = is.getItem();
		for(ToolInfusion t : VALUES)
			if(t.item == i) return t;
		return null;
	}
}