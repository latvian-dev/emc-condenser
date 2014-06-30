package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import latmod.core.FastList;
import latmod.emcc.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ItemBattery extends ItemEMCC
{
	public static final String NBT_VAL = "StoredEMC";
	
	public ItemBattery(int id, String s)
	{
		super(id, s);
		setMaxStackSize(1);
		addAllDamages(1);
	}
	
	public void loadRecipes()
	{
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 0), "QRQ", "QUQ", "QGQ",
				Character.valueOf('Q'), Item.netherQuartz,
				Character.valueOf('R'), Item.redstone,
				Character.valueOf('G'), Item.glowstone,
				Character.valueOf('U'), EMCCItems.UU_ITEM);
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(NBT_VAL))
		{
			double ev = is.stackTagCompound.getDouble(NBT_VAL);
			l.add("Stored EMC: " + ev);
		}
		
		else l.add("Stored EMC: 0.0");
	}
}