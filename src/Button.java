import javax.swing.*;
import java.awt.*;
//
public class Button {
    private final Shape shape; //The bounding box for the button
    private final String title; //Title for the button
    private ImageIcon image; //Stores the base image
    private Color color, baseColor, highlightColor;

    private Color toolbarHighlightColor = new Color(75, 75, 75);

    private int row, col; //Stores row and col of button in the 2d array of components


    public Button(Shape s, String t, ImageIcon i) {
        shape = s;
        title = t;
        image = i;
        baseColor = new Color(48, 48, 48);
        highlightColor = new Color(90, 90, 90);
        color = baseColor;
    }

    public Button(Shape s, String t, ImageIcon i, int r, int c) {
        shape = s;
        title = t;
        image = i;
        row = r;
        col = c;
        baseColor = new Color(48, 48, 48);
        highlightColor = new Color(90, 90, 90);
        color = baseColor;
    }

    public int getRow() {return row;}

    public int getCol() { return col;}

    public String getTitle() {
        return title;
    }

    public Shape getShape() {
        return shape;
    }

    public void highlight() {
        color = highlightColor;
    }

    public void unHighlight() {
        color = baseColor;
    }

    public Color getColor() {
        return color;
    }

    public void drawButton(Graphics g) {
        int x = (int)(this.getShape().getBounds().getX());
        int y = (int)(this.getShape().getBounds().getY());
        int width = (int)(this.getShape().getBounds().getWidth());
        int height = (int)(this.getShape().getBounds().getHeight());
        g.setColor(this.getColor());
        if(image != null) {
            g.fillRect(x, y, width, height);
            g.drawImage(image.getImage(), x, y, width, height, null);
        }
        else if(this.getShape() instanceof Rectangle) {
            g.fillRect(x, y, width, height);
        }
        else {
            g.fillOval(x, y, width, height);
        }
        if(image == null) {
            g.drawString(this.getTitle(), x, y+(height/2));
        }
    }

    public void setRegularImage(ImageIcon img) {
        image = img;
    }

}
