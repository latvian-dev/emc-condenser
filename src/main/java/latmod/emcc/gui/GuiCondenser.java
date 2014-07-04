package latmod.emcc.gui;
import java.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import latmod.core.base.GuiLM;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public TileCondenser condenser;
	public TinyButton buttonSettings, buttonSafeMode, buttonTransItems;
	public TinyButton barEMC, targetIcon;
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		condenser = (TileCondenser)c.tile;
		player = c.player;
		ySize = 240;
		
		buttonSettings = new TinyButton(this, 153, 7, 16, 16);
		buttonSafeMode = new TinyButton(this, 153, 25, 7, 6);
		buttonTransItems = new TinyButton(this, 162, 25, 7, 6);
		
		barEMC = new TinyButton(this, 30, 9, 118, 16);
		targetIcon = new TinyButton(this, 8, 9, 16, 16);
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		super.mouseClicked(x, y, b);
		
		if(buttonSettings.isAt(x - guiLeft, y - guiTop))
		{
			condenser.openGui(false, player, EMCCGuis.COND_SETTINGS);
			playSound("random.click", 1F);
		}
		
		if(buttonSafeMode.isAt(x - guiLeft, y - guiTop))
		{
			condenser.handleGuiButton(false, player, EnumCond.Buttons.SAFE_MODE);
			playSound("random.click", 1F);
		}
		
		if(buttonTransItems.isAt(x - guiLeft, y - guiTop))
		{
			condenser.transferItems(false, player);
			playSound("random.click", 1F);
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		double l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
		
		if(l > 0L)
		barEMC.render(guiLeft, guiTop, 0, ySize, (condenser.storedEMC % l) / l);
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
		targetIcon.render(guiLeft, guiTop, xSize, 0, 1F);
		
		if(condenser.safeMode.isOn())
		buttonSafeMode.render(guiLeft, guiTop, xSize, 16, 1F);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.isAt(x - guiLeft, y - guiTop))
		{
			float l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
			double storedEMC = (long)(condenser.storedEMC * 1000D) / 1000D;
			al.add(EnumChatFormatting.GOLD.toString() + ((l == 0L) ? (storedEMC + "") : (storedEMC + " / " + l)));
		}
		
		if(buttonSettings.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Settings");
		}
		
		if(buttonSafeMode.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Safe Mode");
			al.add(condenser.safeMode.text);
		}
		
		if(buttonTransItems.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Take items");
		}
		
		if(targetIcon.isAt(x - guiLeft, y - guiTop) && condenser.items[TileCondenser.SLOT_TARGET] == null)
		{
			al.add("No Target");
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}