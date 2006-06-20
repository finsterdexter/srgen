package cz.eowyn.srgen.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import cz.eowyn.srgen.Config;
import cz.eowyn.srgen.Generator;
import cz.eowyn.srgen.model.PlayerCharacter;
import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.RepositoryList;
import cz.eowyn.srgen.io.ExportHandler;
import cz.eowyn.srgen.model.PCListener;
import cz.eowyn.srgen.gui.utils.IconUtilities;


public class SRGenWindow extends JFrame {
	private Repository repository = null;
	
	private JMenuBar main_menubar = null;
	private JTabbedPane allCharsPane = null;
	
	private JFileChooser openFileChooser = null;
	private JFileChooser exportFileChooser = null;
	
	private ArrayList pcListeners = null;
	
    public SRGenWindow () {
		super ();

		this.repository = Generator.getRepository ();
		
        this.setSize (800, 560);

        pcListeners = new ArrayList ();

        this.setContentPane (getAllCharsPane ());
        this.setTitle ("Shadowrun character generator");
        this.setIconImage (IconUtilities.getImageIcon ("Icon.gif").getImage());
        this.setJMenuBar (getMain_menubar());

        this.openFileChooser = new JFileChooser ();
        //this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        this.exportFileChooser = new JFileChooser ();
        //exportFileChooser.addChoosableFileFilter();
        //exportFileChooser.ensureFileIsVisible();
        //exportFileChooser.getAcceptAllFileFilter();
        exportFileChooser.setApproveButtonText("Export");
        //exportFileChooser.setCurrentDirectory()
        exportFileChooser.setDialogTitle ("Export");
        //exportFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        //exportFileChooser.setSelectedFile("...");
        
        this.addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent e) {
            	Config.saveConfig ();
                System.exit (0);
            }
        });

	}

	/**
	 * This method initializes JMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */    
	private JMenuBar getMain_menubar() {
		if (main_menubar == null) {
			main_menubar = new JMenuBar();            
            main_menubar.add (getFile_menu());
            //main_menubar.add(getReload_menu());            
		}
		return main_menubar;
	}
	
    private JMenu getFile_menu (){
        JMenu file_menu = new JMenu ("File");

        JMenuItem new_menu = new JMenuItem ("New");
        JMenuItem open_menu = new JMenuItem ("Open");
        open_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	int returnVal = openFileChooser.showOpenDialog(SRGenWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = openFileChooser.getSelectedFile().getAbsolutePath();
                    //System.out.println (fileChooser.getCurrentDirectory());
                    //System.out.println (fileChooser.getSelectedFile().getAbsolutePath());
                    //System.out.println (filename);
                    PlayerCharacter pc = Generator.loadCharacter (filename);
                    addPC (pc);
                    fireGroupChanged ();
                }
            }
        });

        JMenuItem save_menu = new JMenuItem ("Save");

        JMenuItem export_menu = new JMenuItem ("Export");
        export_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	int returnVal = exportFileChooser.showOpenDialog(SRGenWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = exportFileChooser.getSelectedFile().getAbsolutePath();
                    //System.out.println (fileChooser.getCurrentDirectory());
                    //System.out.println (fileChooser.getSelectedFile().getAbsolutePath());
                    //System.out.println (filename);
                    Generator.exportCharacter (getCurrentCharacter (), "templates/pok.tpl", filename);
                }
            }
        });

        JMenuItem close_menu = new JMenuItem ("Close");
        close_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                closeTab ();
            }
        });

        JMenuItem quit_menu = new JMenuItem ("Quit");
        quit_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });
        file_menu.add (new_menu);
        file_menu.add (open_menu);
        file_menu.add (save_menu);
        file_menu.add (export_menu);
        file_menu.add (close_menu);
        file_menu.add (quit_menu);
        return file_menu;
    }
    
    
