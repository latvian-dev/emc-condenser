package com.latmod.emcc.client.gui;

import com.feed_the_beast.ftbl.api.gui.IDrawableObject;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.lib.EnumIO;
import com.feed_the_beast.ftbl.lib.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.EnumRedstoneMode;
import com.feed_the_beast.ftbl.lib.client.ImageProvider;
import com.feed_the_beast.ftbl.lib.gui.Button;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.gui.GuiIcons;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.feed_the_beast.ftbl.lib.gui.Widget;
import com.latmod.emcc.EMCC;
import com.latmod.emcc.EMCCLang;
import com.latmod.emcc.VanillaEMC;
import com.latmod.emcc.block.TileCondenser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiBase
{
	public static final IDrawableObject TEXTURE = ImageProvider.get(EMCC.MOD_ID + ":textures/gui/condenser.png");
	public static final IDrawableObject TEX_BAR = TEXTURE.withUVfromCoords(0, 236, 118, 16, 256, 256);
	public static final IDrawableObject TEX_TARGET = TEXTURE.withUVfromCoords(176, 0, 16, 16, 256, 256);
	public static final IDrawableObject TEX_SIDEBAR = TEXTURE.withUVfromCoords(176, 26, 25, 83, 256, 256);

	public final ContainerCondenser container;
	public final Button buttonTransItems, buttonSecurity, buttonRedstone, buttonInvMode, buttonSafeMode;
	public final Widget barEMC, targetIcon, sidebar;

	public GuiCondenser(ContainerCondenser c)
	{
		super(176, 236);
		container = c;

		buttonTransItems = new Button(153, 9, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, IMouseButton button)
			{
				GuiHelper.playClickSound();
				container.condenser.sendClientAction("trans_items", null);
			}
		};

		buttonTransItems.setTitle(EMCCLang.TAKEITEMS.translate());

		buttonSecurity = new Button(-19, 32, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, IMouseButton button)
			{
				GuiHelper.playClickSound();
				container.condenser.clientPressButton("security", button, null);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(title);
				list.add(container.condenser.security.getValue().getLangKey().translate());
			}

			@Override
			public IDrawableObject getIcon(GuiBase gui)
			{
				return container.condenser.security.getValue().getIcon();
			}
		};

		buttonSecurity.setTitle(EnumPrivacyLevel.ENUM_LANG_KEY.translate());

		buttonRedstone = new Button(-19, 50, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, IMouseButton button)
			{
				GuiHelper.playClickSound();
				container.condenser.clientPressButton("redstone", button, null);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add(container.condenser.redstone_mode.getValue().getLangKey().translate());
			}

			@Override
			public IDrawableObject getIcon(GuiBase gui)
			{
				return container.condenser.redstone_mode.getValue().getIcon();
			}
		};

		buttonRedstone.setTitle(EnumRedstoneMode.ENUM_LANG_KEY.translate());

		buttonInvMode = new Button(-19, 68, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, IMouseButton button)
			{
				GuiHelper.playClickSound();
				container.condenser.clientPressButton("inv_mode", button, null);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add(container.condenser.inv_mode.getValue().getLangKey().translate());
			}

			@Override
			public IDrawableObject getIcon(GuiBase gui)
			{
				return container.condenser.inv_mode.getValue().getIcon();
			}
		};

		buttonInvMode.title = EnumIO.ENUM_LANG_KEY.translate();

		buttonSafeMode = new Button(this, -19, 86, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, IMouseButton button)
			{
				GuiHelper.playClickSound();
				container.condenser.clientPressButton("safe_mode", button, null);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add((container.condenser.safe_mode.getAsBoolean() ? GuiLang.LABEL_ENABLED : GuiLang.LABEL_DISABLED).translate());
			}

			@Override
			public IDrawableObject getIcon(GuiBase gui)
			{
				return container.condenser.safe_mode.getBoolean() ? GuiIcons.ACCEPT : GuiIcons.ACCEPT_GRAY;
			}
		};

		buttonSafeMode.setTitle(EMCCLang.SAFEMODE.translate());

		barEMC = new Widget(this, 30, 9, 118, 16)
		{
			@Override
			public void addMouseOverText(List<String> l)
			{
				ItemStack tar = container.condenser.items[TileCondenser.SLOT_TARGET];
				double emc1 = VanillaEMC.INSTANCE.getEMC(tar);
				l.add(TextFormatting.GOLD.toString() + "" + formatEMC(container.condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
			}
		};

		targetIcon = new Widget(8, 9, 16, 16);
		sidebar = new Widget(-25, 26, 25, 83);
	}

	@Override
	public void addWidgets()
	{
		addAll(buttonTransItems, buttonSecurity, buttonRedstone, buttonInvMode, buttonSafeMode, barEMC, targetIcon, sidebar);
	}

	@Override
	public void drawBackground()
	{
		super.drawBackground();

		ItemStack tar = container.condenser.items[TileCondenser.SLOT_TARGET];

		double emc1 = VanillaEMC.INSTANCE.getEMC(tar);

		if (emc1 > 0L)
		{
			FTBLibClient.setTexture(TEXTURE);
			double d = (container.condenser.storedEMC % emc1) / emc1;
			GuiLM.drawTexturedRectD(guiLeft + barEMC.posX, guiTop + barEMC.posY, zLevel, TEX_BAR.width * d, TEX_BAR.height, TEX_BAR.minU, TEX_BAR.minV, TEX_BAR.minU + (TEX_BAR.maxU - TEX_BAR.minU) * d, TEX_BAR.maxV);
		}

		if (container.condenser.items[TileCondenser.SLOT_TARGET] == null)
		{
			targetIcon.render(TEX_TARGET);
		}

		buttonTransItems.render(GuiIcons.down);

		sidebar.render(TEX_SIDEBAR);

		targetIcon.title = (container.condenser.items[TileCondenser.SLOT_TARGET] == null) ? EMCCLang.lang_notarget.format() : null;
	}

	public static String formatEMC(double d)
	{
		if (d == Double.POSITIVE_INFINITY)
		{
			return TextFormatting.OBFUSCATED + "000000";
		}

		d = ((long) (d * 1000D)) / 1000D;

		String s = Double.toString(d);

		if (!GuiScreen.isShiftKeyDown())
		{
			if (d > 1000)
			{
				double d1 = d / 1000D;
				d1 = ((long) (d1 * 1000D)) / 1000D;
				s = Double.toString(d1) + 'K';
			}

			if (d > 1000000)
			{
				double d1 = d / 1000000D;
				d1 = ((long) (d1 * 100D)) / 100D;
				s = Double.toString(d1) + 'M';
			}
		}

		if (s.endsWith(".0"))
		{
			s = s.substring(0, s.length() - 2);
		}

		return s;
	}

	@Override
	public void getGuiWrapper()
	{
		return new GuiContainerWrapper(this, container);
	}
}