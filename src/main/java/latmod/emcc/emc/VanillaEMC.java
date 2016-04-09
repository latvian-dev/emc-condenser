package latmod.emcc.emc;

import com.google.gson.*;
import ftb.lib.api.item.*;
import latmod.emcc.api.ItemEntry;
import latmod.lib.ByteIOStream;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.IJsonSerializable;

import java.util.*;

public class VanillaEMC implements IJsonSerializable
{
	public final Map<ItemEntry, Float> regEntries;
	public final Map<String, Float> oreEntries;
	
	public VanillaEMC()
	{
		regEntries = new LinkedHashMap<>();
		oreEntries = new LinkedHashMap<>();
	}
	
	public void clear()
	{
		regEntries.clear();
		oreEntries.clear();
	}
	
	public void fromBytes(ByteIOStream io) throws Exception
	{
		clear();
		
		int s = io.readUnsignedShort();
		for(int i = 0; i < s; i++)
		{
			int j = io.readUnsignedShort();
			int m = io.readUnsignedShort();
			float v = io.readFloat();
			
			Item item = Item.getItemById(j);
			
			if(item != null)
			{
				regEntries.put(new ItemEntry(item, m), v);
			}
		}
		
		s = io.readUnsignedShort();
		for(int i = 0; i < s; i++)
		{
			String o = io.readUTF();
			float v = io.readFloat();
			oreEntries.put(o, v);
		}
	}
	
	public void toBytes(ByteIOStream io) throws Exception
	{
		int s = regEntries.size();
		io.writeShort(s);
		for(Map.Entry<ItemEntry, Float> e : regEntries.entrySet())
		{
			io.writeShort(Item.getIdFromItem(e.getKey().item));
			io.writeShort(e.getKey().damage);
			io.writeFloat(e.getValue());
		}
		
		s = oreEntries.size();
		io.writeShort(s);
		for(Map.Entry<String, Float> e : oreEntries.entrySet())
		{
			io.writeUTF(e.getKey());
			io.writeFloat(e.getValue().floatValue());
		}
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
	{
		if(s != null && !s.isEmpty() && v > 0F)
		{
			oreEntries.put(s, v);
		}
	}
	
	private void addReg(ItemStack is, float v)
	{
		if(is != null && v > 0F)
		{
			regEntries.put(new ItemEntry(is), v);
		}
	}
	
	private void addReg(Block b, int dmg, float v)
	{ addReg(new ItemStack(b, 1, dmg), v); }
	
	private void addReg(Item b, int dmg, float v)
	{ addReg(new ItemStack(b, 1, dmg), v); }
	
	private void addReg(String s, float v)
	{
		if(s != null && !s.isEmpty() && v > 0F)
		{
			String[] s1 = s.split("@");
			if(s1.length == 2)
			{
				Item item = LMInvUtils.getItemFromRegName(s1[0]);
				
				if(item != null)
				{
					int dmg = Integer.parseInt(s1[1]);
					addReg(item, dmg, v);
				}
			}
		}
	}
	
	public float getEMC(ItemStack is)
	{
		if(is == null || is.getItem() == null) return 0F;
		
		Float f = regEntries.get(is);
		
		if(f != null) return f;
		
		List<String> ores = ODItems.getOreNames(is);
		
		//TODO: Make prettier
		if(!ores.isEmpty()) for(Map.Entry<String, Float> e1 : oreEntries.entrySet())
		{ if(ores.contains(e1.getKey())) return e1.getValue().floatValue(); }
		
		return 0F;
	}
	
	public void func_152753_a(JsonElement e)
	{
		clear();
		
		JsonObject o = e.getAsJsonObject();
		
		if(o.has("reg_entries") && o.has("ore_entries"))
		{
			JsonObject o1 = o.get("reg_entries").getAsJsonObject();
			
			for(Map.Entry<String, JsonElement> entry : o1.entrySet())
			{
				addReg(entry.getKey(), entry.getValue().getAsFloat());
			}
			
			o1 = o.get("ore_entries").getAsJsonObject();
			
			for(Map.Entry<String, JsonElement> entry : o1.entrySet())
			{
				addOre(entry.getKey(), entry.getValue().getAsFloat());
			}
		}
		else
		{
			loadDefaults();
		}
	}
	
	public JsonElement getSerializableElement()
	{
		JsonObject o = new JsonObject();
		
		JsonObject o1 = new JsonObject();
		
		for(Map.Entry<ItemEntry, Float> e : regEntries.entrySet())
		{
			o1.add(e.getKey().getID(), new JsonPrimitive(e.getValue()));
		}
		
		o.add("reg_entries", o1);
		
		o1 = new JsonObject();
		
		for(Map.Entry<String, Float> e : oreEntries.entrySet())
		{
			o1.add(e.getKey(), new JsonPrimitive(e.getValue()));
		}
		
		o.add("ore_entries", o1);
		
		return o;
	}
}