package latmod.emcc.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IEmcTool extends IEmcStorageItem
{
	public boolean canHarvestBlock(Block b, ItemStack is);
	public boolean isEffective(Block b);
	public boolean canEnchantWith(ItemStack is, ToolInfusion t);
	public int getInfusionLevel(ItemStack is, ToolInfusion t);
	public void setInfusionLevel(ItemStack is, ToolInfusion t, int lvl);
	public EnumToolType getToolType(ItemStack is);
}