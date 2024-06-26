import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BuilderGUI extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener{

    Toolbar toolbar;
    Grid grid;
    String currentWireColor;

    public BuilderGUI() {
        setLayout(new BorderLayout(0, 0));
        currentWireColor = "red";
        grid = new Grid(this);
        toolbar = new Toolbar(grid);
        this.add(grid, BorderLayout.CENTER);
        JScrollPane blank = new JScrollPane(toolbar, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        blank.setBorder(null);
        blank.getVerticalScrollBar().setUnitIncrement(12);

        this.add(blank, BorderLayout.WEST);
    }

    public String getToolHeld() {
        return toolbar.getToolHeld();
    }

    public String getCurrentWireColor() {
        return currentWireColor;
    }

    public void setCurrentWireColor(String s) {
        currentWireColor = s;
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

    @Override
    public void componentResized(ComponentEvent e) {
        toolbar.updateColorWindowPosition();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        toolbar.updateColorWindowPosition();
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
