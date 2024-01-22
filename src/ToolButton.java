import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolButton extends JPanel implements MouseListener {
    private final String title; //Title for the button
    private final ImageIcon image; //Stores the base image
    private Color color;
    private final Color baseColor;
    private final Color highlightColor; //Colors of the button

    private Toolbar containingToolbar;

    public ToolButton(String t, ImageIcon i, Toolbar to) {
        title = t;
        image = i;
        baseColor = new Color(48, 48, 48);
        highlightColor = new Color(90, 90, 90);
        color = baseColor;
        this.setPreferredSize(new Dimension(120, 120));
        this.setMinimumSize(new Dimension(120, 120));
        this.setMaximumSize(new Dimension(120, 120));
        containingToolbar = to;
        this.addMouseListener(this);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        containingToolbar.toolButtonHelper(title);
        if(containingToolbar.getToolHeld().isEmpty()) {
            System.out.println("Tool Unselected");
        }
        else {
            System.out.println("Tool Selected: "+containingToolbar.getToolHeld());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        highlight();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        unHighlight();
    }
}
