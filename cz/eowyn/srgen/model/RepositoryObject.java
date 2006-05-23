package cz.eowyn.srgen.model;

import java.util.Map;

import javax.swing.tree.TreeNode;

public abstract class RepositoryObject implements TreeNode {
	private String Name = null;
	private Map values = null;
	
	private RepositoryObject parent = null;
	
	public RepositoryObject () {
	}
	
	public RepositoryObject (String Name, Map values) {
		this.Name = Name;
		this.values = values;
	}

	public String toString () {
		return getName ();
	}

	public String getName () {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public Map getValues () {
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
		if (values.containsKey(key)) {
			return (String) values.get(key);
		} else {
			return null;
		}
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
