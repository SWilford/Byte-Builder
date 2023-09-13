import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class GUI extends JPanel implements MouseListener, MouseMotionListener {

    private final ArrayList<Button> buttons = new ArrayList<>();
    //private final [][] operators = new [75][75];

    private static int mouseX;
    private static int mouseY;

    // Image Icons

    private final ImageIcon gridSquareImg = new ImageIcon("Images/gridSquare.png");
    private final ImageIcon gridSquareImg2 = new ImageIcon("Images/gridSquare2.png");

    //End of Image Icons

    public GUI() {
        this.setPreferredSize(new Dimension(1440, 1200));
        addMouseListener(this);
        addMouseMotionListener(this);
        mouseX = 0;
        mouseY = 0;
        createGridButtons();
    }

    public void display(Graphics g) {
        for(Button b:buttons) {
            b.drawButton(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        display(g);
    }

    private void createGridButtons() {
        for(int x = 240; x < 1440; x+=48) {
            for(int y = 0; y < 1200; y+=48) {
                Shape gridShape = new Rectangle(x, y, 48, 48);
                Button button = new Button(gridShape, "gridSquare", gridSquareImg, gridSquareImg2);
                buttons.add(button);
            }
        }
    }


    public void mouseClicked(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        for(Button b: buttons) {
            if(b.getShape().contains(mouseX, mouseY)) {
                b.highlight();
            }
            else {
                b.unHighlight();
            }
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

}
