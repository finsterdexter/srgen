package cz.eowyn.srgen.exporttoken;

import cz.eowyn.srgen.model.PlayerCharacter;

// NAME

public class NameToken extends Token {

	public static final String TOKENNAME = "NAME";
	
	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		if (tokenSource.equals("NAME.CHAR")) {
			return pc.getString (pc.STR_CHARNAME);
		}
		else if (tokenSource.equals("NAME.REAL")) {
			return pc.getString (pc.STR_REALNAME);
		}
		else if (tokenSource.equals("NAME.PLAYER")) {
			return pc.getString (pc.STR_PLAYERNAME);
		}
		else if (tokenSource.equals("NAME.STREET")) {
			// FIXME: we should return street name based on index given 
			return pc.getString (pc.STR_STREETNAME1);
		}
		else if (tokenSource.equals("NAME.ARCHETYPE")) {
			return pc.getString (pc.STR_ARCHETYPE);
		}
		else {
			return pc.getString (pc.STR_CHARNAME);
		}
	}

}
