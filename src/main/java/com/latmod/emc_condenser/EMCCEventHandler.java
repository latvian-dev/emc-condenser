package com.latmod.emc_condenser;

import com.feed_the_beast.ftbl.api.EventHandler;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.api.ISyncData;
import com.feed_the_beast.ftbl.api.RegisterSyncDataEvent;
import com.feed_the_beast.ftbl.api.ServerReloadEvent;
import com.feed_the_beast.ftbl.api.player.RegisterContainerProvidersEvent;
import com.feed_the_beast.ftbl.lib.util.JsonUtils;
import com.latmod.emc_condenser.block.TileCondenser;
import com.latmod.emc_condenser.client.gui.ContainerCondenser;
import com.latmod.emc_condenser.util.Blacklist;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventHandler
public class EMCCEventHandler
{
	private static final ResourceLocation RELOAD_CONFIG = new ResourceLocation(EMCC.MOD_ID, "config");
	private static final ResourceLocation RELOAD_BLACKLIST = new ResourceLocation(EMCC.MOD_ID, "blacklist");
	private static final ResourceLocation RELOAD_EMC = new ResourceLocation(EMCC.MOD_ID, "emc");

	@SubscribeEvent
	public static void registerReloadIds(ServerReloadEvent.RegisterIds event)
	{
		event.register(RELOAD_CONFIG);
		event.register(RELOAD_BLACKLIST);
		event.register(RELOAD_EMC);
	}

	@SubscribeEvent
	public static void onServerReload(ServerReloadEvent event)
	{
		if (event.reload(RELOAD_CONFIG))
		{
			EMCCConfig.sync();
		}

		if (event.reload(RELOAD_BLACKLIST) && !Blacklist.load())
		{
			event.failedToReload(RELOAD_BLACKLIST);
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
			public NBTTagCompound writeSyncData(EntityPlayerMP player, IForgePlayer forgePlayer)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Blacklist", JsonUtils.toJson(Blacklist.json));
				nbt.setString("EMCValues", JsonUtils.toJson(EMCValues.json));
				return nbt;
			}

			@Override
			public void readSyncData(NBTTagCompound nbt)
			{
				Blacklist.load(JsonUtils.fromJson(nbt.getString("Blacklist")).getAsJsonObject());
				EMCValues.load(JsonUtils.fromJson(nbt.getString("EMCValues")).getAsJsonArray());
			}
		});
	}

	/*
	@SubscribeEvent
	public static void onAnvilEvent(AnvilUpdateEvent event)
	{
		if (event.getLeft().getItem() instanceof IEmcTool && !event.getRight().isEmpty())
		{
			IEmcTool tool = (IEmcTool) event.getLeft().getItem();
			ToolInfusion t = ToolInfusion.get(event.getRight());

			if (t != null && tool.canEnchantWith(event.getLeft(), t))
			{
				int l = tool.getInfusionLevel(event.getLeft(), t);
				int lvlsToAdd = Math.min(event.getRight().getCount(), t.maxLevel - l);

				if (lvlsToAdd > 0)
				{
					event.setMaterialCost(lvlsToAdd);
					event.setCost(t.requiredLevel.getAsInt() * lvlsToAdd);
					event.setOutput(event.getLeft().copy());
					tool.setInfusionLevel(event.getOutput(), t, l + lvlsToAdd);
				}
			}
		}
	}
	*/

	@SubscribeEvent
	public static void onPlayerDamaged(LivingAttackEvent event)
	{
		//TODO: Check if Life Ring can save them
	}

	@SubscribeEvent
	public static void registerContainers(RegisterContainerProvidersEvent event)
	{
		event.register(ContainerCondenser.ID, (player, pos, data) -> new ContainerCondenser(player, (TileCondenser) player.world.getTileEntity(pos)));
	}
}