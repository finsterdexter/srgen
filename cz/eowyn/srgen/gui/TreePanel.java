/**
 * 
 */
package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import cz.eowyn.srgen.model.ObjectListener;
import cz.eowyn.srgen.model.RepositoryObject;
import cz.eowyn.srgen.model.RepositoryTree;
import cz.eowyn.srgen.model.RepositoryList;



class TreePanel extends JSplitPane implements ObjectListener {
    private ObjectDetailPane detail = null;
    
    private RepositoryTree root;
    private RepositoryList selectedList = null;

    private RepositoryObject selected_node = null;

    
	public TreePanel (RepositoryTree root, RepositoryList selectedList) {
		super ();
		this.root = root;
		this.selectedList = selectedList;

		this.setLeftComponent(getLeftPanel());
		this.setRightComponent(getRightPanel());
		//this.setRightComponent(getDetailView());
		this.setDividerLocation(255);
	}

	public JPanel getLeftPanel () {
		JPanel panel = new JPanel ();
		BorderLayout layout = new BorderLayout ();
		layout.setVgap(10);
		panel.setLayout(layout);
		
		panel.add (getAddButtonPanel (), BorderLayout.NORTH);
		panel.add (getAvailableObjectsPane (), BorderLayout.CENTER);
		
		return panel;
	}
	
    public JPanel getAddButtonPanel () {
    	JPanel panel = new JPanel ();
    	BorderLayout layout = new BorderLayout ();
    	layout.setHgap(20);
    	panel.setLayout(layout);
    	JLabel label = new JLabel ("Available:");
    	panel.add(label, BorderLayout.WEST);
    	JButton button = new JButton ("Add  >>");
        button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent e) {
        		if (selected_node != null) {
        			System.out.println (selected_node.getName());
        			//selectedDataModel.addRow(selected_node);
        		}
        	}
        });

    	panel.add (button, BorderLayout.EAST);
    	return panel;
    }
    
    public JPanel getRightPanel () {
		JPanel panel = new JPanel ();
		BorderLayout layout = new BorderLayout ();
		layout.setVgap(10);
		panel.setLayout(layout);

		panel.add(getRemoveButtonPanel (), BorderLayout.NORTH);
		
		detail = getObjectDetailPane ();
    	JScrollPane userPanel = getSelectedObjectsPane ();
    	JSplitPane split = new JSplitPane ();
    	split.setOrientation(JSplitPane.VERTICAL_SPLIT);
    	split.setTopComponent(userPanel);
    	split.setBottomComponent(detail);
		split.setDividerLocation(255);

		panel.add(split, BorderLayout.CENTER);
    	return panel;
    }
    
    public JPanel getRemoveButtonPanel () {
    	JPanel panel = new JPanel ();
    	BorderLayout layout = new BorderLayout ();
    	layout.setHgap(20);
    	panel.setLayout(layout);
    	JLabel label = new JLabel ("Selected:");
    	panel.add(label, BorderLayout.WEST);
    	JButton button = new JButton ("<<  Remove");
    	panel.add (button, BorderLayout.EAST);
    	return panel;
    }
    
    public JScrollPane getAvailableObjectsPane () {
    	AvailableObjectsPane pane = new AvailableObjectsPane (root);
    	pane.setObjectSelectedListener(this);
    	return pane;
    }
    
    public JScrollPane getSelectedObjectsPane () {
    	SelectedObjectsPane pane = new SelectedObjectsPane (selectedList);
    	pane.setObjectSelectedListener(this);
    	return pane;
    }
    
    public ObjectDetailPane getObjectDetailPane () {
    	return new ObjectDetailPane ();
    }

    public void objectSelected (RepositoryObject obj) {
    	detail.displayObject (obj);
    }

}