package cz.eowyn.srgen.exporttoken;

import cz.eowyn.srgen.model.PlayerCharacter;

public class PoolsToken extends Token {
	
	public static final String TOKENNAME = "POOLS";
	
	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		int stat = 0;
		
		if (tokenSource.equals("POOLS.KARMA")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_KARMA);
		}
		else if (tokenSource.equals("POOLS.COMBAT")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_COMBAT);
		}
		else if (tokenSource.equals("POOLS.CONTROL")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_CONTROL);
		}
		else if (tokenSource.equals("POOLS.HACKING")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_HACKING);
		}
		else if (tokenSource.equals("POOLS.SPELL")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_SPELL);
		}
		else if (tokenSource.equals("POOLS.TASK")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_TASK);
		}
		else if (tokenSource.equals("POOLS.COMBAT_MOD")) {
			stat = pc.getInt( PlayerCharacter.STAT_POOL_COMBAT_MOD);
		}
		else {
			return tokenSource;
		}
		
		return String.valueOf (stat);
	}

}
