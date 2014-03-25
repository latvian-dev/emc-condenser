package mods.lm.emcc.item;
import java.util.List;

import cpw.mods.fml.relauncher.*;
import mods.lm.emcc.block.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class ItemBlockAlchemy extends ItemBlock
{
	public BlockEMCC lmuBlock;

	public ItemBlockAlchemy(int i)
	{
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		lmuBlock = (BlockEMCC)(Block.blocksList[getBlockID()]);
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