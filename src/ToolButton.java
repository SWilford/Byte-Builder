import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class ToolButton extends JPanel implements MouseListener {
    private String title; //Title for the button
    private ImageIcon image; //Stores the base image
    private Color color;
    private Color baseColor;
    private final Color highlightColor; //Colors of the button
    private boolean toolbarColored; //Has the button been colored with toolbarHighlightColor?
    private final Color toolbarHighlightColor = new Color(75, 75, 75); //Color for when tool is selected

    private int hotkey;

    private Toolbar containingToolbar;

    public ToolButton(String t, ImageIcon i, Toolbar to, int k) {
        title = t;
        image = i;
        hotkey = k;
        baseColor = new Color(48, 48, 48);
        highlightColor = new Color(90, 90, 90);
        color = baseColor;
        this.setPreferredSize(new Dimension(120, 120));
        this.setMinimumSize(new Dimension(120, 120));
        this.setMaximumSize(new Dimension(120, 120));
        containingToolbar = to;
        this.addMouseListener(this);
        setToolTipText(t);
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
            g.setColor(Color.cyan);
            g.drawString((char) getHotkey() + "", 0, 10);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            pseudoMouseClicked(title);
        }
        containingToolbar.sting(this.getGraphics());
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setImage(ImageIcon imageIcon) {
        image = imageIcon;
    }

    public void pseudoMouseClicked(String t) {
        if(t.equals("Import")) {
            //System.out.println("Choose file to import as a custom block");
            FileExplorer fileExplorer = new FileExplorer();
            File f = fileExplorer.selectFile(true);
            if(f.getName().isEmpty()) {
                return;
            }
            String fName = f.getName().substring(0, f.getName().length()-4);
            ImageIcon fImage = new ImageIcon("");
            try {
                fImage = new ImageIcon(Manager.getImage(f.getPath()).substring(1));
                //System.out.println(Manager.getImage(f.getPath()));

            } catch (Exception ignored) {
            }
            containingToolbar.getButtons().add(containingToolbar.getButtons().indexOf(this), new ToolButton(fName, fImage, containingToolbar, Toolbar.newHotkey()));
            containingToolbar.reDraw();
        }
        else {
            containingToolbar.toolButtonHelper(t);
            if (containingToolbar.getToolHeld().isEmpty() || t == null) { //unselect tool
                //System.out.println("Tool Unselected");
                color = baseColor;
            } else { //select tool
                //System.out.println("Tool Selected: " + containingToolbar.getToolHeld());
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
    }

    public int getHotkey(){
        return hotkey;
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
