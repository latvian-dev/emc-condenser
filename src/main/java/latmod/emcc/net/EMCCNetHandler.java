package latmod.emcc.net;
import latmod.emcc.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;

public class EMCCNetHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EMCC.getModID());
	private static int nextPacketID = -1; public static int nextPacketID() { return ++nextPacketID; }
	
	public static void init()
	{
		INSTANCE.registerMessage(MessageButtonPressed.class, MessageButtonPressed.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageOpenGui.class, MessageOpenGui.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageTransItems.class, MessageTransItems.class, nextPacketID(), Side.SERVER);
		INSTANCE.registerMessage(MessageModifyRestricted.class, MessageModifyRestricted.class, nextPacketID(), Side.SERVER);
	}

	/*
	
	public void onPacketData(INetworkManager m, Packet250CustomPayload p, Player player)
	{
		try
		{
			if(p.channel.equals(EMCC.MOD_ID) && player instanceof EntityPlayer)
			{
				World worldObj = ((EntityPlayer)player).worldObj;
				
				DataInputStream dis = new DataInputStream(new ByteArrayInputStream(p.data));
				int dim = dis.readShort();
				int x = dis.readInt();
				int y = dis.readInt();
				int z = dis.readInt();
				int packetID = dis.readByte();
				
				if(dim == worldObj.provider.dimensionId && !worldObj.isRemote)
				{
					TileEntity t = worldObj.getBlockTileEntity(x, y, z);
					
					if(t != null && t instanceof TileCondenser)
						packets[packetID].readPacket((TileCondenser)t, dis, (EntityPlayer)player);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static IMessage getCustomPacket(TileCondenser t, PacketCondenser p)
	{
		Packet250CustomPayload packet = new Packet250CustomPayload(EMCC.MOD_ID, new byte[0]);
		
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			dos.writeShort((short)t.worldObj.provider.dimensionId);
			dos.writeInt(t.xCoord);
			dos.writeInt(t.yCoord);
			dos.writeInt(t.zCoord);
			dos.writeByte(p.packetID);
			p.writePacket(t, dos);
			dos.flush();
			
			packet.data = baos.toByteArray();
			packet.length = baos.size();
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		return packet;
	}
	
	*/
}