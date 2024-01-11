import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Grid extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final SparseMatrix cells = new SparseMatrix(49, 49);
    public ArrayList<Wire> wires = new ArrayList<>(); // stores wires to be drawn
    private boolean mouseOverWire;
    public String currentWireColor;
    private int wireToDelete;
    private int selectedWire;
    private boolean wireIsSelected;
    public ArrayList<Button> wireColors = new ArrayList<>();
    public Wire wireToCursor; //The wire being generated when wire tool is selected
    private int mouseX; //X position of cursor
    private int mouseY; //Y position of cursor
    private static Point firstInput; //variable for first component clicked for 2, used for making wiring

    private int cellWidth = 50;

    private double x, y, scale;

    //Image Icons
    private final ImageIcon gridSquareImg = new ImageIcon("Images/gridSquare.png");
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");
    private final ImageIcon trashImage = new ImageIcon("Images/Trash.png");
    private final ImageIcon onImage = new ImageIcon("Images/On.png");
    private final ImageIcon lightOn = new ImageIcon("Images/LightOn.png");
    private final ImageIcon lightOff = new ImageIcon("Images/LightOff.png");
    private final ImageIcon switchOn = new ImageIcon("Images/LeverOn.png");
    private final ImageIcon switchOff = new ImageIcon("Images/LeverOff.png");
    private final ImageIcon inImage = new ImageIcon("Images/Input.png");
    private final ImageIcon outImage = new ImageIcon("Images/Output.png");

    public Grid() {

        setLayout(new GridLayout(50, 50));

        addMouseListener(this); //Listeners
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        mouseX = 0; //Initializes mouse position to 0, 0
        mouseY = 0;
        firstInput = null;
        wireToCursor = null;
        currentWireColor = "red";
        selectedWire = -1;
        wireIsSelected = false;
        mouseOverWire = false;
        wireToDelete = -1;
        x = 0;
        y = 0;
        scale = 1;
        mouseX = -1;
        mouseY = -1;

        this.setBackground(new Color(48,48, 48));

    }

    public void display(Graphics g) {
        //Find box in top left, find box in bottom right
        //draw everything between
        //COMBINE EACH COMPONENT AND BUTTON

        int LeftX = (int)x/cellWidth;
        int LeftY = (int)y/cellWidth;

        int myWidth = this.getWidth();
        int myHeight = this.getHeight();

        int RightX = (int)Math.ceil(toXCoord(myWidth)/cellWidth);
        int RightY = (int)Math.ceil(toYCoord(myHeight)/cellWidth);

        g.setColor(new Color(48, 48, 48));

        for(int i = LeftX; i <= RightX; i++) {
            for(int j = LeftY; j <= RightY; j++) {
                g.fillRect(toXPosOnWindow((i-1)*cellWidth), toYPosOnWindow((j-1)*cellWidth), toXPosOnWindow(x+cellWidth), toXPosOnWindow(x+cellWidth));
            }
        }

        g.setColor(new Color(90, 90, 90));

        if(mouseX != -1) {
            g.fillRect(toXPosOnWindow((mouseX)*cellWidth), toYPosOnWindow((mouseY)*cellWidth), toXPosOnWindow(x+cellWidth), toXPosOnWindow(x+cellWidth));
        }

        g.setColor(Color.BLACK);

        for(int i = LeftX; i <= RightX; i++) {
            g.drawLine(toXPosOnWindow(i*cellWidth), 0, toXPosOnWindow(i*cellWidth), myHeight);
        }

        for(int i = LeftY; i <= RightY; i++) {
            g.drawLine(0, toYPosOnWindow(i*cellWidth), myWidth, toYPosOnWindow(i*cellWidth));
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        display(g);
    }


    private void zoom(double scaleFactor, double x, double y) { // Thanks to BH for the code!!!
        if ((scaleFactor < 1 && scale <= 0.0625)) {
            return;
        }
        if ((scaleFactor > 1 && scale >= 3)) {
            return;
        }
        if (scaleFactor < 1) {
            this.x += scale * x * (1 - scaleFactor);
            this.y += scale * y * (1 - scaleFactor);
        } else {
            this.x += scale * x * (1 - scaleFactor);
            this.y += scale * y * (1 - scaleFactor);
        }
        scale *= scaleFactor;
        repaint();
    }

    private double toXCoord(double x) { //Thanks to BH for the code!!!
        return x * scale + this.x;
    }

    private int toXPosOnWindow(double x) {//Thanks to BH for the code!!!
        return (int) Math.round((x - this.x) / scale);
    }

    private double toYCoord(double y) {//Thanks to BH for the code!!!
        return y * scale + this.y;
    }

    private int toYPosOnWindow(double y) {//Thanks to BH for the code!!!
        return (int) Math.round((y - this.y) / scale);
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
        mouseX = -1;
        mouseY = -1;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(toXCoord(e.getX()));
        mouseX = (int)Math.floor(toXCoord(e.getX())/cellWidth);
        mouseY = (int)Math.floor(toYCoord(e.getY())/cellWidth);



        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { // Thanks to BH for the code!!
        if (e.getPreciseWheelRotation() == 0) {
            return;
        }
        double amount = e.getPreciseWheelRotation();
        double scaleFactor = Math.max(1, Math.abs(amount * 60));
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            scaleFactor = Math.pow(scaleFactor, 0.04);
        }
        scaleFactor = Math.pow(scaleFactor, Math.copySign(1, amount));
        zoom(scaleFactor, e.getX(), e.getY());
        System.out.println(scale);
    }
}
