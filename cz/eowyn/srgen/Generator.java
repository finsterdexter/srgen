package cz.eowyn.srgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Action;

import cz.eowyn.srgen.gui.SRGenWindow;
import cz.eowyn.srgen.gui.SplashScreen;
import cz.eowyn.srgen.gui.actions.AddRepoObjectAction;
import cz.eowyn.srgen.io.*;
import cz.eowyn.srgen.model.*;

public class Generator {
	public static SplashScreen splash = null;

	private static Repository repository = null;
	private static ArrayList<PlayerCharacter>  characters = null;

    // These are the actions defined for the application
    private AddRepoObjectAction addRepoObjectAction;
    
    // Vector for holding all the actions.
    private Vector actions;

	public static void init () {
		characters = new ArrayList<PlayerCharacter> (10);
		repository = new Repository ();
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
		window.setVisible(true);
	}
	
    // This method should be called before creating the UI 
    // to create all the Actions
	// FIXME: atm it's useless
    private void initActions()  {
        actions = new Vector();
        
        addRepoObjectAction = new AddRepoObjectAction();
        registerAction(addRepoObjectAction);
    }
    
    private void registerAction(Action action)  {
        //action.addActionListener(this);
        actions.addElement(action);
    }

	public static void loadSources () {
		NSRCG3_Books_Loader books_loader = new NSRCG3_Books_Loader (repository);
		NSRCG3_DAT_Loader dat_loader = new NSRCG3_DAT_Loader (repository);
		NSRCG3_Skills_Loader skills_loader = new NSRCG3_Skills_Loader (repository);

		for (int i=0; i<Config.getSources().size(); i++) {
			String[] src = Config.getSources().get(i);
			String type = src[0];

			try {
				if (type.equals("books")) {
					books_loader.ImportFile (src[1], repository.getSourceBooks_Tree (), null, repository.getSourceBooksMap ());
				}
				else if (type.equals("skills")) {
					skills_loader.ImportFile (src[1], repository.getSkill_Tree(), Skill.class, null);				
				}
				else if (type.equals("edges")) {
					dat_loader.ImportFile (src[1], repository.getEdgeAndFlaw_Tree(), EdgeAndFlaw.class, null);
				}
				else if (type.equals("gear")) {
					dat_loader.ImportFile (src[1], repository.getGear_Tree(), Equipment.class, repository.getGearFormats());
				}
				else if (type.equals("magegear")) {
					dat_loader.ImportFile (src[1], repository.getMageGear_Tree(), Equipment.class, repository.getMageGearFormats());
				}
				else if (type.equals("bioware")) {
					dat_loader.ImportFile (src[1], repository.getBioware_Tree(), Bioware.class, null);
				}
				else if (type.equals("cyberware")) {
					dat_loader.ImportFile (src[1], repository.getCyberware_Tree(), Cyberware.class, repository.getCyberwareFormats ());
				}
				else if (type.equals("decks")) {
					dat_loader.ImportFile (src[1], repository.getDecks_Tree(), Deck.class, repository.getDecksFormats ());
				}
				else if (type.equals("vehicles")) {
					dat_loader.ImportFile (src[1], repository.getVehicles_Tree(), Vehicle.class, repository.getVehiclesFormats());
				}
				else if (type.equals("spells")) {
					dat_loader.ImportFile (src[1], repository.getSpell_Tree(), Spell.class, repository.getSpellFormats());
				}
				else if (type.equals("contacts")) {
					dat_loader.ImportFile (src[1], repository.getContacts_Tree(), Contact.class, null);
				}
				else if (type.equals("adept")) {
					dat_loader.ImportFile (src[1], repository.getAdeptPowers_Tree(), Contact.class, null);
				}
				else if (type.equals("magic")) {
					//dat_loader.ImportFile (src[1], repository.getMagic_Tree(), Contact.class, null);
				}
				else if (type.equals("totems")) {
					//dat_loader.ImportFile (src[1], repository.getTotems_Tree(), Contact.class, null);
				}
				else {
					System.err.println ("Unknown source type: " + type + ", " + src[1]);
				}
			} catch (IOException e) {
				System.err.println ("Could not load file " + src[1]);
				e.printStackTrace();
			}
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
	
	public static void saveCharacter (String filename, PlayerCharacter pc) {
		NSRCG3_SR3_Loader pcloader = new NSRCG3_SR3_Loader (repository);
		try {
			pcloader.ExportFile (filename, pc);
		} catch (Exception e) {
			System.err.println ("Could not save file");
	        e.printStackTrace ();
		}
	}
	
	public static ArrayList<PlayerCharacter> getCharacters() {
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
	
	public static void message (String message)
	{
		System.err.println(message);
	}
}
