package latmod.emcc.emc;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import latmod.core.util.LMJsonUtils;
import latmod.emcc.*;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.inv.ODItems;
import latmod.ftbu.util.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public class EMCHandler
{
	private static EMCHandler instance = new EMCHandler();
	private static boolean hasEE3 = false;
	
	public static void init(FMLPreInitializationEvent e)
	{
		hasEE3 = LatCoreMC.isModInstalled(OtherMods.EE3);
		
		if(hasEE3) try
		{
			Class<?> clazz = Class.forName("latmod.emcc.emc.EMCHandlerEE3");
			instance = (EMCHandler) clazz.newInstance();
			hasEE3 = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(instance == null) instance = new EMCHandler();
			hasEE3 = false;
		}
		
		if(EMCCConfig.General.forceVanillaEMC || !hasEE3)
			instance.vanillaEMCFile = new File(e.getModConfigurationDirectory(), "/LatMod/EMC_Condenser_VanillaEMC.json");
	}
	
	public static final boolean hasEE3()
	{ return hasEE3; }
	
	public static final EMCHandler instance()
	{ return instance; }
	
	public final VanillaEMC vanillaEMC = new VanillaEMC();
	private File vanillaEMCFile = null;
	
	public void modInited()
	{
		reloadEMCValues();
	}
	
	public void reloadEMCValues()
	{
		if(vanillaEMCFile == null) return;
		
		vanillaEMC.clear();
		
		if(vanillaEMCFile.exists())
		{
			VanillaEMC.EMCFile f = LMJsonUtils.fromJsonFile(vanillaEMCFile, VanillaEMC.EMCFile.class);
			if(f != null) f.saveTo(vanillaEMC);
			else vanillaEMC.loadDefaults();
		}
		else
		{
			vanillaEMC.loadDefaults();
			VanillaEMC.EMCFile f = new VanillaEMC.EMCFile();
			f.loadFrom(vanillaEMC);
			LMJsonUtils.toJsonFile(vanillaEMCFile, f);
		}
	}
	
	public void loadRecipes()
	{
		// Material recipes //
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.ITEM_UUS.getStack(), "MRM", "VSV", "MGM",
				'M', ODItems.DIAMOND,
				'V', ODItems.EMERALD,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'S', Blocks.stone);
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.INGOT_UUS.getStack(8), "III", "IUI", "III",
				'I', ODItems.IRON,
				'U', ItemMaterialsEMCC.ITEM_UUS);
		
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_uu_block, 8), "III", "IUI", "III",
				'I', ODItems.OBSIDIAN,
				'U', ItemMaterialsEMCC.ITEM_UUS);
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.MINIUM_STAR.getStack(), "MMM", "MSM", "MMM",
				Character.valueOf('M'), ODItems.DIAMOND,
				Character.valueOf('S'), Items.nether_star);
		
		// Condenser recipe //
		
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_condenser), "OBO", "ASA", "OIO",
				'O', EMCCItems.b_uu_block,
				'I', ItemMaterialsEMCC.MINIUM_STAR,
				'B', EMCCItems.i_black_hole_band,
				'S', Blocks.diamond_block,
				'A', ODItems.OBSIDIAN);
	}
	
	public float getEMC(ItemStack is)
	{ return vanillaEMC.getEMC(is); }
}