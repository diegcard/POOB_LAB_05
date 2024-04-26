package Square.src.domain;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

/**
 * The square class is a class that represents a square.
 * It has the following attributes:
 *
 * @author Diego Cardenas
 * @author Sebastian Cardona
 *
 * @version 1.0
 */
public class Square {
    private Color color;
    private Color borderColor;
    private Square[][] squares;
    private JButton[][] buttons;
    private boolean canMove = true;
    private HashMap<String, Color> buttonsColors = new HashMap<>();
    private boolean isNotWiner = false;

    public Square(Color color, Color borderColor, Square[][] squares, JButton[][] buttons) {
        this.color = color;
        this.borderColor = borderColor;
        this.squares = squares;
        this.buttons = buttons;
    }

    public void changeColor(Color borderColor) {
        if(!this.color.equals(Color.BLACK)) {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", this.color);
            if(newColor != null) {
                this.color = newColor;
                this.borderColor = borderColor;
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public boolean isNotWiner() {
        return isNotWiner;
    }


    public void moveUp(int currentRow, int currentColumn) {
        if (!this.canMove()) {
            return;
        }
        if (currentRow > 0 && !hasPaintedBorder(buttons[currentRow][currentColumn]) && !buttons[currentRow][currentColumn].getBackground().equals(Color.WHITE) && !isNotWiner){
            Square squareAbove = squares[currentRow - 1][currentColumn];
            if (squareAbove.getColor().equals(Color.WHITE) || hasOnlyBorderColored(squareAbove)) {
                if (hasDifferentBorderColor(buttons[currentRow - 1][currentColumn]) && !isHoleTapped(buttons[currentRow - 1][currentColumn])) {
                    JOptionPane.showMessageDialog(null, "Perdiste", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    isNotWiner = true;
                } else {
                    if (isHole(currentRow - 1, currentColumn)) {
                        squareAbove.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow - 1][currentColumn].setBackground(squareAbove.getColor());
                        buttons[currentRow - 1][currentColumn].setBorder(null);
                        squareAbove.setCanMove(false);
                        if (squareAbove.getColor().equals(this.color)) {
                            buttons[currentRow - 1][currentColumn].setBackground(this.color);
                        }
                        String position = (currentRow - 1) + "," + currentColumn;
                        buttonsColors.put(position, squareAbove.getColor());

                    } else if (isTappedHole(currentRow - 1, currentColumn)) {
                        squareAbove.setCanMove(true);
                        squareAbove.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow - 1][currentColumn].setBackground(squareAbove.getColor());
                    } else {
                        squareAbove.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow - 1][currentColumn].setBackground(squareAbove.getColor());
                    }
                }
            }
        }
        holeee();
    }

    public void holeee() {
        for (String position : buttonsColors.keySet()) {
            String[] coordinates = position.split(",");
            int row = Integer.parseInt(coordinates[0]);
            int column = Integer.parseInt(coordinates[1]);

            Color color = buttonsColors.get(position);

            if (buttons[row][column].getBackground().equals(Color.WHITE)) {
                buttons[row][column].setBackground(color);
                squares[row][column].setCanMove(false);
            }
        }
    }

    public boolean isTappedHole(int row, int column) {
        // Verifica si el fondo del botón es distinto de blanco
        boolean isBackgroundNotWhite = !buttons[row][column].getBackground().equals(Color.WHITE);

        // Verifica si la ficha no se puede mover
        boolean cannotMove = !squares[row][column].canMove();

        // Un botón es un hueco tapado si su fondo es distinto de blanco y la ficha no se puede mover
        return isBackgroundNotWhite && cannotMove;
    }

    public boolean isHole(int row, int column) {
        // Verifica si el fondo del botón es blanco
        boolean isBackgroundWhite = buttons[row][column].getBackground().equals(Color.WHITE);

        // Verifica si el borde del botón está pintado
        boolean isBorderPainted = false;
        if (buttons[row][column].getBorder() instanceof LineBorder) {
            LineBorder border = (LineBorder) buttons[row][column].getBorder();
            isBorderPainted = !border.getLineColor().equals(Color.WHITE);
        }
        // Un botón es un hueco si su fondo es blanco y su borde está pintado
        return isBackgroundWhite && isBorderPainted;
    }

    public boolean hasPaintedBorder(JButton button) {
        if (button.getBorder() instanceof LineBorder) {
            LineBorder border = (LineBorder) button.getBorder();
            // Comprueba si el color del borde no es blanco
            return !border.getLineColor().equals(Color.WHITE);
        }
        return false;
    }


    public boolean hasOnlyBorderColored(Square square) {
        Color borderColor = square.getBorderColor();
        return borderColor != null && !borderColor.equals(Color.WHITE);
    }

    public boolean hasDifferentBorderColor(JButton button) {
        if (button.getBorder() instanceof LineBorder) {
            LineBorder border = (LineBorder) button.getBorder();
            if (border.getLineColor().equals(Color.WHITE)) {
                return false;
            }
            return !border.getLineColor().equals(this.color);
        }
        return false;
    }


    public boolean isHoleTapped(JButton button) {
        if (button.getBorder() instanceof LineBorder) {
            LineBorder border = (LineBorder) button.getBorder();
            // Comprueba si el color del botón es distinto de blanco (ha sido tapado)
            // y si el botón tiene un borde pintado (era un hueco)
            return !button.getBackground().equals(Color.WHITE) && !border.getLineColor().equals(Color.WHITE);
        }
        return false;
    }
    public void moveDown(int currentRow, int currentColumn) {
        if (!this.canMove()) {
            return;
        }
        if (currentRow < squares.length - 1 && !hasPaintedBorder(buttons[currentRow][currentColumn]) && !buttons[currentRow][currentColumn].getBackground().equals(Color.WHITE) && !isNotWiner) {
            Square squareBelow = squares[currentRow + 1][currentColumn];
            if (squareBelow.getColor().equals(Color.WHITE) || hasOnlyBorderColored(squareBelow)) {
                if (hasDifferentBorderColor(buttons[currentRow + 1][currentColumn]) && !isHoleTapped(buttons[currentRow + 1][currentColumn])) {
                    JOptionPane.showMessageDialog(null, "Perdiste", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    isNotWiner = true;
                } else {
                    if (isHole(currentRow + 1, currentColumn)) {
                        squareBelow.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow + 1][currentColumn].setBackground(squareBelow.getColor());
                        buttons[currentRow + 1][currentColumn].setBorder(null);
                        squareBelow.setCanMove(false);
                        if (squareBelow.getColor().equals(this.color)) {
                            buttons[currentRow + 1][currentColumn].setBackground(this.color);
                        }
                        String position = (currentRow + 1) + "," + currentColumn;
                        buttonsColors.put(position, squareBelow.getColor());

                    } else if (isTappedHole(currentRow + 1, currentColumn)) {
                        squareBelow.setCanMove(true);
                        squareBelow.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow + 1][currentColumn].setBackground(squareBelow.getColor());
                    } else {
                        squareBelow.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow + 1][currentColumn].setBackground(squareBelow.getColor());
                    }
                }
            }
        }
        holeee();
    }

    public void moveLeft(int currentRow, int currentColumn) {
        if (!this.canMove()) {
            return;
        }
        if (currentColumn > 0 && !hasPaintedBorder(buttons[currentRow][currentColumn]) && !buttons[currentRow][currentColumn].getBackground().equals(Color.WHITE) && !isNotWiner) {
            Square squareLeft = squares[currentRow][currentColumn - 1];
            if (squareLeft.getColor().equals(Color.WHITE) || hasOnlyBorderColored(squareLeft)) {
                if (hasDifferentBorderColor(buttons[currentRow][currentColumn - 1]) && !isHoleTapped(buttons[currentRow][currentColumn - 1])) {
                    JOptionPane.showMessageDialog(null, "Perdiste", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    isNotWiner = true;
                } else {
                    if (isHole(currentRow, currentColumn - 1)) {
                        squareLeft.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn - 1].setBackground(squareLeft.getColor());
                        buttons[currentRow][currentColumn - 1].setBorder(null);
                        squareLeft.setCanMove(false);
                        if (squareLeft.getColor().equals(this.color)) {
                            buttons[currentRow][currentColumn - 1].setBackground(this.color);
                        }
                        String position = currentRow + "," + (currentColumn - 1);
                        buttonsColors.put(position, squareLeft.getColor());

                    } else if (isTappedHole(currentRow, currentColumn - 1)) {
                        squareLeft.setCanMove(true);
                        squareLeft.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn - 1].setBackground(squareLeft.getColor());
                    } else {
                        squareLeft.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn - 1].setBackground(squareLeft.getColor());
                    }
                }
            }
        }
        holeee();
    }

    public void moveRight(int currentRow, int currentColumn) {
        if (!this.canMove()) {
            return;
        }
        if (currentColumn < buttons[currentRow].length - 1 && !hasPaintedBorder(buttons[currentRow][currentColumn]) && !buttons[currentRow][currentColumn].getBackground().equals(Color.WHITE) && !isNotWiner) {
            Square squareRight = squares[currentRow][currentColumn + 1];
            if (squareRight.getColor().equals(Color.WHITE) || hasOnlyBorderColored(squareRight)) {
                if (hasDifferentBorderColor(buttons[currentRow][currentColumn + 1]) && !isHoleTapped(buttons[currentRow][currentColumn + 1])) {
                    JOptionPane.showMessageDialog(null, "Perdiste", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    isNotWiner = true;
                } else {
                    if (isHole(currentRow, currentColumn + 1)) {
                        squareRight.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn + 1].setBackground(squareRight.getColor());
                        buttons[currentRow][currentColumn + 1].setBorder(null);
                        squareRight.setCanMove(false);
                        if (squareRight.getColor().equals(this.color)) {
                            buttons[currentRow][currentColumn + 1].setBackground(this.color);
                        }
                        String position = currentRow + "," + (currentColumn + 1);
                        buttonsColors.put(position, squareRight.getColor());

                    } else if (isTappedHole(currentRow, currentColumn + 1)) {
                        squareRight.setCanMove(true);
                        squareRight.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn + 1].setBackground(squareRight.getColor());
                    } else {
                        squareRight.setColor(this.color);
                        this.color = color.white;
                        buttons[currentRow][currentColumn].setBackground(this.color);
                        buttons[currentRow][currentColumn + 1].setBackground(squareRight.getColor());
                    }
                }
            }
        }
        holeee();
    }
}
