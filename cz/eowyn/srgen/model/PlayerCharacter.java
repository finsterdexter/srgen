package cz.eowyn.srgen.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import cz.eowyn.srgen.Config;
import cz.eowyn.srgen.model.Stat;


public class PlayerCharacter implements PCAssetListener {
	public static final int SINT = 0;
	public static final int SSTR = 1;
	public static final int SFLOAT = 2;

	public static final int PRIO_RACE = 0;
	public static final int PRIO_MAGIC = 1;
	public static final int PRIO_ATTRS = 2;
	public static final int PRIO_SKILLS = 3;
	public static final int PRIO_RESOURCES = 4;

	public static final int STAT_BOD_BASE = 5;
	public static final int STAT_BOD_M1 = 6;
	public static final int STAT_BOD_M2 = 7;
	public static final int STAT_BOD_M3 = 8;
	public static final int STAT_BOD = 9;
	
	public static final int STAT_QCK_BASE = 10;
	public static final int STAT_QCK_M1 = 11;
	public static final int STAT_QCK_M2 = 12;
	public static final int STAT_QCK_M3 = 13;
	public static final int STAT_QCK = 14;
	
	public static final int STAT_STR_BASE = 15;
	public static final int STAT_STR_M1 = 16;
	public static final int STAT_STR_M2 = 17;
	public static final int STAT_STR_M3 = 18;
	public static final int STAT_STR = 19;

	public static final int STAT_CHR_BASE = 20;
	public static final int STAT_CHR_M1 = 21;
	public static final int STAT_CHR_M2 = 22;
	public static final int STAT_CHR_M3 = 23;
	public static final int STAT_CHR = 24;

	public static final int STAT_INT_BASE = 25;
	public static final int STAT_INT_M1 = 26;
	public static final int STAT_INT_M2 = 27;
	public static final int STAT_INT_M3 = 28;
	public static final int STAT_INT = 29;

	public static final int STAT_WIL_BASE = 30;
	public static final int STAT_WIL_M1 = 31;
	public static final int STAT_WIL_M2 = 32;
	public static final int STAT_WIL_M3 = 33;
	public static final int STAT_WIL = 34;

	// Reaction, initiative, essence and magic is computed 
	public static final int STAT_REA_BASE = 35;
	public static final int STAT_REA_M1 = 36;
	public static final int STAT_REA_M2 = 37;
	public static final int STAT_REA_M3 = 38;
	public static final int STAT_REA = 39;

	public static final int STAT_INI_BASE = 40;
	public static final int STAT_INI_M1 = 41;
	public static final int STAT_INI_M2 = 42;
	public static final int STAT_INI_M3 = 43;
	public static final int STAT_INI = 44;

	public static final int STAT_ESS_BASE = 45;
	public static final int STAT_ESS_M1 = 46;
	public static final int STAT_ESS_M2 = 47;
	public static final int STAT_ESS_M3 = 48;
	public static final int STAT_ESS = 49;

	public static final int STAT_MAG_BASE = 50;
	public static final int STAT_MAG_M1 = 51;
	public static final int STAT_MAG_M2 = 52;
	public static final int STAT_MAG_M3 = 53;
	public static final int STAT_MAG = 54;
	
	public static final int STAT_ATTR_POINTS_INITIAL = 55;
	public static final int STAT_ATTR_POINTS_SPENT = 56;
	public static final int STAT_ATTR_POINTS = 57;
	public static final int STAT_SKILL_POINTS_INITIAL = 58;
	public static final int STAT_SKILL_POINTS_SPENT = 59;
	public static final int STAT_SKILL_POINTS = 60;
	public static final int STAT_RESOURCES_INITIAL = 61;
	public static final int STAT_RESOURCES_SPENT = 62;
	public static final int STAT_RESOURCES = 63;

	
	public static final int STAT_POOL_KARMA = 64;  // NOTE: computed
	public static final int STAT_POOL_COMBAT = 65;
	public static final int STAT_POOL_CONTROL = 66;
	public static final int STAT_POOL_HACKING = 67;
	public static final int STAT_POOL_SPELL = 68;
	public static final int STAT_POOL_TASK = 69;
	public static final int STAT_POOL_COMBAT_MOD = 70;

