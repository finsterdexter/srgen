package cz.eowyn.srgen;

import cz.eowyn.srgen.gui.SRGenWindow;
import cz.eowyn.srgen.gui.SplashScreen;
public class AppInit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Generator generator = new Generator ();
		if (args.length > 0) {
			generator.loadCharacter (args[0]);
		} else {
			generator.newCharacter ();
		}
		//SRGenWindow window = new SRGenWindow (generator);
		//window.show ();
	}

}