package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketModifyRestricted extends PacketCondenser
{
	public static final int PACKET_ID = EMCCNetHandler.nextPacketID();
	
	public boolean add;
	public String name;
	
	public PacketModifyRestricted(boolean b, String s)
	{
		super(PACKET_ID);
		add = b;
		name = s;
	}
	
	public void writePacket(TileCondenser t, ByteBuf data) throws Exception
	{
		data.writeBoolean(add);
		writeString(data, name);
	}
	
	public void readPacket(TileCondenser t, ByteBuf data, EntityPlayer ep) throws Exception
	{
		add = data.readBoolean();
		name = readString(data);
		
		if(add && !t.security.restricted.contains(name) && t.security.restricted.size() < 16)
			t.security.restricted.add(name);
		
		if(!add) t.security.restricted.remove(name);
		
		t.markDirty();
	}
}