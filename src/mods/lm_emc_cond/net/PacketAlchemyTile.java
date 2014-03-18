package mods.lm_emc_cond.net;
import java.io.*;

import cpw.mods.fml.common.network.*;
import mods.lm_emc_cond.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;

public abstract class PacketAlchemyTile extends Packet250CustomPayload
{
	public PacketAlchemyTile() { }
	
	public abstract void writeExtraData(TileAlchemy te, DataOutputStream dios) throws Exception;
	public abstract void readExtraData(TileAlchemy te, DataInputStream dios) throws Exception;
	public abstract void readAlchemyPacket(TileAlchemy te, INetworkManager m, Player p);
	
	public void sendToServer(TileAlchemy te)
	{
		channel = AlchemyFinals.MOD_ID;
		data = new byte[0];
		
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dios = new DataOutputStream(bos);
			
			dios.writeShort((short)te.worldObj.provider.dimensionId);
			dios.writeInt(te.xCoord);
			dios.writeInt(te.yCoord);
			dios.writeInt(te.zCoord);
			
			writeExtraData(te, dios);
			
			dios.flush();
			data = bos.toByteArray();
			length = bos.size();
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		PacketDispatcher.sendPacketToServer(this);
	}
}