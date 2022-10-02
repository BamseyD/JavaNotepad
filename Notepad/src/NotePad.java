import javax.swing.*; //* picks up all classes in library
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class NotePad extends JFrame {
   //attributes
   private JTextArea m_tTextAreaData; 
   private File m_file; 
   private JFileChooser m_fileChooser; 
   private String m_savedFile;

   //constants
   final String DEFAULT_FILENAME = "default.txt";

   //static run method (instantiates the class)
    public static void main(String[] args) throws Exception {
        new NotePad();
    }

    //set many button defaults easily
    private JButton MakeButton(String toolTip, ImageIcon image){ //declare method
        JButton btn = new JButton(image); //declare new JButton setting
        btn.setBorder(BorderFactory.createEmptyBorder()); //off default border and background
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setToolTipText(toolTip); ////accessabilty feature
        return btn; //returns the button to code that calls this method
    }

    //constructor (GUI and controls)
    public NotePad(){
        //super the Jframe (pass title)
        super("JavaPad");//set window text
        //set window size
        setSize(700,500);
        //set window close operation to close app
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //make menu
        JMenuBar menuBar = new JMenuBar(); //create Jmenu object
        setJMenuBar(menuBar); //set it as main bar for this app
        //make menu list
        JMenu menu = new JMenu("File"); //create Jmenu (top lv menu e.g home, insert)
        menu.setMnemonic(KeyEvent.VK_F); //keyboard shortcut for menu (alt-F)
        menu.getAccessibleContext().setAccessibleDescription("File menu"); //accessability feature
        menuBar.add(menu);

        //MENU ITEMS
        //new menu item
        JMenuItem newMenu = new JMenuItem("New", KeyEvent.VK_N); //instantiate new JMenuItem object. indicating shortcut is crlt-n
        newMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK)); //set shortcut crlt-n
        newMenu.getAccessibleContext().setAccessibleDescription("Loads a file"); //text shown whne hover over item
        newMenu.addActionListener(new ActionListener() { //create new ActionListener object and within method is overriden (defines what happens to core control action - mouse click occurs) 
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
        setLayout(new GridBagLayout()); //sets app LM to GridBagsLayout
        GridBagConstrainsts constraints = new GridBagConstrainsts(); //instantiate GridBagCon object. Use to control placemnt of controls within grid.
        //set constraints
        constraints.gridx = 0; //specifiy which column and row the first control will be placed.
        constraints.gridy = 0;
        constraints.weightx = 1; //inform each cell in grid how expand. expand horizontally x but not vertically y
        constraints.weighty = 0;
        constraints.fill = GridBagConstrainsts.HORIZONTAL; //inform cell how fill available space. this instance horizonatlly





        //draw Jframe (test)
        setVisible(true);
    }
}


