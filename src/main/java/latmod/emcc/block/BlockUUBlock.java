package latmod.emcc.block;
import ftb.lib.item.ODItems;
import latmod.emcc.*;
import latmod.emcc.config.EMCCConfigGeneral;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.tile.TileLM;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockUUBlock extends BlockEMCC
{
	public BlockUUBlock(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public float getEnchantPowerBonus(World w, int x, int y, int z)
	{ return (float)EMCCConfigGeneral.uu_block_enchant_power.get(); }
	
	public void loadRecipes()
	{
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_uu_block, 8), "III", "IUI", "III",
				'I', ODItems.OBSIDIAN,
				'U', ItemMaterialsEMCC.ITEM_UUS);
	}
}