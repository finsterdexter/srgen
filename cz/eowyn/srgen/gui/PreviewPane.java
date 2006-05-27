/**
 * 
 */
package cz.eowyn.srgen.gui;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;

import cz.eowyn.srgen.model.PlayerCharacter;



class PreviewPane extends JPanel {
	protected JEditorPane  editor = null;

	public PreviewPane (PlayerCharacter pc) {
		super ();

		this.add (getHTMLPane ());

		try {
			editor.setPage ("file:/home/benkovsk/export.html");
		} catch (IOException e) {
			// ...
		}
	}

	public JScrollPane getHTMLPane () {
    	editor = new JEditorPane();
    	editor.setEditable (false);
    	editor.setEditorKit (new HTMLEditorKit ());
    	editor.setText ("");

		JScrollPane pane = new JScrollPane (editor);

		return pane;
	}

}