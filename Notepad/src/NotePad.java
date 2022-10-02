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



        
        //draw Jframe (test)
        setVisible(true);
    }
}


