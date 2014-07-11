package latmod.emcc.net;
import java.io.*;

import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public abstract class PacketCondenser
{
	public final int packetID;
	
	public PacketCondenser(int id)
	{ packetID = id; }
	
	public abstract void writePacket(TileCondenser t, DataOutputStream dos) throws Exception;
	public abstract void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception;
}