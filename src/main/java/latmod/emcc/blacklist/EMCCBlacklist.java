package latmod.emcc.blacklist;
import java.io.*;
import latmod.core.*;
import latmod.core.util.*;
import latmod.emcc.*;
import cpw.mods.fml.common.event.*;
import net.minecraft.item.*;

public class EMCCBlacklist
{
	public Blacklist list;
	
	public EMCCBlacklist(FMLPreInitializationEvent e)
	{
		File file = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_Blacklist.json");
		
		list = null;
		
		try { list = LMUtils.fromJsonFromFile(file, Blacklist.class); }
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
			
			list.all.addUName("item.expBottle", null);
			
			list.targets.addUName("item.netherStar", null);
			list.targets.addUName("item.enchantedBook", null);
			
			list.fuels.addOreName("cobblestone");
			list.fuels.addOreName("stone");
			list.fuels.addOreName("foodSalt");
			
			list.example.addOreName("oreName");
			list.example.addUName("item.noDamage", null);
			list.example.addUName("item.withDamage", 3);
			list.example.addUName("item.anyDamage", -1);
			
			LMUtils.toJsonFile(file, list);
		}
		
		list.all.reloadList();
		list.targets.reloadList();
		list.fuels.reloadList();
	}
	
	public boolean isBlacklistedFuel(ItemStack is)
	{
		if(!EMCC.config.general.enableBlacklist) return false;
		if(list.fuels.isBlacklistedUN(is) || list.all.isBlacklistedUN(is)) return true;
		
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
		
		if(list.targets.isBlacklistedUN(is) || list.all.isBlacklistedUN(is)) return true;
		
		FastList<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(list.targets.isBlacklistedOre(oreNames) || list.all.isBlacklistedOre(oreNames))
				return true;
		}
		
		return false;
	}
	
}