	public static final int STAT_KARMA_TOTAL = 71;
	public static final int STAT_KARMA_FREE = 72;
	public static final int STAT_KARMA_SPENT = 73;

	public static final int STAT_METAHUMAN = 74;
	public static final int STAT_xxxx = 75;  // FIXME: free to reuse
	public static final int STAT_MAGICAL = 76;
	public static final int STAT_ALLOWED_RACES = 77;
	public static final int STAT_FINALIZED = 78;

	
	// NOTE: keep the indices, the SR3 importer relies on them!
	public static final int STR_CHARNAME = 79;
	public static final int STR_REALNAME = 80;
	public static final int STR_PLAYERNAME = 81;
	// FIXME: use list instead
	public static final int STR_STREETNAME1 = 82;
	public static final int STR_STREETNAME2 = 83;
	public static final int STR_STREETNAME3 = 84;
	public static final int STR_STREETNAME4 = 85;
	public static final int STR_STREETNAME5 = 86;
	public static final int STR_RACE = 87;
	public static final int STR_AGE = 88;
	public static final int STR_SEX = 89;
	public static final int STR_HEIGHT = 90;
	public static final int STR_WEIGHT = 91;
	public static final int STR_HAIR = 92;
	public static final int STR_EYES = 93;
	public static final int STR_BIRTHDATE = 94;
	public static final int STR_BIRTHPLACE = 95;
	public static final int STR_BIRTHNOTES = 96;
	public static final int STR_NOTES = 97;
	public static final int STR_ARCHETYPE = 98;
	public static final int STR_CREATION_DATE = 99;
	public static final int STR_MOD_DATE = 100;
	public static final int STR_CREATION_SRDATE = 101;
	public static final int STR_SRDATE = 102;
	public static final int STR_IMAGE = 103;


	
	//private static int STR_MAX = STR_IMAGE;
	private static final int STAT_NUM_ATTRS = 10;
	private static final int NUM_PRIO = 5;

	//private static final int STAT_MAX = STAT_ALLOWED_RACES;

	public static final int STAT_ATTR_BASE = STAT_BOD_BASE;
	public static final int STAT_ATTR_M1 = STAT_BOD_M1;
	public static final int STAT_ATTR_M2 = STAT_BOD_M2;
	public static final int STAT_ATTR_M3 = STAT_BOD_M3;
	public static final int STAT_ATTR = STAT_BOD;

	
	private static Hashtable keyToStatMap = null;

	//public static final int STAT_ = ;
	
