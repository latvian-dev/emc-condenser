package latmod.emcc.config;

import ftb.lib.PrivacyLevel;
import ftb.lib.api.config.ConfigEntryEnum;
import ftb.lib.api.tile.InvMode;
import ftb.lib.api.tile.RedstoneMode;
import latmod.lib.util.EnumEnabled;

public class EMCCConfigForced
{
	public static final ConfigEntryEnum<InvMode> inv_mode = new ConfigEntryEnum<>("inv_mode", InvMode.VALUES, null, true);
	public static final ConfigEntryEnum<PrivacyLevel> security = new ConfigEntryEnum<>("security", PrivacyLevel.VALUES_3, null, true);
	public static final ConfigEntryEnum<RedstoneMode> redstone_control = new ConfigEntryEnum<>("redstone_control", RedstoneMode.VALUES, null, true);
	public static final ConfigEntryEnum<EnumEnabled> safe_mode = new ConfigEntryEnum<>("safe_mode", EnumEnabled.values(), null, true);
}