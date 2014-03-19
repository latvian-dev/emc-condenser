package mods.lm_emc_cond.block;
import com.pahimar.ee3.lib.*;
import cpw.mods.fml.relauncher.*;
import mods.lm_emc_cond.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockMachines extends BlockAlchemy
{
	public static final String[] names =
	{
		"uusb",
		"condenser",
	};
	
	@SideOnly(Side.CLIENT)
	public Icon condSide, condTop;
	
	public BlockMachines(String s)
	{
		super(s, Material.iron);
		addAllDamages(names.length);
		isBlockContainer = true;
		
		AlchemyRecipes.UUS_BLOCK_ITEM = new ItemStack(this, 1, 0);
		
		Alchemy.inst.addTile(TileCondenser.class, "condenser");
	}
	
	public void loadRecipes()
	{
		AlchemyRecipes.addOre(AlchemyRecipes.UUS_BLOCK, AlchemyRecipes.UUS_BLOCK_ITEM);
		
		AlchemyRecipes.add3x3Recipe(AlchemyRecipes.UUS_ITEM, AlchemyRecipes.UUS_BLOCK_ITEM, true);
		
		AlchemyRecipes.addRecipe(new ItemStack(this, 1, 1), "OPO", "OSO", "OMO",
				Character.valueOf('O'), Block.obsidian,
				Character.valueOf('M'), AlchemyRecipes.UUS_BLOCK,
				Character.valueOf('P'), Item.enderPearl,
				Character.valueOf('S'), new ItemStack(ItemIds.MINIUM_STONE, 1, 0));
	}
	
	public boolean hasTileEntity(int m)
	{ return m > 0; }
	
	public TileAlchemy createTileEntity(World w, int m)
	{
		if(m == 0) return null;
		else if(m == 1) return new TileCondenser();
		return null;
	}
	
	public String getUnlocalizedName(int i)
	{ return AlchemyFinals.getBlockName(names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		blockIcon = ir.registerIcon(AlchemyFinals.ASSETS + "uusb");
		condSide = ir.registerIcon(AlchemyFinals.ASSETS + "condSide");
		condTop = ir.registerIcon(AlchemyFinals.ASSETS + "condTop");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int s, int m)
	{ 
		if(m == 1)
		{
			if(s == TileAlchemy.UP)
			return condTop;
			return condSide;
		}
		
		return blockIcon;
	}
	
	public boolean canCableConnect(IBlockAccess iba, int x, int y, int z)
	{ return iba.getBlockMetadata(x, y, z) > 1; }
}