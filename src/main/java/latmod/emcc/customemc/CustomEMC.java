package latmod.emcc.customemc;
import java.util.*;

import latmod.core.FastList;

import com.google.gson.annotations.*;

public class CustomEMC
{
	@Expose public Map<String, Float> ore_dictionary;
	@Expose public List<UNameValue> unlocazlied_name;
	
	public CustomEMC()
	{
		ore_dictionary = new HashMap<String, Float>();
		unlocazlied_name = new FastList<UNameValue>();
	}
	
	public void addOreValue(String oreName, float val)
	{ ore_dictionary.put(oreName, val); }
	
	public void addUNValue(String name, Integer dmg, float val)
	{
		UNameValue v = new UNameValue();
		v.name = name;
		v.damage = dmg;
		v.value = val;
		unlocazlied_name.add(v);
	}
}