/* SRGen - Shadowrun character generator
 * Copyright (C) 2005 Edheldil, edheldil@users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * $Header$
 *
 */

/**
 *
 */

package cz.eowyn.srgen.io;

import java.io.*;
import java.util.Hashtable;

import cz.eowyn.srgen.model.Bioware;
import cz.eowyn.srgen.model.Contact;
import cz.eowyn.srgen.model.Credstick;
import cz.eowyn.srgen.model.Cyberware;
import cz.eowyn.srgen.model.Deck;
import cz.eowyn.srgen.model.EdgeAndFlaw;
import cz.eowyn.srgen.model.Gear;
import cz.eowyn.srgen.model.MageGear;
import cz.eowyn.srgen.model.Lifestyle;
import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.Skill;
import cz.eowyn.srgen.model.Spell;
import cz.eowyn.srgen.model.Vehicle;

/**
 * @author benkovsk
 *
 */

public class NSRCG3_SR3_Loader {

	private Hashtable map;
	private Repository repository;

	public NSRCG3_SR3_Loader (Repository repository) {
		super();
		this.repository = repository;
	}

	public PlayerCharacter ImportFile (String filename) throws IOException {
		PlayerCharacter pc = new PlayerCharacter ();
		readFile (filename);
	        
		// priorities
		int[] priorities = new int[5];
		for (int p = 1; p <= 5; p++) {
			int pt = ((String) map.get("Priorities.Priority" + p)).charAt(0) - '1';
			priorities[pt] = p -1;
			//System.out.println("PT: " + pt + "  P:" + p);
	    }
		pc.setPriorities(priorities);

		pc.setString (PlayerCharacter.STR_CHARNAME, getString ("General.CharName"));
		pc.setString (PlayerCharacter.STR_REALNAME, getString ("General.RealName"));
		pc.setString (PlayerCharacter.STR_PLAYERNAME, getString ("General.Creator"));
		pc.setString (PlayerCharacter.STR_STREETNAME1, getString ("General.StreetName1"));
		// FIXME: read the rest of street names as well
		
		pc.setString (PlayerCharacter.STR_RACE, getString ("Race.RaceName"));
		
		
		pc.setStat (PlayerCharacter.STAT_ATTR_POINTS, getInt ("Priorities.Attributes"));
		pc.setStat (PlayerCharacter.STAT_SKILL_POINTS, getInt ("Priorities.Skills"));
		
		String[] attrKeys = { "Body", "Quickness", "Strength", "Charisma", "Intelligence", "Willpower" };  
		for (int i = 0; i < 6; i++) {
			String[] attrValues = (getString ("Attributes." + attrKeys[i])).split ("\\|", -1);
			for (int j=0; j < 4; j++) {
				pc.setStat (PlayerCharacter.STAT_BOD_BASE + 5 * i + j, Integer.parseInt (attrValues[j].replace ("+", "")));
			}
		}
		
		// NOTE: Age is not really in the SR3 files as generated by NSRCGv3
		String[] strKeys = { "Age", "Sex", "Height", "Weight", "Hair", "Eyes", "BirthDate", 
				"BirthPlace", "BirthNotes", "Notes", "Archtype", "CreationDate", "ModDate", "GifFile" }; 
		for (int i = 0; i < strKeys.length; i++) {
			pc.setString (PlayerCharacter.STR_AGE + i, getString ("General." + strKeys[i]));
		}
		
		
		String[] poolKeys = { "Combat", "Control", "Hacking", "Spell", "Task", "CombatPoolMod" }; 
		for (int i = 0; i < poolKeys.length; i++) {
			pc.setStat (PlayerCharacter.STAT_POOL_COMBAT + i, getInt ("Pools." + poolKeys[i]));
		}
		
		pc.setStat (PlayerCharacter.STAT_KARMA_TOTAL, getInt ("Karma.TotalKarma"));
		pc.setStat (PlayerCharacter.STAT_KARMA_FREE, getInt ("Karma.RemainKarma"));
		pc.setStat (PlayerCharacter.STAT_KARMA_SPENT, getInt ("Karma.SpentKarma"));
		
		// Lifestyles
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("General.LifeStyle", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("$Cost", fields[5]);
			Lifestyle obj = new Lifestyle (fields[0], values);
			
			pc.AddLifestyle (obj, "", false);
		}

		// Credsticks
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("General.CredStick", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("$Cost", fields[4]);
			Credstick obj = new Credstick (fields[0], values);
			
			pc.AddCredstick (obj, "", false);
		}

		// Edges and Flaws
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Edges.Edge", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("$Cost", fields[1]);
			values.put ("Book.Page", fields[2]);
			values.put ("Mods", fields[3]);
			values.put ("Notes", fields[4]);
			values.put ("EorF", fields[5]);
			EdgeAndFlaw obj = new EdgeAndFlaw(fields[0], values);
			
			pc.AddEdgeAndFlaw(obj, "", false);
		}
		
		// Skills
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Skills.Skill", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("st", fields[1]);
			Skill obj = new Skill(fields[0], values);
			
