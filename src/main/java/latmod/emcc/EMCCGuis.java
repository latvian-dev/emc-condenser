package latmod.emcc;

public class EMCCGuis
{
	private static int nextGuiID = 0;
	public static final int nextGuiID()
	{ return ++nextGuiID; }
	
	public static final int CONDENSER = nextGuiID();
	public static final int COND_SETTINGS = nextGuiID();
	public static final int COND_RESTRICTED = nextGuiID();
	
	public static class Buttons
	{
		private static int nextButtonID = 0;
		public static final int nextButtonID()
		{ return ++nextButtonID; }
		
		public static final int SAFE_MODE = nextButtonID();
		public static final int REDSTONE = nextButtonID();
		public static final int SECURITY = nextButtonID();
		public static final int INV_MODE = nextButtonID();
		public static final int REPAIR_TOOLS = nextButtonID();
	}
}