package cz.eowyn.srgen;

import java.util.ArrayList;

import javax.swing.UIManager;
//import net.sourceforge.napkinlaf.NapkinLookAndFeel;

public class AppInit {

	/**
	 * @param args
	 */
	public static void main (String[] args) {
		boolean useGUI = true;
		boolean isExport = false;
		String exportFile = null;
		ArrayList <String>charFiles = new ArrayList<String> (5);

		Config.loadConfig ();
		Generator.init ();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals ("-e") || args[i].equals ("--export")) {
				if (i + 1 == args.length) {
					System.err.println ("Missing export file param");
					print_help ();
					System.exit (1);
				}
				isExport = true;
				useGUI = false;
				exportFile = args[i+1];
				i++;
				continue;
			}
			if (args[i].equals ("-h") || args[i].equals ("--help")) {
				print_help ();
				System.exit (0);
			}
			charFiles.add (args[i]);
		}

		if (useGUI) {
			String lookAndFeel = Config.get("srgen.gui.lookAndFeel");
			try {
				if (lookAndFeel.equals ("system"))
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				else 
					UIManager.setLookAndFeel(lookAndFeel);
				
				//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				//UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
				//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			} catch (Exception e) {
				System.err.println ("L&F exception...");
				e.printStackTrace ();
				; // Ignore exception because we can't do anything.  Will use default.
			}

			Generator.showSplash ();
		}

		Generator.loadSources();
		
		for (int i = 0; i < charFiles.size(); i++) {
			Generator.loadCharacter (args[i]);
		}
		
		if (Generator.getCharacters ().size() == 0) { 
			Generator.newCharacter ();
		}
	
		if (isExport) {
			Generator.exportCharacter (Generator.getPlayerCharacter (0), "templates/pok.tpl", exportFile);
			System.exit (0);
		}

		Generator.startGUI ();
	}

	public static void print_help () {
		System.out.println (
				"Usage:\n" +
				"    srgen [options] [character ...]\n" +
				"\n" +
				"Options:\n" +
				"    -e, --export <file_name>     Export first character to the file specified\n" +
				"    -h, --help                   Print this help\n");
	}
}
