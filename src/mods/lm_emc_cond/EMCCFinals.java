package mods.lm_emc_cond;
import net.minecraft.util.*;

public class EMCCFinals
{
	// Native Strings //
	public static final String MOD_ID = "lm_emc_cond";
	public static final String MOD_NAME = "EMC Condenser";
	public static final String VERSION = "1.1.0.2";
	public static final String ASSETS = MOD_ID + ":";
	public static final String SIDE_CLIENT = "mods.lm_emc_cond.EMCCClient";
	public static final String SIDE_SERVER = "mods.lm_emc_cond.EMCCCommon";
	
	public static final ResourceLocation getLocation(String s)
	{ return new ResourceLocation(MOD_ID, s); }
	
	public static final String getItemName(String s)
	{ return ASSETS + "i." + s; }

	public static final String getBlockName(String s)
	{ return ASSETS + "b." + s; }

	public static final String translate(String s)
	{ return StatCollector.translateToLocal(ASSETS + s); }
}