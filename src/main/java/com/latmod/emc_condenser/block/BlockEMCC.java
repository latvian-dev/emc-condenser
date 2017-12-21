package com.latmod.emc_condenser.block;

import com.feed_the_beast.ftblib.lib.block.BlockBase;
import com.latmod.emc_condenser.EMCC;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockEMCC extends BlockBase
{
	public BlockEMCC(String s, Material m, MapColor c)
	{
		super(EMCC.MOD_ID, s, m, c);
		setCreativeTab(EMCC.TAB);
	}
}