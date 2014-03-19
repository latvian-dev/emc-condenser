package mods.lm_emc_cond.gui;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import mods.lm_core.LMSecurity;
import mods.lm_core.mod.*;
import mods.lm_emc_cond.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiContainer
{
	public TileCondenser tile;
	public EntityPlayer player;
	
	public TinyButton buttonSafe, barEMC, buttonRedstone, buttonSecurity;
	
	public static final ResourceLocation texture = new ResourceLocation(EMCCFinals.MOD_ID, "textures/gui/condenser.png");
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		tile = c.tile;
		player = c.player;
		ySize = 222;
		
		buttonSafe = new TinyButton(this, 152, 8, 8, 8);
		barEMC = new TinyButton(this, 30, 9, 118, 16);
		buttonRedstone = new TinyButton(this, 152, 18, 8, 8);
		buttonSecurity = new TinyButton(this, 162, 8, 8, 18);
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		super.mouseClicked(x, y, b);
		
		if(buttonSafe.isAt(x - guiLeft, y - guiTop))
		tile.toggleSafeMode(false);
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		tile.toggleRedstoneMode(false);
		
		if(barEMC.isAt(x - guiLeft, y - guiTop) && isShiftKeyDown())
		tile.clearBuffer(false);
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		tile.toggleSecurity(false);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		double l = EMC.getEMC(tile.items[TileCondenser.UP_SLOT]);
		
		if(l > 0L)
		barEMC.render(guiLeft, guiTop, 0, ySize, (tile.storedEMC % l) / l);
		
		if(tile.safeMode)
		buttonSafe.render(guiLeft, guiTop, xSize, 0, 1F);
		
		if(tile.redstoneMode > 0)
		buttonRedstone.render(guiLeft, guiTop, xSize, tile.redstoneMode * 9, 1F);
		
		if(tile.security.level > 0)
		buttonSecurity.render(guiLeft, guiTop, xSize + 9, (tile.security.level - 1) * 19, 1F);
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.isAt(x - guiLeft, y - guiTop))
		{
			float l = EMC.getEMC(tile.items[TileCondenser.UP_SLOT]);
			double storedEMC = (long)(tile.storedEMC * 100D) / 100D;
			
			if(tile.storedEMC == Double.POSITIVE_INFINITY)
			al.add(EnumChatFormatting.LIGHT_PURPLE.toString() + "Infinity");
			else al.add(EnumChatFormatting.GOLD.toString() + ((l == 0L) ? (storedEMC + "") : (storedEMC + " / " + l)));
			
			if(isShiftKeyDown())
			{
				al.add(EnumChatFormatting.RED + "Empty EMC buffer");
			}
		}
		
		if(buttonSafe.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Safe Mode");
			
			if(tile.safeMode) al.add("Enabled");
			else al.add("Disabled");
		}
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Redstone Control");
			
			if(tile.redstoneMode == 0) al.add("Disabled");
			else if(tile.redstoneMode == 1) al.add("High Signal Required");
			else if(tile.redstoneMode == 2) al.add("Low Signal Requird");
		}
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Security");
			
			if(tile.security.level == LMSecurity.PUBLIC) al.add("Public");
			else if(tile.security.level == LMSecurity.PRIVATE) al.add("Private");
			else if(tile.security.level == LMSecurity.RESTRICTED)
			{
				al.add("Restricted");
				
				if(GuiScreen.isShiftKeyDown() && !tile.security.friends.isEmpty())
				{
					al.add("> Friends:");
					
					for(int i = 0; i < tile.security.friends.size(); i++)
					al.add(tile.security.friends.get(i));
				}
			}
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}