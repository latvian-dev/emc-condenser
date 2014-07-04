package latmod.emcc.net;
import java.io.*;

import latmod.emcc.EMCC;
import latmod.emcc.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.*;

public class EMCCNetHandler implements IPacketHandler
{
	public PacketCondenser[] packets = new PacketCondenser[32];
	
	public EMCCNetHandler()
	{
		addPacket(new PacketOpenGui(0));
		addPacket(new PacketButtonPressed(0));
		addPacket(new PacketTransItems());
	}
	
	public void addPacket(PacketCondenser p)
	{ packets[p.packetID] = p; }
	
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
	
	private static Packet250CustomPayload getCustomPacket(TileCondenser t, PacketCondenser p)
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
	
	public static void sendToServer(TileCondenser t, PacketCondenser p)
	{ PacketDispatcher.sendPacketToServer(getCustomPacket(t, p)); }
	
	public static void sendToDimension(TileCondenser t, PacketCondenser p)
	{ PacketDispatcher.sendPacketToAllPlayers(getCustomPacket(t, p)); }
}