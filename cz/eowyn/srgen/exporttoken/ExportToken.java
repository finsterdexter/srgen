package cz.eowyn.srgen.exporttoken;

import java.text.DateFormat;
import java.util.Date;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;

import cz.eowyn.srgen.model.PlayerCharacter;

public class ExportToken extends Token {

	public static final String TOKENNAME = "EXPORT";

	public String getTokenName() {
		return TOKENNAME;
	}

	public String getToken(String tokenSource, PlayerCharacter pc) {
		String ret = tokenSource;
		
		if (tokenSource.equals("EXPORT.DATE")) {
			ret = DateFormat.getDateInstance().format(new Date());
		}
		else if (tokenSource.equals("EXPORT.TIME")) {
			ret = DateFormat.getTimeInstance().format(new Date());
		}
		else if (tokenSource.equals("EXPORT.VERSION")) {
//			try {
//				ResourceBundle d_properties = ResourceBundle.getBundle("pcgen/gui/prop/PCGenProp");
//				ret = d_properties.getString("VersionNumber");
//			}
//			catch (MissingResourceException mre) {
//				//Should this be ignored?
//			}
			// FIXME: this is a hack
			ret = "0.1";
		}
		
		return ret;
	}

}
