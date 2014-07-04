package latmod.emcc;
import java.io.*;
import java.util.*;
import com.pahimar.ee3.addon.*;
import com.pahimar.ee3.api.*;
import com.pahimar.ee3.emc.*;
import latmod.core.*;
import cpw.mods.fml.common.event.*;
import net.minecraft.item.*;

public class EMCCCustomEMC
{
	public CustomEMC map;
	
	public static class ItemEntry
	{
		public String item;
		public int damage;
		
		public ItemEntry(String s, int i)
		{
			item = s;
			damage = i;
		}
		
		public boolean equals(Object o)
		{
			ItemStack is = (ItemStack)o;
			return is != null && is.getUnlocalizedName().equals(item) && (damage == -1 || is.getItemDamage() == damage);
		}
	}
	
	public EMCCCustomEMC(FMLPreInitializationEvent e)
	{
		File file = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_CustomEMC.cfg");
		
		map = null;
		
		try { map = LMUtils.getJsonFromFile(file, CustomEMC.class); }
		catch(Exception ex) { map = null; }
		
		if(map == null)
		{
			map = new CustomEMC();
			
			map.ore_dictionary.put("crystalCertusQuartz", 256F);
			map.ore_dictionary.put("gemApatite", 16F);
			map.ore_dictionary.put("gemRuby", 1024F);
			map.ore_dictionary.put("gemSapphire", 1024F);
			map.ore_dictionary.put("gemPeridot", 1024F);
			
			map.ore_dictionary.put("ingotPlatinum", 2048F);
			map.ore_dictionary.put("ingotAluminum", 256F);
			
			map.ore_dictionary.put("dustCertusQuartz", 256F);
			map.ore_dictionary.put("dustNetherQuartz", 256F);
			map.ore_dictionary.put("dustCoal", 32F);
			map.ore_dictionary.put("dustIron", 256F);
			map.ore_dictionary.put("dustGold", 2048F);
			map.ore_dictionary.put("dustPlatinum", 2048F);
			map.ore_dictionary.put("dustAluminum", 256F);
			map.ore_dictionary.put("dustEnderium", 1728F);
			
			LMUtils.toJsonFile(file, map);
		}
	}
	
	public void registerCustomEmcValues()
	{
		if(!map.ore_dictionary.isEmpty())
		{
			Iterator<String> keys = map.ore_dictionary.keySet().iterator();
			Iterator<Float> values = map.ore_dictionary.values().iterator();
			
			while(keys.hasNext())
			AddonHandler.sendPreValueAssignment(new OreStack(keys.next()), new EmcValue(values.next()));
		}
	}
}