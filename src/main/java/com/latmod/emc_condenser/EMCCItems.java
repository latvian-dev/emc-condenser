package com.latmod.emc_condenser;

import com.feed_the_beast.ftbl.lib.block.ItemBlockBase;
import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.latmod.emc_condenser.block.BlockCondenser;
import com.latmod.emc_condenser.block.BlockUUBlock;
import com.latmod.emc_condenser.block.TileCondenser;
import com.latmod.emc_condenser.item.ItemBlackHoleBand;
import com.latmod.emc_condenser.item.ItemEMCC;
import com.latmod.emc_condenser.item.ItemEmcBattery;
import com.latmod.emc_condenser.item.ItemLifeRing;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LatvianModder
 */
@GameRegistry.ObjectHolder(EMCC.MOD_ID)
@Mod.EventBusSubscriber(modid = EMCC.MOD_ID)
public class EMCCItems
{
	public static final Block UUS_BLOCK = Blocks.AIR;
	public static final Block CONDENSER = Blocks.AIR;

	public static final Item UUS_ITEM = Items.AIR;
	public static final Item UUS_INGOT = Items.AIR;
	public static final Item CRYSTAL_STAR = Items.AIR;

	public static final Item BATTERY = Items.AIR;
	public static final Item LIFE_RING = Items.AIR;
	public static final Item BLACK_HOLE_BAND = Items.AIR;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new BlockUUBlock("uus_block"),
				new BlockCondenser("condenser"));

		GameRegistry.registerTileEntity(TileCondenser.class, EMCC.MOD_ID + ":condenser");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlockBase(UUS_BLOCK),
				new ItemBlockBase(CONDENSER),

				new ItemEMCC("uus_item"),
				new ItemEMCC("uus_ingot"),
				new ItemEMCC("crystal_star"),

				new ItemEmcBattery("battery"),
				new ItemLifeRing("life_ring"),
				new ItemBlackHoleBand("black_hole_band"));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event)
	{
		ClientUtils.registerModel(UUS_BLOCK);
		ClientUtils.registerModel(CONDENSER);

		ClientUtils.registerModel(UUS_ITEM);
		ClientUtils.registerModel(UUS_INGOT);
		ClientUtils.registerModel(CRYSTAL_STAR);

		ClientUtils.registerModel(BATTERY);
		ClientUtils.registerModel(LIFE_RING);
		ClientUtils.registerModel(BLACK_HOLE_BAND);
	}
}