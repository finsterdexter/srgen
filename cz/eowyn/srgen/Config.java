package cz.eowyn.srgen;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.SwingConstants;

//import pcgen.core.Globals;
//import pcgen.core.SettingsHandler;

public class Config {	
	private static Properties options;
	//ArrayList contacts_files = null;
	
	private static int pcTabPlacement;
	private static int maximumEorF;
	
	private static ArrayList<String[]> sources = new ArrayList<String[]> ();
	
	
//	private static File pcgenSystemDir = new File(Globals.getDefaultPath() + File.separator + "system"); //$NON-NLS-1$
//	private static File pcgenThemePackDir = new File(Globals.getDefaultPath() + File.separator + "lib" + File.separator //$NON-NLS-1$
//		    + "themes"); //$NON-NLS-1$
//	private static File pcgenOutputSheetDir = new File(Globals.getDefaultPath() + File.separator + "outputsheets"); //$NON-NLS-1$

	private Config() {
		super();
		// TODO Auto-generated constructor stub
		
		//contacts_files = new ArrayList ();
	}

	/**
	 * Get a writable path for storing files
	 * First check to see if it's been set in-program
	 * Then check user home directory
	 * Else use directory pcgen started from
	 */
	private static String getFilePath (final String aString) {
		//final String fType = SettingsHandler.getFilePaths();
		final String fType = "user";

		if ((fType == null) || fType.equals("srgen")) {
			// we are either running SRGen for the first
			// time or user wants default file locations
			return System.getProperty("user.dir") + File.separator + aString;
		}
		else if (fType.equals("user")) {
			// use the users "home" directory + .srgen
			return System.getProperty("user.home") + File.separator + ".srgen" + File.separator + aString;
		}
		else {
			// use the specified directory
			return fType + File.separator + aString;
		}
	}

	static String getOptionsPath () {
		String aPath;

		// first see if it was specified on the command line
		aPath = System.getProperty ("srgen.options");

		if (aPath == null) {
			aPath = getFilePath ("options.ini");
		}

		//return expandRelativePath(aPath);
		return aPath;
	}

	public static boolean loadConfig () {
		if (options != null)
			return true;
		
		Properties defaults = new Properties ();
		try {
			InputStream in = Config.class.getClassLoader ().getResourceAsStream ("srgen.properties");
			defaults.load(in);
			in.close();
		} catch (IOException e) {
			System.err.println ("Can't load default opts $CP/srgen.properties");
			// FIXME: make it an error
		}
		options = new Properties (defaults);

		
		FileInputStream in = null;
		
		String filename = getOptionsPath ();
		try {
			in = new FileInputStream (filename);
			options.load (in);
		}
		catch (IOException e) {
			// Not an error, this file may not exist yet
			//Logging.debugPrint(PropertyFactory.getString("SettingsHandler.will.create.filter.ini")); //$NON-NLS-1$
			System.err.println ("Can't load opts " + filename);
			saveConfig ();
			return false;
		}
		
		try {
			if (in != null) {
				in.close();
			}
		}
		catch (IOException ex) {
			//Not much to do about it...
			System.err.println ("Can't close opts " + filename);
		}
	
	
		pcTabPlacement = tabPlacementFromString (getOptions ().getProperty ("srgen.gui.pcTabPlacement", "bottom"));
		maximumEorF = getInt ("srgen.rules.maximumEorF");
		
		for (int i=0; ; i++) {
			String s = options.getProperty ("srgen.rules.source." + i, null);
			if (s == null) break;
			String[] src = s.split (":", 2);
			src[0] = src[0].toLowerCase();
			sources.add(src);
		}

		return true;
	}

	public static boolean saveConfig () {
		// Globals.getFilterPath() will _always_ return a string
		//final String filterLocation = Globals.getFilterPath();

		getOptions ().setProperty ("srgen.gui.pcTabPlacement", tabPlacementToString (pcTabPlacement));
		setInt ("srgen.rules.maximumEorF", maximumEorF);
		
		//final String fType = getFilePaths();
		//final String fType = "user";
		final String aLoc = getFilePath ("");
		File file = new File (aLoc);

		if (! file.exists()) {
			file.mkdir ();
			System.err.println ("Creating dir " + aLoc);
		}
		else if (! file.isDirectory()) {
			System.err.println ("Not a dir: " + aLoc);
			return false;
		}
		
		String filename = getOptionsPath ();
		FileOutputStream out = null;

		try {
			out = new FileOutputStream (filename);
			options.store (out, "SRGen options");
		}
		catch (IOException e) {
			// Not an error, this file may not exist yet
			//Logging.debugPrint(PropertyFactory.getString("SettingsHandler.will.create.filter.ini")); //$NON-NLS-1$
			System.err.println ("Can't save opts " + filename);
			return false;
		}
		
		try {
			if (out != null) {
				out.close();
			}
		}
		catch (IOException ex) {
			//Not much to do about it...
			System.err.println ("Can't close opts " + filename);
		}
		
		return true;
	}

	protected static Properties getOptions () {
		return options;
	}

	protected static int getInt (String key) {
		return Integer.parseInt (options.getProperty (key));		
	}
	
	protected static void setInt (String key, int value) {
		getOptions ().setProperty (key, String.valueOf (value));		
	}
	
	public static int tabPlacementFromString (String place) {
		int ret;
		
		if (place.equalsIgnoreCase ("bottom")) {
			ret = SwingConstants.BOTTOM;
		}
		else if (place.equalsIgnoreCase ("left")) {
			ret = SwingConstants.LEFT;
		}
		else if (place.equalsIgnoreCase ("right")) {
			ret = SwingConstants.RIGHT;
		}
		else {
			ret = SwingConstants.TOP;
		}
		
		return ret;
	}
	
	public static String tabPlacementToString (int place) {
		String ret;
		
		if (place == SwingConstants.BOTTOM) {
			ret = "bottom";
		}
		else if (place == SwingConstants.LEFT) {
			ret = "left";
		}
		else if (place == SwingConstants.RIGHT) {
			ret = "right";
		}
		else {
			ret = "top";
		}
		
		return ret;
	}
	
	public static void setPCTabPlacement(final int placement)
	{
		pcTabPlacement = placement;
	}

	public static int getPCTabPlacement()
	{
		return pcTabPlacement;
	}

	public static void setMaximumEorF (final int value)
	{
		maximumEorF = value;
	}

	public static int getMaximumEorF ()
	{
		return maximumEorF;
	}
	
	public static ArrayList<String[]> getSources ()
	{
		return sources;
	}
	
	public static String get (String key)
	{
		return options.getProperty(key);
	}
}