	public static Stat[] statDesc = {
			new Stat(PRIO_RACE, "PRIO_RACE", SINT, "Prio Race", ""),
			new Stat(PRIO_MAGIC, "PRIO_MAGIC", SINT, "Prio Magic", ""),
			new Stat(PRIO_ATTRS, "PRIO_ATTRS", SINT, "Prio Attrs", ""),
			new Stat(PRIO_SKILLS, "PRIO_SKILLS", SINT, "Prio Skills", ""),
			new Stat(PRIO_RESOURCES, "PRIO_RESOURCES", SINT, "Prio Resources", ""),

			new Stat(STAT_BOD_BASE, "BOD.BASE", SINT, "Body base", ""),
			new Stat(STAT_BOD_M1, "BOD.M1", SINT, "Body M1", ""),
			new Stat(STAT_BOD_M2, "BOD.M2", SINT, "Body M2", ""),
			new Stat(STAT_BOD_M3, "BOD.M3", SINT, "Body M3", ""),
			new Stat(STAT_BOD, "BOD", SINT, "", "Body"),
			
			new Stat(STAT_QCK_BASE, "QCK.BASE", SINT, "Quickness base", ""),
			new Stat(STAT_QCK_M1, "QCK.M1", SINT, "Quickness M1", ""),
			new Stat(STAT_QCK_M2, "QCK.M2", SINT, "Quickness M2", ""),
			new Stat(STAT_QCK_M3, "QCK.M3", SINT, "Quickness M3", ""),
			new Stat(STAT_QCK, "QCK", SINT, "Quickness", ""),
			
			new Stat(STAT_STR_BASE, "STR.BASE", SINT, "Strength base", ""),
			new Stat(STAT_STR_M1, "STR.M1", SINT, "Strength M1", ""),
			new Stat(STAT_STR_M2, "STR.M2", SINT, "Strength M2", ""),
			new Stat(STAT_STR_M3, "STR.M3", SINT, "Strength M3", ""),
			new Stat(STAT_STR, "STR", SINT, "Strength", ""),
			
			new Stat(STAT_CHR_BASE, "CHR.BASE", SINT, "Charisma base", ""),
			new Stat(STAT_CHR_M1, "CHR.M1", SINT, "Charisma M1", ""),
			new Stat(STAT_CHR_M2, "CHR.M2", SINT, "Charisma M2", ""),
			new Stat(STAT_CHR_M3, "CHR.M3", SINT, "Charisma M3", ""),
			new Stat(STAT_CHR, "CHR", SINT, "Charisma", ""),
			
			new Stat(STAT_INT_BASE, "INT.BASE", SINT, "Intelligence base", ""),
			new Stat(STAT_INT_M1, "INT.M1", SINT, "Intelligence M1", ""),
			new Stat(STAT_INT_M2, "INT.M2", SINT, "Intelligence M2", ""),
			new Stat(STAT_INT_M3, "INT.M3", SINT, "Intelligence M3", ""),
			new Stat(STAT_INT, "INT", SINT, "Intelligence", ""),
			
			new Stat(STAT_WIL_BASE, "WIL.BASE", SINT, "Will base", ""),
			new Stat(STAT_WIL_M1, "WIL.M1", SINT, "Will M1", ""),
			new Stat(STAT_WIL_M2, "WIL.M2", SINT, "Will M2", ""),
			new Stat(STAT_WIL_M3, "WIL.M3", SINT, "Will M3", ""),
			new Stat(STAT_WIL, "WIL", SINT, "Will", ""),
			
			new Stat(STAT_REA_BASE, "REA.BASE", SINT, "Reaction base", ""),
			new Stat(STAT_REA_M1, "REA.M1", SINT, "Reaction M1", ""),
			new Stat(STAT_REA_M2, "REA.M2", SINT, "Reaction M2", ""),
			new Stat(STAT_REA_M3, "REA.M3", SINT, "Reaction M3", ""),
			new Stat(STAT_REA, "REA", SINT, "Reaction", ""),
			
			new Stat(STAT_INI_BASE, "INI.BASE", SINT, "Initiative base", ""),
			new Stat(STAT_INI_M1, "INI.M1", SINT, "Initiative M1", ""),
			new Stat(STAT_INI_M2, "INI.M2", SINT, "Initiative M2", ""),
			new Stat(STAT_INI_M3, "INI.M3", SINT, "Initiative M3", ""),
			new Stat(STAT_INI, "INI", SINT, "Initiative", ""),
			
			new Stat(STAT_ESS_BASE, "ESS.BASE", SFLOAT, "Essence base", ""),
			new Stat(STAT_ESS_M1, "ESS.M1", SFLOAT, "Essence M1", ""),
			new Stat(STAT_ESS_M2, "ESS.M2", SFLOAT, "Essence M2", ""),
			new Stat(STAT_ESS_M3, "ESS.M3", SFLOAT, "Essence M3", ""),
			new Stat(STAT_ESS, "ESS", SFLOAT, "Essence", ""),
			
			new Stat(STAT_MAG_BASE, "MAG.BASE", SINT, "Magic base", ""),
			new Stat(STAT_MAG_M1, "MAG.M1", SINT, "Magic M1", ""),
			new Stat(STAT_MAG_M2, "MAG.M2", SINT, "Magic M2", ""),
			new Stat(STAT_MAG_M3, "MAG.M3", SINT, "Magic M3", ""),
			new Stat(STAT_MAG, "MAG", SINT, "Magic", ""),
			
			new Stat(STAT_ATTR_POINTS_INITIAL, "ATTR_POINTS_INITIAL", SINT, "Attr. points initial", ""),
			new Stat(STAT_ATTR_POINTS_SPENT, "ATTR_POINTS_SPENT", SINT, "Attr. points spent", ""),
			new Stat(STAT_ATTR_POINTS, "ATTR_POINTS", SINT, "Attr. points", ""),
			new Stat(STAT_SKILL_POINTS_INITIAL, "SKILL_POINTS_INITIAL", SINT, "Skill points initial", ""),
			new Stat(STAT_SKILL_POINTS_SPENT, "SKILL_POINTS_SPENT", SINT, "Skill points spent", ""),
			new Stat(STAT_SKILL_POINTS, "SKILL_POINTS", SINT, "Skill points", ""),
			new Stat(STAT_RESOURCES_INITIAL, "RESOURCES_INITIAL", SINT, "Resources initial", ""),
			new Stat(STAT_RESOURCES_SPENT, "RESOURCES_SPENT", SINT, "Resources spent", ""),
			new Stat(STAT_RESOURCES, "RESOURCES", SINT, "Resources", ""),
			
			new Stat(STAT_POOL_KARMA, "POOL.KARMA", SINT, "Karma pool", ""),
			new Stat(STAT_POOL_COMBAT, "POOL.COMBAT", SINT, "Combat pool", ""),
			new Stat(STAT_POOL_CONTROL, "POOL.CONTROL", SINT, "Control pool", ""),
			new Stat(STAT_POOL_HACKING, "POOL.HACKING", SINT, "Hacking pool", ""),
			new Stat(STAT_POOL_SPELL, "POOL.SPELL", SINT, "Spell pool", ""),
			new Stat(STAT_POOL_TASK, "POOL.TASK", SINT, "Task pool", ""),
			new Stat(STAT_POOL_COMBAT_MOD, "POOL.COMBAT_MOD", SINT, "Combat mod pool", ""),
			
			new Stat(STAT_KARMA_TOTAL, "KARMA.TOTAL", SINT, "Total karma", ""),
			new Stat(STAT_KARMA_FREE,	"KARMA.FREE", SINT, "Free karma", ""),
			new Stat(STAT_KARMA_SPENT, "KARMA.SPENT", SINT, "Spent karma", ""),
			
			new Stat(STAT_METAHUMAN, "METAHUMAN", SINT, "Metahuman", ""),
			new Stat(STAT_xxxx, "xxxx", SINT, "xxxx", ""),
			new Stat(STAT_MAGICAL, "MAGICAL", SINT, "Magic user", ""),
			new Stat(STAT_ALLOWED_RACES, "ALLOWED_RACES", SINT, "Allowed races", ""),
			new Stat(STAT_FINALIZED, "FINALIZED", SINT, "Finalized", ""),

			new Stat(STR_CHARNAME, "NAME", SSTR, "Name", ""),
			new Stat(STR_REALNAME, "REALNAME", SSTR, "Real name", ""),
			new Stat(STR_PLAYERNAME, "PLAYERNAME", SSTR, "Player name", ""),
			new Stat(STR_STREETNAME1, "STREETNAME1", SSTR, "Street name1", ""),
			new Stat(STR_STREETNAME2, "STREETNAME2", SSTR, "Street name2", ""),
			new Stat(STR_STREETNAME3, "STREETNAME3", SSTR, "Street name3", ""),
			new Stat(STR_STREETNAME4, "STREETNAME4", SSTR, "Street name4", ""),
			new Stat(STR_STREETNAME5, "STREETNAME5", SSTR, "Street name5", ""),
			new Stat(STR_RACE, "RACE", SSTR, "Race", ""),
			new Stat(STR_AGE, "AGE", SSTR, "Age", ""),
			new Stat(STR_SEX, "SEX", SSTR, "Sex", ""),
			new Stat(STR_HEIGHT, "HEIGHT", SSTR, "Height", ""),
			new Stat(STR_WEIGHT, "WEIGHT", SSTR, "Weight", ""),
			new Stat(STR_HAIR, "HAIR", SSTR, "Hair", ""),
			new Stat(STR_EYES, "EYES", SSTR, "Eyes", ""),
			new Stat(STR_BIRTHDATE, "BIRTHDATE", SSTR, "Birth date", ""),
			new Stat(STR_BIRTHPLACE, "BIRTHPLACE", SSTR, "Birth place", ""),
			new Stat(STR_BIRTHNOTES, "BIRTHNOTES", SSTR, "Birth notes", ""),
			new Stat(STR_NOTES, "NOTES", SSTR, "Notes", ""),
			new Stat(STR_ARCHETYPE, "ARCHETYPE", SSTR, "Archetype", ""),
			new Stat(STR_CREATION_DATE, "CREATION_DATE", SSTR, "Creation date", ""),
			new Stat(STR_MOD_DATE, "MOD_DATE", SSTR, "Mod. date", ""),
			new Stat(STR_CREATION_SRDATE, "CREATION_SRDATE", SSTR, "SR Creation date", ""),
			new Stat(STR_SRDATE, "SRDATE", SSTR, "Current SR date", ""),
			new Stat(STR_IMAGE, "IMAGE", SSTR, "Image", ""),

	};
	
