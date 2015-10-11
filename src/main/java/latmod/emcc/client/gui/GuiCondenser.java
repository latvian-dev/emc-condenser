package latmod.emcc.client.gui;
import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import latmod.emcc.emc.EMCHandler;
import latmod.emcc.tile.TileCondenser;
import latmod.ftbu.mod.FTBU;
import latmod.ftbu.util.client.LMGuiButtons;
import latmod.ftbu.util.gui.*;
import latmod.lib.FastList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public static final ResourceLocation texLoc = EMCC.mod.getLocation("textures/gui/condenser.png");
	public static final TextureCoords texBar = new TextureCoords(texLoc, 0, 236, 118, 16);
	public static final TextureCoords texTarget = new TextureCoords(texLoc, 176, 0, 16, 16);
	public static final TextureCoords texSidebar = new TextureCoords(texLoc, 176, 26, 25, 83);
	
	public final TileCondenser condenser;
	public final ButtonLM buttonTransItems, buttonSecurity, buttonRedstone, buttonInvMode, buttonSafeMode;
	public final WidgetLM barEMC, targetIcon, sidebar;
	private static String noTargetLang = "";
	
	public GuiCondenser(final ContainerCondenser c)
	{
		super(c, texLoc);
		condenser = (TileCondenser)c.inv;
		xSize = 176;
		ySize = 236;
		
		buttonTransItems = new ButtonLM(this, 153, 9, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				if(b == 0)
				{
					condenser.sendClientAction(TileCondenser.ACTION_TRANS_ITEMS, null);
					playClickSound();
				}
			}
		};
		
		buttonTransItems.title = EMCC.mod.translateClient("takeitems");
		
		buttonSecurity = new ButtonLM(this, -19, 32, 16, 16)
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
		};
		
		buttonRedstone = new ButtonLM(this, -19, 50, 16, 16)
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
		};
		
		buttonInvMode = new ButtonLM(this, -19, 68, 16, 16)
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
		};
		
		buttonSafeMode = new ButtonLM(this, -19, 86, 16, 16)
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
		};
		
		barEMC = new WidgetLM(this, 30, 9, texBar.width, texBar.height)
		{
			public void addMouseOverText(FastList<String> l)
			{
				ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
				double emc1 =  EMCHandler.instance().getEMC(tar);
				l.add(EnumChatFormatting.GOLD.toString() + "" + formatEMC(condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
			}
		};
		
		targetIcon = new WidgetLM(this, 8, 9, 16, 16);
		noTargetLang = EMCC.mod.translateClient("notarget");
		sidebar = new WidgetLM(this, -25, 26, texSidebar.width, texSidebar.height);
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonTransItems);
		mainPanel.add(buttonSecurity);
		mainPanel.add(buttonRedstone);
		mainPanel.add(buttonInvMode);
		mainPanel.add(buttonSafeMode);
		mainPanel.add(barEMC);
		mainPanel.add(targetIcon);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
		
		double emc1 =  EMCHandler.instance().getEMC(tar);
		
		if(emc1 > 0L)
		{
			setTexture(texLoc);
			double d = (condenser.storedEMC % emc1) / (double)emc1;
			drawTexturedRectD(guiLeft + barEMC.posX, guiTop + barEMC.posY, zLevel, texBar.width * d, texBar.height, texBar.minU, texBar.minV, texBar.minU + (texBar.maxU - texBar.minU) * d, texBar.maxV);
		}
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
			targetIcon.render(texTarget);
		
		buttonTransItems.render(GuiIcons.down);
		
		sidebar.render(texSidebar);
		
		buttonRedstone.render(GuiIcons.redstone[condenser.redstoneMode.ID]);
		buttonSecurity.render(GuiIcons.security[condenser.security.level.ID]);
		buttonInvMode.render(GuiIcons.inv[condenser.invMode.ID]);
		buttonSafeMode.render(condenser.safeMode.isOn() ? GuiIcons.accept : GuiIcons.accept_gray);
		
		targetIcon.title = (condenser.items[TileCondenser.SLOT_TARGET] == null) ? noTargetLang : null;
	}
	
	public static String formatEMC(double d)
	{
		if(d == Double.POSITIVE_INFINITY) return EnumChatFormatting.OBFUSCATED + "000000";
		
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