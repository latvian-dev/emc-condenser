package latmod.emcc.emc;

import latmod.emcc.config.EMCCConfigGeneral;
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
		
		if(EMCCConfigGeneral.force_vanilla_emc.getAsBoolean()) super.modInited();
	}
	
	public void addInfusing(ItemStack out, ItemStack in, ItemStack with)
	{ com.pahimar.ee3.recipe.AludelRecipeManager.getInstance().addRecipe(out, in, with); }
	
	public float getEMC(ItemStack is)
	{
		if(is == null || is.stackSize <= 0) return 0F;
		
		if(EMCCConfigGeneral.force_vanilla_emc.getAsBoolean()) return super.getEMC(is);
		
		com.pahimar.ee3.api.exchange.EnergyValue e = com.pahimar.ee3.exchange.EnergyValueRegistry.getInstance().getEnergyValue(is);
		return (e == null) ? 0F : e.getValue();
	}
}