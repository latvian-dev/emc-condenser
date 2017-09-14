package com.latmod.emc_condenser.block;

import com.latmod.emc_condenser.EMCCConfig;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUUBlock extends BlockEMCC
{
	public BlockUUBlock(String s)
	{
		super(s, Material.ROCK, MapColor.OBSIDIAN);
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return (float) EMCCConfig.general.uu_block_enchant_power;
	}
}