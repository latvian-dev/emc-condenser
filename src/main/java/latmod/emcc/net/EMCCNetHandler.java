package latmod.emcc.net;
import latmod.emcc.*;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;

public class EMCCNetHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EMCC.MOD_ID);
	private static int nextPacketID = -1;
	public static final PacketCondenser[] packets = new PacketCondenser[32];
	
	public static int nextPacketID()
	{ return ++nextPacketID; }
	
	public static void addPacket(PacketCondenser p)
	{ packets[p.packetID] = p; }
	
	public static void init()
	{
    	INSTANCE.registerMessage(MessageCondenser.class, MessageCondenser.class, 0, Side.CLIENT);
    	
    	addPacket(new PacketOpenGui(0));
		addPacket(new PacketButtonPressed(0, 0));
		addPacket(new PacketTransItems());
		addPacket(new PacketModifyRestricted(false, null));
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
	
	public static void sendToServer(TileCondenser t, PacketCondenser p)
	{
		//INSTANCE.sendToServer(getCustomMessage(t, p));
	}
}