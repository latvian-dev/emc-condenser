package mods.lm.emcc.block;
import com.pahimar.ee3.lib.*;

import cpw.mods.fml.relauncher.*;
import mods.lm.emcc.*;
import mods.lm.emcc.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockMachines extends BlockEMCC
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
		
		EMCCRecipes.UUS_BLOCK_ITEM = new ItemStack(this, 1, 0);
		
		EMCC.inst.addTile(TileCondenser.class, "condenser");
	}
	
	public void loadRecipes()
	{
		EMCCRecipes.addOre(EMCCRecipes.UUS_BLOCK, EMCCRecipes.UUS_BLOCK_ITEM);
		
		EMCCRecipes.add3x3Recipe(EMCCRecipes.UUS_ITEM, EMCCRecipes.UUS_BLOCK_ITEM, true);
		
		EMCCRecipes.addRecipe(new ItemStack(this, 1, 1), "OPO", "OSO", "OMO",
				Character.valueOf('O'), Block.obsidian,
				Character.valueOf('M'), EMCCRecipes.UUS_BLOCK,
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
	{ return EMCCFinals.getBlockName(names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		blockIcon = ir.registerIcon(EMCCFinals.ASSETS + "uusb");
		condSide = ir.registerIcon(EMCCFinals.ASSETS + "condSide");
		condTop = ir.registerIcon(EMCCFinals.ASSETS + "condTop");
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