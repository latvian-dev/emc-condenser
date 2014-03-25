package mods.lm.emcc;
import net.minecraft.util.*;

public class EMCCFinals
{
	// Native Strings //
	public static final String MOD_ID = "lm.emcc";
	public static final String MOD_NAME = "EMC Condenser";
	public static final String VERSION = "1.1.1.0";
	public static final String ASSETS = "lm_emcc:";
	public static final String SIDE_CLIENT = "mods.lm.emcc.EMCCClient";
	public static final String SIDE_SERVER = "mods.lm.emcc.EMCCCommon";
	
	public static final ResourceLocation getLocation(String s)
	{ return new ResourceLocation("lm_emcc", s); }
	
	public static final String getItemName(String s)
	{ return ASSETS + "i." + s; }

	public static final String getBlockName(String s)
	{ return ASSETS + "b." + s; }
}