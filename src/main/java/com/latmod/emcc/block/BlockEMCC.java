package com.latmod.emcc.block;

import com.feed_the_beast.ftbl.lib.block.BlockBase;
import com.latmod.emcc.EMCC;
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