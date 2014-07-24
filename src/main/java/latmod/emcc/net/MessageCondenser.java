package latmod.emcc.net;
import latmod.emcc.tile.*;
import io.netty.buffer.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public abstract class MessageCondenser implements IMessage
{
	public int posX, posY, posZ;
	
	public MessageCondenser() { }
	
	public MessageCondenser(TileCondenser t)
	{
		posX = t.xCoord;
		posY = t.yCoord;
		posZ = t.zCoord;
	}

	public void fromBytes(ByteBuf data)
	{
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
	}
	
	public void toBytes(ByteBuf data)
	{
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
	}
	
	public String readString(ByteBuf data)
	{
		int s = data.readShort();
		if(s == -1) return null;
		String str = "";
		for(int i = 0; i < s; i++)
			str += data.readChar();
		return str;
	}
	
	public void writeString(ByteBuf data, String s)
	{
		data.writeShort(s == null ? -1 : s.length());
		if(s != null && s.length() > 0)
		for(int i = 0; i < s.length(); i++)
			data.writeChar(s.charAt(i));
	}
	
	public TileCondenser getTile(MessageContext ctx)
	{ return (TileCondenser) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(posX, posY, posZ); }
}