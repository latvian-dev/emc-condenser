package latmod.emcc.emc;

import java.util.*;

import latmod.emcc.EMCC;
import latmod.ftbu.inv.*;
import latmod.lib.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class VanillaEMC
{
	private static final ByteIOStream io = new ByteIOStream();
	
	private final FastList<RegEntry> regEntries;
	private final FastMap<String, Float> oreEntries;
	
	public VanillaEMC()
	{
		regEntries = new FastList<RegEntry>();
		oreEntries = new FastMap<String, Float>();
	}
	
	public void clear()
	{
		regEntries.clear();
		oreEntries.clear();
	}
	
	public void fromBytes(byte[] b) throws Exception
	{
		clear();
		io.setCompressedData(b);
		
		int s = io.readUShort();
		for(int i = 0; i < s; i++)
		{
			int j = io.readUShort();
			int m = io.readUShort();
			float v = io.readFloat();
			regEntries.add(new RegEntry(j, m, v));
		}
		
		s = io.readUShort();
		for(int i = 0; i < s; i++)
		{
			String o = io.readString();
			float v = io.readFloat();
			oreEntries.put(o, v);
		}
		
		EMCC.mod.logger.info("Loaded VanillaEMC from " + b.length + " bytes");
	}
	
	public byte[] toBytes() throws Exception
	{
		io.setCompressedData(new byte[0]);
		
		int s = regEntries.size();
		io.writeUShort(s);
		for(int i = 0; i < s; i++)
		{
			RegEntry r = regEntries.get(i);
			io.writeUShort(Item.getIdFromItem(r.item));
			io.writeUShort(r.damage);
			io.writeFloat(r.value);
		}
		
		s = oreEntries.size();
		io.writeUShort(s);
		for(int i = 0; i < s; i++)
		{
			io.writeString(oreEntries.keys.get(i));
			io.writeFloat(oreEntries.values.get(i).floatValue());
		}
		
		return io.toCompressedByteArray();
	}
	
	public void loadDefaults()
	{
		addOre(ODItems.COBBLE, 1);
		addOre(ODItems.STONE, 1);
		addOre(ODItems.SAND, 4);
		addReg(Blocks.dirt, -1, 1);
		addReg(ODItems.WOOL_WHITE, 48);
		addReg(Blocks.gravel, 0, 4);
		addReg(ODItems.OBSIDIAN, 64);
		addReg(Blocks.tnt, 0, 964);
		addReg(Blocks.rail, 0, 96.25F);
		addReg(Blocks.dragon_egg, 0, 196608);
		
		addOre(ODItems.GLASS, 1);
		addOre(ODItems.GLASS_PANE, 6F / 16F);
		addReg(Blocks.sandstone, -1, 4);
		addReg(Blocks.netherrack, 0, 1);
		addReg(Blocks.nether_brick, 0, 4);
		addReg(Blocks.stonebrick, -1, 4);
		addReg(Blocks.end_stone, 0, 1);
		
		addReg(Blocks.yellow_flower, -1, 16);
		addReg(Blocks.red_flower, -1, 16);
		addReg(Blocks.cactus, 0, 8);
		addReg(Blocks.vine, 0, 8);
        addReg(Blocks.waterlily, 0, 16);
        addReg(Blocks.double_plant, -1, 8);
		
		addOre(ODItems.WOOD, 32);
		addOre(ODItems.SAPLING, 32);
		addOre(ODItems.PLANKS, 8);
		addOre(ODItems.STICK, 4);
		
		addOre(ODItems.PERIDOT, 1024);
		addOre(ODItems.SAPPHIRE, 1024);
		addOre(ODItems.RUBY, 1024);
		
		addReg(Items.coal, -1, 32);
		addOre(ODItems.IRON, 256);
		addOre(ODItems.GOLD, 2048);
		addOre(ODItems.DIAMOND, 8192);
		addOre(ODItems.EMERALD, 8192);
		addReg(new ItemStack(Items.dye, 1, 4), 864);
		addOre(ODItems.REDSTONE, 32);
		addOre(ODItems.GLOWSTONE, 256);
		addOre(ODItems.QUARTZ, 256);
		
		addReg(Blocks.coal_block, 0, 32 * 9);
		addReg(Blocks.iron_block, 0, 256 * 9);
		addReg(Blocks.gold_block, 0, 2048 * 9);
		addReg(Blocks.diamond_block, 0, 8192 * 9);
		addReg(Blocks.emerald_block, 0, 8192 * 9);
		addReg(Blocks.lapis_block, 0, 864 * 9);
		addReg(Blocks.redstone_block, 0, 32 * 9);
		addReg(Blocks.glowstone, 0, 384 * 4);
		addReg(Blocks.quartz_block, -1, 256 * 4);
		
		addOre(ODItems.TIN, 256);
		addOre(ODItems.COPPER, 85);
		addOre(ODItems.SILVER, 512);
		addOre(ODItems.LEAD, 256);
		addOre(ODItems.RUBBER, 32);
		
		addOre(ODItems.SLIMEBALL, 24);
		addOre(ODItems.MEAT_RAW, 64);
		addOre(ODItems.MEAT_COOKED, 64);
		addReg(Items.bone, 0, 24);
		addReg(Items.ender_pearl, 0, 1024);
		addReg(Items.string, 0, 32);
		
		addReg(Items.string, 0, 12);
		addReg(Items.feather, 0, 48);
		addReg(Items.gunpowder, 0, 192);
		addReg(Items.wheat_seeds, 0, 16);
		addReg(Items.wheat, 0, 24);
		addReg(Items.flint, 0, 4);
		addReg(Items.leather, 0, 64);
		addReg(Items.brick, 0, 64);
		addReg(Items.clay_ball, 0, 64);
		addReg(Items.reeds, 0, 32);
		addReg(Items.paper, 0, 32);
		addReg(Items.egg, 0, 32);
		addReg(Items.blaze_rod, 0, 1536);
		addReg(Items.nether_wart, 0, 24);
		addReg(Items.nether_star, 0, 24576);
		addReg(Items.ghast_tear, 0, 4096);
	}
	
	
	private void addOre(String s, float v)
	{ if(s != null && !s.isEmpty() && v > 0F) oreEntries.put(s, v); }
	
	private void addReg(ItemStack is, float v)
	{ if(is != null && v > 0F) regEntries.add(new RegEntry(is, v)); }
	
	private void addReg(Block b, int dmg, float v)
	{ addReg(new ItemStack(b, 1, dmg), v); }
	
	private void addReg(Item b, int dmg, float v)
	{ addReg(new ItemStack(b, 1, dmg), v); }
	
	private void addReg(String s, float v)
	{
		if(s != null && !s.isEmpty() && v > 0F)
		{
			ItemStack is = ItemStackTypeAdapter.parseItem(s);
			if(is != null) addReg(is.getItem(), is.getItemDamage(), v);
		}
	}
	
	public float getEMC(ItemStack is)
	{
		if(is == null || is.getItem() == null) return 0F;
		
		for(int i = 0; i < regEntries.size(); i++)
		{
			RegEntry e = regEntries.get(i);
			if(e.equalsItem(is)) return e.value;
		}
		
		FastList<String> ores = ODItems.getOreNames(is);
		
		if(!ores.isEmpty()) for(int i = 0; i < oreEntries.size(); i++)
		{
			String k = oreEntries.keys.get(i);
			Float v = oreEntries.values.get(i);
			
			if(ores.contains(k)) return v.floatValue();
		}
		
		return 0F;
	}
	
	private static class RegEntry
	{
		public final Item item;
		public final int damage;
		public final float value;
		
		public RegEntry(ItemStack is, float v)
		{
			item = is.getItem();
			damage = is.getItemDamage();
			value = v;
		}
		
		public RegEntry(int i, int d, float v)
		{ this(new ItemStack(Item.getItemById(i), 1, d), v); }
		
		public boolean equalsItem(ItemStack is)
		{ return is != null && item == is.getItem() && (damage == ODItems.ANY || damage == -1 || damage == is.getItemDamage()); }
		
		public String toString()
		{ return LMInvUtils.getRegName(item) + ((damage == 0) ? "" : ("@" + damage)); }
	}
	
	public static class EMCFile
	{
		public Map<String, Float> regNames;
		public Map<String, Float> oreNames;
		
		public void loadFrom(VanillaEMC e)
		{
			if(regNames == null || oreNames == null)
			{
				regNames = new HashMap<String, Float>();
				oreNames = new HashMap<String, Float>();
			}
			else
			{
				regNames.clear();
				oreNames.clear();
			}
			
			for(int i = 0; i < e.regEntries.size(); i++)
			{
				RegEntry r = e.regEntries.get(i);
				regNames.put(r.toString(), r.value);
			}
			
			for(int i = 0; i < e.oreEntries.size(); i++)
				oreNames.put(e.oreEntries.keys.get(i), e.oreEntries.values.get(i));
		}
		
		public void saveTo(VanillaEMC e)
		{
			Iterator<String> keys = regNames.keySet().iterator();
			Iterator<Float> values = regNames.values().iterator();
			
			while(keys.hasNext() && values.hasNext())
			{
				String k = keys.next();
				Float v = values.next();
				
				if(k != null && v != null && !k.isEmpty() && v.floatValue() > 0F)
					e.addReg(k, v);
			}
			
			keys = oreNames.keySet().iterator();
			values = oreNames.values().iterator();
			
			while(keys.hasNext() && values.hasNext())
			{
				String k = keys.next();
				Float v = values.next();
				
				if(k != null && v != null && !k.isEmpty() && v.floatValue() > 0F)
					e.addOre(k, v);
			}
		}
	}
}