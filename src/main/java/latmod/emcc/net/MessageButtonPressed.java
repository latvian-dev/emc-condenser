package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.common.network.simpleimpl.*;

public class MessageButtonPressed extends MessageCondenser implements IMessageHandler<MessageButtonPressed, IMessage>
{
	public int buttonID;
	public int mouseButton;
	
	public MessageButtonPressed() { }
	
	public MessageButtonPressed(TileCondenser t, int b, int mb)
	{
		super(t);
		buttonID = b;
		mouseButton = mb;
	}
	
	public IMessage onMessage(MessageButtonPressed message, MessageContext ctx)
	{
		TileCondenser t = message.getTile(ctx);
		t.handleGuiButton(true, ctx.getServerHandler().playerEntity, message.buttonID, message.mouseButton);
		return null;
	}
	
	public void fromBytes(ByteBuf data)
	{
		super.fromBytes(data);
		buttonID = data.readByte();
		mouseButton = data.readByte();
	}
	
	public void toBytes(ByteBuf data)
	{
		super.toBytes(data);
		data.writeByte(buttonID);
		data.writeByte(mouseButton);
	}
}