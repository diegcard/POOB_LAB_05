package Square.src.presentation;

import Square.src.domain.Square;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * SquareGUI
 * This class is the main class of the application. It is the graphical user interface of the application.
 *
 * @author Diego Cardenas
 * @author Sebastian Cardona
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

    //Panel elemts
    private JPanel panelElements;

    //Game Board

    private JPanel panelBoardGame;
    private JPanel panelArrows;

    //Arrow buttons
    private JButton arrowUp;
    private JButton arrowDown;
    private JButton arrowLeft;
    private JButton arrowRight;

    private Square[][] squares;
    private JButton[][] buttons;

    private HashMap<Color, Square> squaresMap;

    /**
     * prepareElements
     * This method prepares the elements of the menu bar.
     *
     * @return JMenuBar
     */
    private JMenuBar prepareElementsMenu() {
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

    private SquareGUI() {
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
        prepareElementsBoard();
    }

    /**
     * Function to prepare the actions of the window
     */
    private void preparateActions() {
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
    private void closeApp() {
        int option = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            setVisible(false);
            System.exit(0);
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * Function to prepare the actions of the menu
     */
    private void prepareMenuActions() {
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

    /**
     * Function to prepare the elements of the board
     * It is the main board of the game
     */
    private void prepareElementsBoard() {
        preparateArrows();
        prepareElementsBoardGame(4, 4, 3);
    }

    private void prepareElementsBoardGame(int rows, int columns, int filledCount) {
        JPanel panel = new JPanel(new GridLayout(rows, columns));
        int counter = 0;
        ArrayList<Point> positions = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                positions.add(new Point(i, j));
            }
        }
        Collections.shuffle(positions);
        buttons = new JButton[rows][columns];
        // Use a HashMap to store the squares with color as the key
        squaresMap = new HashMap<>();
        Random random = new Random();
        boolean onlyBorder = false;
        Color randomColor = null;

        for (Point position : positions) {
            JButton button = new JButton();
            if (counter < filledCount * 2 && !onlyBorder) { // Pintar el botón completamente
                randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                button.setBackground(randomColor);
                button.setBorder(new LineBorder(Color.WHITE, 10));
                button.setContentAreaFilled(true);
                onlyBorder = true;
            } else if (counter < (filledCount * 2) * 2 && onlyBorder) { // Pintar solo el borde del botón
                button.setBorder(new LineBorder(randomColor, 10));
                button.setContentAreaFilled(false);
                onlyBorder = false;
            } else { // Si ninguna de las condiciones se cumple, pintar el botón de blanco
                button.setBackground(Color.WHITE);
                button.setEnabled(false);
            }
            buttons[position.x][position.y] = button;
            // Crear una instancia de Square para cada botón
//            Color borderColor = Color.WHITE;
//            if (button.getBorder() instanceof LineBorder) {
//                borderColor = ((LineBorder) button.getBorder()).getLineColor();
//            }
//            Square square = new Square(button.getBackground(), borderColor);
//            // Store the square in the HashMap with color as the key
//            squaresMap.put(button.getBackground(), square);
            counter++;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                panel.add(buttons[i][j]);
            }
        }
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
}

    /**
     * Function to prepare the arrows of the board
     * It is the main board of the game
     */
    private void preparateArrows() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        float topAndBottom = (float) (height / 60.5);
        float leftAndRight = (float) (width / 200);
        JPanel boadPaneArrow = new JPanel(new GridLayout(3, 3));
        boadPaneArrow.setBorder(BorderFactory.createEmptyBorder((int) topAndBottom, (int) leftAndRight, (int) topAndBottom, (int) leftAndRight));
        //Create the buttons
        Polygon upArrow = createUpArrow();
        Polygon downArrow = createUpArrow();
        Polygon leftArrow = createUpArrow();
        Polygon rightArrow = createUpArrow();
        //Create the buttons
        arrowUp = createArrowButton(upArrow);
        arrowDown = createArrowButton(downArrow);
        arrowLeft = createArrowButton(leftArrow);
        arrowRight = createArrowButton(rightArrow);
        //Add the buttons to the panel
        boadPaneArrow.add(new JLabel());
        boadPaneArrow.add(arrowUp);
        boadPaneArrow.add(new JLabel());
        boadPaneArrow.add(arrowLeft);
        boadPaneArrow.add(new JLabel());
        boadPaneArrow.add(arrowRight);
        boadPaneArrow.add(new JLabel());
        boadPaneArrow.add(arrowDown);
        boadPaneArrow.add(new JLabel());
        //Add the panel to the window
        this.add(boadPaneArrow, BorderLayout.SOUTH);
    }

    private Polygon createUpArrow() {
        Polygon arrow = new Polygon();
        arrow.addPoint(10, 0);
        arrow.addPoint(20, 20);
        arrow.addPoint(0, 20);
        return arrow;
    }

    private static JButton createArrowButton(Polygon arrow) {
        BufferedImage arrowImage = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = arrowImage.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fill(arrow);
        g2d.dispose();
        ImageIcon icon = new ImageIcon(arrowImage);
        return new JButton(icon);
    }

    public static void main(String[] args) {
        SquareGUI gui = new SquareGUI();
        gui.setVisible(true);
    }
}
