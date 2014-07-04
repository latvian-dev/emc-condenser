package latmod.emcc.gui;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import latmod.core.base.GuiLM;
import latmod.emcc.EMCCGuis;
import latmod.emcc.tile.*;

@SideOnly(Side.CLIENT)
public class GuiCondenserSettings extends GuiLM
{
	public TileCondenser condenser;
	public TinyButton buttonSettings, buttonRedstone, buttonSecurity, buttonInvMode, buttonAutoExport;
	
	public GuiCondenserSettings(ContainerCondenserSettings c)
	{
		super(c);
		condenser = (TileCondenser)c.tile;
		player = c.player;
		ySize = 240;
		
		buttonSettings = new TinyButton(this, 153, 7, 16, 16);
		
		buttonRedstone = new TinyButton(this, 71, 30, 16, 16);
		buttonSecurity = new TinyButton(this, 153, 30, 16, 16);
		buttonInvMode = new TinyButton(this, 71, 75, 16, 16);
		buttonAutoExport = new TinyButton(this, 153, 75, 16, 16);
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		super.mouseClicked(x, y, b);
		
		if(buttonSettings.isAt(x - guiLeft, y - guiTop))
		{
			condenser.openGui(false, player, EMCCGuis.CONDENSER);
			playSound("random.click", 1F);
		}
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		{
			condenser.handleGuiButton(false, player, EnumCond.Buttons.REDSTONE);
			playSound("random.click", 1F);
		}
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		{
			condenser.handleGuiButton(false, player, EnumCond.Buttons.SECURITY);
			playSound("random.click", 1F);
		}
		
		if(buttonInvMode.isAt(x - guiLeft, y - guiTop))
		{
			condenser.handleGuiButton(false, player, EnumCond.Buttons.INV_MODE);
			playSound("random.click", 1F);
		}
		
		if(buttonAutoExport.isAt(x - guiLeft, y - guiTop))
		{
			condenser.handleGuiButton(false, player, EnumCond.Buttons.AUTO_EXPORT);
			playSound("random.click", 1F);
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		buttonRedstone.render(guiLeft, guiTop, xSize + condenser.redstoneMode.ID * 16, 0, 1F);
		buttonSecurity.render(guiLeft, guiTop, xSize + condenser.security.level * 16, 16, 1F);
		buttonInvMode.render(guiLeft, guiTop, xSize + condenser.invMode.ID * 16, 32, 1F);
		
		if(condenser.autoExport.isOn())
			buttonAutoExport.render(guiLeft, guiTop, xSize + 64, 0, 1F);
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonSettings.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Back");
		}
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		{
			al.add(condenser.redstoneMode.text);
		}
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		{
			al.add(condenser.getSecurityEnum().text);
		}
		
		if(buttonInvMode.isAt(x - guiLeft, y - guiTop))
		{
			al.add(condenser.invMode.text);
		}
		
		if(buttonAutoExport.isAt(x - guiLeft, y - guiTop))
		{
			al.add(condenser.autoExport.text);
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}