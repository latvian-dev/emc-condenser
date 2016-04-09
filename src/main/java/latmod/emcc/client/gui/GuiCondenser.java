package latmod.emcc.client.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.FTBLibLang;
import ftb.lib.api.client.FTBLibClient;
import ftb.lib.api.gui.*;
import ftb.lib.api.gui.widgets.*;
import ftb.lib.api.tile.*;
import ftb.lib.mod.FTBLibMod;
import latmod.emcc.*;
import latmod.emcc.block.TileCondenser;
import latmod.emcc.emc.EMCHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiContainerLM
{
	public static final ResourceLocation texLoc = new ResourceLocation("emcc", "textures/gui/condenser.png");
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
		condenser = (TileCondenser) c.inv;
		xSize = 176;
		ySize = 236;
		
		buttonTransItems = new ButtonLM(this, 153, 9, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				if(b == 0)
				{
					condenser.sendClientAction(TileCondenser.ACTION_TRANS_ITEMS, null);
					FTBLibClient.playClickSound();
				}
			}
		};
		
		buttonTransItems.title = EMCC.mod.translate("takeitems");
		
		buttonSecurity = new ButtonLM(this, -19, 32, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton("security", b);
				FTBLibClient.playClickSound();
			}
			
			public void addMouseOverText(List<String> l)
			{
				l.add(I18n.format(PrivacyLevel.enumLangKey));
				l.add(condenser.security.level.lang.format());
			}
		};
		
		buttonRedstone = new ButtonLM(this, -19, 50, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton("redstone", b);
				FTBLibClient.playClickSound();
			}
			
			public void addMouseOverText(List<String> l)
			{
				l.add(I18n.format(RedstoneMode.enumLangKey));
				l.add(condenser.redstone_mode.get().lang.format());
			}
		};
		
		buttonInvMode = new ButtonLM(this, -19, 68, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton("inv_mode", b);
				FTBLibClient.playClickSound();
			}
			
			public void addMouseOverText(List<String> l)
			{
				l.add(I18n.format(InvMode.enumLangKey));
				l.add(condenser.inv_mode.get().lang.format());
			}
		};
		
		buttonSafeMode = new ButtonLM(this, -19, 86, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.clientPressButton(EMCCGuis.Buttons.SAFE_MODE, b);
				FTBLibClient.playClickSound();
			}
			
			public void addMouseOverText(List<String> l)
			{
				l.add(TileCondenser.safeModeLang.format());
				l.add(condenser.safe_mode.getAsBoolean() ? FTBLibLang.label_enabled.format() : FTBLibLang.label_disabled.format());
			}
		};
		
		barEMC = new WidgetLM(this, 30, 9, texBar.widthI(), texBar.heightI())
		{
			public void addMouseOverText(List<String> l)
			{
				ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
				double emc1 = EMCHandler.instance().getEMC(tar);
				l.add(EnumChatFormatting.GOLD.toString() + "" + formatEMC(condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
			}
		};
		
		targetIcon = new WidgetLM(this, 8, 9, 16, 16);
		noTargetLang = EMCC.mod.translate("notarget");
		sidebar = new WidgetLM(this, -25, 26, texSidebar.widthI(), texSidebar.heightI());
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
		mainPanel.add(sidebar);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
		
		double emc1 = EMCHandler.instance().getEMC(tar);
		
		if(emc1 > 0L)
		{
			FTBLibClient.setTexture(texLoc);
			double d = (condenser.storedEMC % emc1) / (double) emc1;
			GuiLM.drawTexturedRectD(guiLeft + barEMC.posX, guiTop + barEMC.posY, zLevel, texBar.width * d, texBar.height, texBar.minU, texBar.minV, texBar.minU + (texBar.maxU - texBar.minU) * d, texBar.maxV);
		}
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null) targetIcon.render(texTarget);
		
		buttonTransItems.render(GuiIcons.down);
		
		sidebar.render(texSidebar);
		
		buttonRedstone.render(GuiIcons.redstone[condenser.redstone_mode.get().ID]);
		buttonSecurity.render(condenser.security.level.getIcon());
		buttonInvMode.render(GuiIcons.inv[condenser.inv_mode.get().ID]);
		buttonSafeMode.render(condenser.safe_mode.getAsBoolean() ? GuiIcons.accept : GuiIcons.accept_gray);
		
		targetIcon.title = (condenser.items[TileCondenser.SLOT_TARGET] == null) ? noTargetLang : null;
	}
	
	public static String formatEMC(double d)
	{
		if(d == Double.POSITIVE_INFINITY) return EnumChatFormatting.OBFUSCATED + "000000";
		
		d = ((long) (d * 1000D)) / 1000D;
		
		String s = "" + d;
		
		if(!FTBLibMod.proxy.isShiftDown())
		{
			if(d > 1000)
			{
				double d1 = d / 1000D;
				d1 = ((long) (d1 * 1000D)) / 1000D;
				s = "" + d1 + "K";
			}
			
			if(d > 1000000)
			{
				double d1 = d / 1000000D;
				d1 = ((long) (d1 * 100D)) / 100D;
				s = "" + d1 + "M";
			}
		}
		
		if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
		
		return s;
	}
}