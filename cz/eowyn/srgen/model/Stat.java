package cz.eowyn.srgen.model;

public class Stat {
	final int index;
	final String key;
	public final int type;
	//Object value;
	final String label;
	final String description;
	
	public Stat (int index, String key, int type, String label, String description) {
		this.index = index;
		this.key = key;
		this.type = type;
		this.label = label;
		this.description = description;
	}
}
