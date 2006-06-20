package cz.eowyn.srgen.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.RepositoryObject;
import cz.eowyn.srgen.model.RepositoryTree;
import cz.eowyn.srgen.model.Skill;


public class NSRCG3_Skills_Loader extends Loader {

	protected RepositoryTree last_group = null; 
	protected RepositoryObject last_object = null; 

	public NSRCG3_Skills_Loader (Repository repository) {
		super (repository);
	}

	public void ImportFile (String filename, RepositoryTree root, Class objectClass, Hashtable sources_map) throws IOException {
		last_group = root;
		last_object = null;

		// if we were given sources map, use it, else create a temporary one
		if (sources_map == null) {
			sources_map = new Hashtable ();
		}		
		
		try {
	    	File srcfile = new File (filename);
	        LineNumberReader src = new LineNumberReader (new FileReader (srcfile));


	        String line;
	        while ((line = src.readLine ()) != null) {
	        	if (line.length() == 0 || line.charAt (0) == '!') {
	        		continue;
	        	}

	        	if (line.charAt (0) == '#') {
	        		line = line.substring (1);
	        		RepositoryTree grp = createGroup (line, 1);
	        		addGroup (grp);
	        	}
	        	else if (line.charAt (0) == ' ') {
	        		line = line.substring (1);
		        	String[] fields = line.split ("\\|", -1);
		        	String name = last_object.getName() + "/" + fields[0];
		        	
		        	LinkedHashMap values = new LinkedHashMap ();
		        	values.put ("Name", name);
		        	if (fields.length == 3) {
		        		values.put ("Book.Page", fields[1]);
		        		values.put ("Notes", fields[2]);
		        	}
		        	Skill obj = new Skill (name, values);
		        	addObject (obj);
	        	}
	        	else {
		        	String[] fields = line.split ("\\|", -1);
		        	if (fields.length != 3) {
			        	System.err.println(filename + ":" + String.valueOf (src.getLineNumber()) + ": Invalid line format: " + line + "<" + String.valueOf (fields.length) + ">");
		        		continue;
		        	}
	
		        	LinkedHashMap values = new LinkedHashMap ();
		        	values.put ("Name", fields[0]);
		        	values.put ("Book.Page", fields[1]);
		        	values.put ("Notes", fields[2]);
	
		        	Skill obj = new Skill (fields[0], values);
		        	addObject (obj);
		        	last_object = obj;
	        	}
	        }
	        src.close();
	    } catch (IOException e) {
	    	//blocker.setBlocked(false);
	    	System.err.println("Can't load file: " + filename);
	    	throw e;
	    }
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
