package latmod.emcc.api;

import latmod.core.mod.recipes.StackEntry;
import latmod.emcc.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.*;

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
	public StackEntry item;
	public int requiredSize;
	
	ToolInfusion(String s, Enchantment e, int max)
	{
		ID = ordinal();
		name = s;
		enchantment = e;
		maxLevel = max;
		init(null, 0);
	}
	
	public void init(Object o, int i)
	{
		item = new StackEntry(o);
		requiredSize = i;
	}
	
	public static void initAll()
	{
		//EMCCConfig
		
		FIRE.init(Items.blaze_rod, EMCC.mod.config().infusion.fire);
		AREA.init(EMCCItems.b_uu_block, EMCC.mod.config().infusion.area);
		FORTUNE.init(Items.gold_ingot, EMCC.mod.config().infusion.fortune);
		UNBREAKING.init(Blocks.obsidian, EMCC.mod.config().infusion.unbreaking);
		FORTUNE.init(Items.string, EMCC.mod.config().infusion.silkTouch);
		SHARPNESS.init(Items.iron_ingot, EMCC.mod.config().infusion.sharpness);
		KNOCKBACK.init(Blocks.piston, EMCC.mod.config().infusion.knockback);
		INFINITY.init(Items.diamond, EMCC.mod.config().infusion.infinity);
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
}