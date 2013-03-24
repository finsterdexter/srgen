package cz.eowyn.srgen.gui;

import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.*;

import cz.eowyn.srgen.model.PlayerCharacter;


public class SummaryPanel extends JPanel implements cz.eowyn.srgen.model.PCListener, DocumentListener {
	private JPanel panel = null;
	private JTextField charNameEntry;
	private JTextField realNameEntry;
	private JTextField playerNameEntry;
	private JTextField streetNameEntry;
	private JTextField birthDateEntry;
	private JTextField birthPlaceEntry;
	private JTextField hairEntry;
	private JTextField eyesEntry;
	private JTextField heightEntry;
	private JTextField weightEntry;
	private JTextField attrPointsLabel = null;
	private JTextField skillPointsLabel = null;
	private JTextField resourcesLabel = null;
	private JTextField magicLabel = null;
	private JTextField racesLabel = null;
	//private JTextField reactionLabel = null;
	private JTextField creationDateLabel = null;
	private JTextField modifiedDateLabel = null;
	private AttributeTable attributeTable = null;
	
	private PlayerCharacter pc = null;

	/** Prevent sending 'pcChanged' signals when updating gui, 
		because it's difficult to distinguish it from
		changes done by user input */
	private boolean inUpdate = false;
	
    public SummaryPanel (PlayerCharacter pc) {
    	super (new MigLayout());
    	this.pc = pc;
    	pc.addListener (this);
    	
    	// commonPanel.setLayout(null);
		// commonPanel.setPreferredSize(new java.awt.Dimension(255,170));

		// Character Tab?? Name
		charNameEntry = getTextEntry ("Char Name", pc.STR_CHARNAME, "cell 0 0", "cell 1 0, width :200:", this);
		realNameEntry = getTextEntry ("Real Name", pc.STR_REALNAME, "cell 0 1", "cell 1 1, width :200:", this);
		playerNameEntry = getTextEntry ("Player Name", pc.STR_PLAYERNAME, "cell 0 2", "cell 1 2, width :200:", this);
		streetNameEntry = getTextEntry ("Street Name", pc.STR_STREETNAME1, "cell 0 3", "cell 1 3, width :200:", this);
		creationDateLabel = getLabelEntry ("Created", pc.STR_CREATION_DATE, "cell 0 4", "cell 1 4", this);
		modifiedDateLabel = getLabelEntry ("Last Changed", pc.STR_MOD_DATE, "cell 0 5", "cell 1 5", this);

		birthDateEntry = getTextEntry ("Birth Date", pc.STR_BIRTHDATE, "cell 2 0", "cell 3 0, width :200:", this);
		birthPlaceEntry = getTextEntry ("Birth Place", pc.STR_BIRTHPLACE, "cell 2 1", "cell 3 1, width :200:", this);
		hairEntry = getTextEntry ("Hair", pc.STR_HAIR, "cell 2 2", "cell 3 2, width :200:", this);
		eyesEntry = getTextEntry ("Eyes", pc.STR_EYES, "cell 2 3", "cell 3 3, width :200:", this);
		heightEntry = getTextEntry ("Height", pc.STR_HEIGHT, "cell 2 4", "cell 3 4, width :200:", this);
		weightEntry = getTextEntry ("Weight", pc.STR_WEIGHT, "cell 2 5", "cell 3 5, width :200:", this);


		getLabel ("Priorities", "cell 0 8 1 1", this);
		this.add(new PriorityTable(pc), "cell 0 9 1 5");
		
		attrPointsLabel = getLabelEntry ("Attribute points", pc.STAT_ATTR_POINTS, "cell 2 9", "cell 3 9", this);
		skillPointsLabel = getLabelEntry ("Skill points", pc.STAT_SKILL_POINTS, "cell 2 10", "cell 3 10", this);
		resourcesLabel = getLabelEntry ("Resources", pc.STAT_RESOURCES, "cell 2 11", "cell 3 11", this);
		magicLabel = getLabelEntry ("Magical", pc.STAT_MAGICAL, "cell 2 12", "cell 3 12", this);
		racesLabel = getLabelEntry ("Races", pc.STAT_ALLOWED_RACES, "cell 2 13", "cell 3 13", this);


		attributeTable = new AttributeTable (pc);
		this.add(attributeTable, "cell 0 15 4 7");
		//this.add(new PriorityTable(pc));

		this.add(new InvalidWarningPane (pc), "cell 0 22 6 1");
		
		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		pcChanged (pc);
    }
    
    
    private JTextField getTextEntry (String name, int stat, String constraintLabel, String constraintValue, JComponent owner) {
    	int type = pc.statDesc[stat].type;
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
		entry.getDocument ().addDocumentListener(this);
		owner.add (label, constraintLabel);
		owner.add (entry, constraintValue);
		
		entry.getDocument().putProperty("stat", stat);
		entry.getDocument().putProperty("statType", type);
		
		return entry;
    }
    
    private JTextField getLabelEntry (String name, int stat, String constraintLabel, String constraintValue, JComponent owner) {
    	int type = pc.statDesc[stat].type;
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
        entry.setEditable (false);
        entry.setFocusable (false);
        
		owner.add (label, constraintLabel);
		owner.add (entry, constraintValue);
		
		return entry;
    }

