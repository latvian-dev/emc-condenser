package mods.lm_emc_cond.gui;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class TinyButton
{
	public final GuiCondenser gui;
	public final int buttonID, posX, posY, width, height;
	
	public TinyButton(GuiCondenser g, int i, int x, int y, int w, int h)
	{
		gui = g;
		buttonID = i;
		posX = x;
		posY = y;
		width = w;
		height = h;
	}
	
	public boolean mouseOver(int x, int y)
	{ return x >= posX && y >= posY && x <= posX + width && y <= posY + height; }
	
	public void render(int ox, int oy, int tx, int ty, double rw)
	{ gui.drawTexturedModalRect(ox + posX, oy + posY, tx, ty, (int)(width * rw), height); }
}