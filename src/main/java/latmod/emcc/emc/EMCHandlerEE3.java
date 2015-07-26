package latmod.emcc.emc;

import latmod.emcc.*;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.recipes.LMRecipes;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public class EMCHandlerEE3 extends EMCHandler
{
	public static ItemStack DUST_VERDANT;
	public static ItemStack DUST_AZURE;
	public static ItemStack DUST_MINIUM;
	
	public void modInited()
	{
		DUST_VERDANT = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 1);
		DUST_AZURE = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 2);
		DUST_MINIUM = new ItemStack(com.pahimar.ee3.init.ModItems.alchemicalDust, 1, 3);
		
		if(EMCCConfig.General.forceVanillaEMC)
			super.modInited();
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.General.forceVanillaRecipes)
		{
			super.loadRecipes();
			return;
		}
		
		// Material recipes //
		
		EMCC.mod.recipes.addRecipe(ItemMaterialsEMCC.ITEM_UUS.stack, "MRM", "VSA", "MGM",
				'M', DUST_MINIUM,
				'V', ODItems.EMERALD,
				'A', ODItems.DIAMOND,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'S', Blocks.stone);
		
		addInfusing(LMRecipes.size(ItemMaterialsEMCC.INGOT_UUS.stack, 8),
				new ItemStack(Items.iron_ingot, 8),
				ItemMaterialsEMCC.ITEM_UUS.stack);
		
		addInfusing(new ItemStack(EMCCItems.b_uu_block, 8),
				new ItemStack(Blocks.obsidian, 8),
				ItemMaterialsEMCC.ITEM_UUS.stack);
		
		addInfusing(ItemMaterialsEMCC.MINIUM_STAR.stack, new ItemStack(Items.nether_star), LMRecipes.size(DUST_MINIUM, 8));
		
		// Condenser recipe //
		
		EMCC.mod.recipes.addRecipe(new ItemStack(EMCCItems.b_condenser), "OBO", "OSO", "OIO",
				'O', EMCCItems.b_uu_block,
				'I', ItemMaterialsEMCC.MINIUM_STAR.stack,
				'B', EMCCItems.i_black_hole_band,
				'S', new ItemStack(com.pahimar.ee3.init.ModItems.stoneMinium, 1, ODItems.ANY));
	}
	
	public void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ com.pahimar.ee3.recipe.AludelRecipeManager.getInstance().addRecipe(out, in, with); }
	
	public float getEMC(ItemStack is)
	{
		if(is == null || is.stackSize <= 0) return 0F;
		
		if(EMCCConfig.General.forceVanillaEMC)
			return super.getEMC(is);
		
		com.pahimar.ee3.api.exchange.EnergyValue e = com.pahimar.ee3.exchange.EnergyValueRegistry.getInstance().getEnergyValue(is);
		return (e == null) ? 0F : e.getValue();
	}
}