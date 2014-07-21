package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCondenser implements IMessage, IMessageHandler<MessageCondenser, IMessage>
{
	public IMessage onMessage(MessageCondenser message, MessageContext ctx)
	{
		return null;
	}
	
	public void fromBytes(ByteBuf buf)
	{
	}
	
	public void toBytes(ByteBuf buf)
	{
	}
}