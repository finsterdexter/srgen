package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.table.*;
import javax.swing.*;

import cz.eowyn.srgen.model.PlayerCharacter;


public class PriorityGeneratorPanel extends JPanel implements cz.eowyn.srgen.model.PCListener {
	private JPanel panel = null;
	private JTextField attrPointsLabel = null;
	private JTextField skillPointsLabel = null;
	private JTextField resourcesLabel = null;
	private JTextField magicLabel = null;
	private JTextField racesLabel = null;
	
	private PlayerCharacter pc = null;

	
    public PriorityGeneratorPanel (PlayerCharacter pc) {
    	super ();
    	this.pc = pc;
    	pc.addListener (this);
    	
    	// commonPanel.setLayout(null);
		// commonPanel.setPreferredSize(new java.awt.Dimension(255,170));
		Box box;
		JLabel label;

		this.add(new PriorityTable(pc));

		attrPointsLabel = getLabelEntry ("Attribute points", this);
		skillPointsLabel = getLabelEntry ("Skill points", this);
		resourcesLabel = getLabelEntry ("Resources", this);
		magicLabel = getLabelEntry ("Magic", this);
		racesLabel = getLabelEntry ("Races", this);


		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		JButton but = new JButton("Create");
		this.add(but);
		
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
        attrPointsLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_ATTR_POINTS)));
        skillPointsLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_SKILL_POINTS)));
        resourcesLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_RESOURCES) + "\u00A5"));

        int magic = pc.getStat(PlayerCharacter.STAT_MAGIC);

        String magicText;
        switch (magic) {
        case 2: 
        	magicText = "Full magical abilities, astral walking";
        	break;
        case 1:
        	magicText = "adept/...?";
        	break;
        default:
        	magicText = "No magical abilities";
        	break;
        }

        magicLabel.setText (magicText);
        racesLabel.setText (String.valueOf (pc.getStat(PlayerCharacter.STAT_ALLOWED_RACES)));
    }

	public void pcExchanged (PlayerCharacter pc) {
		setPlayerCharacter (pc);
	}

	public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	pcChanged ();
    }
}