	private ArrayList<PCListener> listeners = new ArrayList<PCListener> (10);
	
	// Index used to generate new char's name
	private static int numNewChars = 0;

	private Object[] stats;
	// index is priority type (PRIO_*), value is priority (A-E == 0-4)  
	//private int[] priorities;
	//private String[] strings;

	private RepositoryList<EdgeAndFlaw> edges_and_flaws;
	private RepositoryList<Skill> skills;
	private RepositoryList<Spell> spells;
	private RepositoryList<Contact> contacts;
	private RepositoryList<Gear> gear;
	private RepositoryList<MageGear> mage_gear;
	private RepositoryList<Cyberware> cyberwares;
	private RepositoryList<Bioware> biowares;
	private RepositoryList<Vehicle> vehicles;
	private RepositoryList<Deck> decks;
	private RepositoryList<AdeptPower> adept_powers;
	private RepositoryList<Lifestyle> lifestyles;
	private RepositoryList<Credstick> credsticks;
	
	private ArrayList<String> logs;

	private boolean isDirty = false;
	private boolean signalActive = false;
	private String filename = null;


	public PlayerCharacter () {
		setupStatDesc();

		stats = new Object[statDesc.length];

		resetStats ();
		
		edges_and_flaws = new RepositoryList<EdgeAndFlaw> (this);
		skills = new RepositoryList<Skill> (this);
		spells = new RepositoryList<Spell> (this);
		contacts = new RepositoryList<Contact> (this);
		gear = new RepositoryList<Gear> (this);
		mage_gear = new RepositoryList<MageGear> (this);
		cyberwares = new RepositoryList<Cyberware> (this);
		biowares = new RepositoryList<Bioware> (this);
		vehicles = new RepositoryList<Vehicle> (this);
		decks = new RepositoryList<Deck> (this);		
		adept_powers = new RepositoryList<AdeptPower> (this);		
		lifestyles = new RepositoryList<Lifestyle> (this);
		credsticks = new RepositoryList<Credstick> (this);

		edges_and_flaws.addListener(this);
		// FIXME: listen to other tables
		recompute ();
		isDirty = false;
	}
	
