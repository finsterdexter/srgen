package cz.eowyn.srgen.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RepositoryList<R extends RepositoryObject> extends ArrayList<R> {

	private PlayerCharacter pc;
	private ArrayList<PCAssetListener> listeners = null;
	
	public RepositoryList(PlayerCharacter pc) {
		super();
		this.pc = pc;
		listeners = new ArrayList<PCAssetListener> (2);
	}
	
	public boolean add(R obj) {
		return add(obj, null, 0);
	}
	
	public boolean add(R obj, String custom, float value) {
		if (super.add(obj)) {
			firePCAssetChanged();
			return true;
		} else
			return false;
	}
	
	public void addListener (PCAssetListener ls) {
		if (! listeners.contains(ls)) {
			listeners.add (ls);
		}
	}
	
	public void removeListener (PCAssetListener ls) {
		listeners.remove(ls);
	}
	
	protected void firePCAssetChanged () {
		System.err.println("RepositoryList::firePCAssetChanged");
		Iterator<PCAssetListener> iter = listeners.iterator();
		while (iter.hasNext ()) {
			System.err.println("    listener ...");
			
			PCAssetListener ls = iter.next ();
			try {
				ls.pcAssetChanged(pc, this);
			}
			catch (Exception e) {
				// FIXME: don't catch or at least not all
			}
		}
	}

}
