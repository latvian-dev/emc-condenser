package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketTransItems extends PacketCondenser
{
	public static final int PACKET_ID = EMCCNetHandler.nextPacketID();
	
	public PacketTransItems()
	{
		super(PACKET_ID);
	}
	
	public void writePacket(TileCondenser t, ByteBuf data) throws Exception
	{
	}
	
	public void readPacket(TileCondenser t, ByteBuf data, EntityPlayer ep) throws Exception
	{
		t.transferItems(true, ep);
	}
}