package cz.eowyn.srgen.gui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

import cz.eowyn.srgen.model.AssetTableModel;
import cz.eowyn.srgen.model.ObjectListener;
import cz.eowyn.srgen.model.RepositoryObject;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.gui.actions.*;

class SelectedObjectsPane<R extends RepositoryObject> extends JScrollPane {

    //protected RepositoryList<R> selectedList = null;
    protected R selected_node = null;
    protected AssetTableModel<R> selectedDataModel;

    protected JTable table;
    protected ObjectListener listener = null;
    
	
	public SelectedObjectsPane (RepositoryList<R> selectedList, String[] columns) {
		super();

		//this.selectedList = selectedList;
		this.selectedDataModel = new AssetTableModel<R> (selectedList, columns);

		createTable ();
	}
	
	protected void createTable () {
		
		table = new JTable(selectedDataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//	    	    Ask to be notified of selection changes.
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//Ignore extra messages.
				if (e.getValueIsAdjusting()) return;
				
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if (lsm.isSelectionEmpty()) {
					System.err.println ("no rows are selected");
				} else {	
					int selectedRow = lsm.getMinSelectionIndex();
					System.err.println ("selectedRow is selected: " + selectedRow);
					fireObjectSelected (selectedDataModel.getObjectAt (selectedRow));
					
				}
			}
		});
		this.getViewport().setView(table);
	}

	public void setObjectSelectedListener (ObjectListener listener) {
		this.listener = listener;
	}
	
	protected void fireObjectSelected (R obj) {
		if (listener != null) {
			listener.objectSelected (obj);
		}
	}
}
