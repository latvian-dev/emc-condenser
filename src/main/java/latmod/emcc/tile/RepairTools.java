package latmod.emcc.tile;
import latmod.emcc.EMCC;

public enum RepairTools
{
	DISABLED("disabled"),
	ENABLED("enabled");
	
	public static final RepairTools[] VALUES = values();
	
	public final int ID;
	public final String uname;
	
	RepairTools(String s)
	{
		ID = ordinal();
		uname = s;
	}

	public boolean isOn()
	{ return this == ENABLED; }

	public RepairTools next()
	{ return isOn() ? DISABLED : ENABLED; }
	
	public String getText()
	{ return EMCC.mod.translate("repairtools." + uname); }
	
	public String getTitle()
	{ return EMCC.mod.translate("repairtools"); }
}