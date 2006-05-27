package cz.eowyn.srgen.exporttoken;

import cz.eowyn.srgen.model.PlayerCharacter;

public class KarmaToken extends Token {

	public static final String TOKENNAME = "KARMA";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		
		if (tokenSource.equals("KARMA.TOTAL")) {
			ret = String.valueOf (pc.getStat (PlayerCharacter.STAT_KARMA_TOTAL));
		}
		else if (tokenSource.equals("KARMA.SPENT")) {
			ret = String.valueOf (pc.getStat (PlayerCharacter.STAT_KARMA_SPENT));
		}
		else if (tokenSource.equals("KARMA.UNSPENT")) {
			ret = String.valueOf (pc.getStat (PlayerCharacter.STAT_KARMA_TOTAL) - pc.getStat (PlayerCharacter.STAT_KARMA_SPENT));
		}
		else if (tokenSource.equals("KARMA.FREE")) {
			ret = String.valueOf (pc.getStat (PlayerCharacter.STAT_KARMA_TOTAL) - pc.getStat (PlayerCharacter.STAT_KARMA_SPENT) - pc.getStat (PlayerCharacter.STAT_POOL_KARMA) + 1);
		}
		return ret;
	}

}
