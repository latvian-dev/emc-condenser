package latmod.emcc.gui;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import latmod.core.FastList;
import latmod.core.base.gui.*;
import latmod.emcc.*;
import latmod.emcc.net.*;
import latmod.emcc.tile.*;

@SideOnly(Side.CLIENT)
public class GuiRestricted extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonBack;
	public PlayerButton[] players;
	
	public TextBoxLM textBox;
	
	private FastList<String> prevRestricted;
	
	public GuiRestricted(ContainerRestricted c)
	{
		super(c);
		condenser = (TileCondenser)c.tile;
		player = c.player;
		ySize = 202;
		
		widgets.add(buttonBack = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.openGui(false, player, EMCCGuis.COND_SETTINGS);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(new ButtonLM(this, 123, 32, 25, 18)
		{
			public void onButtonPressed(int b)
			{
				sendAddPlayer();
			}
		});
		
		widgets.add(textBox = new TextBoxLM(this, 29, 32, 90, 18)
		{
			public void returnPressed()
			{
				sendAddPlayer();
			}
		});
		
		textBox.charLimit = 25;
		
		players = new PlayerButton[16];
		
		for(int y = 0; y < 2; y++)
		for(int x = 0; x < 8; x++)
			widgets.add(players[x + y * 8] = new PlayerButton(this, 17 + x * 18, 69 + y * 18, 16, 16));
		
		prevRestricted = condenser.security.restricted.clone();
		
		for(int i = 0; i < 16; i++)
		{
			if(i < prevRestricted.size())
				players[i].setName(prevRestricted.get(i));
			else players[i].setName(null);
		}
	}
	
	private void sendAddPlayer()
	{
		if(textBox.text.length() > 0)
		{
			EMCCNetHandler.sendToServer(condenser, new PacketModifyRestricted(true, textBox.text));
			textBox.clear();
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		if(prevRestricted.size() != condenser.security.restricted.size() || !prevRestricted.containsAll(condenser.security.restricted))
		{
			prevRestricted = condenser.security.restricted.clone();
			
			for(int i = 0; i < 16; i++)
			{
				if(i < prevRestricted.size())
					players[i].setName(prevRestricted.get(i));
				else players[i].setName(null);
			}
		}
		
		for(int i = 0; i < players.length; i++)
			players[i].render(17 + (i % 8) * 18, 69 + (i / 8) * 18, 16D, 16D);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		String s = textBox.text + "";
		
		if(textBox.isSelected && textBox.canAddChar() && Minecraft.getSystemTime() % 1000L > 500L)
			s += '_';
		
		if(s.length() > 0)
		fontRenderer.drawString(s, 35, 37, 0xFFC6C6C6);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonBack.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Back");
		}
		
		for(int i = 0; i < players.length; i++)
		{
			if(players[i].isAt(x - guiLeft, y - guiTop))
			if(players[i].playerName != null)
			{
				al.add(players[i].playerName);
				
				if(GuiScreen.isShiftKeyDown())
					al.add("Remove from this list");
			}
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}