//    private JMenu getTreasure_menu(){
//        JMenu treasure_menu = new JMenu("Treasures");
//        treasureTypeButtonGroup = new ButtonGroup();
//        String[] tts = t_manager.getGenerator().getTreasureTypes();
//        for (int i = 0; i < tts.length; i++){
//            JRadioButtonMenuItem ttMenuItem = new JRadioButtonMenuItem(tts[i]);
//            ttMenuItem.setMnemonic(KeyEvent.VK_R);
//            treasureTypeButtonGroup.add(ttMenuItem);
//            treasure_menu.add(ttMenuItem);            
//            if (i == 0) ttMenuItem.setSelected(true);
//        }
//        return treasure_menu;
//    }

    private JTabbedPane getAllCharsPane() {

    	allCharsPane = new JTabbedPane();
            //treasures_jtabbedpane.setPreferredSize(
            //    new java.awt.Dimension(450, 500));

    	allCharsPane.setTabPlacement (Config.getPCTabPlacement());

    	Iterator pcIter = Generator.getCharacters().iterator();
    	while (pcIter.hasNext ()) {
    		PlayerCharacter pc = (PlayerCharacter) pcIter.next ();
    		addPC (pc);
    	}        
        return allCharsPane;
    }

    void addPC (PlayerCharacter pc) {
    	allCharsPane.addTab (
    			pc.getString (PlayerCharacter.STR_CHARNAME),
    			null,
    			getCharacterPane (pc),
    			"");
    }

    void closeTab () {
    	int index = allCharsPane.getSelectedIndex ();
    	// FIXME: ask for confirmation, if the character is dirty
    	if (index < 0) {
    		return;
    	}
    	
    	allCharsPane.remove (index);
    	Generator.getCharacters ().remove (index);
    }
    /**
     * This method initializes treasures_jtabbedpane
     *
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getCharacterPane (PlayerCharacter pc) {
    	JTabbedPane tabbedPane = new JTabbedPane();
    	JTabbedPane equipmentPane = null;
    	JTabbedPane magicPane = null;

    	//treasures_jtabbedpane.setPreferredSize(
    	//    new java.awt.Dimension(450, 500));

    	TreePanel  SourceBooks_Tab = new TreePanel (repository.getSourceBooks_Tree(), new RepositoryList());
    	TreePanel  EdgeAndFlaw_Tab = new TreePanel (repository.getEdgeAndFlaw_Tree(), pc.getEdgeAndFlaw_List());
    	TreePanel  Skill_Tab = new TreePanel (repository.getSkill_Tree(), pc.getSkill_List());
    	TreePanel  Contacts_Tab = new TreePanel (repository.getContacts_Tree(), pc.getContact_List());
    	TreePanel  Spell_Tab = new TreePanel (repository.getSpell_Tree(),  pc.getSpell_List());
    	//TreePanel  Equipment_Tab = new TreePanel (repository.getEquipment_Tree());
    	TreePanel  Gear_Tab = new TreePanel (repository.getGear_Tree(), pc.getGear_List());
    	TreePanel  MageGear_Tab = new TreePanel (repository.getMageGear_Tree(), pc.getMageGear_List());
    	TreePanel  Cyberware_Tab = new TreePanel (repository.getCyberware_Tree(), pc.getCyberware_List());
    	TreePanel  Bioware_Tab = new TreePanel (repository.getBioware_Tree(), pc.getBioware_List ());
    	TreePanel  Vehicles_Tab = new TreePanel (repository.getVehicles_Tree(), pc.getVehicle_List());
    	TreePanel  Decks_Tab = new TreePanel (repository.getDecks_Tree(), pc.getDeck_List());
    	TreePanel  AdeptPowers_Tab = new TreePanel (repository.getAdeptPowers_Tree(), pc.getAdeptPowers_List());
    	
    	SummaryPanel summaryPanel = new SummaryPanel (pc);
    	PreviewPane Preview_Tab = new PreviewPane (pc); 
            
    	addPCListener (summaryPanel);

    	tabbedPane.addTab(
    			"Summary",
    			null,
    			summaryPanel,
    			"Sum...");

    	tabbedPane.addTab(
    			"Source Books",
    			null,
    			SourceBooks_Tab,
    			"add/rem sources");

    	tabbedPane.addTab(
    			"Edges & Flaws",
    			null,
    			EdgeAndFlaw_Tab,
    			"add/rem edges and flaws");

    	tabbedPane.addTab(
    			"Skills",
    			null,
    			Skill_Tab,
    			"add/rem skills");

    	tabbedPane.addTab(
    			"Contacts",
    			null,
    			Contacts_Tab,
    			"add/rem contacts");

            
    	equipmentPane = new javax.swing.JTabbedPane();
/*
            allCharsPane.addTab(
                    "Spells",
                    null,
                    Spell_Tab.getPanel(),
                    "add/rem spells");
*/

    	equipmentPane.addTab(
    			"Gear",
    			null,
    			Gear_Tab,
    			"add/rem gear");
            
    	equipmentPane.addTab(
    			"Cyberware",
    			null,
    			Cyberware_Tab,
    			"add/rem equipment");

    	equipmentPane.addTab(
    			"Bioware",
    			null,
    			Bioware_Tab,
    			"add/rem equipment");
            
    	equipmentPane.addTab(
    			"Vehicles",
    			null,
    			Vehicles_Tab,
    			"add/rem equipment");

    	equipmentPane.addTab(
    			"Decks",
    			null,
    			Decks_Tab,
    			"add/rem equipment");

    	tabbedPane.addTab(
    			"Equipment",
    			null,
    			equipmentPane,
    			"add/rem equipment");
        

    	
    	magicPane = new javax.swing.JTabbedPane ();
    	
    	magicPane.addTab(
    			"Spells",
    			null,
    			Spell_Tab,
    			"add/rem spells");

    	magicPane.addTab(
    			"Mage Gear",
    			null,
    			MageGear_Tab,
    			"add/rem mage's gear");

    	magicPane.addTab(
    			"Adept Powers",
    			null,
    			AdeptPowers_Tab,
    			"add/rem physical adept's powers");

    	tabbedPane.addTab(
    			"Magic",
    			null,
    			magicPane,
    			"add/rem equipment");

    	

    	tabbedPane.addTab(
    			"Preview",
    			null,
    			Preview_Tab,
    			"Character sheet preview");
        
        return tabbedPane;
    }


    
    /**
     * This method initializes jSplitPane
     *
     * @return javax.swing.JSplitPane
     */
