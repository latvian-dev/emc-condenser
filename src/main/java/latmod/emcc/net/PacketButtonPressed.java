package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketButtonPressed extends PacketCondenser
{
	public static final int PACKET_ID = EMCCNetHandler.nextPacketID();
	
	public int buttonID;
	public int mouseButton;
	
	public PacketButtonPressed(int b, int mb)
	{
		super(PACKET_ID);
		buttonID = b;
		mouseButton = mb;
	}
	
	public void writePacket(TileCondenser t, ByteBuf data) throws Exception
	{
		data.writeByte(buttonID);
		data.writeByte(mouseButton);
	}
	
	public void readPacket(TileCondenser t, ByteBuf data, EntityPlayer ep) throws Exception
	{
		buttonID = data.readByte();
		mouseButton = data.readByte();
		t.handleGuiButton(true, ep, buttonID, mouseButton);
	}
}