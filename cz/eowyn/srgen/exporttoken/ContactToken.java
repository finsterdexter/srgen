package cz.eowyn.srgen.exporttoken;

import java.util.StringTokenizer;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;

import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.model.RepositoryObject;

public class ContactToken extends Token {

	public static final String TOKENNAME = "CONTACT";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken (String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		StringTokenizer aTok = new StringTokenizer (tokenSource, ".");
		RepositoryList list = pc.getContact_List ();

		aTok.nextToken ();
		String aString = aTok.nextToken ();
		
		if (aString.equals ("COUNT")) {
			return String.valueOf (list.size()); 
		}
		
		int edgeIndex = 0;

		try {
			edgeIndex = Integer.parseInt (aString);
		} 
		catch (NumberFormatException e) { 
			return ret;
		}
		
		RepositoryObject edge = (RepositoryObject) list.get (edgeIndex);

		if (aTok.hasMoreTokens ()) {		
			String modifier = aTok.nextToken ();
			if (modifier.equals ("NAME")) {
				ret = edge.getName ();
			}
			else if (modifier.equals("RACE")) {
				ret = edge.getValue("Race");
			}
			else if (modifier.equals("COST")) {
				ret = edge.getValue("$Cost");
			}
			else if (modifier.equals("OCCUPATION")) {
				ret = edge.getValue("Occupation");
			}
		}
		
		return ret;
	}

}
