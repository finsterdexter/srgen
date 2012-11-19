package cz.eowyn.srgen.gui;

import net.miginfocom.swing.MigLayout;
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
    	super (new MigLayout());
    	this.pc = pc;
    	pc.addListener (this);
    	
    	// commonPanel.setLayout(null);
		// commonPanel.setPreferredSize(new java.awt.Dimension(255,170));
		Box box;
		JLabel label;

		this.add(new PriorityTable(pc), "cell 0 1 1 5");

		attrPointsLabel = getLabelEntry ("Attribute points", "cell 1 1", "cell 2 1", this);
		skillPointsLabel = getLabelEntry ("Skill points", "cell 1 2", "cell 2 2", this);
		resourcesLabel = getLabelEntry ("Resources", "cell 1 3", "cell 2 3", this);
		magicLabel = getLabelEntry ("Magic", "cell 1 4", "cell 2 4", this);
		racesLabel = getLabelEntry ("Races", "cell 1 5", "cell 2 5", this);


		// commonPanel.add(getNew_radiobutton(), null);
		// commonPanel.add(getAdd_radiobutton(), null);
		// commonPanel.add(getGenerateTreasure_button(), null);

		JButton but = new JButton("Create");
		this.add(but, "cell 0 6 3 1");
		
		pcChanged (pc);
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
    
    private JTextField getLabelEntry (String name, String constraintLabel, String constraintValue, JComponent owner) {
		JLabel label = new JLabel (name + ": ");
        JTextField entry = new JTextField ();
        entry.setEditable (false);
        entry.setFocusable (false);
        
		owner.add(label, constraintLabel);
		owner.add(entry, constraintValue);
		
		return entry;
    }
    
    public void pcChanged (PlayerCharacter pc) {
        attrPointsLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_ATTR_POINTS)));
        skillPointsLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_SKILL_POINTS)));
        resourcesLabel.setText (String.valueOf (pc.getInt(PlayerCharacter.STAT_RESOURCES) + "\u00A5"));

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
        
        //System.err.println("PriorityGenerator: " + String.valueOf(pc.isDirty()));
    }

	public void pcExchanged (PlayerCharacter pc) {
		setPlayerCharacter (pc);
	}

	public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	pcChanged (pc);
    }
}