package latmod.emcc.net;
import io.netty.buffer.ByteBuf;
import latmod.emcc.EMCCGuis;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.*;

public class MessageOpenGui extends MessageCondenser implements IMessageHandler<MessageOpenGui, IMessage>
{
	public int guiID;
	
	public MessageOpenGui() { }
	
	public MessageOpenGui(TileCondenser t, int i)
	{
		super(t);
		guiID = i;
	}
	
	public IMessage onMessage(MessageOpenGui message, MessageContext ctx)
	{
		TileCondenser t = message.getTile(ctx);
		EntityPlayer ep = ctx.getServerHandler().playerEntity;
		
		if(message.guiID != EMCCGuis.COND_RESTRICTED || t.security.isPlayerOwner(ep))
			t.openGui(true, ep, message.guiID);
		else t.printOwner(ep);
		
		return null;
	}
	
	public void fromBytes(ByteBuf data)
	{
		super.fromBytes(data);
		guiID = data.readByte();
	}
	
	public void toBytes(ByteBuf data)
	{
		super.toBytes(data);
		data.writeByte(guiID);
	}
}