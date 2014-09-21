package latmod.emcc.block;
import latmod.core.mod.tile.TileLM;
import latmod.emcc.EMCC;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockUUBlock extends BlockEMCC
{
	public static int renderID;
	
	public IIcon icon_empty, icon_glow;
	
	public BlockUUBlock(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public float getEnchantPowerBonus(World w, int x, int y, int z)
	{ return (float)EMCC.mod.config().general.ununblockEnchantPower; }
	
	public void loadRecipes()
	{
	}
	
	public int getRenderType()
	{ return renderID; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + blockName);
		icon_empty = ir.registerIcon(mod.assets + blockName + "_empty");
		icon_glow = ir.registerIcon(mod.assets + blockName + "_glow");
	}
}