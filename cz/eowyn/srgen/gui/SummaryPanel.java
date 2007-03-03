package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.table.*;
import javax.swing.*;

import cz.eowyn.srgen.model.PlayerCharacter;


public class SummaryPanel extends JPanel implements cz.eowyn.srgen.model.PCListener {
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
	private JTextField reactionLabel = null;
	private JTextField creationDateLabel = null;
	private JTextField modifiedDateLabel = null;
	private AttributeTable attributeTable = null;
	
	private PlayerCharacter pc = null;

	
    public SummaryPanel (PlayerCharacter pc) {
    	super ();
    	this.pc = pc;
    	pc.addListener (this);
    	
    	// commonPanel.setLayout(null);
		// commonPanel.setPreferredSize(new java.awt.Dimension(255,170));
		Box box;
		JLabel label;

		// Character Tab?? Name
		charNameEntry = getTextEntry ("Char Name", this);
		realNameEntry = getTextEntry ("Real Name", this);
		playerNameEntry = getTextEntry ("Player Name", this);
		streetNameEntry = getTextEntry ("Street Name", this);

		attrPointsLabel = getLabelEntry ("Attribute points", this);
		skillPointsLabel = getLabelEntry ("Skill points", this);
		resourcesLabel = getLabelEntry ("Resources", this);

		reactionLabel = getLabelEntry ("Reaction", this);

		birthDateEntry = getTextEntry ("Birth Date", this);
		birthPlaceEntry = getTextEntry ("Birth Place", this);
		hairEntry = getTextEntry ("Hair", this);
		eyesEntry = getTextEntry ("Eyes", this);
		heightEntry = getTextEntry ("Height", this);
		weightEntry = getTextEntry ("Weight", this);

		creationDateLabel = getLabelEntry ("Created", this);
		modifiedDateLabel = getLabelEntry ("Last Changed", this);

		attributeTable = new AttributeTable (pc);
		this.add(attributeTable);
		//this.add(new PriorityTable(pc));

		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		pcChanged ();
    }
    
    
    private JTextField getTextEntry (String name, JComponent owner) {
		Box box = Box.createHorizontalBox ();
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
        //jLabelName.setSize(60, 26);
        //jLabelName.setLocation(150, 290);
		box.add (label);
		box.add (entry);
		owner.add (box);
		
		return entry;
    }
    
    private JTextField getLabelEntry (String name, JComponent owner) {
		Box box = Box.createHorizontalBox ();
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
        entry.setEditable (false);
        entry.setFocusable (false);
        
		box.add (label);
		box.add (entry);
		owner.add (box);
		
		return entry;
    }
    
    public void pcChanged () {
        charNameEntry.setText (pc.getString (PlayerCharacter.STR_CHARNAME));
        realNameEntry.setText (pc.getString (PlayerCharacter.STR_REALNAME));
        playerNameEntry.setText (pc.getString (PlayerCharacter.STR_PLAYERNAME));
        streetNameEntry.setText (pc.getString (PlayerCharacter.STR_STREETNAME1));
        birthDateEntry.setText (pc.getString (PlayerCharacter.STR_BIRTHDATE));
        birthPlaceEntry.setText (pc.getString (PlayerCharacter.STR_BIRTHPLACE));
        hairEntry.setText (pc.getString (PlayerCharacter.STR_HAIR));
        eyesEntry.setText (pc.getString (PlayerCharacter.STR_EYES));
        heightEntry.setText (pc.getString (PlayerCharacter.STR_HEIGHT));
        weightEntry.setText (pc.getString (PlayerCharacter.STR_WEIGHT));
        attrPointsLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_ATTR_POINTS)));
        skillPointsLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_SKILL_POINTS)));
        resourcesLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_RESOURCES) + "\u00A5"));
    	reactionLabel.setText (String.valueOf (pc.getReaction()));
    	creationDateLabel.setText (pc.getString (PlayerCharacter.STR_CREATION_DATE));
    	modifiedDateLabel.setText (pc.getString (PlayerCharacter.STR_MOD_DATE));
    	
    	attributeTable.setPlayerCharacter (pc);
    }

	public void pcExchanged (PlayerCharacter pc) {
		setPlayerCharacter (pc);
	}

	public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	pcChanged ();
    }
}