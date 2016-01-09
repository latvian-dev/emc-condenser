package latmod.emcc.item;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.EMCC;
import latmod.ftbu.item.ItemLM;
import latmod.ftbu.util.LMMod;
import net.minecraft.creativetab.CreativeTabs;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(String s)
	{ super(s); }
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}