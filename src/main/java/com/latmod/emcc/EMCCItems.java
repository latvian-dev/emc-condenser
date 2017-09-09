package com.latmod.emcc;

import com.feed_the_beast.ftbl.lib.block.ItemBlockBase;
import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.latmod.emcc.block.BlockCondenser;
import com.latmod.emcc.block.BlockUUBlock;
import com.latmod.emcc.block.TileCondenser;
import com.latmod.emcc.item.ItemBlackHoleBand;
import com.latmod.emcc.item.ItemEMCC;
import com.latmod.emcc.item.ItemEmcBattery;
import com.latmod.emcc.item.ItemLifeRing;
import com.latmod.emcc.item.ItemUUAxe;
import com.latmod.emcc.item.ItemUUBow;
import com.latmod.emcc.item.ItemUUHoe;
import com.latmod.emcc.item.ItemUUPickaxe;
import com.latmod.emcc.item.ItemUUShovel;
import com.latmod.emcc.item.ItemUUSmasher;
import com.latmod.emcc.item.ItemUUSword;
import com.latmod.emcc.item.ItemUUWrench;
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
	public static final Block UNUNSEPTIUM_BLOCK = Blocks.AIR;
	public static final Block CONDENSER = Blocks.AIR;

	public static final Item UNUNSEPTIUM = Items.AIR;
	public static final Item UNUNSEPTIUM_INGOT = Items.AIR;
	public static final Item MINIUM_STAR = Items.AIR;

	public static final Item EMC_BATTERY = Items.AIR;
	public static final Item LIFE_RING = Items.AIR;
	public static final Item BLACK_HOLE_BAND = Items.AIR;

	public static final Item WRENCH = Items.AIR;
	public static final Item SWORD = Items.AIR;
	public static final Item PICKAXE = Items.AIR;
	public static final Item SHOVEL = Items.AIR;
	public static final Item AXE = Items.AIR;
	public static final Item HOE = Items.AIR;
	public static final Item SMASHER = Items.AIR;
	public static final Item BOW = Items.AIR;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new BlockUUBlock("ununseptium_block"),
				new BlockCondenser("condenser"));

		GameRegistry.registerTileEntity(TileCondenser.class, EMCC.MOD_ID + ":condenser");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlockBase(UNUNSEPTIUM_BLOCK),
				new ItemBlockBase(CONDENSER),

				new ItemEMCC("ununseptium"),
				new ItemEMCC("ununseptium_ingot"),
				new ItemEMCC("minium_star"),

				new ItemEmcBattery("emc_battery"),
				new ItemLifeRing("life_ring"),
				new ItemBlackHoleBand("black_hole_band"),

				new ItemUUWrench("wrench"),
				new ItemUUSword("sword"),
				new ItemUUPickaxe("pickaxe"),
				new ItemUUShovel("shovel"),
				new ItemUUAxe("axe"),
				new ItemUUHoe("hoe"),
				new ItemUUSmasher("smasher"),
				new ItemUUBow("bow"));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event)
	{
		ClientUtils.registerModel(UNUNSEPTIUM_BLOCK);
		ClientUtils.registerModel(CONDENSER);

		ClientUtils.registerModel(UNUNSEPTIUM);
		ClientUtils.registerModel(UNUNSEPTIUM_INGOT);
		ClientUtils.registerModel(MINIUM_STAR);

		ClientUtils.registerModel(EMC_BATTERY);
		ClientUtils.registerModel(LIFE_RING);
		ClientUtils.registerModel(BLACK_HOLE_BAND);

		ClientUtils.registerModel(WRENCH);
		ClientUtils.registerModel(SWORD);
		ClientUtils.registerModel(PICKAXE);
		ClientUtils.registerModel(SHOVEL);
		ClientUtils.registerModel(AXE);
		ClientUtils.registerModel(HOE);
		ClientUtils.registerModel(SMASHER);
		ClientUtils.registerModel(BOW);
	}
}