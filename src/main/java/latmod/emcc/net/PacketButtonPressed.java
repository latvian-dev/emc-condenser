package latmod.emcc.net;
import java.io.*;

import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketButtonPressed extends PacketCondenser
{
	public int button;
	
	public PacketButtonPressed(int b)
	{
		super(ID_BUTTON_PRESSED);
		button = b;
	}
	
	public void writePacket(TileCondenser t, DataOutputStream dos) throws Exception
	{
		dos.writeByte(button);
	}
	
	public void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception
	{
		button = dis.readByte();
		t.handleGuiButton(true, ep, button);
	}
}