	private static void setupStatDesc () {
		// Only do this once
		if (keyToStatMap != null)
			return;
		
		keyToStatMap = new Hashtable();
		
		for (int i=0; i < statDesc.length; i++) {
			// FIXME: check index == i
			keyToStatMap.put(statDesc[i].key, i);
		}
	}

	private void resetStats () {
		for (int i = 0; i < statDesc.length; i++) {
			if (statDesc[i].type == SINT)
				stats[i] = 0;
			else if (statDesc[i].type == SFLOAT)
				stats[i] = 0.0;
			else
				stats[i] = null;
		}

		for (int i = 0; i < 5; i++) {
			stats[PRIO_RACE + i] = i;
		}

		stats[STR_CHARNAME] = "new-" + numNewChars++;
		stats[STR_PLAYERNAME] = Config.get("srgen.player_name");
		stats[STR_CREATION_DATE] = DateFormat.getDateInstance().format (new Date());
	}

	// Computed stats

	public void recompute () {
		computeInitialResources ();
		
		setStat (STAT_REA_BASE, getReaction ());

		
		
		setFloat (STAT_ESS, getFloat (STAT_ESS_BASE) + getFloat (STAT_ESS_M1) + getFloat (STAT_ESS_M2) + getFloat (STAT_ESS_M3));
		setStat (STAT_MAG_BASE, (getInt (STAT_MAGICAL) != 0) ? getInt (STAT_ESS) : 0); // this rounds the float down

		for (int i = 0; i < STAT_NUM_ATTRS * 5; i += 5) {
			if (STAT_ATTR + i != STAT_ESS)
				setStat (STAT_ATTR + i, getInt (STAT_ATTR_BASE + i) + getInt (STAT_ATTR_M1 + i) + getInt (STAT_ATTR_M2 + i) + getInt (STAT_ATTR_M3 + i));
		}


		setStat (STAT_POOL_KARMA, getKarmaPool ());
		setStat (STAT_POOL_COMBAT, getCombatPool ());
	}
	
