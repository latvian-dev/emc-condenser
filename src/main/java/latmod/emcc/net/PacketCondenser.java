package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public abstract class PacketCondenser
{
	public final int packetID;
	
	public PacketCondenser(int id)
	{ packetID = id; }
	
	public abstract void writePacket(TileCondenser t, ByteBuf out) throws Exception;
	public abstract void readPacket(TileCondenser t, ByteBuf in, EntityPlayer ep) throws Exception;
	
	public void writeString(ByteBuf data, String s)
	{
		if(s == null) data.writeByte(-1);
		data.writeByte(s.length());
		for(int i = 0; i < s.length(); i++)
			data.writeChar(s.charAt(i));
	}
	
	public String readString(ByteBuf data)
	{
		int i = data.readByte();
		if(i == -1) return null;
		char[] c = new char[i];
		for(int j = 0; j < i; j++)
			c[j] = data.readChar();
		return new String(c);
	}
}