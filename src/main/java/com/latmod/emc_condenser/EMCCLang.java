package com.latmod.emc_condenser;

import com.feed_the_beast.ftblib.lib.util.LangKey;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;

/**
 * @author LatvianModder
 */
public interface EMCCLang
{
	DecimalFormat FORMATTER = new DecimalFormat("###,###,###,###");
	LangKey NOTARGET = LangKey.of("lang.emc_condenser.notarget");
	LangKey EMC = LangKey.of("lang.emc_condenser.emc", String.class);
	LangKey TOTAL_EMC = LangKey.of("lang.emc_condenser.total_emc", String.class, String.class);
	LangKey CEMC = LangKey.of("lang.emc_condenser.construction_emc", String.class);
	LangKey CTOTAL_EMC = LangKey.of("lang.emc_condenser.total_construction_emc", String.class, String.class);
	LangKey DEMC = LangKey.of("lang.emc_condenser.destruction_emc", String.class);
	LangKey DTOTAL_EMC = LangKey.of("lang.emc_condenser.total_destruction_emc", String.class, String.class);
	LangKey SAFEMODE = LangKey.of("lang.emc_condenser.safemode");
	LangKey TAKEITEMS = LangKey.of("lang.emc_condenser.takeitems");

	static String format(long num, TextFormatting color)
	{
		return color + FORMATTER.format(num) + TextFormatting.RESET;
	}
}