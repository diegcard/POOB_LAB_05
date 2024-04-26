package Square.src.presentation;

import Square.src.domain.Square;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
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

    private JMenuItem changeSize;

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

    //private HashMap<Color, Square> squaresMap;

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
        //Add settings menu
        JMenu menuSettings = new JMenu("Settings");
        changeSize = new JMenuItem("Change size");
        JMenuItem changeColor = new JMenuItem("Reset");
        //Functions to hand
        changeSize.addMouseListener(menuItemMouseListener);
        changeColor.addMouseListener(menuItemMouseListener);
        //Add items to menu
        menuSettings.add(changeSize);
        menuSettings.add(changeColor);
        //Add menu to menu bar
        menuSettings.addMouseListener(menuItemMouseListener);
        menuBar.add(menuSettings);
        return menuBar;
    }

    /**
     * logicChangeSize
     * This method is the logic to change the size of the board.
     */
    private void logicChangeSize() throws NumberFormatException{
        String size = JOptionPane.showInputDialog(this, "Enter the size of the board", "Change size", JOptionPane.QUESTION_MESSAGE);
        if (size != null) {
            try {
                int newSize = Integer.parseInt(size);
                if (newSize > 0) {
                    getContentPane().removeAll();
                    //Integer m = Math.pow(newSize, 2) / 2;
                    prepareElementsBoardGame(newSize, newSize, newSize / 2);
                    preparateArrows();
                } else {
                    JOptionPane.showMessageDialog(this, "The size must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "The size must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        this.revalidate();
        this.repaint();
        this.refresh();
    }

    /**
     * SquareGUI
     * This is the constructor of the class. It prepares the elements and actions of the window.
     */
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
        prepareArrowsActions();
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

        changeSize.addActionListener(e -> {
            logicChangeSize();
        });
    }

    /**
     * Function to prepare the elements of the board
     * It is the main board of the game
     */
    private void prepareElementsBoard() {
        preparateArrows();
        prepareElementsBoardGame(5, 5, 3);
    }

    /**
     * Function to prepare the elements of the board game
     *
     * @param rows        nums of rows
     * @param columns     nums of columns
     * @param filledCount nums of filled squares
     */
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
        this.buttons = new JButton[rows][columns];
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
                button.addMouseListener(changeColorButtonAction());
            } else if (counter < (filledCount * 2) * 2 && onlyBorder) { // Pintar solo el borde del botón
                button.setBorder(new LineBorder(randomColor, 10));
                button.setContentAreaFilled(false);
                button.setEnabled(false);
                onlyBorder = false;
            } else { // Si ninguna de las condiciones se cumple, pintar el botón de blanco
                button.setBackground(Color.WHITE);
                button.setEnabled(false);
            }
            buttons[position.x][position.y] = button;
            //button.addMouseListener(changeColorButtonAction());
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
     * Function to change the color of the buttons
     *
     * @return MouseListener
     */
    private MouseListener changeColorButtonAction() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                Color originalColor = button.getBackground();
                Color borderColor = null;
                Color newColor = JColorChooser.showDialog(null, "Choose a color", originalColor);

                button.setBackground(newColor);
                button.setBorder(new LineBorder(Color.WHITE, 10));
                for (int i = 0; i < buttons.length; i++) {
                    for (int j = 0; j < buttons[i].length; j++) {
                        JButton currentButton = buttons[i][j];
                        if (currentButton.getBorder() instanceof LineBorder) {
                            if (((LineBorder) currentButton.getBorder()).getLineColor().equals(originalColor)) {
                                currentButton.setBackground(newColor);
                                currentButton.setBorder(new LineBorder(newColor, 10));
                            }
                        }
                    }
                }
            }
        };
    }

    /**
     * Function to prepare the actions of the arrows
     */
    private void prepareArrowsActions() {
        arrowUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        arrowDown.addActionListener(e -> {
            //TODO
        });

        arrowLeft.addActionListener(e -> {
            //TODO
        });

        arrowRight.addActionListener(e -> {
            //TODO
        });
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
        Polygon upArrow = createArrowUpDesign();
        Polygon downArrow = createArrowDownDesign();
        Polygon leftArrow = createArrowLeftDesign();
        Polygon rightArrow = createArrowRightDesing();
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

    /**
     * Refresh the window
     */
    private void refresh() {
        revalidate();
        repaint();
    }

    /**
     * Function to create the up arrow
     *
     * @return Polygon
     */
    private Polygon createArrowUpDesign() {
        int[] xPoints = {10, 20, 0};
        int[] yPoints = {0, 20, 20};
        int nPoints = 3;
        return new Polygon(xPoints, yPoints, nPoints);
    }

    /**
     * Function to create the down arrow
     *
     * @return Polygon
     */
    private Polygon createArrowDownDesign() {
        int[] xPoints = {0, 20, 10};
        int[] yPoints = {0, 0, 20};
        int nPoints = 3;
        return new Polygon(xPoints, yPoints, nPoints);
    }

    /**
     * Function to create the left arrow
     *
     * @return Polygon
     */
    private Polygon createArrowLeftDesign() {
        int[] xPoints = {0, 20, 20};
        int[] yPoints = {10, 0, 20};
        int nPoints = 3;
        return new Polygon(xPoints, yPoints, nPoints);
    }

    /**
     * Function to create the right arrow
     *
     * @return Polygon
     */
    private Polygon createArrowRightDesing() {
        Polygon arrow = new Polygon();
        arrow.addPoint(0, 0);
        arrow.addPoint(20, 10);
        arrow.addPoint(0, 20);
        return arrow;
    }

    /**
     * Function to create the arrow button
     *
     * @param arrow Polygon
     * @return JButton
     */
    private static JButton createArrowButton(Polygon arrow) {
        BufferedImage arrowImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
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
