package cz.eowyn.srgen.model;

import java.util.Map;
import javax.swing.tree.TreeNode;


public abstract class RepositoryObject implements TreeNode {
	private String name = null;
	private Map<String, String> values = null;
	private String custom = null;
	private float amount = 0;
	
	private RepositoryObject parent = null;
	
	public RepositoryObject () {
	}
	
	public RepositoryObject (String name, Map<String, String> values) {
		this.name = name;
		this.values = values;
	}

	public String toString () {
		return getName ();
	}

	public String getName () {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public Map<String, String> getValues () {
		return values;
	}

	public void setParent (RepositoryTree parent) {
		this.parent = parent;
	}

	public void print (String indent) {
		System.out.println(indent + toString ());
	}

	public String getValue (String key)
	{
		if (key.equals("Name"))
			return getName ();
		
		if (values.containsKey(key)) {
			return values.get(key);
		} else {
			return null;
		}
	}
	
	/**
	 * @param custom the custom to set
	 */
	public void setCustom(String custom) {
		this.custom = custom;
	}

	/**
	 * @return the custom
	 */
	public String getCustom() {
		return custom;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	// TreeNode interface
	public boolean isLeaf () {
		return true;
	}
	
	public TreeNode getParent () {
		return parent;
	}
	
	public boolean getAllowsChildren () {
		return false;
	}
	
	public int getChildCount () {
		return 0;
	}
	
	public java.util.Enumeration children () {
		return null;
	}
	
	public TreeNode getChildAt (int index) {
		return null;
	}
	
	public int getIndex (TreeNode obj) {
		return -1;
	}
}
