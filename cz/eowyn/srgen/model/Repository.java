package cz.eowyn.srgen.model;

import java.util.Hashtable;

import cz.eowyn.srgen.io.NSRCG3_Format;

public class Repository {
	private RepositoryTree edges_and_flaws = null;
	private RepositoryTree skills = null;
	private RepositoryTree gear = null;
	private RepositoryTree mage_gear = null;
	private RepositoryTree bioware = null;
	private RepositoryTree cyberware = null;
	private RepositoryTree decks = null;
	private RepositoryTree vehicles = null;
	private RepositoryTree spells = null;
	private RepositoryTree contacts = null;
	private RepositoryTree adept_powers = null;
	private RepositoryTree totems = null;
	private RepositoryTree magic = null;
	private RepositoryTree source_books = null;
	
	// These are for NSRCG3 loaders
	private Hashtable gear_formats = null;
	private Hashtable mage_gear_formats = null;
	private Hashtable vehicles_formats = null;
	private Hashtable cyberware_formats = null;
	private Hashtable decks_formats = null;
	private Hashtable spell_formats = null;

	private Hashtable source_books_map = null;
	
	public Repository () {
		adept_powers = new RepositoryTree ("Adept Powers", 0);
		bioware = new RepositoryTree ("Bioware", 0);
		contacts = new RepositoryTree ("Contacts", 0);
		cyberware = new RepositoryTree ("Cyberware", 0);
		decks = new RepositoryTree ("Decks", 0);
		edges_and_flaws = new RepositoryTree ("Edges and Flaws", 0);
		gear = new RepositoryTree ("Gear", 0);
		mage_gear = new RepositoryTree ("Mage Gear", 0);
		magic = new RepositoryTree ("Magic", 0);
		skills = new RepositoryTree ("Skills", 0);
		source_books = new RepositoryTree ("Sources Books", 0);
		spells = new RepositoryTree ("Spells", 0);
		totems = new RepositoryTree ("Totems", 0);
		vehicles = new RepositoryTree ("Vehicles", 0);
		
		gear_formats = new Hashtable ();
		mage_gear_formats = new Hashtable ();
		cyberware_formats = new Hashtable ();
		vehicles_formats = new Hashtable ();
		decks_formats = new Hashtable ();
		spell_formats = new Hashtable ();
		
		source_books_map = new Hashtable ();
	}
	
	public void setEdgeAndFlaw_Tree (RepositoryTree root) {
		edges_and_flaws = root;
	}

	public RepositoryTree getEdgeAndFlaw_Tree () {
		return edges_and_flaws;
	}

	public void setSkill_Tree (RepositoryTree root) {
		skills = root;
	}

	public RepositoryTree getSkill_Tree () {
		return skills;
	}


	public void setGear_Tree (RepositoryTree root) {
		gear = root;
	}

	public RepositoryTree getGear_Tree () {
		return gear;
	}

	public Hashtable getGearFormats () {
		return gear_formats;
	}
	
	public NSRCG3_Format getGearFormat (String key) {
		return (NSRCG3_Format) gear_formats.get (key);
	}

	
	public void setMageGear_Tree (RepositoryTree root) {
		mage_gear = root;
	}

	public RepositoryTree getMageGear_Tree () {
		return mage_gear;
	}

	public Hashtable getMageGearFormats () {
		return mage_gear_formats;
	}

	public NSRCG3_Format getMageGearFormat (String key) {
		return (NSRCG3_Format) mage_gear_formats.get (key);
	}

	

	public void setAdeptPowers_Tree (RepositoryTree root) {
		adept_powers = root;
	}

	public RepositoryTree getAdeptPowers_Tree () {
		return adept_powers;
	}

	
	public void setTotems_Tree (RepositoryTree root) {
		totems = root;
	}

	public RepositoryTree getTotems_Tree () {
		return totems;
	}


	public void setMagic_Tree (RepositoryTree root) {
		magic = root;
	}

	public RepositoryTree getMagic_Tree () {
		return magic;
	}


	public void setBioware_Tree (RepositoryTree root) {
		bioware = root;
	}

	public RepositoryTree getBioware_Tree () {
		return bioware;
	}

	public void setCyberware_Tree (RepositoryTree root) {
		cyberware = root;
	}

	public RepositoryTree getCyberware_Tree () {
		return cyberware;
	}

	public Hashtable getCyberwareFormats () {
		return cyberware_formats;
	}

	public NSRCG3_Format getCyberwareFormat (String key) {
		return (NSRCG3_Format) cyberware_formats.get (key);
	}
	
	public void setDecks_Tree (RepositoryTree root) {
		decks = root;
	}

	public RepositoryTree getDecks_Tree () {
		return decks;
	}

	public Hashtable getDecksFormats () {
		return decks_formats;
	}

	public NSRCG3_Format getDecksFormat (String key) {
		return (NSRCG3_Format) decks_formats.get (key);
	}	

	
	public void setVehicles_Tree (RepositoryTree root) {
		vehicles = root;
	}

	public RepositoryTree getVehicles_Tree () {
		return vehicles;
	}

	public void setSpell_Tree (RepositoryTree root) {
		spells = root;
	}

	public RepositoryTree getSpell_Tree () {
		return spells;
	}

	public Hashtable getSpellFormats () {
		return spell_formats;
	}

	public NSRCG3_Format getSpellFormat (String key) {
		return (NSRCG3_Format) spell_formats.get (key);
	}	

	
	public void setContacts_Tree (RepositoryTree root) {
		contacts = root;
	}

	public RepositoryTree getContacts_Tree () {
		return contacts;
	}

	public Hashtable getVehiclesFormats () {
		return vehicles_formats;
	}

	public NSRCG3_Format getVehiclesFormat (String key) {
		return (NSRCG3_Format) vehicles_formats.get (key);
	}
	
	public RepositoryTree getSourceBooks_Tree () {
		return source_books;
	}
	
	public void setSourceBooks_Tree (RepositoryTree root) {
		source_books = root;
	}

	public Hashtable getSourceBooksMap () {
		return source_books_map;
	}


	public SourceBook getSourceBook (String key) {
		return (SourceBook) source_books_map.get (key);
	}
}
