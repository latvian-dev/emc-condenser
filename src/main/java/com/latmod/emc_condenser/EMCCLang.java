package com.latmod.emc_condenser;


import com.feed_the_beast.ftbl.lib.util.LangKey;

/**
 * @author LatvianModder
 */
public interface EMCCLang
{
	LangKey NOTARGET = LangKey.of("lang.emc_condenser.notarget");
	LangKey EMC = LangKey.of("lang.emc_condenser.emc", String.class);
	LangKey TOTAL_EMC = LangKey.of("lang.emc_condenser.total_emc", String.class, String.class);
	LangKey CEMC = LangKey.of("lang.emc_condenser.construction_emc", String.class);
	LangKey CTOTAL_EMC = LangKey.of("lang.emc_condenser.total_construction_emc", String.class, String.class);
	LangKey DEMC = LangKey.of("lang.emc_condenser.destruction_emc", String.class);
	LangKey DTOTAL_EMC = LangKey.of("lang.emc_condenser.total_destruction_emc", String.class, String.class);
	LangKey SAFEMODE = LangKey.of("lang.emc_condenser.safemode");
	LangKey TAKEITEMS = LangKey.of("lang.emc_condenser.takeitems");
}