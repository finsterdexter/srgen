package cz.eowyn.srgen.exporttoken;

import java.util.StringTokenizer;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;

import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.model.RepositoryObject;

public class SkillToken extends Token {

	public static final String TOKENNAME = "SKILL";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		StringTokenizer aTok = new StringTokenizer (tokenSource, ".");
		RepositoryList list = pc.getSkill_List();

		aTok.nextToken();
		String aString = aTok.nextToken();
		
		if (aString.equals ("COUNT")) {
			return String.valueOf (list.size()); 
		}
		
		int skillIndex = 0;

		try {
			skillIndex = Integer.parseInt (aString);
		} 
		catch (NumberFormatException e) { 
			return ret;
		}
		
		RepositoryObject skill = (RepositoryObject) list.get(skillIndex);

		if (aTok.hasMoreTokens()) {		
			String modifier = aTok.nextToken();
			if (modifier.equals("NAME")) {
				ret = skill.getName();
			}
			else if (modifier.equals("ST")) {
				ret = skill.getValue("st");
			}
		}
		
		return ret;
	}

}
