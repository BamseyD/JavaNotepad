import javax.swing.*; //* picks up all classes in library
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class NotePad extends JFrame {
   //attributes
   private JTextArea m_tTextAreaData;           //global ref to swing JTextArea object used as main control of app. Allows editing of contents presently loaded txt file
   private File m_file;                         //global ref of FIle object. a pointer to location where presently load file is.
   private JFileChooser m_fileChooser;          //global ref filechooser used to browse for file to open or a location to save presently load file
   private String m_savedFile;                  //global ref to content of last file loaded. used to compare content of JTextArea to before a save to see if changes been made (if save required)

   //constants
   final String DEFAULT_FILENAME = "default.txt";       //adds static ref to the default fileanme the app will set for new files.

   //static run method (instantiates the class)
    public static void main(String[] args) throws Exception {
        new NotePad();
    }

    //set many button defaults easily
    private JButton MakeButton(String toolTip, ImageIcon image){                            //declare method
        JButton btn = new JButton(image);                                                   //declare new JButton setting
        btn.setBorder(BorderFactory.createEmptyBorder());                                   //off default border and background
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setToolTipText(toolTip);                                                        //accessabilty feature
        return btn;                                                                         //returns the button to code that calls this method
    }

    //constructor (GUI and controls)
    public NotePad(){
        //super the Jframe (pass title)
        super("JavaPad");                                       //set window text
        //set window size
        setSize(700,500);
        //set window close operation to close app
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //make menu
        JMenuBar menuBar = new JMenuBar();                                              //create Jmenu object
        setJMenuBar(menuBar);                                                           //set it as main bar for this app
        //make menu list
        JMenu menu = new JMenu("File");                                             //create Jmenu (top lv menu e.g home, insert)
        menu.setMnemonic(KeyEvent.VK_F);                                               //keyboard shortcut for menu (alt-F)
        menu.getAccessibleContext().setAccessibleDescription("File menu");          //accessability feature
        menuBar.add(menu);

        //MENU ITEMS
        //new menu item
        JMenuItem newMenu = new JMenuItem("New", KeyEvent.VK_N);                                    //instantiate new JMenuItem object. indicating shortcut is crlt-n
        newMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));             //set shortcut crlt-n
        newMenu.getAccessibleContext().setAccessibleDescription("Loads a file");                       //text shown whne hover over item
        newMenu.addActionListener(new ActionListener() {                                                  //create new ActionListener object and within method is overriden (defines what happens to core control action - mouse click occurs) 
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        //add to the file menu
        menu.add(newMenu);

        //load menu item
        JMenuItem loadMenu = new JMenuItem("Load", KeyEvent.VK_L); 
        loadMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK)); 
        loadMenu.getAccessibleContext().setAccessibleDescription("Loads a file");
        loadMenu.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        //add to the file menu
        menu.add(loadMenu);

        //save menu item
        JMenuItem saveMenu = new JMenuItem("Save", KeyEvent.VK_S); 
        saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK)); 
        saveMenu.getAccessibleContext().setAccessibleDescription("Save the current file");
        saveMenu.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        //add to the file menu
        menu.add(saveMenu);

        //save as menu item
        JMenuItem saveAsMenu = new JMenuItem("Save As", KeyEvent.VK_A); 
        saveAsMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK)); 
        saveAsMenu.getAccessibleContext().setAccessibleDescription("Save the current file");
        saveAsMenu.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        //add to the file menu
        menu.add(saveAsMenu);

        //SET LAYOUT MANAGER
        setLayout(new GridBagLayout());                                         //sets app LM to GridBagsLayout
        GridBagConstraints constraints = new GridBagConstraints();              //instantiate GridBagCon object. Use to control placemnt of controls within grid.
        //set constraints
        constraints.gridx = 0;                                                  //specifiy which column and row the first control will be placed.
        constraints.gridy = 0;
        constraints.weightx = 1;                                                //inform each cell in grid how expand. expand horizontally x but not vertically y
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;                       //inform cell how fill available space. this instance horizonatlly
        //do button row
        JPanel buttonRow = new JPanel();                                        //creates JPanel instance
        buttonRow.setLayout(new FlowLayout(FlowLayout.LEFT));                   //sets JPanel to use FlowLayout LM. Set to align left. Any control added to this panel is arranged in row from left.
        //add button row to JFrame
        add(buttonRow,constraints);                                             //adds JPanel to UI using constraints object. Placed using objects dictates.
        
        //new button
        JButton btnNew = MakeButton("New File", new ImageIcon("resources/images/NewFile56x56.gif"));    //uses utility method created in btn defaults to create new JButton (pre-sets)
        btnNew.addActionListener(new ActionListener() {                                                                   //new ActionListener add to button. listen for mouse click.
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        buttonRow.add(btnNew);

        //load button
        JButton btnLoad = MakeButton("Load File", new ImageIcon("resources/images/Load56x56.gif"));    
        btnLoad.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        buttonRow.add(btnLoad);

        //save button
        JButton btnSave = MakeButton("Save File", new ImageIcon("resources/images/Save56x56.gif"));    
        btnSave.addActionListener(new ActionListener() {                                                 
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        buttonRow.add(btnSave);

        //Save As button
        JButton btnSaveAs = MakeButton("Save As...", new ImageIcon("resources/images/SaveAs56x56.gif"));
        btnSaveAs.addActionListener(new ActionListener() {                                                               
            public void actionPerformed(ActionEvent e) {
                //TODO new file method call
            }
        });
        buttonRow.add(btnSaveAs);

        //draw Jframe (test)
        setVisible(true);
    }
}


