package cz.eowyn.srgen.lisp;

import java.util.ArrayList;

public class LispList extends LispObject {

	protected ArrayList children = new ArrayList ();
	
	public LispList() {
		super (LispObject.TYPE_LIST);
		// TODO Auto-generated constructor stub
	}

	public void addChild (LispObject l) {
		children.add (l);
	}
}
