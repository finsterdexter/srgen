package cz.eowyn.srgen.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.RepositoryTree;
import cz.eowyn.srgen.model.SourceBook;


public class NSRCG3_Books_Loader extends Loader {

	public NSRCG3_Books_Loader (Repository repository) {
		super (repository);
	}

	public void ImportFile (String filename, RepositoryTree root, Class objectClass, Hashtable sources_map) throws IOException {

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

	        	String[] fields = line.split (";", -1);
	        	if (fields.length != 2) {
		        	System.err.println(filename + ":" + String.valueOf (src.getLineNumber()) + ": Invalid line format: " + line);
	        		continue;
	        	}

	        	boolean load = false;
	        	
	        	if (fields[0].charAt(0) == '*') {
	        		load = true;
	        		fields[0] = fields[0].substring(1);
	        	}
	        	LinkedHashMap values = new LinkedHashMap ();
	        	values.put ("Name", fields[0]);
	        	values.put ("Key", fields[1]);
	        	values.put ("LoadOnStartup", load ? "Y" : "N");

	        	SourceBook source = new SourceBook (fields[0], values);
	        	root.addChild (source);
	        	sources_map.put (fields[1], source);
	        	
	        }
	        src.close();
	    } catch (IOException e) {
	    	//blocker.setBlocked(false);
	    	System.err.println("Can't load file: " + filename);
	    	throw e;
	    }
	}

}
