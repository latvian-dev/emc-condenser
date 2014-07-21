package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.EMCCGuis;
import latmod.emcc.tile.*;

public class PacketOpenGui extends PacketCondenser
{
	public static final int PACKET_ID = EMCCNetHandler.nextPacketID();
	
	public int guiID;
	
	public PacketOpenGui(int id)
	{
		super(PACKET_ID);
		guiID = id;
	}
	
	public void writePacket(TileCondenser t, ByteBuf data) throws Exception
	{
		data.writeByte(guiID);
	}
	
	public void readPacket(TileCondenser t, ByteBuf data, EntityPlayer ep) throws Exception
	{
		guiID = data.readByte();
		
		if(guiID != EMCCGuis.COND_RESTRICTED || t.security.isPlayerOwner(ep))
			t.openGui(true, ep, guiID);
		else t.printOwner(ep);
	}
}