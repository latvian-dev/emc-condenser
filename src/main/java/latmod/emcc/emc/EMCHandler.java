package latmod.emcc.emc;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ftb.lib.*;
import latmod.emcc.config.EMCCConfigGeneral;
import latmod.lib.LMJsonUtils;
import net.minecraft.item.ItemStack;

public class EMCHandler
{
	private static EMCHandler instance = new EMCHandler();
	private static boolean hasEE3 = false;
	
	public static void init(FMLPreInitializationEvent e)
	{
		hasEE3 = FTBLib.isModInstalled(OtherMods.EE3);
		
		if(hasEE3) try
		{
			Class<?> clazz = Class.forName("latmod.emcc.emc.EMCHandlerEE3");
			instance = (EMCHandler) clazz.newInstance();
			hasEE3 = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(instance == null) instance = new EMCHandler();
			hasEE3 = false;
		}
		
		if(EMCCConfigGeneral.force_vanilla_emc.get() || !hasEE3)
			instance.vanillaEMCFile = new File(e.getModConfigurationDirectory(), "EMC_Condenser/vanilla_emc.json");
	}
	
	public static final boolean hasEE3()
	{ return hasEE3; }
	
	public static final EMCHandler instance()
	{ return instance; }
	
	public final VanillaEMC vanillaEMC = new VanillaEMC();
	private File vanillaEMCFile = null;
	
	public void modInited()
	{
		reloadEMCValues();
	}
	
	public void reloadEMCValues()
	{
		if(vanillaEMCFile == null) return;
		
		vanillaEMC.clear();
		
		if(vanillaEMCFile.exists())
		{
			VanillaEMC.EMCFile f = LMJsonUtils.fromJsonFile(vanillaEMCFile, VanillaEMC.EMCFile.class);
			if(f != null) f.saveTo(vanillaEMC);
			else vanillaEMC.loadDefaults();
		}
		else
		{
			vanillaEMC.loadDefaults();
			VanillaEMC.EMCFile f = new VanillaEMC.EMCFile();
			f.loadFrom(vanillaEMC);
			LMJsonUtils.toJsonFile(vanillaEMCFile, f);
		}
	}
	
	public float getEMC(ItemStack is)
	{ return vanillaEMC.getEMC(is); }
}