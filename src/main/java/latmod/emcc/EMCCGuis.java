package latmod.emcc;

public class EMCCGuis
{
	private static int nextGuiID = 0;
	public static final int nextGuiID()
	{ return ++nextGuiID; }
	
	public static final int CONDENSER = nextGuiID();
	public static final int COND_SETTINGS = nextGuiID();
	
	public static class Buttons
	{
		public static final String SAFE_MODE = "safe_mode";
		public static final String REPAIR_TOOLS = "repair_tools";
	}
}