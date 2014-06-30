package latmod.emcc.block;
import com.pahimar.ee3.lib.*;

import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.core.base.BlockLM;
import latmod.emcc.*;
import latmod.emcc.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockMachines extends BlockLM
{
	public static final String[] names =
	{
		"uub",
		"condenser",
	};
	
	@SideOnly(Side.CLIENT)
	public Icon condSide, condTop;
	
	public BlockMachines(int id, String s)
	{
		super(EMCC.mod, id, s, Material.iron);
		addAllDamages(names.length);
		isBlockContainer = true;
		
		EMCCItems.UU_BLOCK = new ItemStack(this, 1, 0);
		EMCCItems.CONDENSER = new ItemStack(this, 1, 1);
		
		EMCC.mod.addTile(TileCondenser.class, "condenser");
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.recipes.infuseUUBlock)
		EMCC.addInfusing(EMCCItems.UU_BLOCK, new ItemStack(Block.obsidian), EMCC.recipes.size(EMCCItems.UU_ITEM, 8));
		else EMCC.recipes.addRecipe(EMCCItems.UU_BLOCK, "UUU", "UOU", "UUU",
			Character.valueOf('U'), EMCCItems.UU_ITEM,
			Character.valueOf('O'), Block.obsidian);
		
		ItemStack is = EMCCItems.UU_BLOCK;
		
		if(EMCC.config.recipes.recipeDifficulty == 1) is = new ItemStack(Item.netherStar);
		else if(EMCC.config.recipes.recipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 1), "OBO", "OSO", "OIO",
				Character.valueOf('O'), Block.obsidian,
				Character.valueOf('I'), is,
				Character.valueOf('B'), new ItemStack(EMCCItems.i_uuBattery, 1, LatCore.ANY),
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
	{ return mod.getBlockName(names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "uub");
		condSide = ir.registerIcon(mod.assets + "condSide");
		condTop = ir.registerIcon(mod.assets + "condTop");
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
	
	public TileEntity createNewTileEntity(World w)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }
}