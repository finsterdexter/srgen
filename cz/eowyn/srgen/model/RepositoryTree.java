package cz.eowyn.srgen.model;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ArrayList;
import javax.swing.tree.TreeNode;

public class RepositoryTree extends RepositoryObject {

	private ArrayList child_list = null;
	private int level = 0;
	
	public RepositoryTree (String Name, int level) {
		super (Name, null);
		child_list = new ArrayList (10);
		this.level = level;
	}

	public void addChild (RepositoryObject child) {
		child_list.add (child);
		child.setParent (this);
	}
	
	public int getLevel () {
		return level;
	}

	public void setLevel (int level) {
		this.level = level;
	}
	
	public void print (String indent) {
		System.out.println (indent + " [" + String.valueOf(level) + "] " + toString ());
		for (int i = 0; i < child_list.size (); i++) {
			RepositoryObject obj = (RepositoryObject) child_list.get (i);
			obj.print (indent + "  ");
		}
	}
	// TreeNode interface
	public boolean isLeaf () {
		return false;
	}
	
	public boolean getAllowsChildren () {
		return true;
	}
	
	public int getChildCount () {
		return child_list.size ();
	}
	
	public Enumeration children () {
		return Collections.enumeration (child_list);
	}
	
	public TreeNode getChildAt (int index) {
		return (RepositoryObject) child_list.get (index);
	}
	
	public int getIndex (TreeNode obj) {
		return child_list.indexOf (obj);
	}
	
}
