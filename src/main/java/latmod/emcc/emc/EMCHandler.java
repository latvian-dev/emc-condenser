package latmod.emcc.emc;

import ftb.lib.*;
import latmod.emcc.config.EMCCConfigGeneral;
import latmod.lib.LMJsonUtils;
import net.minecraft.item.ItemStack;

import java.io.File;

public class EMCHandler
{
	private static EMCHandler instance = new EMCHandler();
	private static boolean hasEE3 = false;
	
	public static void init()
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
		
		if(EMCCConfigGeneral.force_vanilla_emc.getAsBoolean() || !hasEE3)
			instance.vanillaEMCFile = new File(FTBLib.folderConfig, "EMC_Condenser/vanilla_emc.json");
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
			vanillaEMC.func_152753_a(LMJsonUtils.fromJson(vanillaEMCFile));
		}
		else
		{
			vanillaEMC.loadDefaults();
			LMJsonUtils.toJson(vanillaEMCFile, vanillaEMC.getSerializableElement());
		}
	}
	
	public float getEMC(ItemStack is)
	{ return vanillaEMC.getEMC(is); }
}