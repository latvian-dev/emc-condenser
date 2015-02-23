package latmod.emcc.emc;

import java.io.File;

import latmod.core.LatCoreMC;
import latmod.emcc.EMCCConfig;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class EMCHandler
{
	private static EMCHandler instance = EMCHandlerDef.instance;
	private static boolean hasEE3 = false;
	
	public static void init(FMLPreInitializationEvent e)
	{
		hasEE3 = LatCoreMC.isModInstalled("EE3");
		
		if(hasEE3) try
		{
			Class<?> clazz = Class.forName("latmod.emcc.emc.EMCHandlerEE3");
			instance = (EMCHandler) clazz.newInstance();
			hasEE3 = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			instance = EMCHandlerDef.instance;
			hasEE3 = false;
		}
		
		if(EMCCConfig.General.forceVanillaEMC || !hasEE3)
			EMCHandlerDef.instance.vanillaEMCFile = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_VanillaEMC.json");
	}
	
	public static final boolean hasEE3()
	{ return hasEE3; }
	
	public static final EMCHandler instance()
	{ return instance; }
	
	public abstract void modInited();
	public abstract void loadRecipes();
	public abstract float getEMC(ItemStack is);
}