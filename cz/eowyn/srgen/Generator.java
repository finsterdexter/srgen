package cz.eowyn.srgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cz.eowyn.srgen.gui.SRGenWindow;
import cz.eowyn.srgen.gui.SplashScreen;
import cz.eowyn.srgen.io.*;
import cz.eowyn.srgen.model.*;

public class Generator {
	public static SplashScreen splash = null;

	private static Repository repository = null;
	private static ArrayList  characters = null;
	
	public static void init () {
		characters = new ArrayList (10);
		repository = new Repository ();

		Config.loadConfig ();
	}
	
	public static void showSplash () {
		splash = new SplashScreen ();
	}
	
	public static void hideSplash () {
		splash.dispose ();
	}
	
	public static void startGUI () {
		SRGenWindow window = new SRGenWindow ();
		hideSplash ();
		window.show ();
	}
	
	public static void loadSources () {
		String dir = "/home/benkovsk/nsr_data/";
		//String dir = "";

		// Load list of source books
		try {
			NSRCG3_Books_Loader loader = new NSRCG3_Books_Loader (repository);
			loader.ImportFile (dir + "books.dat", repository.getSourceBooks_Tree (), null, repository.getSourceBooksMap ());
		} catch (Exception e) {
			System.err.println ("Could not load file");
	        e.printStackTrace();
		}
		
		// Load repository objects
		// FIXME: single bad file will prevent loading of all subsequent files 
		try {
			NSRCG3_DAT_Loader loader = new NSRCG3_DAT_Loader (repository);

			loader.ImportFile (dir + "EDGE.DAT", repository.getEdgeAndFlaw_Tree(), EdgeAndFlaw.class, null);
			loader.ImportFile (dir + "GEAR.DAT", repository.getGear_Tree(), Equipment.class, repository.getGearFormats());
			loader.ImportFile (dir + "MAGEGEAR.DAT", repository.getMageGear_Tree(), Equipment.class, repository.getMageGearFormats());
			loader.ImportFile (dir + "bioware.dat", repository.getBioware_Tree(), Bioware.class, null);
			loader.ImportFile (dir + "cyber.dat", repository.getCyberware_Tree(), Cyberware.class, repository.getCyberwareFormats ());
			loader.ImportFile (dir + "DECK.dat", repository.getDecks_Tree(), Deck.class, repository.getDecksFormats ());
			loader.ImportFile (dir + "vehicles.dat", repository.getVehicles_Tree(), Vehicle.class, repository.getVehiclesFormats());
			loader.ImportFile (dir + "SPELLS.DAT", repository.getSpell_Tree(), Spell.class, repository.getSpellFormats());
			loader.ImportFile (dir + "contacts.dat", repository.getContacts_Tree(), Contact.class, null);
			loader.ImportFile (dir + "adept.dat", repository.getAdeptPowers_Tree(), Contact.class, null);
			//loader.ImportFile (dir + "MAGIC.DAT", repository.getMagic_Tree(), Contact.class, null);
			//loader.ImportFile (dir + "TOTEMS.DAT", repository.getTotems_Tree(), Contact.class, null);
		} catch (Exception e) {
			System.err.println ("Could not load file");
	        e.printStackTrace();
		}
		// Load repository objects

		try {
			NSRCG3_Skills_Loader loader = new NSRCG3_Skills_Loader (repository);
			loader.ImportFile (dir + "SKILLS.DAT", repository.getSkill_Tree(), Skill.class, null);
		} catch (Exception e) {
			System.err.println ("Could not load file");
			e.printStackTrace();
		}
	}

	public static Repository getRepository () {
		return repository;
	}

	public static PlayerCharacter newCharacter () {
		PlayerCharacter pc = new PlayerCharacter ();
		characters.add (pc);
		return pc;
	}
	
	public static PlayerCharacter loadCharacter (String filename) {
		PlayerCharacter pc;
		NSRCG3_SR3_Loader pcloader = new NSRCG3_SR3_Loader (repository);
		try {
			pc = pcloader.ImportFile (filename);
			characters.add (pc);
			return pc;
		} catch (Exception e) {
			System.err.println ("Could not load file");
	        e.printStackTrace ();
	        return null;
		}
		
	}
	
	public static ArrayList getCharacters() {
		return characters;
	}

	public static PlayerCharacter getPlayerCharacter(int index) {
		return (PlayerCharacter) characters.get(index);
	}

	public static void exportCharacter (PlayerCharacter pc, String template, String filename) {
        try {
        	ExportHandler exporter = new ExportHandler (new File (template));
        	BufferedWriter bw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (filename)));
            exporter.write (pc, bw);
            bw.close ();
            System.err.println ("Character exported");
        }
        catch (IOException exc) {
        	System.err.println ("Can't export file:" + exc.getMessage());
        }
		
	}
}
