package mods.lm_emc_cond.gui;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import mods.lm_core.mod.*;
import mods.lm_emc_cond.*;
import mods.lm_emc_cond.ev.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiContainer
{
	public TileCondenser tile;
	public EntityPlayer player;
	
	public TinyButton buttonSafe, barEMC, buttonRedstone;
	
	public static final ResourceLocation texture = new ResourceLocation(AlchemyFinals.MOD_ID, "textures/gui/condenser.png");
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		tile = c.tile;
		player = c.player;
		ySize = 222;
		
		buttonSafe = new TinyButton(this, 0, 30, 8, 8, 8);
		barEMC = new TinyButton(this, 1, 44, 9, 124, 16);
		buttonRedstone = new TinyButton(this, 2, 30, 18, 8, 8);
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		super.mouseClicked(x, y, b);
		
		if(buttonSafe.mouseOver(x - guiLeft, y - guiTop))
		tile.toggleSafeMode(false);
		
		if(buttonRedstone.mouseOver(x - guiLeft, y - guiTop))
		tile.toggleRedstoneMode(false);
		
		if(barEMC.mouseOver(x - guiLeft, y - guiTop) && isShiftKeyDown())
		{
			int pid = PlayerID.inst.get(player);
			if(pid == tile.security.owner) tile.clearBuffer(false);
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		double l = EnergyValues.inst.getValue(tile.items[TileCondenser.UP_SLOT]);
		
		if(l > 0L)
		{
			barEMC.render(guiLeft, guiTop, 0, ySize, (tile.storedEMC % l) / l);
			//drawTexturedModalRect(guiLeft + barX, guiTop + barY, 0, ySize, siz, barH);
		}
		
		if(tile.safeMode)
		{
			buttonSafe.render(guiLeft, guiTop, xSize, 0, 1F);
		}
		
		if(tile.redstoneMode > 0)
		{
			buttonSafe.render(guiLeft, guiTop, xSize, tile.redstoneMode * 9, 1F);
		}
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.mouseOver(x - guiLeft, y - guiTop))
		{
			if(isShiftKeyDown())
			{
				al.add(EnumChatFormatting.RED + "Empty EMC buffer");
				int pid = PlayerID.inst.get(player);
				
				if(pid == tile.security.owner)
				al.add(EnumChatFormatting.GREEN + "Only owner can empty the buffer");
				else al.add(EnumChatFormatting.RED + "You are not the owner!");
			}
			else
			{
				float l = EnergyValues.inst.getValue(tile.items[TileCondenser.UP_SLOT]);
				double storedEMC = (long)(tile.storedEMC * 100D) / 100D;
				
				if(tile.storedEMC == Double.POSITIVE_INFINITY)
				al.add(EnumChatFormatting.LIGHT_PURPLE.toString() + "Infinity");
				else al.add(EnumChatFormatting.GOLD.toString() + ((l == 0L) ? (storedEMC + "") : (storedEMC + " / " + l)));
			}
		}
		
		if(buttonSafe.mouseOver(x - guiLeft, y - guiTop))
		{
			if(tile.safeMode) al.add("Safe Mode");
			else al.add("Direct Mode");
		}
		
		if(buttonRedstone.mouseOver(x - guiLeft, y - guiTop))
		{
			if(tile.redstoneMode == 0) al.add("Redstone Disabled");
			else if(tile.redstoneMode == 1) al.add("High Signal Required");
			else if(tile.redstoneMode == 2) al.add("Low Signal Requird");
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}