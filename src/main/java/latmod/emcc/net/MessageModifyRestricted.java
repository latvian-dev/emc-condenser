package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.common.network.simpleimpl.*;

public class MessageModifyRestricted extends MessageCondenser implements IMessageHandler<MessageModifyRestricted, IMessage>
{
	public boolean add;
	public String name;
	
	public MessageModifyRestricted() { }
	
	public MessageModifyRestricted(TileCondenser t, boolean b, String s)
	{
		super(t);
		add = b;
		name = s;
	}
	
	public IMessage onMessage(MessageModifyRestricted message, MessageContext ctx)
	{
		TileCondenser t = message.getTile(ctx);
		
		if(message.add && !t.security.restricted.contains(message.name) && t.security.restricted.size() < 16)
			t.security.restricted.add(message.name);
		
		if(!message.add) t.security.restricted.remove(message.name);
		
		t.markDirty();
		
		return null;
	}
	
	public void fromBytes(ByteBuf data)
	{
		super.fromBytes(data);
		add = data.readBoolean();
		name = readString(data);
	}
	
	public void toBytes(ByteBuf data)
	{
		super.toBytes(data);
		data.writeBoolean(add);
		writeString(data, name);
	}
}