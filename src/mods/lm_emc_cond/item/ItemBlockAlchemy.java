package mods.lm_emc_cond.item;
import java.util.List;

import cpw.mods.fml.relauncher.*;
import mods.lm_emc_cond.block.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class ItemBlockAlchemy extends ItemBlock
{
	public BlockAlchemy lmuBlock;

	public ItemBlockAlchemy(int i)
	{
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		lmuBlock = (BlockAlchemy)(Block.blocksList[getBlockID()]);
	}

	public int getMetadata(int m)
	{ return m; }

	public String getUnlocalizedName(ItemStack is)
	{ return lmuBlock.getUnlocalizedName(is.getItemDamage()); }

	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs c, List l)
	{ lmuBlock.getSubBlocks(j, c, l); }
}