package com.latmod.emc_condenser;

import com.feed_the_beast.ftblib.events.RegisterSyncDataEvent;
import com.feed_the_beast.ftblib.events.ServerReloadEvent;
import com.feed_the_beast.ftblib.events.player.RegisterContainerProvidersEvent;
import com.feed_the_beast.ftblib.lib.EventHandler;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ISyncData;
import com.feed_the_beast.ftblib.lib.util.JsonUtils;
import com.latmod.emc_condenser.block.TileConstructor;
import com.latmod.emc_condenser.block.TileDestructor;
import com.latmod.emc_condenser.gui.ContainerConstructor;
import com.latmod.emc_condenser.gui.ContainerDestructor;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventHandler
public class EMCCEventHandler
{
	private static final ResourceLocation RELOAD_CONFIG = new ResourceLocation(EMCC.MOD_ID, "config");
	private static final ResourceLocation RELOAD_EMC = new ResourceLocation(EMCC.MOD_ID, "emc");

	@SubscribeEvent
	public static void registerReloadIds(ServerReloadEvent.RegisterIds event)
	{
		event.register(RELOAD_CONFIG);
		event.register(RELOAD_EMC);
	}

	@SubscribeEvent
	public static void onServerReload(ServerReloadEvent event)
	{
		if (event.reload(RELOAD_CONFIG))
		{
			EMCCConfig.sync();
		}

		if (event.reload(RELOAD_EMC) && !EMCValues.load())
		{
			event.failedToReload(RELOAD_EMC);
		}
	}

	@SubscribeEvent
	public static void registerSyncData(RegisterSyncDataEvent event)
	{
		event.register(EMCC.MOD_ID, new ISyncData()
		{
			@Override
			public NBTTagCompound writeSyncData(EntityPlayerMP player, ForgePlayer forgePlayer)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("EMCValues", JsonUtils.toJson(EMCValues.json));
				nbt.setInteger("C_Speed", EMCCConfig.constructor.speed);
				nbt.setInteger("D_Cooldown", EMCCConfig.destructor.cooldown);
				nbt.setInteger("D_MaxEMC", EMCCConfig.destructor.max_emc);
				return nbt;
			}

			@Override
			public void readSyncData(NBTTagCompound nbt)
			{
				EMCValues.load(JsonUtils.fromJson(nbt.getString("EMCValues")).getAsJsonObject());
				EMCCConfig.constructor.speed = nbt.getInteger("C_Speed");
				EMCCConfig.destructor.cooldown = nbt.getInteger("D_Cooldown");
				EMCCConfig.destructor.max_emc = nbt.getInteger("D_MaxEMC");
			}
		});
	}

	@SubscribeEvent
	public static void registerContainers(RegisterContainerProvidersEvent event)
	{
		event.register(ContainerConstructor.ID, ((player, pos, nbt) -> new ContainerConstructor(player, (TileConstructor) player.world.getTileEntity(pos))));
		event.register(ContainerDestructor.ID, (player, pos, data) -> new ContainerDestructor(player, (TileDestructor) player.world.getTileEntity(pos)));
	}
}