	private void computeInitialResources () {
		int[] attr_points = { 30, 27, 24, 21, 18 };
		int[] skill_points = { 50, 40, 34, 30, 27 };
		int[] res = { 1000000, 400000, 90000, 20000, 5000 };
		int[] magic = { 2, 1, 0, 0, 0 };
		int[] races = { 2, 2, 2, 1, 0 };

		int attr_points_spent = 0;
		for (int i = 0; i < STAT_NUM_ATTRS * 5; i += 5) {
			attr_points_spent += getInt (STAT_ATTR_BASE + i);
		}

		setFloat (STAT_ESS_BASE, 6.0); // FIXME: cyberware etc.

		setStat (STAT_ATTR_POINTS_INITIAL, attr_points[getInt(PRIO_ATTRS)]);
		setStat (STAT_ATTR_POINTS, getInt(STAT_ATTR_POINTS_INITIAL) - getInt(STAT_ATTR_POINTS_SPENT));
		setStat (STAT_SKILL_POINTS_INITIAL, skill_points[getInt(PRIO_SKILLS)]);
		setStat (STAT_SKILL_POINTS, getInt(STAT_SKILL_POINTS_INITIAL) - getInt(STAT_SKILL_POINTS_SPENT));
		setStat (STAT_RESOURCES_INITIAL, res[getInt(PRIO_RESOURCES)]);
		setStat (STAT_RESOURCES, getInt(STAT_RESOURCES_INITIAL) - getInt(STAT_RESOURCES_SPENT));
		setStat (STAT_MAGICAL, magic[getInt(PRIO_MAGIC)]);
		setStat (STAT_ALLOWED_RACES, races[getInt(PRIO_RACE)]);
	}

	public int getReaction () {
		return (getInt (STAT_INT) + getInt (STAT_QCK)) / 2;
	}

