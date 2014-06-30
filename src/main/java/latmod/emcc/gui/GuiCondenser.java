package latmod.emcc.gui;
import java.util.*;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.base.GuiLM;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public TileCondenser condenser;
	public TinyButton buttonSafe, barEMC, buttonRedstone, buttonSecurity;
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		condenser = (TileCondenser)c.tile;
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
		{
			condenser.toggleSafeMode(false);
			mc.sndManager.playSoundFX("random.click", 1F, 1F);
		}
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		{
			condenser.toggleRedstoneMode(false);
			mc.sndManager.playSoundFX("random.click", 1F, 1F);
		}
		
		if(barEMC.isAt(x - guiLeft, y - guiTop) && isShiftKeyDown())
		{
			condenser.clearBuffer(false);
			mc.sndManager.playSoundFX("random.fizz", 1F, 1F);
		}
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		{
			condenser.toggleSecurity(false, player);
			mc.sndManager.playSoundFX("random.click", 1F, 1F);
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		double l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
		
		if(l > 0L)
		barEMC.render(guiLeft, guiTop, 0, ySize, (condenser.storedEMC % l) / l);
		
		if(condenser.safeMode)
		buttonSafe.render(guiLeft, guiTop, xSize, 0, 1F);
		
		if(condenser.redstoneMode > 0)
		buttonRedstone.render(guiLeft, guiTop, xSize, condenser.redstoneMode * 9, 1F);
		
		if(condenser.security.level > 0)
		buttonSecurity.render(guiLeft, guiTop, xSize + 9, (condenser.security.level - 1) * 19, 1F);
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		super.drawGuiContainerForegroundLayer(x, y);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.isAt(x - guiLeft, y - guiTop))
		{
			float l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
			double storedEMC = (long)(condenser.storedEMC * 100D) / 100D;
			
			if(condenser.storedEMC == Double.POSITIVE_INFINITY)
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
			
			if(condenser.safeMode) al.add("Enabled");
			else al.add("Disabled");
		}
		
		if(buttonRedstone.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Redstone Control");
			
			if(condenser.redstoneMode == 0) al.add("Disabled");
			else if(condenser.redstoneMode == 1) al.add("High Signal Required");
			else if(condenser.redstoneMode == 2) al.add("Low Signal Requird");
		}
		
		if(buttonSecurity.isAt(x - guiLeft, y - guiTop))
		{
			al.add("Security");
			
			if(condenser.security.level == LMSecurity.PUBLIC) al.add("Public");
			else if(condenser.security.level == LMSecurity.PRIVATE) al.add("Private");
			else if(condenser.security.level == LMSecurity.WHITELIST) al.add("Restricted");
			al.add("Owner: " + condenser.security.owner);
			
			if(condenser.security.level == LMSecurity.WHITELIST && GuiScreen.isShiftKeyDown() && !condenser.security.restricted.isEmpty())
			{
				al.add(" ");
				al.add("> Friends:");
				
				for(int i = 0; i < condenser.security.restricted.size(); i++)
					al.add(condenser.security.restricted.get(i));
			}
		}
		
		if(!al.isEmpty()) drawHoveringText(al, x - guiLeft, y - guiTop, fontRenderer);
	}
}