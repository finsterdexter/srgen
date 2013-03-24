package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cz.eowyn.srgen.model.PCListener;
import cz.eowyn.srgen.model.PlayerCharacter;

public class InvalidWarningPane extends JPanel implements PCListener {
    private PlayerCharacter pc;

	JButton but = new JButton("X");
	JLabel label = new JLabel();

	public InvalidWarningPane(PlayerCharacter pc) {
		super();
		this.pc = pc;
    	pc.addListener (this);
    	
		//FlowLayout layout = new FlowLayout ();
		//layout.setVgap(10);
		//setLayout(layout);
		
		this.add (but);
		this.add (label);
		
		pcChanged(pc);
	}

	@Override
	public void pcChanged(PlayerCharacter pc) {
		if (! pc.isValid()) {
			Color fg = new Color(255, 0, 0);
			label.setForeground(fg);
			label.setText("Character is invalid");
			setVisible(true);
		}
		else {
			setVisible(false);
			label.setText("Character is ok");
		}
		
	}

	@Override
	public void pcExchanged(PlayerCharacter pc) {
		this.pc = pc;
		pcChanged (pc);
	}

}