			pc.AddSkill(obj, "", false);
		}
		
		// Contacts
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Contacts.Contact", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("Occupation", fields[4]);
			values.put ("Race", fields[6]);
			values.put ("$Cost", fields[8]);
			Contact obj = new Contact(fields[0], values);
			
			pc.AddContact(obj, "", false);
		}
		
		// Magic ...
		
		// Gears
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Gear.Gear", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			String[] fields2 = fields[1].split("\\|", -1);
			NSRCG3_Format format = repository.getGearFormat (fields2[0]);
			Hashtable values = new Hashtable ();
			//values.put ("Name", fields[0]);
			if (format != null) {
				values.putAll (format.getValuesMap(fields2));
			} else {
				values.put ("Data", fields[1]);
			}
			Gear obj = new Gear(fields[0], values);
			
			pc.AddGear(obj, "", false);
		}
		
		// Gears
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Magic.MageGear", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			String[] fields2 = fields[1].split("\\|", -1);
			NSRCG3_Format format = repository.getMageGearFormat (fields2[0]);
			Hashtable values = new Hashtable ();
			//values.put ("Name", fields[0]);
			if (format != null) {
				values.putAll (format.getValuesMap (fields2));
			} else {
				values.put ("Data", fields[1]);
			}
			MageGear obj = new MageGear (fields[0], values);
			
			pc.AddMageGear (obj, "", false);
		}
		
		// Cyberware
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Cyberware.Cyber", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace ('_', ' ').split ("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("EssCost", fields[1]);
			values.put ("$Cost", fields[2]);
			values.put ("Book.Page", fields[3]);
			values.put ("Mods", fields[4]);
			values.put ("LegalCode", fields[5]);
			values.put ("Notes", fields[6]);
			values.put ("??", fields[7]);
			values.put ("slot", fields[8]);
			values.put ("Availability", fields[9]);
			Cyberware obj = new Cyberware (fields[0], values);
			
			pc.AddCyberware(obj, "", false);
		}
		
		// Bioware ...
		
		// Vehicle
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Vech.Vech", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace ('_', ' ').split ("~", -1);
			String[] fields2 = fields[1].split ("\\|", -1);
			NSRCG3_Format format = repository.getVehiclesFormat (fields2[0]);
			Hashtable values = new Hashtable ();
			//values.put ("Name", fields[0]);
			if (format != null) {
				values.putAll (format.getValuesMap (fields2));
			} else {
				values.put ("Data", fields[1]);
			}
			Vehicle obj = new Vehicle (fields[0], values);
			
			pc.AddVehicle (obj, "", false);
		}
		
		// Deck ...
		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Deck.Deck", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace ('_', ' ').split ("~", -1);
			String[] fields2 = fields[1].split ("\\|", -1);
			NSRCG3_Format format = repository.getDecksFormat (fields2[0]);
			Hashtable values = new Hashtable ();
			//values.put ("Name", fields[0]);
			if (format != null) {
				values.putAll (format.getValuesMap (fields2));
			} else {
				values.put ("Data", fields[1]);
			}
			Deck obj = new Deck (fields[0], values);
			
			pc.AddDeck (obj, "", false);
		}

		for (int index = 0; ; index++) {
			String value_raw = getIndexedString ("Magic.Spell", index, null);
			if (value_raw == null) {
				break;
			}
			String[] fields = value_raw.replace('_', ' ').split("~", -1);
			Hashtable values = new Hashtable ();
			values.put ("Name", fields[0]);
			values.put ("Type", fields[1]);
			values.put ("Target", fields[2]);
			values.put ("Duration", fields[3]);
			values.put ("Range", fields[4]);
			values.put ("Drain", fields[5]);
			values.put ("Rank", fields[6]);
			values.put ("Book.Page", fields[7]);
			values.put ("??1", fields[8]);
			values.put ("??2", fields[9]);
			values.put ("??3", fields[10]);

			Spell obj = new Spell (fields[0], values);
			
			pc.AddSpell (obj, "", false);
		}
		
		return pc;

	}

	protected void readFile (String filename) throws IOException {
		String section = "";

		try {
			File srcfile = new File (filename);
			LineNumberReader src = new LineNumberReader (new FileReader (srcfile));

			map = new Hashtable ();
	
			String line;
			while ((line = src.readLine ()) != null) {
				line = line.trim();
				
				if (line.length() == 0) {
					continue;
		        }
				
				if (line.charAt (0) == '[') {
					section = line.substring(1, line.length() - 1);
					//System.out.println ("Section " + section);
				}
				else {
					String[] parts = line.split ("=", 2);
					String key = parts[0].trim ();
					String value = parts[1].trim();
					map.put (section + "." + key, value);
					//System.out.println (section + "." + key + " <<:>> " + value);
				}
	        }
	        src.close();
	    } catch (IOException e) {
	    	//blocker.setBlocked(false);
	    	System.err.println("Can't load file: " + filename);
	    	throw e;
	    }	
	}

	
	protected int getInt (String key) {
		return getInt (key, 0);
	}

	protected int getInt (String key, int defaultValue) {
		try {
			return Integer.parseInt((String)map.get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	protected int getIndexedInt (String key, int index, int defaultValue) {
		try {
			return Integer.parseInt((String)map.get(key + String.valueOf(index)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	protected String getString (String key) {
		return getString (key, "");
	}

	protected String getString (String key, String defaultValue) {
		try {
			return (String)map.get(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	protected String getIndexedString (String key, int index, String defaultValue) {
		try {
			return (String)map.get(key + String.valueOf(index));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
