package latmod.emcc.block;
import latmod.core.tile.TileLM;
import latmod.emcc.*;
import net.minecraft.block.material.Material;
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
	{ return (float)EMCCConfig.General.ununblockEnchantPower; }
	
	public void loadRecipes()
	{
	}
}