	public int getKarmaPool () {
		return 1 + (getInt (STAT_KARMA_TOTAL) - 1) / ((getInt (STAT_METAHUMAN) != 0) ? 20 : 10); 
	}
	
	public int getCombatPool () {
		return (getInt (STAT_INT) + getInt (STAT_QCK) + getInt (STAT_WIL)) / 2;
	}

	// Checks made before the char is finalized
	public boolean isValidUnfinalizedChar() {
		// ...
		// check item rating and availability
		// check ammo?
		// check has lifestyle
		return true;
	}
	
	public boolean isValid () {
		return getInt (STAT_BOD) > 4; // just a test
	}
	
	// beware of Object.finalize()!
	public boolean finalizeChar () {
		return true;
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

		// FIXME: should be in setDirty(), but we need the
		//   recursion protection
		
		System.err.println("fire0 " + String.valueOf(isDirty));
		if (isDirty())
			recompute();

		Iterator<PCListener> iter = listeners.iterator();
		while (iter.hasNext ()) {
			PCListener ls = iter.next ();
			try {
				System.err.println("fire1 " + String.valueOf(isDirty));
				ls.pcChanged(this);
			}
			catch (Exception e) {
				e.printStackTrace();
				// FIXME: don't catch or at least not all
			}
		}
		
		signalActive = false;
	}

	public int[] getPriorities () {
		int[] res = new int[NUM_PRIO];
		for (int i=0; i<NUM_PRIO; i++)
			res[i] = (Integer)stats[PRIO_RACE + i];
		return res;
	}

	public void setPriorities (int[] newPriorities) {
		//resetStats ();
		for (int i=0; i<NUM_PRIO; i++)
			stats[PRIO_RACE + i] = newPriorities[i];
		computeInitialResources ();
		setDirty ();
	}
	
	public int getInt (int stat) {
		if (statDesc[stat].type == SFLOAT)
			return ((Double)stats[stat]).intValue();
		else
			return (Integer)stats[stat];
	}
	
	public int getInt (String key) {
		int stat = (Integer)keyToStatMap.get(key);
		return getInt(stat);
	}
	
	public String getString (int stat) {
		if (statDesc[stat].type == SSTR)
			return (String)stats[stat];
		else
			return String.valueOf(stats[stat]);
	}
	
	public double getFloat (int stat) {
		if (statDesc[stat].type == SFLOAT)
			return (Double)stats[stat];
		else if (statDesc[stat].type == SINT)
			return Double.valueOf((Integer)stats[stat]);
		else
			return Double.valueOf((String)stats[stat]);
	}
	
	public void setStat (int stat, int value) {
		//System.out.println (String.valueOf(stat) + " := " + String.valueOf(value));
		if ((Integer)stats[stat] != value) {
			stats[stat] = value;
			setDirty ();
		}
	}
	
	
	public void setString (int stat, String value) {
		if ((String)stats[stat] != value) {
			stats[stat] = value;
			setDirty ();
		}
	}

	public void setFloat (int stat, double value) {
		if (Double.compare((Double)stats[stat], value) != 0) {
			stats[stat] = value;
			setDirty ();
		}
	}

	public boolean isDirty () {
		return this.isDirty;
	}
	
	public void setDirty () {
		isDirty = true;
		stats[STR_MOD_DATE] = DateFormat.getDateInstance().format (new Date());
		firePCChanged ();
	}

