import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolButton extends JPanel implements MouseListener {
    private final String title; //Title for the button
    private final ImageIcon image; //Stores the base image
    private Color color;
    private Color baseColor;
    private final Color highlightColor; //Colors of the button
    private boolean toolbarColored; //Has the button been colored with toolbarHighlightColor?
    private final Color toolbarHighlightColor = new Color(75, 75, 75); //Color for when tool is selected

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

    public void toolbarHighlight() {
        baseColor = toolbarHighlightColor;
        color = baseColor;
        toolbarColored = true;
        repaint();
    }
    public void resetToolbarColor() {
        baseColor = new Color(48, 48, 48);
        color = baseColor;
        toolbarColored = false;
        repaint();
    }

    public boolean isToolbarColored() {
        return toolbarColored;
    }

    public void highlight() {
        if(!title.equals("")) {
        color = highlightColor;
        repaint();
        }
    }

    public void unHighlight() {
        if(!title.equals("")) {
            color = baseColor;
            repaint();
        }
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
        if(e.getButton() == MouseEvent.BUTTON1) {
            pseudoMouseClicked(title);
        }
        containingToolbar.sting(this.getGraphics());
    }

    public void pseudoMouseClicked(String t) {
        containingToolbar.toolButtonHelper(t);
        if (containingToolbar.getToolHeld().isEmpty() || t == null) {
            System.out.println("Tool Unselected");
            color = baseColor;
        } else {
            System.out.println("Tool Selected: " + containingToolbar.getToolHeld());
            color = toolbarHighlightColor;
        }

        for (ToolButton b : containingToolbar.getButtons()) {
            if (!b.title.isEmpty()) {
                if (containingToolbar.getToolHeld().equals(b.getTitle()) && !b.isToolbarColored()) { //when a tool is selected primary color becomes the selection color
                    b.toolbarHighlight();
                } else if (b.isToolbarColored() && !containingToolbar.getToolHeld().equals(b.getTitle())) { //when a tool is not selected the primary color becomes base color
                    b.resetToolbarColor();
                }
            }
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
        containingToolbar.sting(this.getGraphics());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        unHighlight();
        containingToolbar.sting(this.getGraphics());
    }
}
