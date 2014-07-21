package latmod.emcc.tile;
import latmod.emcc.EMCC;

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
	
	public String getText()
	{ return EMCC.mod.translate("safemode." + uname); }
	
	public String getTitle()
	{ return EMCC.mod.translate("safemode"); }
}