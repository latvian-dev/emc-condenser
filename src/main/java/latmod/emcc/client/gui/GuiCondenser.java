package latmod.emcc.client.gui;
import latmod.emcc.*;
import latmod.emcc.api.IEmcStorageItem;
import latmod.emcc.client.container.ContainerCondenser;
import latmod.emcc.emc.EMCHandler;
import latmod.emcc.tile.TileCondenser;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.ftbu.mod.FTBU;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public static final ResourceLocation texLoc = EMCC.mod.getLocation("textures/gui/condenser.png");
	
	public final TextureCoords
	texBar = new TextureCoords(texLoc, 0, 236, 118, 16),
	texTarget = new TextureCoords(texLoc, 176, 0, 16, 16),
	texSidebar = new TextureCoords(texLoc, 176, 26, 25, 83);
	
	public TileCondenser condenser;
	public ButtonLM buttonTransItems, buttonSecurity, buttonRedstone, buttonInvMode, buttonSafeMode;
	public WidgetLM barEMC, targetIcon, sidebar;
	
	public GuiCondenser(final ContainerCondenser c)
	{
		super(c, texLoc);
		condenser = (TileCondenser)c.inv;
		xSize = 176;
		ySize = 236;
		
		widgets.add(buttonTransItems = new ButtonLM(this, 153, 9, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				if(b == 0)
				{
					condenser.sendClientAction(TileCondenser.ACTION_TRANS_ITEMS, null);
					playClickSound();
				}
			}
		});
		
		buttonTransItems.title = EMCC.mod.translate("takeitems");
		
		widgets.add(buttonSecurity = new ButtonLM(this, -19, 32, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.SECURITY, b);
				playClickSound();
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(condenser.security.level.getTitle());
				l.add(condenser.security.level.getText());
			}
		});
		
		widgets.add(buttonRedstone = new ButtonLM(this, -19, 50, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.REDSTONE, b);
				playClickSound();
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(condenser.redstoneMode.getTitle());
				l.add(condenser.redstoneMode.getText());
			}
		});
		
		widgets.add(buttonInvMode = new ButtonLM(this, -19, 68, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(LMGuiButtons.INV_MODE, b);
				playClickSound();
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(condenser.invMode.getTitle());
				l.add(condenser.invMode.getText());
			}
		});
		
		widgets.add(buttonSafeMode = new ButtonLM(this, -19, 86, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(EMCCGuis.Buttons.SAFE_MODE, b);
				playClickSound();
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(condenser.safeMode.getTitle());
				l.add(condenser.safeMode.getText());
			}
		});
		
		widgets.add(barEMC = new WidgetLM(this, 30, 9, texBar.width, texBar.height)
		{
			public void addMouseOverText(FastList<String> l)
			{
				ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
				
				double emc1 =  EMCHandler.instance().getEMC(tar);
				
				boolean charging = tar != null && tar.getItem() instanceof IEmcStorageItem;
				
				l.add(EnumChatFormatting.GOLD.toString() + "" + formatEMC(condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
				if(charging && condenser.storedEMC > 0D) l.add(EMCC.mod.translate("charging"));
			}
		});
		
		widgets.add(targetIcon = new WidgetLM(this, 8, 9, 16, 16));
		targetIcon.title = EMCC.mod.translate("notarget");
		sidebar = new WidgetLM(this, -25, 26, texSidebar.width, texSidebar.height);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
		
		double emc1 =  EMCHandler.instance().getEMC(tar);
		
		if(emc1 > 0L)
			barEMC.render(texBar, (condenser.storedEMC % emc1) / emc1, 1D);
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
			targetIcon.render(texTarget);
		
		buttonTransItems.render(Icons.down);
		
		sidebar.render(texSidebar);
		
		buttonRedstone.render(Icons.redstone[condenser.redstoneMode.ID]);
		buttonSecurity.render(Icons.security[condenser.security.level.ID]);
		buttonInvMode.render(Icons.inv[condenser.invMode.ID]);
		buttonSafeMode.render(condenser.safeMode.isOn() ? Icons.accept : Icons.accept_gray);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		String s = targetIcon.title + "";
		targetIcon.title = (condenser.items[TileCondenser.SLOT_TARGET] == null) ? s : null;
		
		super.drawScreen(mx, my, f);
		
		targetIcon.title = s;
	}
	
	public static String formatEMC(double d)
	{
		d = ((long)(d * 1000D)) / 1000D;
		
		String s = "" + d;
		
		if(!FTBU.proxy.isShiftDown())
		{
			if(d > 1000)
			{
				double d1 = d / 1000D;
				d1 = ((long)(d1 * 1000D)) / 1000D;
				s = "" + d1 + "K";
			}
			
			if(d > 1000000)
			{
				double d1 = d / 1000000D;
				d1 = ((long)(d1 * 100D)) / 100D;
				s = "" + d1 + "M";
			}
		}
		
		if(s.endsWith(".0"))
			s = s.substring(0, s.length() - 2);
		
		return s;
	}
}