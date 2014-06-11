package mods.emcc.block;
import com.pahimar.ee3.lib.*;

import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import mods.emcc.*;
import mods.emcc.tile.*;
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
		
		EMCCRecipes.UUS_BLOCK = new ItemStack(this, 1, 0);
		
		EMCC.inst.addTile(TileCondenser.class, "condenser");
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.infuseUUBlock)
		EMCCRecipes.addInfusing(EMCCRecipes.UUS_BLOCK, new ItemStack(Block.obsidian), EMCCRecipes.siz(EMCCRecipes.UUS_ITEM, 8));
		else EMCCRecipes.addRecipe(EMCCRecipes.UUS_BLOCK, "UUU", "UOU", "UUU",
			Character.valueOf('U'), EMCCRecipes.UUS_ITEM,
			Character.valueOf('O'), Block.obsidian);
		
		ItemStack is = EMCCRecipes.UUS_BLOCK;
		
		if(EMCCConfig.recipeDifficulty == 1) is = new ItemStack(Item.netherStar);
		else if(EMCCConfig.recipeDifficulty == 2) is = EMCCRecipes.MINIUM_STAR;
		
		EMCCRecipes.addRecipe(new ItemStack(this, 1, 1), "OBO", "OSO", "OIO",
				Character.valueOf('O'), Block.obsidian,
				Character.valueOf('I'), is,
				Character.valueOf('B'), new ItemStack(EMCC.i_battery, 1, LatCore.ANY),
				Character.valueOf('S'), new ItemStack(ItemIds.MINIUM_STONE, 1, LatCore.ANY));
	}
	
	public boolean hasTileEntity(int m)
	{ return m > 0; }
	
	public TileEMCC createTileEntity(World w, int m)
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
			if(s == TileEMCC.UP)
			return condTop;
			return condSide;
		}
		
		return blockIcon;
	}
	
	public boolean canCableConnect(IBlockAccess iba, int x, int y, int z)
	{ return iba.getBlockMetadata(x, y, z) > 1; }
}