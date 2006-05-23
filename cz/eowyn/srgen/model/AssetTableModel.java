package cz.eowyn.srgen.model;

import javax.swing.table.AbstractTableModel;


public class AssetTableModel extends AbstractTableModel {
	RepositoryList  list;
	public AssetTableModel (RepositoryList list) {
		super ();
		this.list = list;
	}
	
    public int getColumnCount() { return 1; }
    
    public int getRowCount() { return list.size(); }
    
    public Object getValueAt(int row, int col) {
    	if (col == 0) {
    		return ((RepositoryObject) list.get(row)).getName ();
    	} else {
    		return "";
    	}
    }
    public void addRow(RepositoryObject obj) {
		list.add(obj);
    	this.fireTableRowsInserted(list.size()-1, list.size()-1);
    }
}