//     private javax.swing.JSplitPane getJSplitPane() {
//         if (jSplitPane == null) {
//             jSplitPane = new javax.swing.JSplitPane();
//             //jSplitPane.setRightComponent(getTreasures_jtabbedpane());
//             jSplitPane.setLeftComponent(getTreeView());
//             jSplitPane.setDividerLocation(255);
//         }
//         return jSplitPane;
//     }

//     private javax.swing.JPanel getLeftPanel(){
//         
//         if (leftPanel == null){
//             leftPanel = new javax.swing.JPanel();
//             leftPanel.setPreferredSize(new java.awt.Dimension(250,500));
//             //leftPanel.add(getLeft_jTabPane());
//             leftPanel.add(getTreeView());
//         }
//         return leftPanel;
//     }

    public PlayerCharacter getCurrentCharacter () {
    	int index = allCharsPane.getSelectedIndex ();
    	return Generator.getPlayerCharacter (index);
    }
     
     public void addPCListener (PCListener listener) {
    	 pcListeners.add (listener);
     }
     
     protected void fireGroupChanged () {
// 		Iterator iter = pcListeners.iterator();
//		while (iter.hasNext ()) {
//			PCListener ls = (PCListener) iter.next ();
//			try {
//				ls.pcExchanged (pc);
//			}
//			catch (Exception e) {
//				
//			}
//		}
    	 
     }
}
