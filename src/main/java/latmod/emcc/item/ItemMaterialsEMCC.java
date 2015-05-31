package latmod.emcc.item;
import latmod.core.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public class ItemMaterialsEMCC extends ItemEMCC
{
	public static final String[] names =
	{
		"item_uus",
		"minium_star",
		"ingot_uus"
	};
	
	public static ItemStack ITEM_UUS;
	public static ItemStack MINIUM_STAR;
	public static ItemStack INGOT_UUS;
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemMaterialsEMCC(String s)
	{ super(s); }
	
	public void onPostLoaded()
	{
		itemsAdded.add(ODItems.add("itemUUS", ITEM_UUS = new ItemStack(this, 1, 0)));
		itemsAdded.add(ODItems.add("miniumStar", MINIUM_STAR = new ItemStack(this, 1, 1)));
		itemsAdded.add(ODItems.add("ingotUUS", INGOT_UUS = new ItemStack(this, 1, 2)));
	}
	
	public void loadRecipes()
	{
	}
	
	public String getUnlocalizedName(ItemStack is)
	{
		int dmg = is.getItemDamage();
		if(dmg < 0 || dmg >= names.length) return "unknown";
		return mod.getItemName(names[dmg]);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < names.length; i++)
			icons[i] = ir.registerIcon(mod.assets + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int m, int r)
	{ return (m >= 0 && m < icons.length) ? icons[m] : LatCoreMC.Client.unknownItemIcon; }
}