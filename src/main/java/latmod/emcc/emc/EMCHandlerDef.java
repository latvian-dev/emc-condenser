package latmod.emcc.emc;

import java.io.File;

import latmod.core.ODItems;
import latmod.core.recipes.LMRecipes;
import latmod.core.util.LatCore;
import latmod.emcc.*;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public class EMCHandlerDef extends EMCHandler
{
	public static final EMCHandlerDef instance = new EMCHandlerDef();
	public final VanillaEMC vanillaEMC = new VanillaEMC();
	public File vanillaEMCFile = null;
	
	public void modInited()
	{
		if(vanillaEMCFile == null) return;
		
		vanillaEMC.clear();
		
		if(vanillaEMCFile.exists())
		{
			VanillaEMC.EMCFile f = LatCore.fromJsonFromFile(vanillaEMCFile, VanillaEMC.EMCFile.class);
			if(f != null) f.saveTo(vanillaEMC);
			else vanillaEMC.loadDefaults();
		}
		else
		{
			vanillaEMC.loadDefaults();
			VanillaEMC.EMCFile f = new VanillaEMC.EMCFile();
			f.loadFrom(vanillaEMC);
			LatCore.toJsonFile(vanillaEMCFile, f);
		}
	}
	
	public void loadRecipes()
	{
		// Material recipes //
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.ITEM_UUS, "MRM", "VSV", "MGM",
				'M', ODItems.DIAMOND,
				'V', ODItems.EMERALD,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'S', Blocks.stone);
		
		EMCC.mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsEMCC.INGOT_UUS, 8), "III", "IUI", "III",
				'I', ODItems.IRON,
				'U', ItemMaterialsEMCC.ITEM_UUS);
		
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_uu_block, 8), "III", "IUI", "III",
				'I', ODItems.OBSIDIAN,
				'U', ItemMaterialsEMCC.ITEM_UUS);
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.MINIUM_STAR, "MMM", "MSM", "MMM",
				Character.valueOf('M'), ODItems.DIAMOND,
				Character.valueOf('S'), Items.nether_star);
		
		// Condenser recipe //
		
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_condenser), "OBO", "OSO", "OIO",
				'O', EMCCItems.b_uu_block,
				'I', ItemMaterialsEMCC.MINIUM_STAR,
				'B', EMCCItems.i_black_hole_band,
				'S', Blocks.diamond_block);
	}
	
	public float getEMC(ItemStack is)
	{ return vanillaEMC.getEMC(is); }
}