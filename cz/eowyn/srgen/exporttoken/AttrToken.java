package cz.eowyn.srgen.exporttoken;

import java.util.StringTokenizer;

import cz.eowyn.srgen.model.PlayerCharacter;

// ATTR.BOD
// ATTR.BOD.BASE
// ATTR.BOD.M1
// ATTR.BOD.M2
// ATTR.BOD.M3

// ATTR.QCK...
// ATTR.STR...
// ATTR.CHR...
// ATTR.INT...
// ATTR.WIL...

public class AttrToken extends Token {

	public static final String TOKENNAME = "ATTR";
	
	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		StringTokenizer aTok = new StringTokenizer (tokenSource, ".");
		aTok.nextToken ();

		String longName = "";
		String shortName = "";
		int baseAttr = 0;
		int modifier = PlayerCharacter.STAT_BOD - PlayerCharacter.STAT_BOD_BASE;


		if (aTok.hasMoreTokens()) {
			String attrName = aTok.nextToken ();

			if (attrName.equals("BOD") || attrName.equals("0")) {
				baseAttr = PlayerCharacter.STAT_BOD_BASE;
				longName = "Body";
				shortName = "BOD";
			}
			else if (attrName.equals("QCK") || attrName.equals("1")) {
				baseAttr = PlayerCharacter.STAT_QCK_BASE;
				longName = "Quickness";
				shortName = "QCK";
			}
			else if (attrName.equals("STR") || attrName.equals("2")) {
				baseAttr = PlayerCharacter.STAT_STR_BASE;
				longName = "Strength";
				shortName = "STR";
			}
			else if (attrName.equals("CHR") || attrName.equals("3")) {
				baseAttr = PlayerCharacter.STAT_CHR_BASE;
				longName = "Charisma";
				shortName = "CHR";
			}
			else if (attrName.equals("INT") || attrName.equals("4")) {
				baseAttr = PlayerCharacter.STAT_INT_BASE;
				longName = "Intelligence";
				shortName = "INT";
			}
			else if (attrName.equals("WIL") || attrName.equals("5")) {
				baseAttr = PlayerCharacter.STAT_WIL_BASE;
				longName = "Willpower";
				shortName = "WIL";
			}
			else {
				//...
			}

			if (aTok.hasMoreTokens()) {
				String modName = aTok.nextToken ();

				if (modName.equals("BASE")) {
					modifier = 0;
				}
				else if (modName.equals("M1")) {
					modifier = PlayerCharacter.STAT_BOD_M1 - PlayerCharacter.STAT_BOD_BASE;
				}
				else if (modName.equals("M2")) {
					modifier = PlayerCharacter.STAT_BOD_M2 - PlayerCharacter.STAT_BOD_BASE;
				}
				else if (modName.equals("M3")) {
					modifier = PlayerCharacter.STAT_BOD_M3 - PlayerCharacter.STAT_BOD_BASE;
				}
				else if (modName.equals("LONGNAME")) {
					return longName; 
				}
				else if (modName.equals("SHORTNAME")) {
					return shortName; 
				}
			}
			
			ret = String.valueOf (pc.getInt (baseAttr + modifier));
		}
		return ret;
	}

}
