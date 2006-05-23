package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import cz.eowyn.srgen.model.PlayerCharacter;



public class AttributeTable extends JPanel{
	private PlayerCharacter pc;
	private JTable table;
	private TableModel dataModel;
	
	public AttributeTable (PlayerCharacter pc) {
		super ();
		this.pc = pc;
        this.setLayout(new BorderLayout());
        
        table = getTable ();
        
        this.add(table.getTableHeader(), BorderLayout.PAGE_START);
        this.add(table, BorderLayout.CENTER);
	}
	
    private JTable getTable () {
        dataModel = new AbstractTableModel() {
        	String[] columnNames = {
            		"Attribute",
            		"Original",
                    "Racial adj.",
                    "Other adj.",
                    "M3",
                    "Total"};

        	Object[] data = { 
        			"Body", 
        			"Quickness", 
        			"Strength", 
        			"Charisma", 
        			"Intelligence", 
        			"Willpower" };
        	
        	public int getColumnCount() { return 6; }
            public int getRowCount() { return 6;}
            public Object getValueAt(int row, int col) {
            	if (col == 0) {
            		return data[row];
            	} else {
            		// FIXME: 5 is ugly hardwired
            		return new Integer (pc.getStat (PlayerCharacter.STAT_BOD_BASE + 5 * row + (col - 1)));
            	}
            }
            public String getColumnName(int col) {
                return columnNames[col];
            }
            public boolean isCellEditable(int row, int col) {
            	return (col != 0 && col != 5);
            }

            public Class getColumnClass(int col) {
                return getValueAt(0, col).getClass();
            }

            public void setValueAt(Object value, int row, int col) {
            	// null is special, used to change PCs
            	if (value == null) {
            		fireTableDataChanged ();
            		return;
            	}
            	pc.setStat (PlayerCharacter.STAT_ATTR_BASE + row * 5 + (col - 1), ((Integer) value).intValue());
            	fireTableCellUpdated (row, col);
            	fireTableCellUpdated (row, 5);
            }
            
        };
        
        JTable table = new JTable (dataModel);
    	table.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
    
    public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	dataModel.setValueAt(null, 0, 0);
    }
}
