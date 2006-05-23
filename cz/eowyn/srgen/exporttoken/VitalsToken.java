package cz.eowyn.srgen.exporttoken;

import cz.eowyn.srgen.model.PlayerCharacter;

public class VitalsToken extends Token {

	public static final String TOKENNAME = "VITALS";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		
		if (tokenSource.equals("VITALS.EYES")) {
			ret = pc.getString (pc.STR_EYES);
		}
		else if (tokenSource.equals("VITALS.HAIR")) {
			ret = pc.getString (pc.STR_HAIR);
		}
		else if (tokenSource.equals("VITALS.HEIGHT")) {
			ret = pc.getString (pc.STR_HEIGHT) + " cm";
		}
		else if (tokenSource.equals("VITALS.WEIGHT")) {
			ret = pc.getString (pc.STR_WEIGHT) + " kg";
		}
		else if (tokenSource.equals("VITALS.BIRTHDATE")) {
			ret = pc.getString (pc.STR_BIRTHDATE);
		}
		else if (tokenSource.equals("VITALS.BIRTHPLACE")) {
			ret = pc.getString (pc.STR_BIRTHPLACE);
		}
		else if (tokenSource.equals("VITALS.RACE")) {
			ret = pc.getString (pc.STR_RACE);
		}
		else if (tokenSource.equals("VITALS.AGE")) {
			ret = pc.getString (pc.STR_AGE);
		}
		else if (tokenSource.equals("VITALS.SEX")) {
			ret = pc.getString (pc.STR_SEX);
		}
		return ret;
	}

}
