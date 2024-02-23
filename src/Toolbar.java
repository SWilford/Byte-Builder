import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Toolbar extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{

    public final JWindow colorWindow;
    private final colorPanel colorpanel = new colorPanel(this);

    private final ImageIcon wireToolImg = new ImageIcon("Images/copwire.png");
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");
    private final ImageIcon trashImage = new ImageIcon("Images/Trash.png");
    private final ImageIcon onImage = new ImageIcon("Images/On.png");
    private final ImageIcon lightOn = new ImageIcon("Images/LightOn.png");
    private final ImageIcon switchOff = new ImageIcon("Images/LeverOff.png");
    private final ImageIcon inImage = new ImageIcon("Images/Input.png");
    private final ImageIcon outImage = new ImageIcon("Images/Output.png");

    private final ImageIcon plusImage = new ImageIcon("Images/Plus.png");

    private String toolHeld;
    private boolean toolSelected;

    private Grid associatedGrid;

    private ArrayList<ToolButton> buttons = new ArrayList<>();

    private String currentWireColor;

    public Toolbar(Grid grid) {

        buttons.add(new ToolButton("Wire", wireToolImg, this));
        buttons.add(new ToolButton("Trash", trashImage, this));
        buttons.add(new ToolButton("Not", notImage, this));
        buttons.add(new ToolButton("And", andImage, this));
        buttons.add(new ToolButton("On", onImage, this));
        buttons.add(new ToolButton("Light", lightOn, this));
        buttons.add(new ToolButton("Switch", switchOff, this));
        buttons.add(new ToolButton("In", inImage, this));
        buttons.add(new ToolButton("Out", outImage, this));
        buttons.add(new ToolButton("Import", plusImage, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));
        buttons.add(new ToolButton("", null, this));

        setLayout(new GridLayout(0, 2, 0, 0));
        this.setBackground(new Color(48, 48, 48));

        for(ToolButton t : buttons) {
            this.add(t);
        }

        toolSelected = false;
        associatedGrid = grid;
        toolHeld = "";

        currentWireColor = grid.getAssociatedGUI().getCurrentWireColor();

        colorWindow = new JWindow();
        colorWindow.setPreferredSize(new Dimension(238, 160));
        colorWindow.add(colorpanel);
        colorWindow.pack();
        if(colorWindow.isVisible()) {
            colorWindow.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y+associatedGrid.getHeight()-colorWindow.getHeight());
        }


    }

    public void reDraw() {
        this.removeAll();
        for(ToolButton t: buttons) {
            this.add(t);
        }

        revalidate();
        repaint();
    }



    public void updateColorWindowPosition() {
        if(colorWindow.isVisible()) {
            colorWindow.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y+associatedGrid.getHeight()-colorWindow.getHeight());
        }
        colorWindow.repaint();
    }

    public void sting(Graphics g) {
        boolean case1 = associatedGrid.getAssociatedGUI().getToolHeld() != null && (associatedGrid.getAssociatedGUI().getToolHeld().equals("Wire") || associatedGrid.isWireIsSelected());
        colorWindow.setVisible(case1);
        updateColorWindowPosition();
    }

    public Grid getAssociatedGrid() {
        return associatedGrid;
    }

    public ArrayList<ToolButton> getButtons() {
        return buttons;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public String getToolHeld() {
        return toolHeld;
    }

    public void toolButtonHelper(String s) {
        if (!toolSelected) { //When no tool is selected...
            toolSelected = true; //Now there is a tool selected
            toolHeld = s; //Sets which tool is set
        } else if (toolHeld.equals(s)) { //If the current tool is click then no tool is selected
            toolSelected = false;
            toolHeld = "";
        } else {
            toolHeld = s; //Switching from one tool to another
        }
        if (!s.equals("wire") || toolHeld.isEmpty()) {
            associatedGrid.setFirstInput(null);
        }

    }

    public void processUserInput(int k) { //k is key input from kb

        switch (k) {
            case KeyEvent.VK_ESCAPE -> { //deselect wire
                if (associatedGrid.getFirstInput() != null) { //fix
                    associatedGrid.setFirstInput(null);
                } else if (toolSelected) {
                    buttons.get(18).pseudoMouseClicked("");
                }
            }
            case KeyEvent.VK_1 -> buttons.get(0).pseudoMouseClicked("Wire");
            case KeyEvent.VK_2 -> buttons.get(1).pseudoMouseClicked("Trash");
            case KeyEvent.VK_3 -> buttons.get(2).pseudoMouseClicked("Not");
            case KeyEvent.VK_4 -> buttons.get(3).pseudoMouseClicked("And");
            case KeyEvent.VK_5 -> buttons.get(4).pseudoMouseClicked("On");
            case KeyEvent.VK_6 -> buttons.get(5).pseudoMouseClicked("Light");
            case KeyEvent.VK_7 -> buttons.get(6).pseudoMouseClicked("Switch");
            case KeyEvent.VK_8 -> buttons.get(7).pseudoMouseClicked("In");
            case KeyEvent.VK_9 -> buttons.get(8).pseudoMouseClicked("Out");


        }
        sting(this.getGraphics());
    }
}
