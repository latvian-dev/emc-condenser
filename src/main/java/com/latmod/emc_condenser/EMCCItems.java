package com.latmod.emc_condenser;

import com.feed_the_beast.ftblib.lib.block.ItemBlockBase;
import com.feed_the_beast.ftblib.lib.client.ClientUtils;
import com.latmod.emc_condenser.block.BlockConstructor;
import com.latmod.emc_condenser.block.BlockDestructor;
import com.latmod.emc_condenser.block.BlockUUBlock;
import com.latmod.emc_condenser.block.TileConstructor;
import com.latmod.emc_condenser.block.TileDestructor;
import com.latmod.emc_condenser.item.ItemBalancedClay;
import com.latmod.emc_condenser.item.ItemEMCC;
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
	public static final Block CONSTRUCTOR = Blocks.AIR;
	public static final Block DESTRUCTOR = Blocks.AIR;

	public static final Item UUS_ITEM = Items.AIR;
	public static final Item UUS_INGOT = Items.AIR;
	public static final Item CRYSTAL_STAR = Items.AIR;
	public static final Item BALANCED_CLAY = Items.AIR;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				new BlockUUBlock("uus_block"),
				new BlockConstructor("constructor"),
				new BlockDestructor("destructor"));

		GameRegistry.registerTileEntity(TileConstructor.class, EMCC.MOD_ID + ":constructor");
		GameRegistry.registerTileEntity(TileDestructor.class, EMCC.MOD_ID + ":destructor");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlockBase(UUS_BLOCK),
				new ItemBlockBase(CONSTRUCTOR),
				new ItemBlockBase(DESTRUCTOR),
				new ItemEMCC("uus_item"),
				new ItemEMCC("uus_ingot"),
				new ItemEMCC("crystal_star"),
				new ItemBalancedClay("balanced_clay"));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event)
	{
		ClientUtils.registerModel(UUS_BLOCK);
		ClientUtils.registerModel(CONSTRUCTOR);
		ClientUtils.registerModel(DESTRUCTOR);

		ClientUtils.registerModel(UUS_ITEM);
		ClientUtils.registerModel(UUS_INGOT);
		ClientUtils.registerModel(CRYSTAL_STAR);
		ClientUtils.registerModel(BALANCED_CLAY);
	}
}