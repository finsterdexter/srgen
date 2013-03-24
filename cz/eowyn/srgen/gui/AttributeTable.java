package cz.eowyn.srgen.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
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
        table.setRowHeight((int)(table.getRowHeight()*1.3));
        
        this.add(table.getTableHeader(), BorderLayout.PAGE_START);
        this.add(table, BorderLayout.CENTER);
	}
	
    private JTable getTable () {

        dataModel = new AbstractTableModel() {
        	String[] columnNames = {
            		"Attribute",
            		"Base",
                    "Racial adj.",
                    "Other adj.",
                    "M3",
                    "Total",
                    "+",
                    "-"};

        	Object[] data = { 
        			"Body", 
        			"Quickness", 
        			"Strength", 
        			"Charisma", 
        			"Intelligence", 
        			"Willpower",
        			"Reaction",
        			"Initiative",
        			"Essence",
        			"Magic"};
        	

        	
        	public int getColumnCount() { return 8; }
            public int getRowCount() { return data.length;}
            public Object getValueAt(final int row, final int col) {
            	switch (col) {
            	case 0: return data[row];
            	case 6:
            		final JButton butPlus = new JButton("+");
            		butPlus.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent arg0) {
            				int stat = pc.STAT_ATTR_BASE + 5*row;
            				if (pc.getInt(stat) < 6) {
            					pc.setStat(stat, pc.getInt(stat)+1);
            					pc.setStat(pc.STAT_ATTR_POINTS_SPENT, pc.getInt(pc.STAT_ATTR_POINTS_SPENT)+1);
            				}
            			}
            		});
            		//if (row >= 6)
            		//butPlus.setVisible(false);
            		return butPlus;
            	case 7:
            		final JButton butMinus = new JButton("-");
            		butMinus.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent arg0) {
            				int stat = pc.STAT_ATTR_BASE + 5*row;
            				if (pc.getInt(stat) > 1) {
            					pc.setStat(stat, pc.getInt(stat)-1);
            					pc.setStat(pc.STAT_ATTR_POINTS_SPENT, pc.getInt(pc.STAT_ATTR_POINTS_SPENT)-1);
            				}
            			}
            		});
            		//if (row >= 6)
            		//	butMinus.setVisible(false);
            		return butMinus;
            	default:
            		// FIXME: 5 is ugly hardwired
            		return new Integer (pc.getInt (PlayerCharacter.STAT_BOD_BASE + 5 * row + (col - 1)));
            	}
            }
            public String getColumnName(int col) {
                return columnNames[col];
            }
            public boolean isCellEditable(int row, int col) {
            	return (col != 0 && col < 5 && row < 6);
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
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("+").setCellRenderer(buttonRenderer);
		table.getColumn("-").setCellRenderer(buttonRenderer);
		int w = table.getColumn("Base").getWidth() / 2;
		table.getColumn("+").setMaxWidth(w);
		table.getColumn("-").setMaxWidth(w);
		table.addMouseListener(new JTableButtonMouseListener(table));
		return table;
    }
    
    public void setPlayerCharacter (PlayerCharacter pc) {
    	this.pc = pc;
    	dataModel.setValueAt(null, 0, 0);
    }
    
    // Taken from http://www.cordinc.com/blog/2010/01/jbuttons-in-a-jtable.html
    private static class JTableButtonRenderer implements TableCellRenderer {		
		@Override 
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JButton button = (JButton)value;
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
		    } else {
		    	button.setForeground(table.getForeground());
		    	button.setBackground(UIManager.getColor("Button.background"));
		    }
			//button.setVisible(row < 6);
			button.setEnabled(row < 6);
			return button;	
		}
	}

    private static class JTableButtonMouseListener extends MouseAdapter {
		private final JTable table;
		
		public JTableButtonMouseListener(JTable table) {
			this.table = table;
		}

		public void mouseClicked(MouseEvent e) {
			int column = table.getColumnModel().getColumnIndexAtX(e.getX());
			int row    = e.getY()/table.getRowHeight(); 

			if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
			    Object value = table.getValueAt(row, column);
			    if (value instanceof JButton) {
			    	((JButton)value).doClick();
			    }
			}
		}
	}}
