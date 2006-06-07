/**
 * 
 */
package cz.eowyn.srgen.io;

import java.util.LinkedHashMap;
import java.util.Map;

public class NSRCG3_Format {
	private String[] keys;
	private int length;

	public NSRCG3_Format (String[] fields) {
		keys = fields;
		length = Integer.parseInt (fields[2]);
	}
	
	public Map getValuesMap (String[] values) {
		Map value_map = new LinkedHashMap ();

		for (int i = 0; i < length; i++) {
			try {
				value_map.put (keys[i + 3], values[i + 1]);
			} catch (Exception e) {
				value_map.put (keys[i + 3], "");
			}
		}

		return value_map;
	}

}