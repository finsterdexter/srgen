package cz.eowyn.srgen.model;

import java.util.Hashtable;

import cz.eowyn.srgen.io.NSRCG3_Format;

public class Repository {
	private RepositoryTree<EdgeAndFlaw> edges_and_flaws = null;
	private RepositoryTree<Skill> skills = null;
	private RepositoryTree<Gear> gear = null;
	private RepositoryTree<MageGear> mage_gear = null;
	private RepositoryTree<Bioware> bioware = null;
	private RepositoryTree<Cyberware> cyberware = null;
	private RepositoryTree<Deck> decks = null;
	private RepositoryTree<Vehicle> vehicles = null;
	private RepositoryTree<Spell> spells = null;
	private RepositoryTree<Contact> contacts = null;
	private RepositoryTree<AdeptPower> adept_powers = null;
	private RepositoryTree totems = null;
	private RepositoryTree magic = null;
	private RepositoryTree<SourceBook> source_books = null;
	
	// These are for NSRCG3 loaders
	private Hashtable gear_formats = null;
	private Hashtable mage_gear_formats = null;
	private Hashtable vehicles_formats = null;
	private Hashtable cyberware_formats = null;
	private Hashtable decks_formats = null;
	private Hashtable spell_formats = null;

	private Hashtable source_books_map = null;
	
	public Repository () {
		adept_powers = new RepositoryTree<AdeptPower> ("Adept Powers", 0);
		bioware = new RepositoryTree<Bioware> ("Bioware", 0);
		contacts = new RepositoryTree<Contact> ("Contacts", 0);
		cyberware = new RepositoryTree<Cyberware> ("Cyberware", 0);
		decks = new RepositoryTree<Deck> ("Decks", 0);
		edges_and_flaws = new RepositoryTree<EdgeAndFlaw> ("Edges and Flaws", 0);
		gear = new RepositoryTree<Gear> ("Gear", 0);
		mage_gear = new RepositoryTree<MageGear> ("Mage Gear", 0);
		magic = new RepositoryTree ("Magic", 0);
		skills = new RepositoryTree<Skill> ("Skills", 0);
		source_books = new RepositoryTree<SourceBook> ("Sources Books", 0);
		spells = new RepositoryTree<Spell> ("Spells", 0);
		totems = new RepositoryTree ("Totems", 0);
		vehicles = new RepositoryTree<Vehicle> ("Vehicles", 0);
		
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

	public RepositoryTree<EdgeAndFlaw> getEdgeAndFlaw_Tree () {
		return edges_and_flaws;
	}

	public void setSkill_Tree (RepositoryTree root) {
		skills = root;
	}

	public RepositoryTree<Skill> getSkill_Tree () {
		return skills;
	}


	public void setGear_Tree (RepositoryTree root) {
		gear = root;
	}

	public RepositoryTree<Gear> getGear_Tree () {
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

	public RepositoryTree<MageGear> getMageGear_Tree () {
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

	public RepositoryTree<AdeptPower> getAdeptPowers_Tree () {
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


	public void setBioware_Tree (RepositoryTree<Bioware> root) {
		bioware = root;
	}

	public RepositoryTree<Bioware> getBioware_Tree () {
		return bioware;
	}

	public void setCyberware_Tree (RepositoryTree root) {
		cyberware = root;
	}

	public RepositoryTree<Cyberware> getCyberware_Tree () {
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

	public RepositoryTree<Deck> getDecks_Tree () {
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

	public RepositoryTree<Vehicle> getVehicles_Tree () {
		return vehicles;
	}

	
	public void setSpell_Tree (RepositoryTree root) {
		spells = root;
	}

	public RepositoryTree<Spell> getSpell_Tree () {
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

	public RepositoryTree<Contact> getContacts_Tree () {
		return contacts;
	}

	public Hashtable getVehiclesFormats () {
		return vehicles_formats;
	}

	public NSRCG3_Format getVehiclesFormat (String key) {
		return (NSRCG3_Format) vehicles_formats.get (key);
	}
	
	public RepositoryTree<SourceBook> getSourceBooks_Tree () {
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
