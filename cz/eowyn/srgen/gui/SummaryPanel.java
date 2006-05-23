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
	private JLabel resourcesLabel = null;
	private JLabel reactionLabel = null;
	private JLabel creationDateLabel = null;
	private JLabel modifiedDateLabel = null;
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
		box = Box.createHorizontalBox();
		label = new JLabel();
		label.setText("Char Name:");
		box.add(label);
		box.add(getCharNameEntry());
		this.add(box);

		// Character Real Name
		box = Box.createHorizontalBox();
		label = new JLabel();
		label.setText("Real Name:");
		box.add(label);
		box.add(getRealNameEntry());
		this.add(box);

		// Player Name
		box = Box.createHorizontalBox();
		label = new JLabel();
		label.setText("Player Name:");
		box.add(label);
		box.add(getPlayerNameEntry());
		this.add(box);

		// Character Street Name
		box = Box.createHorizontalBox();
		label = new JLabel();
		label.setText("Street Name:");
		box.add(label);
		box.add(getStreetNameEntry());
		this.add(box);

		// Resources
		box = Box.createHorizontalBox();
		label = new JLabel("Resources: ");
		box.add(label);
		box.add(getResourcesLabel());
		this.add(box);

		// Reaction
		box = Box.createHorizontalBox();
		label = new JLabel("Reaction: ");
		box.add(label);
		box.add(getReactionLabel());
		this.add(box);


		// BirthDate
		box = Box.createHorizontalBox();
		label = new JLabel("Birth Date: ");
		box.add(label);
		box.add(getBirthDateEntry());
		this.add(box);

		// BirthPlace
		box = Box.createHorizontalBox();
		label = new JLabel("Birth Place: ");
		box.add(label);
		box.add(getBirthPlaceEntry());
		this.add(box);

		// Hair
		box = Box.createHorizontalBox();
		label = new JLabel("Hair: ");
		box.add(label);
		box.add(getHairEntry());
		this.add(box);

		// Eyes
		box = Box.createHorizontalBox();
		label = new JLabel("Eyes: ");
		box.add(label);
		box.add(getEyesEntry());
		this.add(box);

		// Height
		box = Box.createHorizontalBox();
		label = new JLabel("Height: ");
		box.add(label);
		box.add(getHeightEntry());
		this.add(box);

		// Weight
		box = Box.createHorizontalBox();
		label = new JLabel("Weight: ");
		box.add(label);
		box.add(getWeightEntry());
		this.add(box);

		// Creation date
		box = Box.createHorizontalBox();
		label = new JLabel("Created: ");
		box.add(label);
		box.add(getCreationDateLabel ());
		this.add(box);

		// Last Modified date
		box = Box.createHorizontalBox();
		label = new JLabel("Last changed: ");
		box.add(label);
		box.add(getModifiedDateLabel ());
		this.add(box);

		attributeTable = new AttributeTable (pc);
		this.add(attributeTable);
		this.add(new PriorityTable(pc));

		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		pcChanged ();
    }
    
    private JTextField getCharNameEntry() {
        if (charNameEntry == null) {
            charNameEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return charNameEntry;
    }
    
    private JTextField getRealNameEntry() {
        if (realNameEntry == null) {
            realNameEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return realNameEntry;
    }
    
    private JTextField getPlayerNameEntry() {
        if (playerNameEntry == null) {
            playerNameEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return playerNameEntry;
    }

    private JTextField getStreetNameEntry() {
        if (streetNameEntry == null) {
            streetNameEntry = new JTextField();
            // jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return streetNameEntry;
    }
    
    private JTextField getBirthDateEntry() {
        if (birthDateEntry == null) {
            birthDateEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return birthDateEntry;
    }

    private JTextField getBirthPlaceEntry() {
        if (birthPlaceEntry == null) {
            birthPlaceEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return birthPlaceEntry;
    }

    private JTextField getHairEntry() {
        if (hairEntry == null) {
            hairEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return hairEntry;
    }

    private JTextField getEyesEntry() {
        if (eyesEntry == null) {
            eyesEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return eyesEntry;
    }

    private JTextField getHeightEntry() {
        if (heightEntry == null) {
            heightEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return heightEntry;
    }
    
    private JTextField getWeightEntry() {
        if (weightEntry == null) {
            weightEntry = new JTextField();
            //jLabelName.setSize(60, 26);
            //jLabelName.setLocation(150, 290);
        }
        return weightEntry;
    }
    
    private JLabel getResourcesLabel() {
        if (resourcesLabel == null) {
            resourcesLabel = new JLabel();
        }
        return resourcesLabel;
    }
    
    private JLabel getReactionLabel() {
        if (reactionLabel == null) {
            reactionLabel = new JLabel();
        }
        return reactionLabel;
    }
    
    private JLabel getCreationDateLabel() {
        if (creationDateLabel == null) {
            creationDateLabel = new JLabel();
        }
        return creationDateLabel;
    }

    private JLabel getModifiedDateLabel() {
        if (modifiedDateLabel == null) {
            modifiedDateLabel = new JLabel();
        }
        return modifiedDateLabel;
    }

    private void updateResourcesLabel () {
        resourcesLabel.setText (String.valueOf(pc.getStat(PlayerCharacter.STAT_RESOURCES) + "\u00A5"));
    }
    
    private void updateReactionLabel () {
    	reactionLabel.setText (String.valueOf(pc.getReaction()));
    }
    
    private void updateCreationDateLabel () {
    	creationDateLabel.setText (pc.getString(PlayerCharacter.STR_CREATION_DATE));
    }
    
    private void updateModifiedDateLabel () {
    	modifiedDateLabel.setText (pc.getString(PlayerCharacter.STR_MOD_DATE));
    }
    
    public void pcChanged () {
        charNameEntry.setText(pc.getString(PlayerCharacter.STR_CHARNAME));
        realNameEntry.setText(pc.getString(PlayerCharacter.STR_REALNAME));
        playerNameEntry.setText(pc.getString(PlayerCharacter.STR_PLAYERNAME));
        streetNameEntry.setText(pc.getString(PlayerCharacter.STR_STREETNAME1));
        birthDateEntry.setText(pc.getString(PlayerCharacter.STR_BIRTHDATE));
        birthPlaceEntry.setText(pc.getString(PlayerCharacter.STR_BIRTHPLACE));
        hairEntry.setText(pc.getString(PlayerCharacter.STR_HAIR));
        eyesEntry.setText(pc.getString(PlayerCharacter.STR_EYES));
        heightEntry.setText(pc.getString(PlayerCharacter.STR_HEIGHT));
        weightEntry.setText(pc.getString(PlayerCharacter.STR_WEIGHT));
    	updateResourcesLabel ();
    	updateReactionLabel ();
    	updateCreationDateLabel ();
    	updateModifiedDateLabel ();
    	
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