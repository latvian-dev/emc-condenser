package latmod.emcc.emc;

import latmod.core.LatCoreMC;
import net.minecraft.item.ItemStack;

public abstract class EMCHandler
{
	private static EMCHandler instance = EMCHandlerDef.instance;
	private static boolean hasEE3 = false;
	
	public static void init()
	{
		hasEE3 = LatCoreMC.isModInstalled("EE3");
		
		if(hasEE3) try
		{
			Class<?> clazz = Class.forName("latmod.emcc.emc.EMCHandlerEE3");
			instance = (EMCHandler) clazz.newInstance();
			hasEE3 = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			instance = EMCHandlerDef.instance;
			hasEE3 = false;
		}
	}
	
	public static final boolean hasEE3()
	{ return hasEE3; }
	
	public static final EMCHandler instance()
	{ return instance; }
	
	public abstract void modInited();
	public abstract void loadRecipes();
	public abstract float getEMC(ItemStack is);
}