package cz.eowyn.srgen.model;

import javax.swing.table.AbstractTableModel;


public class AssetTableModel extends AbstractTableModel {
	private RepositoryList  list;
	private String[]  columns;
	
	public AssetTableModel (RepositoryList list, String[] columns) {
		super ();
		this.list = list;
		this.columns = columns;
	}
	
    public int getColumnCount() { return columns.length; }
    
    public int getRowCount() { return list.size(); }
    
    public String getColumnName(int col) {
    	return columns[col];
    }

    public Object getValueAt(int row, int col) {
    	return ((RepositoryObject) list.get(row)).getValue(columns[col]);
    }
    
    public void addRow(RepositoryObject obj) {
		list.add(obj);
    	this.fireTableRowsInserted(list.size()-1, list.size()-1);
    }
}

