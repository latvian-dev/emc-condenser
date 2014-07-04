package latmod.emcc.gui;
import latmod.core.base.GuiLM;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class TinyButton
{
	public final GuiLM gui;
	public final int posX, posY, width, height;
	
	public TinyButton(GuiLM g, int x, int y, int w, int h)
	{
		gui = g;
		posX = x;
		posY = y;
		width = w;
		height = h;
	}
	
	public boolean isAt(int x, int y)
	{ return x >= posX && y >= posY && x <= posX + width && y <= posY + height; }
	
	public void render(int ox, int oy, int tx, int ty, double rw)
	{ gui.drawTexturedModalRect(ox + posX, oy + posY, tx, ty, (int)(width * rw), height); }
}