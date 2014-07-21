package latmod.emcc.gui;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import latmod.core.base.gui.*;
import latmod.emcc.*;
import latmod.emcc.tile.*;

@SideOnly(Side.CLIENT)
public class GuiCondenserSettings extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonRedstone, buttonSecurity, buttonSecuritySettings, buttonInvMode, buttonRepairItems;
	
	public GuiCondenserSettings(ContainerCondenserSettings c)
	{
		super(c);
		condenser = (TileCondenser)c.inv;
		ySize = 205;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.openGui(false, container.player, EMCCGuis.CONDENSER);
				playClickSound();
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, 71, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, container.player, EMCCGuis.Buttons.REDSTONE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonSecurity = new ButtonLM(this, 153, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, container.player, EMCCGuis.Buttons.SECURITY, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonSecuritySettings = new ButtonLM(this, 92, 48, 74, 17)
		{
			public void onButtonPressed(int b)
			{
				if(condenser.security.level.isRestricted())
				{
					if(!condenser.openGui(false, container.player, EMCCGuis.COND_RESTRICTED))
						condenser.printOwner(container.player);
					playClickSound();
				}
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, 71, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, container.player, EMCCGuis.Buttons.INV_MODE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonRepairItems = new ButtonLM(this, 153, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, container.player, EMCCGuis.Buttons.REPAIR_TOOLS, b);
				playClickSound();
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		buttonRedstone.render(xSize + condenser.redstoneMode.ID * 16, 0);
		buttonSecurity.render(xSize + condenser.security.level.ID * 16, 16);
		buttonInvMode.render(xSize + condenser.invMode.ID * 16, 32);
		
		if(condenser.security.level.isRestricted() && condenser.security.isPlayerOwner(container.player))
			buttonSecuritySettings.render(xSize, 48);
		
		if(condenser.repairTools.isOn())
			buttonRepairItems.render(xSize + 48, 0);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonSettings.mouseOver(mx, my))
			al.add(EMCC.mod.translate("back"));
		
		if(buttonRedstone.mouseOver(mx, my))
			al.add(condenser.redstoneMode.getText());
		
		if(buttonSecurity.mouseOver(mx, my))
			al.add(condenser.security.level.getText());
		
		if(buttonInvMode.mouseOver(mx, my))
			al.add(condenser.invMode.getText());
		
		if(buttonRepairItems.mouseOver(mx, my))
			al.add(condenser.repairTools.getText());
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}