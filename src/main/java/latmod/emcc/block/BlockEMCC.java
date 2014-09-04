package latmod.emcc.block;
import latmod.core.mod.LMMod;
import latmod.core.mod.block.BlockLM;
import latmod.emcc.EMCC;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public abstract class BlockEMCC extends BlockLM
{
	public BlockEMCC(String s, Material m)
	{ super(s, m); }
	
	public LMMod<?, ?> getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }
}