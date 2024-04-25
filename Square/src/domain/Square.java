package Square.src.domain;

import javax.swing.*;
import java.awt.*;

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

    public Square(Color color, Color borderColor) {
        this.color = color;
        this.borderColor = borderColor;
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

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }
}
