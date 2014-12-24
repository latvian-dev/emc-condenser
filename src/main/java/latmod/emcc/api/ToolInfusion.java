package latmod.emcc.api;

import latmod.emcc.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.*;
import net.minecraft.item.Item;

public enum ToolInfusion
{
	FIRE("fire", Enchantment.fireAspect, 3),
	AREA("area", Enchantment.thorns, 1),
	FORTUNE("fortune", Enchantment.fortune, 5),
	UNBREAKING("unbreaking", Enchantment.unbreaking, 10),
	SHARPNESS("sharpness", Enchantment.sharpness, 10),
	KNOCKBACK("knockback", Enchantment.knockback, 5),
	SILKTOUCH("silkTouch", Enchantment.silkTouch, 1),
	INFINITY("infinity", Enchantment.infinity, 1),
	
	;
	
	public static final ToolInfusion[] VALUES = values();
	
	public final int ID;
	public final String name;
	public final Enchantment enchantment;
	public final int maxLevel;
	public Item item;
	public int requiredSize;
	
	ToolInfusion(String s, Enchantment e, int max)
	{
		ID = ordinal();
		name = s;
		enchantment = e;
		maxLevel = max;
		init(null, 0);
	}
	
	public void init(Item it, int i)
	{ item = it; requiredSize = i; }
	
	public static void initAll()
	{
		//EMCCConfig
		
		FIRE.init(Items.blaze_rod, EMCCConfig.Infusion.fire);
		AREA.init(EMCCItems.b_uu_block.getItem(), EMCCConfig.Infusion.area);
		FORTUNE.init(Items.gold_ingot, EMCCConfig.Infusion.fortune);
		UNBREAKING.init(Item.getItemFromBlock(Blocks.obsidian), EMCCConfig.Infusion.unbreaking);
		FORTUNE.init(Items.string, EMCCConfig.Infusion.silkTouch);
		SHARPNESS.init(Items.iron_ingot, EMCCConfig.Infusion.sharpness);
		KNOCKBACK.init(Item.getItemFromBlock(Blocks.piston), EMCCConfig.Infusion.knockback);
		INFINITY.init(Items.diamond, EMCCConfig.Infusion.infinity);
	}
	
	public boolean is(ToolInfusion... t)
	{
		for(ToolInfusion t1 : t)
		if(t1 == this) return true;
		return false;
	}
	
	public static final ToolInfusion get(Enchantment e)
	{
		for(ToolInfusion t : VALUES)
			if(t.enchantment == e) return t;
		return null;
	}
	
	public static final ToolInfusion get(String s)
	{
		if(s == null || s.length() == 0) return null;
		for(ToolInfusion t : VALUES)
			if(t.name.equals(s)) return t;
		return null;
	}
	
	public static final ToolInfusion get(Item i)
	{
		for(ToolInfusion t : VALUES)
		if(t.item == i) return t; return null;
	}
}