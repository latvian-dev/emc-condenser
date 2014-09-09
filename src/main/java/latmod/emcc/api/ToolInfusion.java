package latmod.emcc.api;

import latmod.core.mod.recipes.StackEntry;
import latmod.emcc.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.*;

public enum ToolInfusion
{
	FIRE(Enchantment.fireAspect),
	AREA(Enchantment.thorns),
	FORTUNE(Enchantment.fortune),
	UNBREAKING(Enchantment.unbreaking),
	SHARPNESS(Enchantment.sharpness),
	KNOCKBACK(Enchantment.knockback),
	SILKTOUCH(Enchantment.silkTouch),
	INFINITY(Enchantment.infinity),
	
	;
	
	public static final ToolInfusion[] VALUES = values();
	
	public final int ID;
	public final Enchantment enchantment;
	public StackEntry item;
	public int requiredSize;
	
	ToolInfusion(Enchantment e)
	{
		ID = ordinal();
		enchantment = e;
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
		AREA.init(EMCCItems.BLOCK_UUS, EMCC.mod.config().infusion.area);
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
}