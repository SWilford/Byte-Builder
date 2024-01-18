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

    public Toolbar() {
        setLayout(new GridLayout(0, 2, 0, 0));
        this.setBackground(new Color(48, 48, 48));
        this.add(new ToolButton("Wire", wireToolImg));
        this.add(new ToolButton("And", andImage));
        this.add(new ToolButton("Not", notImage));
        this.add(new ToolButton("Trash", trashImage));
        this.add(new ToolButton("On", onImage));
        this.add(new ToolButton("Light", lightOn));
        this.add(new ToolButton("Switch", switchOff));
        this.add(new ToolButton("In", inImage));
        this.add(new ToolButton("Out", outImage));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
        this.add(new ToolButton("null", null));
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
}
