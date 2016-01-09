package latmod.emcc.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IEmcTool extends IEmcStorageItem
{
	boolean canHarvestBlock(Block b, ItemStack is);
	boolean isEffective(Block b);
	boolean canEnchantWith(ItemStack is, ToolInfusion t);
	int getInfusionLevel(ItemStack is, ToolInfusion t);
	void setInfusionLevel(ItemStack is, ToolInfusion t, int lvl);
	EnumToolType getToolType(ItemStack is);
}