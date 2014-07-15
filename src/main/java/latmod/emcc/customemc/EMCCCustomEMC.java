package latmod.emcc.customemc;
import java.io.*;
import java.util.*;
import com.pahimar.ee3.addon.*;
import com.pahimar.ee3.api.*;
import com.pahimar.ee3.emc.*;
import latmod.core.*;
import latmod.emcc.EMCC;
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
		File file = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_CustomEMC.json");
		
		map = null;
		
		try { map = LMUtils.fromJsonFromFile(file, CustomEMC.class); }
		catch(Exception ex) { map = null; }
		
		if(map == null)
		{
			map = new CustomEMC();
			
			map.addOreValue("crystalCertusQuartz", 256F);
			map.addOreValue("gemApatite", 16F);
			map.addOreValue("gemAmber", 64F);
			map.addOreValue("gemRuby", 1024F);
			map.addOreValue("gemSapphire", 1024F);
			map.addOreValue("gemPeridot", 1024F);
			map.addOreValue("gemChimerite", 32F);
			
			map.addOreValue("ingotPlatinum", 2048F);
			map.addOreValue("ingotAluminum", 256F);
			map.addOreValue("ingotFzDarkIron", 512F);
			
			map.addOreValue("dustPlatinum", 2048F);
			map.addOreValue("dustVinteum", 512F);
			
			map.addOreValue("shardAir", 512F);
			map.addOreValue("shardWater", 512F);
			map.addOreValue("shardEarth", 512F);
			map.addOreValue("shardFire", 512F);
			map.addOreValue("shardOrder", 512F);
			map.addOreValue("shardEntropy", 512F);
			map.addOreValue("quicksilver", 512F);
			
			map.addUNValue("item.skull.skeleton", 0, 1024F);
			map.addUNValue("item.skull.wither", 1, 1024F);
			map.addUNValue("item.skull.zombie", 2, 1024F);
			map.addUNValue("item.skull.creeper", 4, 1024F);
			map.addUNValue("item.enchantedBook", LatCore.ANY, 2048F);
			
			map.addUNValue("tile.projectred.exploration.stone.stonemarble", 0 ,1F);
			map.addUNValue("tile.projectred.exploration.stone.cobblebasalt", 2, 1F);
			map.addUNValue("tile.projectred.exploration.stone.stonebasalt", 3, 1F);
			
			map.addUNValue("railcraft.cube.stone.abyssal", 6, 1F);
			map.addUNValue("railcraft.cube.brick.abyssal.2", 2, 1F);
			
			LMUtils.toJsonFile(file, map);
		}
	}
	
	public void registerUNItems()
	{
		if(!EMCC.config.general.enableCustomEMC) return;
		
		if(!map.ore_dictionary.isEmpty())
		{
			Iterator<String> keys = map.ore_dictionary.keySet().iterator();
			Iterator<Float> values = map.ore_dictionary.values().iterator();
			
			while(keys.hasNext())
			AddonHandler.sendPreValueAssignment(new OreStack(keys.next()), new EmcValue(values.next()));
		}
		
		if(!map.unlocazlied_name.isEmpty())
		{
			for(UNameValue v : map.unlocazlied_name)
			{
				if(v.value > 0F)
				AddonHandler.sendPreValueAssignment(new OreStack("emcc_" + v.name), new EmcValue(v.value));
			}
		}
	}
	
	public void postRegisterUNItems()
	{
		if(!map.unlocazlied_name.isEmpty())
		{
			for(UNameValue v : map.unlocazlied_name)
			{
				int dmg = (v.damage == null) ? LatCore.ANY : v.damage;
				ItemStack is = LMUtils.getStackFromUnlocazliedName(v.name, dmg);
				if(is != null && v.value > 0F)
				LatCore.addOreDictionary("emcc_" + v.name, is);
				else System.out.println("Invalid Unlocalized item: " + v.name + "@" + dmg + ", " + is);
			}
		}
	}
}