package latmod.emcc.customemc;
import java.io.File;
import java.util.Iterator;

import latmod.core.*;
import latmod.emcc.EMCC;
import net.minecraft.item.ItemStack;

import com.pahimar.ee3.exchange.*;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
		
		try { map = LatCoreMC.fromJsonFromFile(file, CustomEMC.class); }
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
			
			map.addRegNameValue("item.skull.skeleton", 0, 1024F);
			map.addRegNameValue("item.skull.wither", 1, 1024F);
			map.addRegNameValue("item.skull.zombie", 2, 1024F);
			map.addRegNameValue("item.skull.creeper", 4, 1024F);
			map.addRegNameValue("item.enchantedBook", LatCoreMC.ANY, 2048F);
			
			map.addRegNameValue("tile.projectred.exploration.stone.stonemarble", 0 ,1F);
			map.addRegNameValue("tile.projectred.exploration.stone.cobblebasalt", 2, 1F);
			map.addRegNameValue("tile.projectred.exploration.stone.stonebasalt", 3, 1F);
			
			map.addRegNameValue("railcraft.cube.stone.abyssal", 6, 1F);
			map.addRegNameValue("railcraft.cube.brick.abyssal.2", 2, 1F);
			
			LatCoreMC.toJsonFile(file, map);
		}
	}
	
	public void initRegNameItems()
	{
		if(!EMCC.config.general.enableCustomEMC) return;
		
		if(!map.ore_dictionary.isEmpty())
		{
			Iterator<String> keys = map.ore_dictionary.keySet().iterator();
			Iterator<Float> values = map.ore_dictionary.values().iterator();
			
			while(keys.hasNext())
			EnergyValueRegistry.getInstance().addPreAssignedEnergyValue(new OreStack(keys.next()), values.next());
		}
		
		if(!map.registry_name.isEmpty())
		{
			for(RegNameValue v : map.registry_name)
			{
				if(v.value > 0F)
				EnergyValueRegistry.getInstance().addPreAssignedEnergyValue(new OreStack("emcc_" + v.name), v.value);
			}
		}
	}
	
	public void postInitRegNameItems()
	{
		if(!map.registry_name.isEmpty())
		{
			for(RegNameValue v : map.registry_name)
			{
				int dmg = (v.damage == null) ? LatCoreMC.ANY : v.damage;
				ItemStack is = LatCoreMC.getStackFromRegName(v.name, dmg);
				if(is != null && v.value > 0F)
				LatCoreMC.addOreDictionary("emcc_" + v.name, is);
				else System.out.println("Invalid REgistry Name: " + v.name + "@" + dmg + ", " + is);
			}
		}
	}
}