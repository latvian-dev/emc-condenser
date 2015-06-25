package latmod.emcc.tile;
import latmod.emcc.EMCC;
import cpw.mods.fml.relauncher.*;

public enum SafeMode
{
	DISABLED("disabled"),
	ENABLED("enabled");
	
	public static final SafeMode[] VALUES = values();
	
	public final int ID;
	public final String uname;
	
	SafeMode(String s)
	{
		ID = ordinal();
		uname = s;
	}

	public boolean isOn()
	{ return this == ENABLED; }

	public SafeMode next()
	{ return isOn() ? DISABLED : ENABLED; }
	
	@SideOnly(Side.CLIENT)
	public String getText()
	{ return EMCC.mod.translateClient("safemode." + uname); }
	
	@SideOnly(Side.CLIENT)
	public String getTitle()
	{ return EMCC.mod.translateClient("safemode"); }
}