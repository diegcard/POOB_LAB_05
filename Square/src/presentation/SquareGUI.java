package Square.src.presentation;

import Square.src.domain.Square;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * SquareGUI
 * This class is the main class of the application. It is the graphical user interface of the application.
 *
 * @author Diego Cardenas
 * @author Sebastian Cardona
 *
 * @version 1.0
 */
public class SquareGUI extends JFrame {

    //Dimensions
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = screenSize.width / 2;
    private static final int HEIGHT = screenSize.height / 2;
    private static final Dimension windowSize = new Dimension(WIDTH, HEIGHT);

    //Menu
    private JMenuItem menuNew;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;

    //Game Board
    private JPanel panelBoardGame;
    private JButton startGameButton;

    /**
     * prepareElements
     * This method prepares the elements of the menu bar.
     *
     * @return JMenuBar
     */
    private JMenuBar prepareElementsMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        menuNew = new JMenuItem("New");
        menuOpen = new JMenuItem("Open");
        menuSave = new JMenuItem("Save");
        menuExit = new JMenuItem("Exit");
        MouseAdapter menuItemMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                source.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        };
        //Functions to hand
        menuNew.addMouseListener(menuItemMouseListener);
        menuOpen.addMouseListener(menuItemMouseListener);
        menuSave.addMouseListener(menuItemMouseListener);
        menuExit.addMouseListener(menuItemMouseListener);
        //Add items to menu
        menuFile.add(menuNew);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        //Add menu to menu bar
        menuBar.add(menuFile);
        menuFile.addMouseListener(menuItemMouseListener);
        return menuBar;
    }

    private SquareGUI(){
        prepareElements();
        preparateActions();
    }

    /**
     * Function to prepare the elements of the window
     */
    private void prepareElements() {
        setTitle("Square");
        setSize(windowSize);
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setJMenuBar(prepareElementsMenu());
    }

    /**
     * Function to prepare the actions of the window
     */
    private void preparateActions(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                closeApp();
            }
        });
        prepareMenuActions();

    }

    /**
     * Function to close the application
     */
    private void closeApp(){
        int option = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            setVisible(false);
            System.exit(0);
        }else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * Function to prepare the actions of the menu
     */
    private void prepareMenuActions(){
        menuNew.addActionListener(e -> {
            //TODO
        });

        menuOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Square files", "square");
                fileChooser.setFileFilter(filter);
                int option = fileChooser.showOpenDialog(SquareGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(SquareGUI.this, "File opened successfully", "Open", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        menuSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Square files", "square");
                fileChooser.setFileFilter(filter);
                int option = fileChooser.showSaveDialog(SquareGUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(SquareGUI.this, "File saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        menuExit.addActionListener(e -> {
            closeApp();
        });
    }

    private void prepareElementsGame(){
        panelBoardGame = new JPanel();
    }

    public static void main(String[] args) {
        SquareGUI gui = new SquareGUI();
        gui.setVisible(true);
    }
}
