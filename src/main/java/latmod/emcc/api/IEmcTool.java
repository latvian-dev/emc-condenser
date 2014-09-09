package latmod.emcc.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IEmcTool extends IEmcStorageItem
{
	public boolean canHarvestBlock(Block b, ItemStack is);
	public boolean isEffective(Block b);
	public boolean canEnchantWith(ItemStack is, ToolInfusion t);
}