package cz.eowyn.srgen.io;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.lang.reflect.*;

import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.RepositoryObject;
import cz.eowyn.srgen.model.RepositoryTree;

public class NSRCG3_DAT_Loader extends Loader {

	protected class Record {
		private String[] values;
		private String format_key;
		
		public Record (String[] fields) {
    		fields[0] = fields[0].substring (4);
    		int format_pos = fields[0].length();
    		
    		while (true) {
    			char c = fields[0].charAt (format_pos - 1);
    			if (c < '0' || c > '9') {
    				break;
    			}
    			format_pos--;
    		}
    		format_key = fields[0].substring (format_pos);
    		if (format_key.contentEquals("")) {
    			format_key = "1";
    		}
    		fields[0] = fields[0].substring (0, format_pos).trim();
			
    		values = fields;
		}

		public String getName () {
			return values[0];
		}
		
		public Map getValuesMap () {
    		NSRCG3_Format format = (NSRCG3_Format) format_map.get (format_key);
    		return format.getValuesMap (values);
		}
	}
	

	protected RepositoryTree last_group = null; 
	protected Hashtable format_map = null;
	
	public NSRCG3_DAT_Loader (Repository repository) {
		super (repository);
		// TODO Auto-generated constructor stub
	}


	public void ImportFile (String filename, RepositoryTree root, Class objectClass, Hashtable formats) throws IOException {
		last_group = root;
		
		// if we were given format map, use it, else create a temporary one
		if (formats == null) {
			format_map = new Hashtable ();
		} else {
			format_map = formats;
		}

		// create constructor class to generate instances of SRGenRepositoryObject's subclasses
		Class[] argTypes = new Class[] { String.class, Map.class }; 
		Constructor constructor;
		try {
			constructor = objectClass.getConstructor (argTypes);
		} catch (NoSuchMethodException e) {
			System.err.println(e);
			return;
		}
		
		
		try {
	    	File srcfile = new File (filename);
	        LineNumberReader src = new LineNumberReader (new FileReader (srcfile));


	        String line;
	        while ((line = src.readLine ()) != null) {
	        	if (line.length() == 0 || line.charAt (0) == '!') {
	        		continue;
	        	}

	        	String[] fields = line.split ("\\|", -1);
	        	if (fields[0].length() < 3 || fields[0].charAt(1) != '-') {
		        	System.err.println(filename + ":" + String.valueOf (src.getLineNumber()) + ": Invalid line format: " + line);
	        		continue;
	        	}

	        	if (fields[0].charAt (0) == '0') {
	        		createFormat (fields);
	        	} 
	        	else if (fields[0].charAt(2) == '*') {
	        		Record record = new Record (fields);

	        		// instantiate RepositoryObject's subclass given as objectClass parameter
	        		RepositoryObject obj;
	        		try {
	        			obj = (RepositoryObject) constructor.newInstance (new Object[] {record.getName(), record.getValuesMap()});
	        		} catch (Exception e) {
	        			System.out.println(e);
	        			return;
	        		}
	        	    addObject (obj);
	        	}
	        	else {
	        		int level = fields[0].charAt (0) - '0';
	        		fields[0] = fields[0].substring(2);
	        		RepositoryTree grp = createGroup (fields[0], level);
	        		addGroup (grp);
	        	}
	        	
	        }
	        src.close();
	    } catch (IOException e) {
	    	//blocker.setBlocked(false);
	    	System.err.println("Can't load file: " + filename);
	    	throw e;
	    }
		
	}

	
	public void createFormat (String[] fields) {
		String format_key = fields[0].substring(2);
		format_map.put (format_key, new NSRCG3_Format (fields));
	}
	
	public RepositoryTree createGroup (String Name, int Level) {
		return new RepositoryTree (Name, Level);
	}

	public void addGroup (RepositoryTree grp) {
		while (last_group.getLevel () >= grp.getLevel()) {
			last_group = (RepositoryTree) last_group.getParent();
		}
		grp.setLevel (last_group.getLevel () + 1);
		last_group.addChild (grp);
		last_group = grp;
	}
	
	public void addObject (RepositoryObject obj) {
		last_group.addChild (obj);
	}
}
