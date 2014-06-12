package latmod.emcc;
import net.minecraft.util.*;

public class EMCCFinals
{
	// Native Strings //
	public static final String MOD_ID = "emcc";
	public static final String MOD_NAME = "EMC Condenser Mod";
	public static final String VERSION = "1.2.0";
	public static final String ASSETS = MOD_ID + ":";
	public static final String SIDE_CLIENT = "latmod.emcc.EMCCClient";
	public static final String SIDE_SERVER = "latmod.emcc.EMCCCommon";
	
	public static final ResourceLocation getLocation(String s)
	{ return new ResourceLocation("emcc", s); }
	
	public static final String getItemName(String s)
	{ return ASSETS + "i." + s; }
	
	public static final String getBlockName(String s)
	{ return ASSETS + "b." + s; }
}