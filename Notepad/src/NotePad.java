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
   private JTextArea m_textAreaData;           //global ref to swing JTextArea object used as main control of app. Allows editing of contents presently loaded txt file
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
                //new file method call
                NewFile();
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
                //new file method call
                LoadFile();
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
                //new file method call
                SaveFile(m_file);
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
                //new file method call
                ShowSaveAsDialog();
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
                //new file method call
                NewFile();
            }
        });
        buttonRow.add(btnNew);

        //load button
        JButton btnLoad = MakeButton("Load File", new ImageIcon("resources/images/Load56x56.gif"));    
        btnLoad.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                //new file method call
                LoadFile();
            }
        });
        buttonRow.add(btnLoad);

        //save button
        JButton btnSave = MakeButton("Save File", new ImageIcon("resources/images/Save56x56.gif"));    
        btnSave.addActionListener(new ActionListener() {                                                 
            public void actionPerformed(ActionEvent e) {
                //new file method call
                SaveFile(m_file);
            }
        });
        buttonRow.add(btnSave);

        //Save As button
        JButton btnSaveAs = MakeButton("Save As...", new ImageIcon("resources/images/SaveAs56x56.gif"));
        btnSaveAs.addActionListener(new ActionListener() {                                                               
            public void actionPerformed(ActionEvent e) {
                //new file method call
                ShowSaveAsDialog();
            }
        });
        buttonRow.add(btnSaveAs);

        //amend constraints
        constraints.gridy = 1;                                                      //changes next row from 0 to 1
        constraints.fill = GridBagConstraints.BOTH;                                 //changes fill direction to both directions. expand row to grab as much space possible - push button row to top.
        constraints.weighty = 1;                                                    //instruct row to expand vert. already expands hori.
        //set some insets on the window (so we have border)
        constraints.insets = new Insets(2,2,2,2);           //set insets which provide 2px of margin inside row - control not fill right to edge of window.
        //get textArea
        m_textAreaData = new JTextArea();                                                           //instanciate JTextAre object
        m_textAreaData.setEditable(true);                                                        //sets it as editiable 
        m_textAreaData.setMargin(new InsetsUIResource(2,2,2,2));           //set some interior margins. text displayed not collide with edge.
        m_textAreaData.setFont(new Font("Arial", Font.PLAIN, 20));                      //set font and size used in control
        //get scroll pane
        JScrollPane scrollPane = new JScrollPane(m_textAreaData);                                  //instantiate new JScrollPane which accepts JTextArea object to scroll
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);   //removes hori bars
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);      //forces vert bar to always draw.
        //add textarea to screen
        add(scrollPane, constraints);                                                              //adds JScrollPane to UI using current constraints

        //draw Jframe (test)
        setVisible(true);

        //set operational defaults ----------------------
        m_file = new File(DEFAULT_FILENAME);                                        //set a File ob ref to default name set in constant at top of the class
        m_savedFile = "";                                                           //sets current loaded file content as an empty string
        //setup file chooser
        m_fileChooser = new JFileChooser();                                         //instanciate the global JFileChooser ref
        //set to PWD
        m_fileChooser.setCurrentDirectory(new File("."));                 //sets start directory of JFileChooser to current proj directory. It creates new file ref to the shorthand for current directory.
        //filter by text files
        m_fileChooser.setFileFilter(new FileFilter() {                             //set new file filter to JFileChooser
            //set description
            public String getDescription() {                                       //override default getDesc method. Displays the desc of files we want to filter (text files)
                return "Text files (*.txt)";
            }
            //set file extension
            public boolean accept(File f) {                                        //override default accept method. instruct JFileCHooser whether a file can be displayed in chooser or not.
                if (f.isDirectory()) return true;
                else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".txt");                      //this case: allows file to be displayed if it is directory or ends with .txt extension
                }
            }
        });
    }

    //DIALOG BOXES
    //show a simple error message dialog
    private void ShowErrorDialog(String title, String message) {                                //declares method as private. This dosn't matter (one file project). Method returns no values and accpts 2 arguments - the title text (appear in dialog window bar) and actual message text to be displayed.
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);         //use built in Swing JoptionPane ob to create new message box from arguements passed to this method
    }

    //show simple yes/no confirm box
    private int ShowConfirmDialog(String title, String message) {                               //declares method as private. Method returns an int which corrisponds to JOptionPane return value codes and accepts 2 arguements - title text (window bar) and actual message text be displayed. Draws dialog with yes/no buttons and dif icon
        //show yes/ no dialog
        return JOptionPane.showConfirmDialog(this, message, title,                              //declares dialog described above. retun statement ensures that whatever btn pressed, code is passed to whichever process calls this method.
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
    }

    //quick check to save unsaved changes
    private boolean DiscardChanges() {                                                                                          //declare private. method returns boolean, used to indicate whether or not user is happy to lose changes to current file. no arguments required.
        if (m_savedFile.equals(m_textAreaData.getText())) return true;                                                          //tests current JTextAre content against last saved version of current file. If same, no changes been made so file can be uploaded without fear of work loss.
        return ShowConfirmDialog("File changed", "Discard current changes?") == JOptionPane.YES_OPTION;          //if code reaches this line, user must have made changes. uses previous method. returns whether they agree with discarding the changes.
    }

    //BUTTON METHODS
    //discards current proj and loads new one
    private void NewFile() {                                                //method returns no value, requires no arguments. Called from button and menu item for NEW FILE. Uploads any data presently loaded and resets app to new unsaved doc
        //check for changes to discard, if found bin out
        if (!DiscardChanges()) return;                                      //tests for currently unsaved changes. if user indicates that they dont want to abondon these, this process is aborted with return statement
        //reset text box
        m_textAreaData.setText("");                                      //empty all of various data. Sets JTextArea to empty string.
        //reset filename
        m_file = new File(DEFAULT_FILENAME);                               //resets file ref to default filename declared in constant.
        //reset saved file
        m_savedFile = "";                                                  //sets last opened doc txt to an empty string.
    }

    //load in new file from disk
    private void LoadFile() {
        //check for changes to discard, if found bin out
        if (!DiscardChanges()) return;
        //trap issues
        try{                                                               //starts with try block to trap any exceptions thrown by any load data process. contains the main app logic process for loading data 
            //set the chooser title
            m_fileChooser.setDialogTitle("Load file");                          //sets title for JFileChooser. Using same object for load and save processes, changing title is neccessary.
            //show dialog and trap result
            int selection = m_fileChooser.showOpenDialog(this);                             //draws filechooser. records the result of dialog (the file chooser has been used to select a file or been closed)
            //if a file is selected...
            if (selection == JFileChooser.APPROVE_OPTION){                                  //tests if file chooser been used to select file(if not, no need to do anything else)
                //show results
                m_file = m_fileChooser.getSelectedFile();                                   //gets ref to selected file from file chooser ob
                //get a file reader
                BufferedReader fileReader = new BufferedReader(new FileReader(m_file));     //declares a BufferedReader used to read file in. takes one argue (the selected file ref) used to create a fileReader ob 
                //read file into stringbuilder
                StringBuilder sb = new StringBuilder();                                     //declare new String builder with which to collect content of file as it is read in
                String line = fileReader.readLine();                                        //reads first line of file into new string var
                while (line !=null) {                                                       //while loop to read text from file and store in StringBuilder one line at time. If no next line, line var will be set to null and loop ends.
                    sb.append(line + "\n");
                    line = fileReader.readLine();
                }
                //close file
                fileReader.close();                                                         //closes file as it has now been read. releases file lock and allows editing or read by other apps.
                //set text
                m_textAreaData.setText(sb.toString());                                      //takes text collected by sb and sets it as content of JTeaxtArea
                //set last saved
                m_savedFile = sb.toString();                                                //records txt collected in sb  and stores it as record of last daved state of this data
            }
        }
        //catch issues
        catch (Exception e) {                                             //corrisonding catch block, calls previously coded error dialog method to alert user as issues experianced
            ShowErrorDialog("Load error", e.getMessage());
        }
    }

    //save file method
    private void SaveFile(File file) {
        //trap issues
        try{
            //check for any changes - alert and exit if none
            if(m_savedFile.equals(m_textAreaData.getText()))                                        //test if current file need saving.
                throw new Exception("Nothing to save!");                                   //if not new exception is thrown with message (will trigger catch statment and generate error dialog)
            //does file exist, if not make a new one at specified path?
            if (!file.exists())                                                                    //test if file currently exists. if not, new file is created using path specified in File ob
                file.createNewFile();
            //does file have write access?
            if (!file.canWrite())                                                                  //test if possible to write to selected file. if not, exception thrown. alerts user as before.
                throw new Exception("Unable to obtain file write access" + file.getName());
            //should be okay - pass file to File Writer
            FileWriter fw = new FileWriter(file);                                                  //instanciates new FW ob with ref of File
            //write file
            fw.write(m_textAreaData.getText());                                                    //writes contents of JTextArea to the open file
            //close file (release file lock)
            fw.close();                                                                            //closes file releasing lock and leaving the file to be editable of viewable in other apps
            //set saved file content
            m_savedFile = m_textAreaData.getText();                                                //stores copy of saved file to compare as before and determine if unsaved changes have been made
        }
        //catch issues and display error dialog
        catch (Exception e) {
            ShowErrorDialog("Save Error", e.getMessage());
        }
    }

      //save as dialog method
      private void ShowSaveAsDialog() {                                 //method called from button and menu item for save as. it wrapper the SaveFile method, passing a selected File to it.
        //set the chooser title
        m_fileChooser.setDialogTitle("Save where?");       //sets title of the JFIleChooser dialog
        //show dialog and trap result
        int selection = m_fileChooser.showSaveDialog(this);            //displays dialog and awaits status code response which it records in temp int var
        //if a file is selcted...
        if (selection == JFileChooser.APPROVE_OPTION) {                //tests if user indicates positively that the selcted file is where they would like to save current data 
            //get selected file
            File f = m_fileChooser.getSelectedFile();                  //creates ref to the file selected by user
            //pass result to SaveFile
            SaveFile(f);                                               //passes selected file ref to SaveFile method
        }
    }
}


