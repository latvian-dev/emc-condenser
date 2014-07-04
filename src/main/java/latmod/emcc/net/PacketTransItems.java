package latmod.emcc.net;
import java.io.*;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketTransItems extends PacketCondenser
{
	public PacketTransItems()
	{
		super(ID_TRANS_ITEMS);
	}
	
	public void writePacket(TileCondenser t, DataOutputStream dos) throws Exception
	{
	}
	
	public void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception
	{
		t.transferItems(true, ep);
	}
}