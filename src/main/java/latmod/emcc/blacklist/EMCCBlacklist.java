package latmod.emcc.blacklist;

import com.google.gson.JsonElement;
import ftb.lib.FTBLib;
import ftb.lib.api.item.ODItems;
import latmod.emcc.EMCCItems;
import latmod.emcc.config.EMCCConfigGeneral;
import latmod.lib.LMJsonUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.List;

public class EMCCBlacklist
{
	public Blacklist list;
	
	public EMCCBlacklist()
	{
		File file = new File(FTBLib.folderConfig, "/EMC_Condenser/blacklist.json");
		
		list = null;
		
		JsonElement je = LMJsonUtils.fromJson(file);
		
		if(je != null && je.isJsonObject())
		{
			list = new Blacklist();
		}
		
		if(list == null)
		{
			list = new Blacklist();
			
			list.all.addRegistryName(Items.experience_bottle, 0);
			
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
			
			list.targets.addRegistryName(Items.nether_star, 0);
			list.targets.addRegistryName(Items.enchanted_book, -1);
			
			list.fuels.addOreName(ODItems.COBBLE);
			list.fuels.addOreName(ODItems.STONE);
			list.fuels.addOreName("foodSalt");
			
			LMJsonUtils.toJson(file, list.getSerializableElement());
		}
		
		list.all.reloadList();
		list.targets.reloadList();
		list.fuels.reloadList();
	}
	
	public boolean isBlacklistedFuel(ItemStack is)
	{
		if(!EMCCConfigGeneral.blacklist.getAsBoolean()) return false;
		if(list.fuels.isBlacklistedRegName(is) || list.all.isBlacklistedRegName(is)) return true;
		
		List<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(list.fuels.isBlacklistedOre(oreNames) || list.all.isBlacklistedOre(oreNames)) return true;
		}
		
		return false;
	}
	
	public boolean isBlacklistedTarget(ItemStack is)
	{
		if(is.getItem() == EMCCItems.i_wrench) return true;
		
		if(!EMCCConfigGeneral.blacklist.getAsBoolean()) return false;
		
		if(list.targets.isBlacklistedRegName(is) || list.all.isBlacklistedRegName(is)) return true;
		
		List<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(list.targets.isBlacklistedOre(oreNames) || list.all.isBlacklistedOre(oreNames)) return true;
		}
		
		return false;
	}
	
}