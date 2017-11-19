package com.latmod.emc_condenser.gui;

import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.feed_the_beast.ftbl.lib.gui.Button;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.Widget;
import com.feed_the_beast.ftbl.lib.icon.Icon;
import com.feed_the_beast.ftbl.lib.icon.ItemIcon;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.latmod.emc_condenser.EMCC;
import com.latmod.emc_condenser.EMCCLang;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class GuiConstructor extends GuiBase
{
	private static final Icon EMC_ICON = Icon.getIcon(EMCC.MOD_ID + ":blocks/emc_still");

	private class EMCWidget extends Widget
	{
		private final int index;

		public EMCWidget(GuiBase g, int x, int y)
		{
			super(g, x, y, 4, 4);
			index = emc.size();
		}

		@Override
		public Icon getIcon()
		{
			return gui.getTheme().getContainerSlot().combineWith(EMC_ICON);
		}
	}

	public final ContainerConstructor container;
	public final Widget target;
	public final List<EMCWidget> emc;

	public GuiConstructor(ContainerConstructor c)
	{
		super(176, 192);
		container = c;

		emc = new ArrayList<>(16);
		emc.add(new EMCWidget(this, 68, 33));
		emc.add(new EMCWidget(this, 77, 33));
		emc.add(new EMCWidget(this, 86, 33));
		emc.add(new EMCWidget(this, 95, 33));
		emc.add(new EMCWidget(this, 104, 33));
		emc.add(new EMCWidget(this, 104, 42));
		emc.add(new EMCWidget(this, 104, 51));
		emc.add(new EMCWidget(this, 104, 60));
		emc.add(new EMCWidget(this, 104, 69));
		emc.add(new EMCWidget(this, 95, 69));
		emc.add(new EMCWidget(this, 86, 69));
		emc.add(new EMCWidget(this, 77, 69));
		emc.add(new EMCWidget(this, 68, 69));
		emc.add(new EMCWidget(this, 68, 60));
		emc.add(new EMCWidget(this, 68, 51));
		emc.add(new EMCWidget(this, 68, 42));

		target = new Button(this, 80, 45, 16, 16, "", getTheme().getContainerSlot())
		{
			@Override
			public void addMouseOverText(List<String> list)
			{
				list.add(container.constructor.target.isEmpty() ? EMCCLang.NOTARGET.translate() : container.constructor.target.getDisplayName());
				list.add(EMCCLang.EMC.translate(EMCCLang.format(container.constructor.emc, TextFormatting.GOLD)));
			}

			@Override
			public Icon getIcon()
			{
				return super.getIcon().combineWith(ItemIcon.getItemIcon(container.constructor.target));
			}

			@Override
			public void onClicked(MouseButton button)
			{
				container.constructor.setTarget(container.player);
				ClientUtils.MC.playerController.sendEnchantPacket(container.windowId, 0);
			}
		};
	}

	@Override
	public void addWidgets()
	{
		addAll(target);
		addAll(emc);
	}

	@Override
	public GuiScreen getWrapper()
	{
		return new GuiContainerWrapper(this, container);
	}
}