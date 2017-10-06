package com.latmod.emc_condenser.client.gui;

import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.feed_the_beast.ftbl.lib.gui.Button;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.gui.GuiIcons;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.feed_the_beast.ftbl.lib.gui.Widget;
import com.feed_the_beast.ftbl.lib.icon.Icon;
import com.feed_the_beast.ftbl.lib.util.misc.EnumIO;
import com.feed_the_beast.ftbl.lib.util.misc.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.util.misc.EnumRedstoneMode;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.latmod.emc_condenser.EMCC;
import com.latmod.emc_condenser.EMCCLang;
import com.latmod.emc_condenser.block.TileCondenser;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiBase
{
	public static final Icon TEXTURE = Icon.getIcon(EMCC.MOD_ID + ":textures/gui/condenser.png");
	public static final Icon TEX_BACKGROUND = TEXTURE.withUVfromCoords(0, 0, 176, 236, 256, 256);
	public static final Icon TEX_BAR = TEXTURE.withUVfromCoords(0, 236, 118, 16, 256, 256);
	public static final Icon TEX_TARGET = TEXTURE.withUVfromCoords(176, 0, 16, 16, 256, 256);
	public static final Icon TEX_SIDEBAR = TEXTURE.withUVfromCoords(176, 26, 25, 83, 256, 256);

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
			public void onClicked(GuiBase gui, MouseButton button)
			{
				GuiHelper.playClickSound();
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, TileCondenser.BUTTON_TRANSFER_ITEMS);
			}
		};

		buttonTransItems.setTitle(EMCCLang.TAKEITEMS.translate());
		buttonTransItems.setIcon(GuiIcons.DOWN);

		buttonSecurity = new Button(-19, 32, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, MouseButton button)
			{
				GuiHelper.playClickSound();
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? TileCondenser.BUTTON_SECURITY_LEFT : TileCondenser.BUTTON_SECURITY_RIGHT);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add(container.condenser.security.getValue().getLangKey().translate());
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				return container.condenser.security.getValue().getIcon();
			}
		};

		buttonSecurity.setTitle(EnumPrivacyLevel.ENUM_LANG_KEY.translate());

		buttonRedstone = new Button(-19, 50, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, MouseButton button)
			{
				GuiHelper.playClickSound();
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? TileCondenser.BUTTON_REDSTONE_MODE_LEFT : TileCondenser.BUTTON_REDSTONE_MODE_RIGHT);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add(container.condenser.redstone_mode.getValue().getLangKey().translate());
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				return container.condenser.redstone_mode.getValue().getIcon();
			}
		};

		buttonRedstone.setTitle(EnumRedstoneMode.ENUM_LANG_KEY.translate());

		buttonInvMode = new Button(-19, 68, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, MouseButton button)
			{
				GuiHelper.playClickSound();
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? TileCondenser.BUTTON_INVENTORY_MODE_LEFT : TileCondenser.BUTTON_INVENTORY_MODE_RIGHT);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add(container.condenser.inv_mode.getValue().getLangKey().translate());
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				return container.condenser.inv_mode.getValue().getIcon();
			}
		};

		buttonInvMode.setTitle(EnumIO.ENUM_LANG_KEY.translate());

		buttonSafeMode = new Button(-19, 86, 16, 16)
		{
			@Override
			public void onClicked(GuiBase gui, MouseButton button)
			{
				GuiHelper.playClickSound();
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, TileCondenser.BUTTON_SAFE_MODE);
			}

			@Override
			public void addMouseOverText(GuiBase gui, List<String> list)
			{
				list.add(getTitle(gui));
				list.add((container.condenser.safe_mode.getBoolean() ? GuiLang.ENABLED : GuiLang.DISABLED).translate());
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				return container.condenser.safe_mode.getBoolean() ? GuiIcons.ACCEPT : GuiIcons.ACCEPT_GRAY;
			}
		};

		buttonSafeMode.setTitle(EMCCLang.SAFEMODE.translate());

		barEMC = new Widget(30, 9, 118, 16)
		{
			@Override
			public void addMouseOverText(GuiBase gui, List<String> l)
			{
				ItemStack tar = container.condenser.target.getStackInSlot(0);
				int emc1 = EMCValues.getEMC(tar).value;
				l.add(TextFormatting.GOLD.toString() + "" + formatEMC(container.condenser.storedEMC) + (emc1 <= 0 ? "" : (" / " + formatEMC(emc1))));
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				int emc1 = EMCValues.getEMC(container.condenser.target.getStackInSlot(0)).value;

				if (emc1 > 0)
				{
					int w = (container.condenser.storedEMC % emc1) / emc1 * width;

					if (w > 0)
					{
						return TEX_BAR.withUVfromCoords(0, 0, w, height, width, height);
					}
				}

				return Icon.EMPTY;
			}
		};

		targetIcon = new Widget(8, 9, 16, 16)
		{
			@Override
			public String getTitle(GuiBase gui)
			{
				return (!container.condenser.target.getStackInSlot(0).isEmpty() || !container.player.inventory.getItemStack().isEmpty()) ? "" : EMCCLang.NOTARGET.translate();
			}

			@Override
			public Icon getIcon(GuiBase gui)
			{
				return !container.condenser.target.getStackInSlot(0).isEmpty() ? Icon.EMPTY : TEX_TARGET;
			}
		};

		sidebar = new Widget(-25, 26, 25, 83)
		{
			@Override
			public Icon getIcon(GuiBase gui)
			{
				return TEX_SIDEBAR;
			}
		};
	}

	@Override
	public void addWidgets()
	{
		addAll(buttonTransItems, buttonSecurity, buttonRedstone, buttonInvMode, buttonSafeMode, barEMC, targetIcon, sidebar);
	}

	public static String formatEMC(int emc)
	{
		if (emc == Integer.MAX_VALUE)
		{
			return TextFormatting.OBFUSCATED + "000000";
		}

		double d = ((long) (emc * 1000D)) / 1000D;

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
	public Icon getIcon(GuiBase gui)
	{
		return TEX_BACKGROUND;
	}

	@Override
	public GuiScreen getWrapper()
	{
		return new GuiContainerWrapper(this, container);
	}
}