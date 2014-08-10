package latmod.emcc.net;
import latmod.emcc.EMCC;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class EMCCNetHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EMCC.getModID());
	private static int nextPacketID = -1; public static int nextPacketID() { return ++nextPacketID; }
	
	public static void init()
	{
		INSTANCE.registerMessage(MessageButtonPressed.class, MessageButtonPressed.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageOpenGui.class, MessageOpenGui.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageTransItems.class, MessageTransItems.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageModifyRestricted.class, MessageModifyRestricted.class, nextPacketID(), Side.SERVER);
	}
}