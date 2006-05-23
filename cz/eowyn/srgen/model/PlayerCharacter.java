package cz.eowyn.srgen.model;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerCharacter {
	ArrayList listeners;
	
	public static final int PRIO_RACE = 0;
	public static final int PRIO_MAGIC = 1;
	public static final int PRIO_ATTRS = 2;
	public static final int PRIO_SKILLS = 3;
	public static final int PRIO_RESOURCES = 4;

	
	public static final int STAT_BOD_BASE = 0;
	public static final int STAT_BOD_M1 = 1;
	public static final int STAT_BOD_M2 = 2;
	public static final int STAT_BOD_M3 = 3;
	public static final int STAT_BOD = 4;
	
	public static final int STAT_QCK_BASE = 5;
	public static final int STAT_QCK_M1 = 6;
	public static final int STAT_QCK_M2 = 7;
	public static final int STAT_QCK_M3 = 8;
	public static final int STAT_QCK = 9;
	
	public static final int STAT_STR_BASE = 10;
	public static final int STAT_STR_M1 = 11;
	public static final int STAT_STR_M2 = 12;
	public static final int STAT_STR_M3 = 13;
	public static final int STAT_STR = 14;

	public static final int STAT_CHR_BASE = 15;
	public static final int STAT_CHR_M1 = 16;
	public static final int STAT_CHR_M2 = 17;
	public static final int STAT_CHR_M3 = 18;
	public static final int STAT_CHR = 19;

	public static final int STAT_INT_BASE = 20;
	public static final int STAT_INT_M1 = 21;
	public static final int STAT_INT_M2 = 22;
	public static final int STAT_INT_M3 = 23;
	public static final int STAT_INT = 24;

	public static final int STAT_WIL_BASE = 25;
	public static final int STAT_WIL_M1 = 26;
	public static final int STAT_WIL_M2 = 27;
	public static final int STAT_WIL_M3 = 28;
	public static final int STAT_WIL = 29;
	
	private static final int STAT_NUM_ATTRS = 6;
	
	public static final int STAT_ATTR_POINTS = 30;
	public static final int STAT_SKILL_POINTS = 31;
	public static final int STAT_RESOURCES = 32;

	public static final int STAT_POOL_KARMA = 33;  // NOTE: computed
	public static final int STAT_POOL_COMBAT = 34;
	public static final int STAT_POOL_CONTROL = 35;
	public static final int STAT_POOL_HACKING = 36;
	public static final int STAT_POOL_SPELL = 37;
	public static final int STAT_POOL_TASK = 38;
	public static final int STAT_POOL_COMBAT_MOD = 39;

	public static final int STAT_KARMA_TOTAL = 40;
	public static final int STAT_KARMA_FREE = 41;
	public static final int STAT_KARMA_SPENT = 42;

	public static final int STAT_METAHUMAN = 43;
	public static final int STAT_REACTION = 44;  // NOTE: computed


	private static final int STAT_MAX = STAT_REACTION;

	
	public static final int STAT_ATTR_BASE = STAT_BOD_BASE;
	public static final int STAT_ATTR_M1 = STAT_BOD_M1;
	public static final int STAT_ATTR_M2 = STAT_BOD_M2;
	public static final int STAT_ATTR_M3 = STAT_BOD_M3;
	public static final int STAT_ATTR = STAT_BOD;

	
	// NOTE: keep the indices, the SR3 importer relies on them!
	public static final int STR_CHARNAME = 0;
	public static final int STR_REALNAME = 1;
	public static final int STR_PLAYERNAME = 2;
	public static final int STR_STREETNAME1 = 3;
	// space for the rest of streetnames
	public static final int STR_STREETNAME5 = 7;
	public static final int STR_RACE = 8;
	public static final int STR_AGE = 9;
	public static final int STR_SEX = 10;
	public static final int STR_HEIGHT = 11 ;
	public static final int STR_WEIGHT = 12;
	public static final int STR_HAIR = 13;
	public static final int STR_EYES = 14;
	public static final int STR_BIRTHDATE = 15;
	public static final int STR_BIRTHPLACE = 16;
	public static final int STR_BIRTHNOTES = 17;
	public static final int STR_NOTES = 18;
	public static final int STR_ARCHETYPE = 19;
	public static final int STR_CREATION_DATE = 20;
	public static final int STR_MOD_DATE = 21;
	public static final int STR_IMAGE = 22;

	
	private static int STR_MAX = STR_IMAGE;
	
	//public static final int STAT_ = ;

	private int[] stats;
	// index is priority type (PRIO_*), value is priority (A-E == 0-4)  
	private int[] priorities;
	private String[] strings;

	private RepositoryList edges_and_flaws;
	private RepositoryList skills;
	private RepositoryList contacts;

	private RepositoryList gears;
	private RepositoryList cyberwares;
	private RepositoryList biowares;
	private RepositoryList vehicles;
	private RepositoryList decks;

	private RepositoryList lifestyles;
	private RepositoryList credsticks;
	
	
	private boolean signalActive = false;
	
	public PlayerCharacter () {
		listeners = new ArrayList (10);

		stats = new int[STAT_MAX+1];
		strings = new String[STR_MAX + 1];
		resetStats ();
		
		priorities = new int[5];
		for (int i = 0; i < 5; i++) {
			priorities[i] = i;
		}
		
		edges_and_flaws = new RepositoryList (5);
		skills = new RepositoryList (10);
		contacts = new RepositoryList (5);

		gears = new RepositoryList (10);
		cyberwares = new RepositoryList (5);
		biowares = new RepositoryList (5);
		vehicles = new RepositoryList (5);
		decks = new RepositoryList (5);		
		
		lifestyles = new RepositoryList (3);
		credsticks = new RepositoryList (5);
		
		computeInitialResources ();
	}
	
	private void computeInitialResources () {
		int[] res = { 1000000, 400000, 90000, 20000, 5000 };
		int[] attr_points = { 30, 27, 24, 21, 18 };
		int[] skill_points = { 50, 40, 34, 30, 27 };
		
		setStat (STAT_ATTR_POINTS, res[priorities[PRIO_ATTRS]]);
		setStat (STAT_SKILL_POINTS, res[priorities[PRIO_SKILLS]]);
		setStat (STAT_RESOURCES, res[priorities[PRIO_RESOURCES]]);
	}

	private void resetStats () {
		for (int i = 0; i <= STAT_MAX; i++) {
			stats[i] = 0;
		}
		for (int i = 0; i <= STR_MAX; i++) {
			strings[i] = null;
		}
	}

	
	public void addListener (PCListener ls) {
		if (! listeners.contains(ls)) {
			listeners.add (ls);
		}
	}
	
	public void removeListener (PCListener ls) {
		listeners.remove(ls);
	}
	
	protected void firePCChanged () {
		if (signalActive) return;
		signalActive = true;

		recompute();

		Iterator iter = listeners.iterator();
		while (iter.hasNext ()) {
			PCListener ls = (PCListener) iter.next ();
			try {
				ls.pcChanged();
			}
			catch (Exception e) {
				
			}
		}
		
		signalActive = false;
	}

	public int[] getPriorities () {
		return (int[]) priorities.clone ();
	}

	public void setPriorities (int[] newPriorities) {
		resetStats ();
		priorities = (int[]) newPriorities.clone();
		computeInitialResources ();
		firePCChanged ();
	}
	
	public int getStat (int stat) {
		return stats[stat];
	}
	
	public void setStat (int stat, int value) {
		//System.out.println (String.valueOf(stat) + " := " + String.valueOf(value));
		stats[stat] = value;
		firePCChanged ();
	}

	public String getString (int str) {
		return strings[str];
	}
	
	public void setString (int str, String value) {
		strings[str] = value;
		firePCChanged ();
	}


	
	public void AddEdgeAndFlaw (EdgeAndFlaw edge, String custom, boolean unique) {
		edges_and_flaws.add (edge);
		firePCChanged ();
	}
	
	public void RemoveEdgeAndFlaw (EdgeAndFlaw edge) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getEdgeAndFlaw_List () {
		return edges_and_flaws;
	}

	
	public void AddSkill (Skill skill, String custom, boolean unique) {
		skills.add (skill);
		firePCChanged ();
	}
	
	public void RemoveSkill (Skill skill) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getSkill_List () {
		return skills;
	}


	public void AddLifestyle (Lifestyle lifestyle, String custom, boolean unique) {
		lifestyles.add (lifestyle);
		firePCChanged ();
	}
	
	public void RemoveLifestyle (Lifestyle lifestyle) {
	}
	
	public RepositoryList getLifestyle_List () {
		return lifestyles;
	}

	
	public void AddCredstick (Credstick credstick, String custom, boolean unique) {
		credsticks.add (credstick);
		firePCChanged ();
	}
	
	public void RemoveCredstick (Credstick credstick) {
	}
	
	public RepositoryList getCredstick_List () {
		return credsticks;
	}

	

	public void AddContact (Contact contact, String custom, boolean unique) {
		contacts.add (contact);
		firePCChanged ();
	}
	
	public void RemoveContact (Contact contact) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getContact_List () {
		return contacts;
	}

	
	public void AddGear (Gear gear, String custom, boolean unique) {
		gears.add (gear);
		firePCChanged ();
	}
	
	public void RemoveGear (Gear gear) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getGear_List () {
		return gears;
	}


	public void AddCyberware (Cyberware cyber, String custom, boolean unique) {
		cyberwares.add (cyber);
		firePCChanged ();
	}
	
	public void RemoveCyberware (Cyberware cyber) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getCyberware_List () {
		return cyberwares;
	}

	
	public void AddBioware (Bioware bio, String custom, boolean unique) {
		biowares.add (bio);
		firePCChanged ();
	}
	
	public void RemoveBioware (Bioware bio) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getBioware_List () {
		return biowares;
	}

	
	public void AddVehicle (Vehicle vehicle, String custom, boolean unique) {
		vehicles.add (vehicle);
		firePCChanged ();
	}
	
	public void RemoveVehicle (Vehicle vehicle) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getVehicle_List () {
		return vehicles;
	}


	public void AddDeck (Deck deck, String custom, boolean unique) {
		decks.add (deck);
		firePCChanged ();
	}
	
	public void RemoveDeck (Deck deck) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList getDeck_List () {
		return decks;
	}
	

	// Computed stats

	public void recompute () {
		setStat (STAT_REACTION, getReaction ());
		setStat (STAT_POOL_KARMA, getKarmaPool ());
		setStat (STAT_POOL_COMBAT, getCombatPool ());

		for (int i = 0; i < STAT_NUM_ATTRS * 5; i += 5) {
			setStat (STAT_ATTR + i, getStat (STAT_ATTR_BASE + i) + getStat (STAT_ATTR_M1 + i) + getStat (STAT_ATTR_M2 + i) + getStat (STAT_ATTR_M3 + i));
		}
	}
	
	public int getReaction () {
		return (getStat (STAT_INT) + getStat (STAT_QCK)) / 2;
	}

	public int getKarmaPool () {
		return 1 + (getStat (STAT_KARMA_TOTAL) - 1) / ((getStat (STAT_METAHUMAN) != 0) ? 20 : 10); 
	}
	
	public int getCombatPool () {
		return (getStat (STAT_INT) + getStat (STAT_QCK) + getStat (STAT_WIL)) / 2;
	}	
}
