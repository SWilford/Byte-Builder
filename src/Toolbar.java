import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Toolbar extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final ImageIcon wireToolImg = new ImageIcon("Images/copwire.png");
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");
    private final ImageIcon trashImage = new ImageIcon("Images/Trash.png");
    private final ImageIcon onImage = new ImageIcon("Images/On.png");
    private final ImageIcon lightOn = new ImageIcon("Images/LightOn.png");
    private final ImageIcon switchOff = new ImageIcon("Images/LeverOff.png");
    private final ImageIcon inImage = new ImageIcon("Images/Input.png");
    private final ImageIcon outImage = new ImageIcon("Images/Output.png");

    private String toolHeld;
    private boolean toolSelected;

    private Grid associatedGrid;

    public Toolbar(Grid grid) {
        setLayout(new GridLayout(0, 2, 0, 0));
        this.setBackground(new Color(48, 48, 48));
        this.add(new ToolButton("Wire", wireToolImg, this));
        this.add(new ToolButton("And", andImage, this));
        this.add(new ToolButton("Not", notImage, this));
        this.add(new ToolButton("Trash", trashImage, this));
        this.add(new ToolButton("On", onImage, this));
        this.add(new ToolButton("Light", lightOn, this));
        this.add(new ToolButton("Switch", switchOff, this));
        this.add(new ToolButton("In", inImage, this));
        this.add(new ToolButton("Out", outImage, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));
        this.add(new ToolButton("", null, this));

        toolSelected = false;
        associatedGrid = grid;
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
        if(!toolSelected) { //When no tool is selected...
            toolSelected = true; //Now there is a tool selected
            toolHeld = s; //Sets which tool is set
        }
        else if(toolHeld.equals(s)) { //If the current tool is click then no tool is selected
            toolSelected = false;
            toolHeld = "";
        }
        else {
            toolHeld = s; //Switching from one tool to another
        }
        if(!s.equals("wire") || toolHeld.isEmpty()) {
            associatedGrid.setFirstInput(null);
        }
    }



}
