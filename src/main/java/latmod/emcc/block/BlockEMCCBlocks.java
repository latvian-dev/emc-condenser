package latmod.emcc.block;
import com.pahimar.ee3.init.ModItems;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.base.*;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockEMCCBlocks extends BlockLM
{
	public static final String[] names =
	{
		"uusb",
		"condenser",
	};
	
	@SideOnly(Side.CLIENT)
	public IIcon condSide, condTop;
	
	public BlockEMCCBlocks(String s)
	{
		super(EMCC.mod, s, Material.iron);
		isBlockContainer = true;
		
		EMCCItems.UNUNSEPTIUM_BLOCK = new ItemStack(this, 1, 0);
		EMCCItems.CONDENSER = new ItemStack(this, 1, 1);
		//EMCCItems.ALCH_ANVIL = new ItemStack(this, 1, 2);
		
		EMCC.mod.addTile(TileCondenser.class, "condenser");
	}
	
	public void onPostLoaded()
	{ addAllDamages(names.length); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }
	
	public void loadRecipes()
	{
		if(EMCC.config.general.infuseUUBlock)
		EMCCItems.addInfusing(EMCCItems.UNUNSEPTIUM_BLOCK, new ItemStack(Blocks.obsidian), EMCC.recipes.size(EMCCItems.UNUNSEPTIUM, 8));
		else EMCC.recipes.addRecipe(EMCCItems.UNUNSEPTIUM_BLOCK, "UUU", "UOU", "UUU",
			Character.valueOf('U'), EMCCItems.UNUNSEPTIUM,
			Character.valueOf('O'), Blocks.obsidian);
		
		ItemStack is = EMCCItems.UNUNSEPTIUM_BLOCK;
		
		if(EMCC.config.general.recipeDifficulty == 1) is = new ItemStack(Items.nether_star);
		else if(EMCC.config.general.recipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 1), "OBO", "OSO", "OIO",
				Character.valueOf('O'), Blocks.obsidian,
				Character.valueOf('I'), is,
				Character.valueOf('B'), new ItemStack(EMCCItems.i_battery, 1, LatCore.ANY),
				Character.valueOf('S'), new ItemStack(ModItems.stoneMinium, 1, LatCore.ANY));
	}
	
	public boolean hasTileEntity(int m)
	{ return m > 0; }
	
	public TileEntity createNewTileEntity(World w, int m)
	{
		if(m == 0) return null;
		else if(m == 1) return new TileCondenser();
		return null;
	}
	
	public String getUnlocalizedName(int i)
	{ return mod.getBlockName(names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "uusb");
		condSide = ir.registerIcon(mod.assets + "condSide");
		condTop = ir.registerIcon(mod.assets + "condTop");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ 
		if(m == 1)
		{
			if(s == TileLM.UP)
			return condTop;
			return condSide;
		}
		
		return blockIcon;
	}
	
	public boolean canCableConnect(IBlockAccess iba, int x, int y, int z)
	{ return iba.getBlockMetadata(x, y, z) > 1; }
}