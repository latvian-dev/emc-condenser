package mods.lm.emcc;
import latmod.core.*;
import cpw.mods.fml.common.event.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;

public class EMCCBlacklist
{
	public static Configuration config;
	
	public static FastList<String> blacklistedOres;
	
	public static final void load(FMLPreInitializationEvent e)
	{
		config = LatCore.loadConfig(e, "/LatMod/EMC_Condenser_Blacklist.cfg");
		
		blacklistedOres = new FastList<String>();
		
		for(FastList<String> al : OreHelper.oreNames)
		{
			for(String s : al)
			{
				if(s.startsWith("ore"))
				{
					if(isOreDisabled(s))
					blacklistedOres.add(s);
				}
			}
		}
		
		if(config.hasChanged()) 
			config.save();
	}
	
	private static boolean isOreDisabled(String s)
	{ return config.get("ore_dictionary", s, true).getBoolean(true); }
	
	public static boolean canCondenseItem(ItemStack is)
	{
		FastList<String> oreNames = OreHelper.getOreNames(is);
		
		if(blacklistedOres.containsAny(oreNames)) return false;
		
		return true;
	}
}