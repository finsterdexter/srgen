package cz.eowyn.srgen.gui;

import java.util.Iterator;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import cz.eowyn.srgen.model.RepositoryObject;


public class ObjectDetailPane extends JScrollPane {
	protected JEditorPane  editor = null;

	public ObjectDetailPane(JEditorPane editor) {
    	super(editor);
    	this.editor = editor;
	}	
	
	public ObjectDetailPane() {
    	this(new JEditorPane());

    	editor.setEditable(false);
    	editor.setEditorKit(new HTMLEditorKit ());
    	editor.setText ("");
	}

	public void displayObject(RepositoryObject obj) {
   	    //Object nodeInfo = node.getUserObject();
   	    if (obj != null) {
   	    	//System.err.println (obj.getName ());
   	    	Map values = obj.getValues ();
   	    	Iterator keyIterator = values.keySet().iterator();
   	    	//String text = "<h1>" + obj.getName() + "\u00A5</h1>\n<table>\n";
   	    	String text = "<h1>" + obj.getName() + "</h1>\n<table>\n";
   	    	while (keyIterator.hasNext ()) {
   	    		String key = (String) keyIterator.next();
   	    		String val = (String) values.get (key);
   	    		text += "<tr><td><b>" + key + "</b></td><td>" + val + "</td></tr>\n";
   	    		//text = "<tr><td><b>" + key + "</b></td><td>";
   	    	}
   	    	if (obj.getName().contains("->")) {
   	    		text += "<tr><td><b>Choice:</b></td><td><form><input type=text></form></td></tr>\n";
   	    	}
   	    	text += "</table>";
   	    	//System.err.println (text);
   	    	
   	    	// Set text and scroll to the top
   	    	editor.setEditable(true);
   	    	editor.setText (text);
   			editor.setCaretPosition(0);
   			editor.setEditable(false);
   	    	//selected_node = node;
   	    } else {
   	    	System.err.println ("Null");
   	    	editor.setText ("");
   	    	//selected_node = null;
   	    }
 		
	}
}
