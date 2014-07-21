package latmod.emcc.block;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import latmod.core.base.*;
import latmod.emcc.*;

public abstract class BlockEMCC extends BlockLM
{
	public BlockEMCC(String s, Material m)
	{ super(EMCC.mod, s, m); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }
}