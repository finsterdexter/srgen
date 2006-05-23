package cz.eowyn.srgen.exporttoken;

import cz.eowyn.srgen.model.PlayerCharacter;

public abstract class Token {

	public abstract String getTokenName();

	public abstract String getToken(String tokenSource, PlayerCharacter pc);

	//public String getToken(String tokenSource, PlayerCharacter pc, ExportHandler eh) {
	//	return getToken(tokenSource, pc);
	//}
}
