package latmod.emcc.net;
import java.io.*;

import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketOpenGui extends PacketCondenser
{
	public int guiID;
	
	public PacketOpenGui(int id)
	{
		super(ID_OPEN_GUI);
		guiID = id;
	}
	
	public void writePacket(TileCondenser t, DataOutputStream dos) throws Exception
	{
		dos.writeByte(guiID);
	}
	
	public void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception
	{
		guiID = dis.readByte();
		t.openGui(true, ep, guiID);
	}
}