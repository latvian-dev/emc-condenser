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
		condenser = (TileCondenser)c.tile;
		player = c.player;
		ySize = 205;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.openGui(false, player, EMCCGuis.CONDENSER);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, 71, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, player, EnumCond.Buttons.REDSTONE);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonSecurity = new ButtonLM(this, 153, 30, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, player, EnumCond.Buttons.SECURITY);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonSecuritySettings = new ButtonLM(this, 92, 48, 74, 17)
		{
			public void onButtonPressed(int b)
			{
				if(condenser.security.isLevelRestricted())
				{
					condenser.openGui(player, EMCCGuis.COND_RESTRICTED);
					playSound("random.click", 1F);
				}
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, 71, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, player, EnumCond.Buttons.INV_MODE);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonRepairItems = new ButtonLM(this, 153, 75, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, player, EnumCond.Buttons.REPAIR_ITEMS);
				playSound("random.click", 1F);
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		buttonRedstone.render(xSize + condenser.redstoneMode.ID * 16, 0);
		buttonSecurity.render(xSize + condenser.security.level * 16, 16);
		buttonInvMode.render(xSize + condenser.invMode.ID * 16, 32);
		
		if(condenser.security.isLevelRestricted() && condenser.security.isPlayerOwner(player))
			buttonSecuritySettings.render(xSize, 48);
		
		if(condenser.repairItems.isOn())
			buttonRepairItems.render(xSize + 48, 0);
	}
	
	public void drawGuiContainerForegroundLayer(int mx, int my)
	{
		super.drawGuiContainerForegroundLayer(mx, my);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonSettings.mouseOver(mx, my))
			al.add("Back");
		
		if(buttonRedstone.mouseOver(mx, my))
			al.add(condenser.redstoneMode.text);
		
		if(buttonSecurity.mouseOver(mx, my))
			al.add(condenser.getSecurityEnum().text);
		
		if(buttonInvMode.mouseOver(mx, my))
			al.add(condenser.invMode.text);
		
		if(buttonRepairItems.mouseOver(mx, my))
			al.add(condenser.repairItems.text);
		
		if(!al.isEmpty()) drawHoveringText(al, mx - guiLeft, my - guiTop, fontRenderer);
	}
}