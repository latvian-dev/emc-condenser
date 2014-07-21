package latmod.emcc.customemc;
import java.util.*;
import latmod.core.util.FastList;

import com.google.gson.annotations.*;

public class CustomEMC
{
	@Expose public Map<String, Float> ore_dictionary;
	@Expose public List<RegNameValue> registry_name;
	
	public CustomEMC()
	{
		ore_dictionary = new HashMap<String, Float>();
		registry_name = new FastList<RegNameValue>();
	}
	
	public void addOreValue(String oreName, float val)
	{ ore_dictionary.put(oreName, val); }
	
	public void addRegNameValue(String name, int dmg, float val)
	{
		RegNameValue v = new RegNameValue();
		v.name = name;
		v.damage = dmg;
		v.value = val;
		registry_name.add(v);
	}
}