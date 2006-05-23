package cz.eowyn.srgen.lisp;

abstract public class LispObject {
	public static final int TYPE_NIL = 0;
	public static final int TYPE_INT = 1;
	public static final int TYPE_STRING = 2;
	public static final int TYPE_IDENT = 3;
	public static final int TYPE_LIST = 4;
	
	protected int type = TYPE_NIL;

	LispObject (int type) {
		this.type = type;
	}
}
