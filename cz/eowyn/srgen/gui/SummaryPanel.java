package cz.eowyn.srgen.gui;

import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
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
	private JTextField reactionLabel = null;
	private JTextField creationDateLabel = null;
	private JTextField modifiedDateLabel = null;
	private AttributeTable attributeTable = null;
	
	private PlayerCharacter pc = null;

	
    public SummaryPanel (PlayerCharacter pc) {
    	super (new MigLayout());
    	this.pc = pc;
    	pc.addListener (this);
    	
    	// commonPanel.setLayout(null);
		// commonPanel.setPreferredSize(new java.awt.Dimension(255,170));

		// Character Tab?? Name
		charNameEntry = getTextEntry ("Char Name", "cell 0 0", "cell 1 0, width :200:", this);
		realNameEntry = getTextEntry ("Real Name", "cell 0 1", "cell 1 1, width :200:", this);
		playerNameEntry = getTextEntry ("Player Name", "cell 0 2", "cell 1 2, width :200:", this);
		streetNameEntry = getTextEntry ("Street Name", "cell 0 3", "cell 1 3, width :200:", this);
		creationDateLabel = getLabelEntry ("Created", "cell 0 4", "cell 1 4", this);
		modifiedDateLabel = getLabelEntry ("Last Changed", "cell 0 5", "cell 1 5", this);

		birthDateEntry = getTextEntry ("Birth Date", "cell 2 0", "cell 3 0, width :200:", this);
		birthPlaceEntry = getTextEntry ("Birth Place", "cell 2 1", "cell 3 1, width :200:", this);
		hairEntry = getTextEntry ("Hair", "cell 2 2", "cell 3 2, width :200:", this);
		eyesEntry = getTextEntry ("Eyes", "cell 2 3", "cell 3 3, width :200:", this);
		heightEntry = getTextEntry ("Height", "cell 2 4", "cell 3 4, width :200:", this);
		weightEntry = getTextEntry ("Weight", "cell 2 5", "cell 3 5, width :200:", this);


		attrPointsLabel = getLabelEntry ("Attribute points", "cell 0 7", "cell 1 7", this);
		skillPointsLabel = getLabelEntry ("Skill points", "cell 0 8", "cell 1 8", this);
		resourcesLabel = getLabelEntry ("Resources", "cell 0 9", "cell 1 9", this);

		reactionLabel = getLabelEntry ("Reaction", "cell 0 10", "cell 1 10", this);

		attributeTable = new AttributeTable (pc);
		this.add(attributeTable, "cell 0 12 4 4");
		//this.add(new PriorityTable(pc));

		
		charNameEntry.getDocument ().addDocumentListener(this);
		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		pcChanged (pc);
    }
    
    
    private JTextField getTextEntry (String name, String constraintLabel, String constraintValue, JComponent owner) {
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
		owner.add (label, constraintLabel);
		owner.add (entry, constraintValue);
		
		return entry;
    }
    
    private JTextField getLabelEntry (String name, String constraintLabel, String constraintValue, JComponent owner) {
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
        entry.setEditable (false);
        entry.setFocusable (false);
        
		owner.add (label, constraintLabel);
		owner.add (entry, constraintValue);
		
		return entry;
    }
    
    public void pcChanged (PlayerCharacter pc) {
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
    	pcChanged (pc);
    }


	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println ("changed...");
		
	}


	@Override
	public void insertUpdate(DocumentEvent arg0) {
		pc.setString(PlayerCharacter.STR_CHARNAME, charNameEntry.getText());		
	}


	@Override
	public void removeUpdate(DocumentEvent arg0) {
		pc.setString(PlayerCharacter.STR_CHARNAME, charNameEntry.getText());		
	}
}