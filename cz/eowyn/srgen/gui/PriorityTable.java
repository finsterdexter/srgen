package cz.eowyn.srgen.gui;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.event.*;
import javax.swing.JLabel;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import cz.eowyn.srgen.model.PCListener;
import cz.eowyn.srgen.model.PlayerCharacter;

public class PriorityTable extends JPanel implements ActionListener, PCListener {

	private static String PROPERTY_NAME = "previousPriority";
	
	private PlayerCharacter pc;
	// index: priority
	private JComboBox[] combos;
	// index: priority type
	private int[] priorities;
	
	public PriorityTable(PlayerCharacter pc) {
		super();
		this.pc = pc;
		pc.addListener(this);

		combos = new JComboBox[5];
		priorities = (int[]) pc.getPriorities();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;        

        this.addPriorityCombo (0, 0, gridbag, c);
		this.addPriorityCombo (1, 1, gridbag, c);
		this.addPriorityCombo (2, 2, gridbag, c);
		this.addPriorityCombo (3, 3, gridbag, c);
		this.addPriorityCombo (4, 4, gridbag, c);
	}
	
	private JComboBox addPriorityCombo (int prio_type, int prio_value, GridBagLayout gridbag, GridBagConstraints c) {
		String[] labels = { "A", "B", "C", "D", "E" };

		JLabel label = new JLabel (labels[prio_value] + ":    ");
        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
		gridbag.setConstraints(label, c);
		this.add (label);

		JComboBox combo =  new JComboBox (new Object[]  {
				"Race",
				"Magic",
				"Attributes",
				"Skills",
				"Resources",
			});
		combo.setSelectedIndex(prio_type);
		combo.putClientProperty(PriorityTable.PROPERTY_NAME, new Integer (prio_type));
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(combo, c);
		combo.addActionListener(this);
		this.add (combo);
		combos[prio_value] = combo;
		priorities[prio_type] = prio_value; 
		return combo;
	}
	
	public void actionPerformed(ActionEvent e) {
        JComboBox combo = (JComboBox)e.getSource();
        int old_prio_type = ((Integer) combo.getClientProperty(PriorityTable.PROPERTY_NAME)).intValue ();
        int new_prio_type = combo.getSelectedIndex();
        int this_prio_val = priorities[old_prio_type];
        int other_prio_val = priorities[new_prio_type];
        
        JComboBox combo2 = combos[other_prio_val];

        // Swap priorities of combo and combo2, and update related info
        combo2.setSelectedIndex (old_prio_type);
        priorities[old_prio_type] = other_prio_val;
        combo2.putClientProperty(PriorityTable.PROPERTY_NAME, new Integer (old_prio_type));
        
        priorities[new_prio_type] = this_prio_val;
        combo.putClientProperty(PriorityTable.PROPERTY_NAME, new Integer (new_prio_type));

        pc.setPriorities(priorities);
	}

	public void pcChanged (PlayerCharacter pc) {
		//
	}

	public void pcExchanged (PlayerCharacter pc) {
		this.pc = pc;
		// FIXME: update
	}
}
