import javax.swing.*;
import java.awt.*;

public class Button {
    private final Shape shape; //The bounding box for the button
    private final String title; //Title for the button
    private final Color color, textColor; //base color for image, base text color for image
    private ImageIcon image, regularImage, highlightImage; //image is the base image, rI is the base image, hI is the image when button is hovered over


    public Button(Shape s, Color c, Color tc, String t, ImageIcon ri, ImageIcon hi) {
        shape = s;
        color = c;
        textColor = tc;
        title = t;
        regularImage = ri;
        highlightImage = hi;
        image = regularImage;
    }

    public Button(Shape s, String t, ImageIcon ri, ImageIcon hi) {
        shape = s;
        title = t;
        color = null;
        textColor = null;
        regularImage = ri;
        highlightImage = hi;
        image = regularImage;
    }

    public String getTitle() {
        return title;
    }

    public Shape getShape() {
        return shape;
    }

    public void highlight() {
        image = highlightImage;
    }

    public void unHighlight() {
        image = regularImage;
    }

    private Color getTextColor() {
        return textColor;
    }

    private Color getColor() {
        return color;
    }

    public void drawButton(Graphics g) {
        int x = (int)(this.getShape().getBounds().getX());
        int y = (int)(this.getShape().getBounds().getY());
        int width = (int)(this.getShape().getBounds().getWidth());
        int height = (int)(this.getShape().getBounds().getHeight());
        g.setColor(this.getColor());
        if(image != null) {
            g.drawImage(image.getImage(), x, y, width, height, null);
        }
        else if(this.getShape() instanceof Rectangle) {
            g.fillRect(x, y, width, height);
        }
        else {
            g.fillOval(x, y, width, height);
        }
        if(image == null) {
            g.setColor(this.getTextColor());
            g.drawString(this.getTitle(), x, y+(height/2));
        }
    }

    public void setRegularImage(ImageIcon img) {
        regularImage = img;
    }

    public void setHighlightImage(ImageIcon img) {
        highlightImage = img;
    }




}
