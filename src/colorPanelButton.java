import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class colorPanelButton extends JPanel implements MouseListener, MouseMotionListener {

    private String color;

    public colorPanelButton(String c) {
        color = c;
        //set background to each color
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //returns color
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //highlighting 'n stuff
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //highlighting 'n stuff
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
