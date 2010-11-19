package cz.eowyn.srgen.model;

import javax.swing.table.AbstractTableModel;


public class AssetTableModel<R extends RepositoryObject> extends AbstractTableModel implements PCAssetListener {
	private RepositoryList<R>  list;
	private String[]  columns;
	
	public AssetTableModel (RepositoryList<R> list, String[] columns) {
		super ();
		this.list = list;
		this.columns = columns;
		this.list.addListener(this);
	}
	
    public int getColumnCount() { return columns.length; }
    
    public int getRowCount() { return list.size(); }
    
    public String getColumnName(int col) {
    	return columns[col];
    }

    public Object getValueAt(int row, int col) {
    	return ((RepositoryObject) list.get(row)).getValue(columns[col]);
    }
    
    public void addRow(R obj) {
		list.add(obj);
    	//this.fireTableRowsInserted(list.size()-1, list.size()-1);
    }
    
    public R getObjectAt(int row) {
    	return list.get(row);
    }

	@Override
	public void pcAssetChanged(PlayerCharacter pc, RepositoryList list) {
		// TODO Auto-generated method stub
		System.err.println("AssetTableModel::pcAssetChanged");
    	this.fireTableDataChanged();
	}
}

