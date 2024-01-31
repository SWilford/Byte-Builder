import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Grid extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private BuilderGUI associatedGUI;
    private final SparseMatrix<Operator> cells = new SparseMatrix<>();
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

    private int cellWidth = 50; //width of imaginary coordinates

    private double x, y, scale;

    private boolean middleClicking;

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

    public BuilderGUI getAssociatedGUI() {
        return associatedGUI;
    }

    public Grid(BuilderGUI gui) {

        setLayout(new GridLayout(50, 50));

        addMouseListener(this); //Listeners
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        mouseX = 0; //Initializes mouse position to 0, 0
        mouseY = 0;
        firstInput = null;
        wireToCursor = null;
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
        associatedGUI = gui;
        middleClicking = false;

        currentWireColor = associatedGUI.getCurrentWireColor();
    }

    public void setFirstInput(Point p) {
        firstInput = p;
    }

    public void display(Graphics g) {

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

        for(Operator o: cells) {
            if((o.col >= LeftX && o.row >= LeftY) && (o.col <= RightX && o.row <= RightY)) {
                if(o instanceof NotBlock) {
                    g.drawImage(notImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof AndBlock) {
                    g.drawImage(andImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof OnBlock) {
                    g.drawImage(onImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Light) {
                    g.drawImage(lightOff.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Switch) {
                    g.drawImage(switchOff.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Input) {
                    g.drawImage(inImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Output) {
                    g.drawImage(outImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
            }
        }

        for(Wire w : wires) {
            w.drawWire(g, this, cells);
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
    } //Mouse coord into sparse matrix coord

    public int toXPosOnWindow(double x) {//Thanks to BH for the code!!!
        return (int) Math.round((x - this.x) / scale);
    } //Sparse matrix coord to mouse coord

    private double toYCoord(double y) {//Thanks to BH for the code!!!
        return y * scale + this.y;
    } //Mouse coord into sparse matrix coord

    public int toYPosOnWindow(double y) {//Thanks to BH for the code!!!
        return (int) Math.round((y - this.y) / scale);
    }  //Sparse matrix coord to mouse coord

    @Override
    public void mouseClicked(MouseEvent e) {

        int col = (int)Math.floor(toXCoord(e.getX())/cellWidth);
        int row = (int)Math.floor(toYCoord(e.getY())/cellWidth);

        switch (associatedGUI.getToolHeld()) {
            case "" -> {
                if(cells.get(col, row) instanceof Switch) {
                    ((Switch)(cells.get(col, row))).switchInput();
                }
            }
            case "Wire" -> {
                if(cells.get(col, row) != null) {
                    if(firstInput == null) {
                        firstInput = new Point(col, row);
                    }
                    else {
                        if(cells.get(col, row) instanceof OnBlock || cells.get(col, row) instanceof Switch || cells.get(col, row).isFull()) { //do nothing since start
                            break;
                        }
                        if(cells.get(col, row) instanceof Custom) {
                            Operator temp = ((Custom) (cells.get(col, row))).getFirstEmpty();
                            if(temp != null) {
                                temp.setPrev1(cells.get((int)firstInput.getX(), (int)firstInput.getY()));
                            }
                            else {
                                break;
                            }
                        }
                        else if (cells.get(col, row) instanceof Operator2I && cells.get(col, row).getPrev1() != null) { //if it has two inputs and the first is taken
                            ((Operator2I)cells.get(col, row)).setPrev2(cells.get((int)firstInput.getX(), (int)(firstInput.getY()))); //Go to the second slot
                            ((Operator2I)cells.get(col, row)).setColor2(currentWireColor);
                        }
                        else { //first slot
                            cells.get(col, row).setPrev1(cells.get((int)firstInput.getX(), (int)(firstInput.getY()))); //set 2nd operator's previous to 1st operator
                            cells.get(col, row).setColor(currentWireColor);
                        }
                        createWire((int)firstInput.getX(), (int)firstInput.getY(), col, row);
                        firstInput = null;
                    }
                }
            }
            case "Not" -> {
                Operator notBlock = new NotBlock(row, col, null); //(row, col) = (y, x)  <---- !!!
                cells.set(col, row, notBlock);
            }
            case "And" -> {
                Operator andBlock = new AndBlock(row, col,null, null);
                cells.set(col, row, andBlock);
            }
            case "Trash" -> {
                deletePointersTo(cells.get(row, col));
                cells.set(col, row, null);
            }
            case "On" -> {
                Operator onBlock = new OnBlock(row, col);
                cells.set(col, row, onBlock);
            }
            case "Light" -> {
                Operator lt = new Light(row, col,null);
                cells.set(col, row, lt);
            }
            case "Switch" -> {
                Operator swi = new Switch(row, col);
                cells.set(col, row, swi);
            }
            case "In" -> {
                Operator inBlock = new Input(row, col);
                cells.set(col, row, inBlock);
            }
            case "Out" -> {
                Operator outBlock = new Output(row, col, null);
                cells.set(col, row, outBlock);
            }
        }

        repaint();
    }

    private void deletePointersTo(Operator n) {

    }

    private void createWire(int c1, int r1, int c2, int r2) {
        wires.add(new Wire(c1, r1, c2, r2, currentWireColor));
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public SparseMatrix<Operator> getCells() {
        return cells;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleClicking = true;
            mouseY = e.getY();
            mouseX = e.getX();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleClicking = false;
        }
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
        if(middleClicking) {
            x += scale * (mouseX - e.getX());
            mouseX = e.getX();
            y += scale * (mouseY - e.getY());
            mouseY = e.getY();
            repaint();
        }
    }

    public int getEks() {
        return (int)x;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseX = (int)Math.floor(toXCoord(e.getX())/cellWidth);
        mouseY = (int)Math.floor(toYCoord(e.getY())/cellWidth);

        System.out.println("X: "+mouseX+ " Y: "+mouseY);


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
