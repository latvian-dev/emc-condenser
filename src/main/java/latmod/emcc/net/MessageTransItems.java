package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.common.network.simpleimpl.*;

public class MessageTransItems extends MessageCondenser implements IMessageHandler<MessageTransItems, IMessage>
{
	public MessageTransItems() { }
	
	public MessageTransItems(TileCondenser t)
	{
		super(t);
	}
	
	public IMessage onMessage(MessageTransItems message, MessageContext ctx)
	{
		TileCondenser t = message.getTile(ctx);
		t.transferItems(true, ctx.getServerHandler().playerEntity);
		return null;
	}
	
	public void fromBytes(ByteBuf data)
	{
		super.fromBytes(data);
	}
	
	public void toBytes(ByteBuf data)
	{
		super.toBytes(data);
	}
}