import javax.swing.*;
import java.awt.*;

public class ToolButton extends JPanel {
    private final String title; //Title for the button
    private final ImageIcon image; //Stores the base image
    private Color color;
    private final Color baseColor;
    private final Color highlightColor; //Colors of the button

    public ToolButton(String t, ImageIcon i) {
        title = t;
        image = i;
        baseColor = new Color(48, 48, 48);
        highlightColor = new Color(90, 90, 90);
        color = baseColor;
        this.setPreferredSize(new Dimension(120, 120));
        this.setMinimumSize(new Dimension(120, 120));
        this.setMaximumSize(new Dimension(120, 120));
    }

    public void highlight() {
        color = highlightColor;
        repaint();
    }

    public void unHighlight() {
        color = baseColor;
        repaint();
    }

    public String getTitle() {
        return title;
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setBackground(color);
        super.paintComponent(g);
        if(image!=null) {
            g.drawImage(image.getImage(), 0,0, super.getWidth(), this.getHeight(), null);
        }
    }
}
