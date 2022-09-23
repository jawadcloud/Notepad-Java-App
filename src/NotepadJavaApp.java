// Import Dependencies
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NotepadJavaApp extends KeyAdapter implements ActionListener, KeyListener {
    // Setting Default 'Font Size'
    int fsize = 20;
    private JTextArea area;
    private JScrollPane scpane;
    String text = "";

    // Setting 'Title'
    JFrame f = new JFrame("NotepadJavaApp");
    JTextField title;
    Font newFont;
    JPanel bottomPanel;
    JLabel detailsOfFile;
    JList fontFamilyList, fontStyleList, fontSizeList;
    JScrollPane sb;
    JMenuBar menuBar;
    JMenu file, edit, format;
    JMenuItem newdoc, open, save, print, exit, copy, paste, cut, selectall, fontfamily, fontstyle, fontsize;
    // Setting 'Fonts'
    String fontFamilyValues[] = {"Agency FB", "Antiqua", "Architect", "Arial", "Calibri", "Comic Sans", "Courier", "Cursive", "Impact", "Serif"};
    // Setting 'Font Sizes'
    String fontSizeValues[] = {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70"};
    int[] stylevalue = {Font.PLAIN, Font.BOLD, Font.ITALIC};
    // Setting 'Font Styles'
    String[] fontStyleValues = {"Default", "Bold", "Italic"};
    String fontFamily, fontSize, fontStyle;
    int fstyle;
    int cl;
    int linecount;
    JScrollPane sp;

    public NotepadJavaApp() {
        // Calling 'initUI()' To Add UI
        initUI();
        // Calling 'addActionEvents()' To Add Events
        addActionEvents();
    }

    public void actionPerformed(ActionEvent ae) {
        // If 'New' Menu Item Is Chosen
        if (ae.getActionCommand().equals("New File")) {
            // Set Default Text To ''
            area.setText("");
        } // If 'Open' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Open File")) {
            // Set Default Directory To 'C:'
            JFileChooser chooser = new JFileChooser("C:/");
            chooser.setAcceptAllFileFilterUsed(false);
            // Only Allowed '.txt' Files
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Requires '.txt' File", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(f);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    // Opening & Reading The File After
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read(br, null);
                    // Closing The File / Clearing The Memory
                    br.close();
                    area.requestFocus();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        } // If 'Save' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Save File")) {
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save File");
            // Open Save Dialog To Ask Where To Save The File
            int actionDialog = SaveAs.showOpenDialog(f);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // If 'Print' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Print File")) {
            try {
                // Open Print Dialog To Ask Where To Print The File
                area.print();
            } catch (Exception e) {
            }
        } // If 'Exit Notepad' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Exit Notepad")) {
            // Close / Destroy Notepad
            f.dispose();
        } // If 'Copy' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Copy")) {
            // 'Copy' Selected Text
            text = area.getSelectedText();
        } // If 'Paste' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Paste")) {
            // 'Paste' Selected Text
            area.insert(text, area.getCaretPosition());
        } // If 'Cut' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Cut")) {
            // 'Cut' & 'Paste' Selected Text
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } // If 'Select All' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Select All")) {
            // 'Select All' Text
            area.selectAll();
        } // If 'Font' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Font")) {
            // Set Selected 'Font'
            JOptionPane.showConfirmDialog(null, fontFamilyList, "Select Font", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            fontFamily = String.valueOf(fontFamilyList.getSelectedValue());
            newFont = new Font(fontFamily, fstyle, fsize);
            area.setFont(newFont);
        } // If 'Font Size' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Font Size")) {
            // Set Selected 'Font Size'
            JOptionPane.showConfirmDialog(null, fontSizeList, "Select Font Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            fontSize = String.valueOf(fontSizeList.getSelectedValue());
            fsize = Integer.parseInt(fontSize);
            newFont = new Font(fontFamily, fstyle, fsize);
            area.setFont(newFont);
        } // If 'Font Style' Menu Item Is Chosen
        else if (ae.getActionCommand().equals("Font Style")) {
            // Set Selected 'Font Style'
            JOptionPane.showConfirmDialog(null, fontStyleList, "Select Font Style", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            fstyle = stylevalue[fontStyleList.getSelectedIndex()];
            newFont = new Font(fontFamily, fstyle, fsize);
            area.setFont(newFont);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        // Calculating Text Length & Count
        cl = area.getText().length();
        linecount = area.getLineCount();
        detailsOfFile.setText("Length: " + cl + " Line: " + linecount);
    }

    public void initUI() {
        // Creating 'Details' Label
        detailsOfFile = new JLabel();
        // Creating 'Bottom Panel' Panel
        bottomPanel = new JPanel();
        // Creating 'Menu Bar' Menu Bar
        menuBar = new JMenuBar();

        // Creating 'File' Menu
        file = new JMenu("File");
        // Creating 'New' Menu Item In 'File'
        newdoc = new JMenuItem("New File");
        // Creating 'New (Ctrl + N)' Menu Item Shortcut In 'File'
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        // Creating 'Open' Menu Item In 'File'
        open = new JMenuItem("Open File");
        // Creating 'Open (Ctrl + O)' Menu Item Shortcut In 'File'
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        // Creating 'Save' Menu Item In 'File'
        save = new JMenuItem("Save File");
        // Creating 'Save (Ctrl + S)' Menu Item Shortcut In 'File'
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        // Creating 'Print' Menu Item In 'File'
        print = new JMenuItem("Print File");
        // Creating 'Print (Ctrl + P)' Menu Item Shortcut In 'File'
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        // Creating 'Exit Notepad' Menu Item In 'File'
        exit = new JMenuItem("Exit Notepad");
        // Creating 'Exit Notepad (ESC)' Menu Item Shortcut In 'File'
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        // Creating 'Edit' Menu
        edit = new JMenu("Edit");
        // Creating 'Copy' Menu Item In 'Edit'
        copy = new JMenuItem("Copy");
        // Creating 'Copy (Ctrl + C)' Menu Item Shortcut In 'Edit'
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        // Creating 'Paste' Menu Item In 'Edit'
        paste = new JMenuItem("Paste");
        // Creating 'Paste (Ctrl + V)' Menu Item Shortcut In 'Edit'
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        // Creating 'Cut' Menu Item In 'Edit'
        cut = new JMenuItem("Cut");
        // Creating 'Cut (Ctrl + X)' Menu Item Shortcut In 'Edit'
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        // Creating 'Select All' Menu Item In 'Edit'
        selectall = new JMenuItem("Select All");
        // Creating 'Select All (Ctrl + A)' Menu Item Shortcut In 'Edit'
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        // Creating 'Format' Menu
        format = new JMenu("Format");
        // Creating 'Font' Menu Item In 'Format'
        fontfamily = new JMenuItem("Font");
        // Creating 'Font Size' Menu Item In 'Format'
        fontsize = new JMenuItem("Font Size");
        // Creating 'Font Style' Menu Item In 'Format'
        fontstyle = new JMenuItem("Font Style");

        // Creating & Assigning List Of Fonts
        fontFamilyList = new JList(fontFamilyValues);
        // Creating & Assigning List Of Font Sizes
        fontSizeList = new JList(fontSizeValues);
        // Creating & Assigning List Of Font Styles
        fontStyleList = new JList(fontStyleValues);

        // Only Allowed To Select One Option
        fontFamilyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Creating Notepad Editor
        area = new JTextArea();
        // Setting Default 'Font' To 'Arial' / 'Font Size' To '20' / 'Font Style' To 'Default' In Notepad Editor
        area.setFont(new Font("Arial", Font.PLAIN, 20));
        // Setting 'Line-Wrapping' To True In Notepad Editor
        area.setLineWrap(true);
        // Setting 'Word-Wrapping' To True In Notepad Editor
        area.setWrapStyleWord(true);
        // Creating 'Scrollbar' In Notepad Editor
        scpane = new JScrollPane(area);
        // Creating 'Border Scrollbar' In Notepad Editor
        scpane.setBorder(BorderFactory.createEmptyBorder());

        // Setting 'Menu Bar' Above Notepad Editor
        f.setJMenuBar(menuBar);

        // Creating Menus & Menu Items In Menu Bar Above Notepad Editor
        menuBar.add(file); // 'File' Menu
        menuBar.add(edit); // 'Edit' Menu
        menuBar.add(format); // 'Format' Menu

        file.add(newdoc); // 'New' Menu Item In 'File'
        file.add(open); // 'Open' Menu Item In 'File'
        file.add(save); // 'Save' Menu Item In 'File'
        file.add(print); // 'Print' Menu Item In 'File'
        file.add(exit); // 'Exit Notepad' Menu Item In 'File'

        edit.add(copy); // 'Copy' Menu Item In 'Edit'
        edit.add(paste); // 'Paste' Menu Item In 'Edit'
        edit.add(cut); // 'Cut' Menu Item In 'Edit'
        edit.add(selectall); // 'Select All' Menu Item In 'Edit'

        format.add(fontfamily); // 'Font' Menu Item In 'Format'
        format.add(fontsize); // 'Font Size' Menu Item In 'Format'
        format.add(fontstyle); // 'Font Style' Menu Item In 'Format'

        // Setting 'Bottom Panel' Below Notepad Editor
        bottomPanel.add(detailsOfFile); // 'Details' In 'Bottom Panel'

        // Setting Notepad Dimensions
        f.setSize(980, 480);
        // Setting Notepad Layout
        f.setLayout(new BorderLayout());
        // Setting 'Scroll Bar' In Notepad
        f.add(scpane, BorderLayout.CENTER);
        // Setting 'Bottom Panel' In Notepad
        f.add(bottomPanel, BorderLayout.SOUTH);
        // Setting Notepad Visibility
        f.setVisible(true);
    }

    public void addActionEvents() {
        // Action Listeners For Menu Items
        newdoc.addActionListener(this);
        save.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        cut.addActionListener(this);
        selectall.addActionListener(this);
        open.addActionListener(this);
        fontfamily.addActionListener(this);
        fontsize.addActionListener(this);
        fontstyle.addActionListener(this);
    }

    public static void main(String ar[]) {
        NotepadJavaApp tnp = new NotepadJavaApp();
    }
}