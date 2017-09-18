package com.latmod.emc_condenser;


import com.feed_the_beast.ftbl.lib.LangKey;

/**
 * @author LatvianModder
 */
public interface EMCCLang
{
	LangKey NOTARGET = LangKey.of("lang.emc_condenser.notarget");
	LangKey EMC = LangKey.of("lang.emc_condenser.emc", String.class);
	LangKey TOTAL_EMC = LangKey.of("lang.emc_condenser.total_emc", String.class);
	LangKey SAFEMODE = LangKey.of("lang.emc_condenser.safemode");
	LangKey TAKEITEMS = LangKey.of("lang.emc_condenser.takeitems");
}