    private JTextField getReadOnly (int stat, String constraintValue, JComponent owner) {
    	int type = pc.statDesc[stat].type;
        JTextField entry = new JTextField ();
		owner.add (entry, constraintValue);
		
		entry.getDocument().putProperty("stat", stat);
		entry.getDocument().putProperty("statType", type);
		
		return entry;
    }
    
    private void getLabel (String name, String constraintLabel, JComponent owner) {
		JLabel label = new JLabel (name + ": ");        
		owner.add (label, constraintLabel);
    }

    public void pcChanged (PlayerCharacter pc) {
    	System.err.println("SummaryPanel::pcChanged: " + String.valueOf(inUpdate));
    	if (inUpdate)
    		return;
    	
    	inUpdate = true;
        //System.err.println("SummaryPanel0: " + String.valueOf(pc.isDirty()));
        charNameEntry.setText (pc.getString (PlayerCharacter.STR_CHARNAME));
        //System.err.println("SummaryPanel1: " + String.valueOf(pc.isDirty()));
        realNameEntry.setText (pc.getString (PlayerCharacter.STR_REALNAME));
        playerNameEntry.setText (pc.getString (PlayerCharacter.STR_PLAYERNAME));
        streetNameEntry.setText (pc.getString (PlayerCharacter.STR_STREETNAME1));
        birthDateEntry.setText (pc.getString (PlayerCharacter.STR_BIRTHDATE));
        birthPlaceEntry.setText (pc.getString (PlayerCharacter.STR_BIRTHPLACE));
        hairEntry.setText (pc.getString (PlayerCharacter.STR_HAIR));
        eyesEntry.setText (pc.getString (PlayerCharacter.STR_EYES));
        heightEntry.setText (pc.getString (PlayerCharacter.STR_HEIGHT));
        weightEntry.setText (pc.getString (PlayerCharacter.STR_WEIGHT));
        attrPointsLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_ATTR_POINTS)));
        skillPointsLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_SKILL_POINTS)));
        resourcesLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_RESOURCES) + "\u00A5"));
        //magicLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_MAGIC)));
        //racesLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_ALLOWED_RACES)));
    	//reactionLabel.setText (String.valueOf (pc.getReaction()));
    	creationDateLabel.setText (pc.getString (PlayerCharacter.STR_CREATION_DATE));
    	modifiedDateLabel.setText (pc.getString (PlayerCharacter.STR_MOD_DATE));
    	
    	
        int magical = pc.getInt(PlayerCharacter.STAT_MAGICAL);
        String magicText;

        switch (magical) {
        case 2: 
        	magicText = "Full magical abilities, astral walking";
        	break;
        case 1:
        	magicText = "Adept/Aspected magician";
        	break;
        default:
        	magicText = "No magical abilities";
        	break;
        }

        magicLabel.setText (magicText);
        
        int races = pc.getInt(PlayerCharacter.STAT_ALLOWED_RACES);
        String racesText;
        
        switch (races) {
        case 0: 
        	racesText = "Human";
        	break;
        case 1:
        	racesText = "Dwarf/Ork";
        	break;
        default:
        	racesText = "Elf/Troll";
        	break;
        }
        
        racesLabel.setText (racesText);

    	
    	attributeTable.setPlayerCharacter (pc);
        System.err.println("SummaryPanel2: " + String.valueOf(pc.isDirty()));
        inUpdate = false;
    }

    protected void guiChanged (int stat, int statType, String text, int num) {
    	System.err.println("SummaryPanel::guiChanged: " + String.valueOf(inUpdate));
    	if (inUpdate)
    		return;
    				
    	inUpdate = true;
    	
    	if (stat >= 0) {
    		if (statType == pc.SSTR)
    			pc.setString(stat, text);
    		else
    			pc.setStat(stat, num);
    	} else {
    		pc.setString(PlayerCharacter.STR_CHARNAME, charNameEntry.getText());		
    		pc.setString(PlayerCharacter.STR_REALNAME, realNameEntry.getText());		
    		pc.setString(PlayerCharacter.STR_PLAYERNAME, playerNameEntry.getText());		
    		pc.setString(PlayerCharacter.STR_STREETNAME1, streetNameEntry.getText());
    	}
    	inUpdate = false;
    	
    }

	public void pcExchanged (PlayerCharacter pc) {
		setPlayerCharacter (pc);
	}

	public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	pcChanged (pc);
    }


	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println ("changeUpdate...");
		
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		System.err.println ("insertUpdate...");
		// FIXME: hopelessly ugly
		Document doc = e.getDocument();
		int stat = (Integer)doc.getProperty("stat");
		int statType = (Integer)doc.getProperty("statType");
		String text = "";
		int num = 0;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException ex) {
			// ...
		}
		try {
			num = Integer.valueOf(text);
		} catch (NumberFormatException ex) {
			// ...
		}
		guiChanged(stat, statType, text, num);
	}


	@Override
	public void removeUpdate(DocumentEvent e) {
		System.err.println ("removeUpdate...");
		// FIXME: hopelessly ugly
		Document doc = e.getDocument();
		int stat = (Integer)doc.getProperty("stat");
		int statType = (Integer)doc.getProperty("statType");
		String text = "";
		int num = 0;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException ex) {
			// ...
		}
		try {
			num = Integer.valueOf(text);
		} catch (NumberFormatException ex) {
			// ...
		}
		guiChanged(stat, statType, text, num);
	}
}