package cz.eowyn.srgen;

import java.util.ArrayList;

import cz.eowyn.srgen.gui.SplashScreen;
import cz.eowyn.srgen.io.*;
import cz.eowyn.srgen.model.*;

public class Generator {
	public SplashScreen splash;

	private Repository repository = null;
	private ArrayList  characters = null;
	
	public Generator () {
		splash = new SplashScreen ();

		characters = new ArrayList (10);
		repository = new Repository ();

		Config.loadConfig ();
		
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
		NSRCG3_DAT_Loader loader = new NSRCG3_DAT_Loader (repository);

		// FIXME: single bad file will prevent loading of all subsequent files 
		try {
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

		// create and load PCs
		//PlayerCharacter pc = new PlayerCharacter ();
		//pc.setStreetName("Jingo");
		//pc.setRealName("John Bull");
		//characters.add(pc);

		//loadCharacter ("/home/benkovsk/dos/srgen/sr3/Jarda2.sr3");	
	}
	
	public Repository getRepository () {
		return repository;
	}

	public PlayerCharacter newCharacter () {
		PlayerCharacter pc = new PlayerCharacter ();
		characters.add (pc);
		return pc;
	}
	
	public PlayerCharacter loadCharacter (String filename) {
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
	
	public ArrayList getCharacters() {
		return characters;
	}

	public PlayerCharacter getPlayerCharacter(int index) {
		return (PlayerCharacter) characters.get(index);
	}
}
