package latmod.emcc.item;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ItemBattery extends ItemEMCC
{
	public static final String NBT_VAL = "StoredEMC";
	
	public ItemBattery(String s)
	{
		super(s);
		setMaxStackSize(1);
		addAllDamages(1);
	}
	
	public void loadRecipes()
	{
		EMCCRecipes.addRecipe(new ItemStack(this, 1, 0), "QRQ", "QUQ", "QGQ",
				Character.valueOf('Q'), Item.netherQuartz,
				Character.valueOf('R'), Item.redstone,
				Character.valueOf('G'), Item.glowstone,
				Character.valueOf('U'), EMCCRecipes.UUS_ITEM);
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List l, boolean b)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(NBT_VAL))
		{
			double ev = is.stackTagCompound.getDouble(NBT_VAL);
			l.add("Stored EMC: " + ev);
		}
		
		else l.add("Stored EMC: 0.0");
	}
}