package cz.eowyn.srgen.exporttoken;

import java.util.StringTokenizer;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;

import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.model.RepositoryObject;

public class VehicleToken extends Token {

	public static final String TOKENNAME = "VEHICLE";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		StringTokenizer aTok = new StringTokenizer (tokenSource, ".");
		RepositoryList list = pc.getVehicle_List ();

		aTok.nextToken ();
		String aString = aTok.nextToken ();
		
		if (aString.equals ("COUNT")) {
			return String.valueOf (list.size ()); 
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
			else if (modifier.equals ("SIG_AUTONAV")) {
				ret = obj.getValue ("Sig/Autonav");
			}
			else if (modifier.equals ("SPEED_ACCEL")) {
				ret = obj.getValue ("Speed/Accel");
			}
			else if (modifier.equals ("HANDLING")) {
				ret = obj.getValue ("Handling");
			}
			else if (modifier.equals ("PILOT_SENSOR")) {
				ret = obj.getValue ("Pilot/Sensor");
			}
			else if (modifier.equals ("BODY_ARMOR")) {
				ret = obj.getValue ("Body/Armor");
			}
			else if (modifier.equals ("CARGO_LOAD")) {
				ret = obj.getValue ("Cargo/Load");
			}
			else if (modifier.equals ("NOTES")) {
				ret = obj.getValue ("Notes");
			}
		}
		
		return ret;
	}

}
