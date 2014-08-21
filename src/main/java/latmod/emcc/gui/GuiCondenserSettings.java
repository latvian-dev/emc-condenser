package latmod.emcc.gui;
import java.util.ArrayList;

import latmod.core.LatCoreMC;
import latmod.core.mod.*;
import latmod.core.mod.gui.*;
import latmod.emcc.*;
import latmod.emcc.tile.TileCondenser;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenserSettings extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonRedstone, buttonSecurity, buttonInvMode, buttonRepairItems;
	
	public GuiCondenserSettings(ContainerCondenserSettings c)
	{
		super(c, LatCoreMC.getLocation(EMCC.MOD_ID, "textures/gui/condenserSettings.png"));
		condenser = (TileCondenser)c.inv;
		ySize = 205;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientOpenGui(EMCCGuis.CONDENSER);
				playClickSound();
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, 71, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.REDSTONE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonSecurity = new ButtonLM(this, 153, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.SECURITY, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, 71, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LCGuis.Buttons.INV_MODE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonRepairItems = new ButtonLM(this, 153, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(EMCCGuis.Buttons.REPAIR_TOOLS, b);
				playClickSound();
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		buttonRedstone.render(button_redstone[condenser.redstoneMode.ID]);
		buttonSecurity.render(button_security[condenser.security.level.ID]);
		buttonInvMode.render(button_inv[condenser.invMode.ID]);
		
		if(condenser.repairTools.isOn())
			buttonRepairItems.render(button_inner_pressed);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonSettings.mouseOver(mx, my))
			al.add(LC.mod.translate("back"));
		
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