package cz.eowyn.srgen.exporttoken;

import java.util.StringTokenizer;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;

import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.model.RepositoryObject;

public class CyberwareToken extends Token {

	public static final String TOKENNAME = "CYBERWARE";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken (String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		StringTokenizer aTok = new StringTokenizer (tokenSource, ".");
		RepositoryList list = pc.getCyberware_List ();

		aTok.nextToken ();
		String aString = aTok.nextToken ();
		
		if (aString.equals ("COUNT")) {
			return String.valueOf (list.size()); 
		}
		
		int index = 0;

		try {
			index = Integer.parseInt (aString);
		} 
		catch (NumberFormatException e) { 
			return ret;
		}
		
		RepositoryObject obj = (RepositoryObject) list.get (index);

		if (aTok.hasMoreTokens ()) {		
			String modifier = aTok.nextToken ();
			if (modifier.equals ("NAME")) {
				ret = obj.getName ();
			}
			else if (modifier.equals ("ESSCOST")) {
				ret = obj.getValue ("EssCost");
			}
			else if (modifier.equals ("COST")) {
				ret = obj.getValue ("$Cost");
			}
			else if (modifier.equals ("AVAILABILITY")) {
				ret = obj.getValue ("Availability");
			}
			else if (modifier.equals ("NOTES")) {
				ret = obj.getValue ("Notes");
			}
		}
		
		return ret;
	}

}
