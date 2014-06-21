package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import latmod.core.FastList;
import latmod.emcc.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

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
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 0), "QRQ", "QUQ", "QGQ",
				Character.valueOf('Q'), Items.quartz,
				Character.valueOf('R'), Items.redstone,
				Character.valueOf('G'), Items.glowstone_dust,
				Character.valueOf('U'), EMCCItems.UNUNSEPTIUM);
	}
	
	public double getStoredEMC(ItemStack is)
	{ return is.hasTagCompound() ? is.stackTagCompound.getDouble(NBT_VAL) : 0D; }
	
	public void setStoredEMC(ItemStack is, double d)
	{
		if(!is.hasTagCompound()) is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setDouble(NBT_VAL, d);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{ l.add("Stored EMC: " + getStoredEMC(is)); }
}