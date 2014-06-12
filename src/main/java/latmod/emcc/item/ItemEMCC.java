package latmod.emcc.item;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class ItemEMCC extends Item
{
	public final String itemName;
	public ArrayList<ItemStack> itemsAdded = new ArrayList<ItemStack>();

	public ItemEMCC(String s)
	{
		super(EMCCConfig.getItemID(s));
		itemName = s;
		setUnlocalizedName(EMCCFinals.getItemName(s));
	}
	
	public void onPostLoaded() { }

	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }

	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs c, List l)
	{ l.addAll(itemsAdded); }

	public String getUnlocalizedName(ItemStack is)
	{ return EMCCFinals.getItemName(itemName); }

	public void addAllDamages(int until)
	{
		for(int i = 0; i < until; i++)
		itemsAdded.add(new ItemStack(this, 1, i));
	}
	
	public void addAllDamages(int[] dmg)
	{
		for(int i = 0; i < dmg.length; i++)
		itemsAdded.add(new ItemStack(this, 1, dmg[i]));
	}

	public final boolean requiresMultipleRenderPasses()
	{ return true; }

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{ itemIcon = ir.registerIcon(EMCCFinals.ASSETS + itemName); }

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int i, int r)
	{ return itemIcon; }

	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack is, int r)
	{ return getIconFromDamageForRenderPass(is.getItemDamage(), r); }

	public ItemStack create(Object... o)
	{ return new ItemStack(this); }

	public void loadRecipes()
	{
	}
}