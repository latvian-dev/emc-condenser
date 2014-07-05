package latmod.emcc.gui;
import java.util.*;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.*;
import latmod.core.base.gui.*;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonSafeMode, buttonTransItems;
	public WidgetLM barEMC, targetIcon;
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		condenser = (TileCondenser)c.tile;
		player = c.player;
		ySize = 240;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.openGui(false, player, EMCCGuis.COND_SETTINGS);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonSafeMode = new ButtonLM(this, 153, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, player, EnumCond.Buttons.SAFE_MODE);
				playSound("random.click", 1F);
			}
		});
		
		widgets.add(buttonTransItems = new ButtonLM(this, 162, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				condenser.transferItems(false, player);
				playSound("random.click", 1F);
			}
		});
		
		barEMC = new WidgetLM(this, 30, 9, 118, 16);
		targetIcon = new WidgetLM(this, 8, 9, 16, 16);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		double l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
		
		if(l > 0L)
		barEMC.render(0, ySize, (condenser.storedEMC % l) / l, 1D);
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
		targetIcon.render(xSize, 0);
		
		if(condenser.safeMode.isOn())
		buttonSafeMode.render(xSize, 16);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawGuiContainerForegroundLayer(int mx, int my)
	{
		super.drawGuiContainerForegroundLayer(mx, my);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.mouseOver(mx, my))
		{
			float l = EMCC.getEMC(condenser.items[TileCondenser.SLOT_TARGET]);
			double storedEMC = (long)(condenser.storedEMC * 1000D) / 1000D;
			al.add(EnumChatFormatting.GOLD.toString() + ((l == 0L) ? (storedEMC + "") : (storedEMC + " / " + l)));
		}
		
		if(buttonSettings.mouseOver(mx, my))
			al.add("Settings");
		
		if(buttonSafeMode.mouseOver(mx, my))
		{
			al.add("Safe Mode");
			al.add(condenser.safeMode.text);
		}
		
		if(buttonTransItems.mouseOver(mx, my))
			al.add("Take items");
		
		if(targetIcon.mouseOver(mx, my) && condenser.items[TileCondenser.SLOT_TARGET] == null)
			al.add("No Target");
		
		if(!al.isEmpty()) drawHoveringText(al, mx - guiLeft, my - guiTop, fontRenderer);
	}
}