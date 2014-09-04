package latmod.emcc.blacklist;
import java.io.File;

import latmod.core.*;
import latmod.core.util.FastList;
import latmod.emcc.*;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class EMCCBlacklist
{
	public Blacklist list;
	
	public EMCCBlacklist(FMLPreInitializationEvent e)
	{
		File file = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_Blacklist.json");
		
		list = null;
		
		try { list = LatCoreMC.fromJsonFromFile(file, Blacklist.class); }
		catch(Exception ex) { list = null; }
		
		if(list == null)
		{
			list = new Blacklist();
			
			list.all.addOreName("oreIron");
			list.all.addOreName("oreGold");
			list.all.addOreName("oreCoal");
			list.all.addOreName("oreRedstone");
			list.all.addOreName("oreLapis");
			list.all.addOreName("oreDiamond");
			list.all.addOreName("oreEmerald");
			list.all.addOreName("oreQuartz");
			list.all.addOreName("oreTin");
			list.all.addOreName("oreCopper");
			list.all.addOreName("oreUranium");
			list.all.addOreName("oreNickel");
			list.all.addOreName("oreLead");
			list.all.addOreName("oreSilver");
			list.all.addOreName("oreRuby");
			list.all.addOreName("oreSapphire");
			list.all.addOreName("oreAluminum");
			list.all.addOreName("oreFzDarkIron");
			
			// Items
			
			list.all.addRegistryName("experience_bottle", null);
			
			list.targets.addRegistryName("nether_star", null);
			list.targets.addRegistryName("enchanted_book", null);
			
			list.fuels.addOreName("cobblestone");
			list.fuels.addOreName("stone");
			list.fuels.addOreName("foodSalt");
			
			list.example.addOreName("oreName");
			list.example.addRegistryName("itemNoDamage", null);
			list.example.addRegistryName("itemWithDamage", 3);
			list.example.addRegistryName("itemAnyDamage", -1);
			
			LatCoreMC.toJsonFile(file, list);
		}
		
		list.all.reloadList();
		list.targets.reloadList();
		list.fuels.reloadList();
	}
	
	public boolean isBlacklistedFuel(ItemStack is)
	{
		if(!EMCC.config.general.enableBlacklist) return false;
		if(list.fuels.isBlacklistedRegName(is) || list.all.isBlacklistedRegName(is)) return true;
		
		FastList<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(list.fuels.isBlacklistedOre(oreNames) || list.all.isBlacklistedOre(oreNames))
				return true;
		}
		
		return false;
	}
	
	public boolean isBlacklistedTarget(ItemStack is)
	{
		if(is.getItem() == EMCCItems.i_wrench) return true;
		
		if(!EMCC.config.general.enableBlacklist) return false;
		
		if(list.targets.isBlacklistedRegName(is) || list.all.isBlacklistedRegName(is)) return true;
		
		FastList<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(list.targets.isBlacklistedOre(oreNames) || list.all.isBlacklistedOre(oreNames))
				return true;
		}
		
		return false;
	}
	
}