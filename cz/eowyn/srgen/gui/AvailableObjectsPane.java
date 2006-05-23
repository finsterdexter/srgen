package cz.eowyn.srgen.gui;

//import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import cz.eowyn.srgen.model.ObjectListener;
import cz.eowyn.srgen.model.RepositoryObject;
import cz.eowyn.srgen.model.RepositoryTree;



class AvailableObjectsPane extends JScrollPane {

    private JTree tree = null;
    private RepositoryTree root;
    private RepositoryObject selected_node = null;

    protected ObjectListener listener = null;

    
    public AvailableObjectsPane(RepositoryTree root) {
		super();
		
		this.root = root;
		
		createTree ();
	}

	protected void createTree () {
        tree = new JTree (root);
        tree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(new TreeSelectionListener () {
        	public void valueChanged(TreeSelectionEvent e) {
        		RepositoryObject node = (RepositoryObject) tree.getLastSelectedPathComponent();

        		if (node == null) return;

        		//Object nodeInfo = node.getUserObject();
        		if (node.isLeaf()) {
        			selected_node = node;
        		} else {
        			selected_node = null;
        		}
    			fireObjectSelected (selected_node);
        	}
        });

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        tree.setCellRenderer(renderer);
        tree.setScrollsOnExpand(false);
        tree.setToggleClickCount(1);
		
		this.getViewport().setView(tree);
	}
	
	public void setObjectSelectedListener (ObjectListener listener) {
		this.listener = listener;
	}
	
	protected void fireObjectSelected (RepositoryObject obj) {
		if (listener != null) {
			listener.objectSelected (obj);
		}
	}
}
