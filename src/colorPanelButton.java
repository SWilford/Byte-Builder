import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class colorPanelButton extends JPanel implements MouseListener, MouseMotionListener {

    private String title;

    private Color color;
    private Toolbar containingToolbar;

    private Color baseColor;

    private colorPanel associatedColorPanel;

    private final Color highlightColor; //Colors of the button
    private boolean toolbarColored; //Has the button been colored with toolbarHighlightColor?
    private final Color toolbarHighlightColor = new Color(75, 75, 75); //Color for when tool is selected

    private final ImageIcon RedWire = new ImageIcon("Images/RedWire.png");
    private final ImageIcon GreenWire = new ImageIcon("Images/GreenWire.png");
    private final ImageIcon BlueWire = new ImageIcon("Images/BlueWire.png");
    private final ImageIcon OrangeWire = new ImageIcon("Images/OrangeWire.png");
    private final ImageIcon YellowWire = new ImageIcon("Images/YellowWire.png");
    private final ImageIcon WhiteWire = new ImageIcon("Images/WhiteWire.png");


    public colorPanelButton(String t, Toolbar to, colorPanel colp) {
        title = t;
        containingToolbar = to;
        associatedColorPanel = colp;
        baseColor = new Color(48, 48, 48);
        color = baseColor;
        highlightColor = new Color(90, 90, 90);
        this.setBackground(color);
        this.setLayout(new BorderLayout());

        switch (title) {
            case "red" -> {
                add(new JLabel(RedWire), BorderLayout.CENTER);
            }
            case "green" -> {
                add(new JLabel(GreenWire), BorderLayout.CENTER);
            }
            case "blue" -> {add(new JLabel(BlueWire), BorderLayout.CENTER);}
            case "orange" -> {add(new JLabel(OrangeWire), BorderLayout.CENTER);;}
            case "yellow" -> {add(new JLabel(YellowWire), BorderLayout.CENTER);}
            case "white" -> {add(new JLabel(WhiteWire), BorderLayout.CENTER);}
        }
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    public void toolbarHighlight() {
        baseColor = toolbarHighlightColor;
        color = baseColor;
        toolbarColored = true;
        this.setBackground(color);
        repaint();
    }

    public Color getColor() {
        switch (title) {
            case "red" -> {return Color.RED;}
            case "green" -> {return Color.GREEN;}
            case "blue" -> {return Color.BLUE;}
            case "orange" -> {return Color.ORANGE;}
            case "yellow" -> {return Color.YELLOW;}
            case "white" -> {return Color.WHITE;}
        }
        return null;
    }

    public void highlight() {
        color = highlightColor;
        updateHighlighting();
    }

    public void unHighlight() {
        color = baseColor;
        updateHighlighting();
    }

    public void resetToolbarColor() {
        baseColor = new Color(48, 48, 48);
        color = baseColor;
        toolbarColored = false;
        this.setBackground(color);
        repaint();
    }

    public void updateHighlighting() {
        if(containingToolbar.getAssociatedGrid().getAssociatedGUI().getCurrentWireColor().equals(title) && !isToolbarColored()) {
            toolbarHighlight();
        }
        else if(toolbarColored && !containingToolbar.getAssociatedGrid().getAssociatedGUI().getCurrentWireColor().equals(title)) {
            resetToolbarColor();
        }
        containingToolbar.getAssociatedGrid().repaint();
    }

    public boolean isToolbarColored() {
        return toolbarColored;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        containingToolbar.getAssociatedGrid().getAssociatedGUI().setCurrentWireColor(title);
        for(colorPanelButton c : associatedColorPanel.getThings()) {
            c.updateHighlighting();
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
        this.setBackground(color);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        unHighlight();
        this.setBackground(color);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
