package mods.lm_emc_cond.net;
import java.io.*;

import cpw.mods.fml.common.network.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.network.*;

public class PacketGuiButton extends PacketAlchemyTile
{
	public int buttonID = 0;
	
	public PacketGuiButton(int bid)
	{
		buttonID = bid;
	}
	
	@Override
	public void writeExtraData(TileAlchemy te, DataOutputStream dios) throws Exception
	{
		dios.writeByte((byte)buttonID);
	}
	
	@Override
	public void readExtraData(TileAlchemy te, DataInputStream dios) throws Exception
	{
		buttonID = dios.readByte();
	}
	
	@Override
	public void readAlchemyPacket(TileAlchemy te, INetworkManager m, Player p)
	{
		if(te instanceof TileCondenser)
		((TileCondenser)te).handleGuiButton(buttonID);
	}
}