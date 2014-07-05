package latmod.emcc.net;
import java.io.*;

import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public abstract class PacketCondenser
{
	public static final int ID_OPEN_GUI = 0;
	public static final int ID_BUTTON_PRESSED = 1;
	public static final int ID_TRANS_ITEMS = 2;
	public static final int ID_MODIFY_RESTRICTED = 3;
	
	public final int packetID;
	
	public PacketCondenser(int id)
	{ packetID = id; }
	
	public abstract void writePacket(TileCondenser t, DataOutputStream dos) throws Exception;
	public abstract void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception;
}