	public void clearDirty () {
		if (isDirty()) {
			//strings[STR_MOD_DATE] = DateFormat.getDateInstance().format (new Date());
			isDirty = false;
			
			System.err.println("clear");
			firePCChanged ();
		}
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void addRepositoryObject (EdgeAndFlaw obj, String custom, int amount, int amount100) {
		edges_and_flaws.add (obj);
		setDirty ();
	}

	public void addRepositoryObject (Skill obj, String custom, int amount, int amount100) {
		skills.add (obj);
		setDirty ();
	}

	public void addRepositoryObject (Spell obj, String custom, int amount, int amount100) {
		spells.add (obj);
		setDirty ();
	}

	public void AddEdgeAndFlaw (EdgeAndFlaw edge, String custom, boolean unique) {
		edges_and_flaws.add (edge);
		setDirty ();
	}
	
	public void RemoveEdgeAndFlaw (EdgeAndFlaw edge) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<EdgeAndFlaw> getEdgeAndFlaw_List () {
		return edges_and_flaws;
	}

	
	public void AddSkill (Skill skill, String custom, boolean unique) {
		skills.add (skill);
		setDirty ();
	}
	
	public void RemoveSkill (Skill skill) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Skill> getSkill_List () {
		return skills;
	}


	public void AddSpell (Spell spell, String custom, boolean unique) {
		spells.add (spell);
		setDirty ();
	}
	
	public void RemoveSpell (Spell spell) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Spell> getSpell_List () {
		return spells;
	}


	public void AddLifestyle (Lifestyle lifestyle, String custom, boolean unique) {
		lifestyles.add (lifestyle);
		setDirty ();
	}
	
	public void RemoveLifestyle (Lifestyle lifestyle) {
	}
	
	public RepositoryList<Lifestyle> getLifestyle_List () {
		return lifestyles;
	}

	
	public void AddCredstick (Credstick credstick, String custom, boolean unique) {
		credsticks.add (credstick);
		setDirty ();
	}
	
	public void RemoveCredstick (Credstick credstick) {
	}
	
	public RepositoryList<Credstick> getCredstick_List () {
		return credsticks;
	}

	

	public void AddContact (Contact contact, String custom, boolean unique) {
		contacts.add (contact);
		setDirty ();
	}
	
	public void RemoveContact (Contact contact) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Contact> getContact_List () {
		return contacts;
	}

	
	public void AddGear (Gear gear, String custom, boolean unique) {
		this.gear.add (gear);
		setDirty ();
	}
	
	public void RemoveGear (Gear gear) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Gear> getGear_List () {
		return gear;
	}


	public void AddMageGear (MageGear obj, String custom, boolean unique) {
		mage_gear.add (obj);
		setDirty ();
	}
	
	public void RemoveMageGear (MageGear obj) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<MageGear> getMageGear_List () {
		return mage_gear;
	}


	public void AddCyberware (Cyberware cyber, String custom, boolean unique) {
		cyberwares.add (cyber);
		setDirty ();
	}
	
	public void RemoveCyberware (Cyberware cyber) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Cyberware> getCyberware_List () {
		return cyberwares;
	}

	
	public void AddBioware (Bioware bio, String custom, boolean unique) {
		biowares.add (bio);
		setDirty ();
	}
	
	public void RemoveBioware (Bioware bio) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Bioware> getBioware_List () {
		return biowares;
	}

	
	public void AddVehicle (Vehicle vehicle, String custom, boolean unique) {
		vehicles.add (vehicle);
		setDirty ();
	}
	
	public void RemoveVehicle (Vehicle vehicle) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Vehicle> getVehicle_List () {
		return vehicles;
	}


	public void AddDeck (Deck deck, String custom, boolean unique) {
		decks.add (deck);
		setDirty ();
	}
	
	public void RemoveDeck (Deck deck) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<Deck> getDeck_List () {
		return decks;
	}
	

	public void AddAdeptPower (AdeptPower obj, String custom, boolean unique) {
		adept_powers.add (obj);
		setDirty ();
	}
	
	public void RemoveAdeptPower (AdeptPower obj) {
		//edges_and_flaws.add (edge);
	}
	
	public RepositoryList<AdeptPower> getAdeptPowers_List () {
		return adept_powers;
	}
	

	@Override
	public void pcAssetChanged(PlayerCharacter pc, RepositoryList list) {
		setDirty ();
	}	
}
