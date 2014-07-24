package latmod.emcc.gui;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import latmod.core.base.gui.*;
import latmod.emcc.net.*;

@SideOnly(Side.CLIENT)
public class PlayerButton extends ButtonLM
{
	public String playerName = null;
	public ResourceLocation texture;
	public ThreadDownloadImageData thread;
	
	public PlayerButton(GuiLM g, int x, int y, int w, int h)
	{
		super(g, x, y, w, h);
		setName(null);
	}
	
	public void setName(String s)
	{
		playerName = s;
		texture = AbstractClientPlayer.locationStevePng;
		
		if(s != null)
		{
			texture = AbstractClientPlayer.locationStevePng;
			texture = AbstractClientPlayer.getLocationSkin(playerName);
			AbstractClientPlayer.getDownloadImageSkin(texture, playerName);
		}
	}
	
	public void render(int ox, int oy, double w, double h)
	{
		ox += gui.getPosX();
		oy += gui.getPosY();
		
		if(playerName == null || playerName.length() == 0) return;
		
		gui.setTexture(texture);
		
		double z = gui.getZLevel();
		Tessellator tessellator = Tessellator.instance;
		
		double minU = 1D / 8D;
		double minV = 1D / 4D;
		double maxU = 2D / 8D;
		double maxV = 2D / 4D;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(ox + 0, oy + h, z, minU, maxV);
		tessellator.addVertexWithUV(ox + w, oy + h, z, maxU, maxV);
		tessellator.addVertexWithUV(ox + w, oy + 0, z, maxU, minV);
		tessellator.addVertexWithUV(ox + 0, oy + 0, z, minU, minV);
		tessellator.draw();
		
		double minU2 = 5D / 8D;
		double minV2 = 1D / 4D;
		double maxU2 = 6D / 8D;
		double maxV2 = 2D / 4D;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(ox + 0, oy + h, z, minU2, maxV2);
		tessellator.addVertexWithUV(ox + w, oy + h, z, maxU2, maxV2);
		tessellator.addVertexWithUV(ox + w, oy + 0, z, maxU2, minV2);
		tessellator.addVertexWithUV(ox + 0, oy + 0, z, minU2, minV2);
		tessellator.draw();
	}

	public void onButtonPressed(int b)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			EMCCNetHandler.INSTANCE.sendToServer(new MessageModifyRestricted(((GuiRestricted)gui).condenser, false, playerName));
			gui.playSoundFX("random.fizz", 1F);
		}
	}
}