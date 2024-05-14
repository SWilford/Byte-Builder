import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Grid extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private BuilderGUI associatedGUI;
    private SparseMatrix<Operator> cells = new SparseMatrix<>();

    private Stack<changeMessage> toUndo = new Stack<>();
    private Stack<changeMessage> toRedo = new Stack<>();

    public ArrayList<Wire> wires = new ArrayList<>(); // stores wires to be drawn
    private boolean mouseOverWire;
    public String currentWireColor;
    private int wireToDelete;
    private int selectedWire;
    private boolean wireIsSelected;

    public Wire wireToCursor; //The wire being generated when wire tool is selected
    private int mouseX; //X position of cursor COLUMN
    private int mouseY; //Y position of cursor ROW

    private int cursorX; //X position of cursor in pixel space
    private int cursorY; //Y position of cursor in pixel space
    private static Point firstInput; //variable for first component clicked for 2, used for making wiring

    private int cellWidth = 50; //width of imaginary coordinates

    private double x, y, scale;

    private boolean middleClicking;


    //Image Icons
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");
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
    public Point getFirstInput() {
        return firstInput;
    }

    public Stack<wireMessage> buffer = new Stack<>();

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
            if(o != null && (o.col >= LeftX && o.row >= LeftY) && (o.col <= RightX && o.row <= RightY)) {
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
                    if(o.getPrev1() != null && o.getOutput()) {
                        g.drawImage(lightOn.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                    }
                    else {
                        g.drawImage(lightOff.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                    }
                }
                if(o instanceof Switch) {
                    if(o.getOutput()) {
                        g.drawImage(switchOn.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                    }
                    else {
                        g.drawImage(switchOff.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                    }
                }
                if(o instanceof Input) {
                    g.drawImage(inImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Output) {
                    g.drawImage(outImage.getImage(), toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                }
                if(o instanceof Custom){
                    try {
                        g.drawImage((new ImageIcon(Manager.getImage("Saves/" + ((Custom) o).getName() + ".txt").substring(1))).getImage(),
                                toXPosOnWindow(o.getCol() * cellWidth), toYPosOnWindow(o.getRow() * cellWidth), toXPosOnWindow(cellWidth + x), toYPosOnWindow(cellWidth + y), null);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        ArrayList<Wire> wiresDelete = new ArrayList<>();

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        currentWireColor = associatedGUI.getCurrentWireColor();


        for(Wire w : wires) {
            if(w.isSelected()) {
                w.setColor(currentWireColor);
            }

            if(cells.get(w.getX1(), w.getY1()) == null || cells.get(w.getX2(), w.getY2()) == null) {
                wiresDelete.add(w);
                wireMessage message = new wireMessage(w);
                buffer.push(message);
            }
            else {
                w.drawWire(g2, this, cells);
                drawTriangle(g2, w.getxOne(), w.getyOne(), w.getxTwo(), w.getyTwo());
            }
        }
        for(Wire w : wiresDelete) {
            wires.remove(w);
        }

        g2.setColor(stringToColor(associatedGUI.getCurrentWireColor()));

        if(wireToCursor != null) {
            wireToCursor.drawWire(g, this);
            drawTriangle(g2, wireToCursor.getX1(), wireToCursor.getY1(), wireToCursor.getX2(), wireToCursor.getY2());
        }

        associatedGUI.toolbar.sting(g2);

    }

    private Color stringToColor(String s) {
        switch (s) {
            case "red" -> {return Color.RED;}
            case "green" -> {return Color.GREEN;}
            case "blue" -> {return Color.BLUE;}
            case "orange" -> {return Color.ORANGE;}
            case "yellow" -> {return Color.YELLOW;}
            case "white" -> {return Color.WHITE;}
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        display(g);
    }

    public boolean isWireIsSelected() {
        return wireIsSelected;
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
        if(!mouseOverWire) {
            if(!associatedGUI.getToolHeld().equals("Wire")) {
                cellMessage cm = new cellMessage(cells.get(col, row), new Point(col, row));
                toUndo.push(cm);
            }
            Operator test = cells.get(0, 0);
            switch (associatedGUI.getToolHeld()) {
                case "" -> {
                    if (cells.get(col, row) instanceof Switch) {
                        ((Switch) (cells.get(col, row))).switchInput();
                        toUndo.pop(); //makes it so flicking the switch doesn't generate states of the grid that need to be undone/redone
                    }
                    break;
                }
                case "Wire" -> {
                    if (cells.get(col, row) != null) {
                        if (firstInput == null) {
                            firstInput = new Point(col, row);
                        } else {
                            if (cells.get(col, row) instanceof OnBlock || cells.get(col, row) instanceof Switch || cells.get(col, row).isFull() || (firstInput.getX() == col && firstInput.getY() == row)) { //do nothing since start
                                break;
                            }

                            if (cells.get(col, row) instanceof Custom) {
                                Operator temp = ((Custom) (cells.get(col, row))).getFirstEmpty();
                                if (temp != null) {
                                    temp.setPrev1(cells.get((int) firstInput.getX(), (int) firstInput.getY()));
                                } else {
                                    break;
                                }
                            } else if (cells.get(col, row) instanceof Operator2I && cells.get(col, row).getPrev1() != null) { //if it has two inputs and the first is taken
                                ((Operator2I) cells.get(col, row)).setPrev2(cells.get((int) firstInput.getX(), (int) (firstInput.getY()))); //Go to the second slot
                                ((Operator2I) cells.get(col, row)).setColor2(currentWireColor);
                            } else { //first slot
                                cells.get(col, row).setPrev1(cells.get((int) firstInput.getX(), (int) (firstInput.getY()))); //set 2nd operator's previous to 1st operator
                                cells.get(col, row).setColor(currentWireColor);
                            }
                            createWire((int) firstInput.getX(), (int) firstInput.getY(), col, row);
                            firstInput = null;
                            updateWireToCursor();
                        }
                    }
                    break;
                }
                case "Not" -> {
                    Operator notBlock = new NotBlock(row, col, null); //(row, col) = (y, x)  <---- !!!!!!!!!!
                    cells.set(col, row, notBlock);
                    break;
                }
                case "And" -> {
                    Operator andBlock = new AndBlock(row, col, null, null);
                    cells.set(col, row, andBlock);
                    break;
                }
                case "Trash" -> {
                    if (cells.get(col, row) != null) {
                        cellMessage message = new cellMessage(cells.get(col, row), new Point(col, row));
                        toUndo.push(message);
                        deletePointersTo(cells.get(col, row));
                        cells.set(col, row, null);
                    }
                    break;
                }
                case "On" -> {
                    Operator onBlock = new OnBlock(row, col);
                    cells.set(col, row, onBlock);
                    break;
                }
                case "Light" -> {
                    Operator lt = new Light(row, col, null);
                    cells.set(col, row, lt);
                    break;
                }
                case "Switch" -> {
                    Operator swi = new Switch(row, col);
                    cells.set(col, row, swi);
                    break;
                }
                case "In" -> {
                    Operator inBlock = new Input(row, col);
                    cells.set(col, row, inBlock);
                    break;
                }
                case "Out" -> {
                    Operator outBlock = new Output(row, col, null);
                    cells.set(col, row, outBlock);
                    break;
                }
                default -> { //place custom object!
                    try {
                        Operator customBlock = new Custom(row, col, Manager.readFile("Saves/" + associatedGUI.getToolHeld() + ".txt"), associatedGUI.getToolHeld());
                        cells.set(col, row, customBlock);
                        //System.out.println(customBlock);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        if(!wires.isEmpty()) {
            for(Wire w : wires) {
                if (w.contains(cursorX, cursorY) && !associatedGUI.getToolHeld().equals("Trash")) { //select wire
                    selectWire(wires.indexOf(w));
                }
                if (w.contains(cursorX, cursorY) && associatedGUI.getToolHeld().equals("Trash")) {
                    int clm = (int)Math.floor(toXCoord(w.getxOne())/cellWidth);
                    int rw = (int)Math.floor(toYCoord(w.getyOne())/cellWidth);
                    deletePointersTo(cells.get(clm, rw));
                    wireToDelete = wires.indexOf(w);
                }
            }
            if (wireToDelete != -1) {
                //System.out.println("Wire deleted at index: "+wireToDelete);
                wires.remove(wireToDelete);
                selectedWire = -1;
                wireIsSelected = false;
                wireToDelete = -1;
                mouseOverWire = false;
            }
        }

        repaint();
    }



    private void undo() {
        changeMessage message = toUndo.pop();
        if(message instanceof cellMessage) {
            int xPoint = ((cellMessage) message).getPoint().x;
            int yPoint = ((cellMessage) message).getPoint().y;
            cellMessage redoMessage = new cellMessage(cells.get(xPoint, yPoint), ((cellMessage) message).getPoint());
            toRedo.push(redoMessage);
            cells.set(xPoint, yPoint, ((cellMessage) message).getOperator());
            if(!buffer.isEmpty()) {
                wireMessage msg = buffer.pop();
                Wire theWire = msg.getWire();
                wires.add(theWire);
                int clm1 = (int)Math.floor(toXCoord(theWire.getxOne())/cellWidth);
                int rw1 = (int)Math.floor(toYCoord(theWire.getyOne())/cellWidth);
                int clm2 = (int)Math.floor(toXCoord(theWire.getxTwo())/cellWidth);
                int rw2 = (int)Math.floor(toYCoord(theWire.getyTwo())/cellWidth);
                if(cells.get(clm2, rw2) instanceof Operator2I) { //some sort of error in here
                    if(cells.get(clm2, rw2).getPrev1() == null) {
                        cells.get(clm2, rw2).setPrev1(cells.get(clm1, rw1));
                    }
                    else {
                        ((Operator2I) cells.get(clm2, rw2)).setPrev2(cells.get(clm1, rw1));
                    }
                }
                else {
                    cells.get(clm2, rw2).setPrev1(cells.get(clm1, rw1));
                }
                if(xPoint == clm1 && yPoint == rw1) {
                    buffer.add(new wireMessage(theWire));
                }
            }
        }
        else if(message instanceof wireMessage) {
            Wire theWire = ((wireMessage) message).getWire();
            wireMessage redoMessage = new wireMessage(theWire);
            toRedo.push(redoMessage);
            int index = wires.indexOf(theWire);
            int clm = (int)Math.floor(toXCoord(theWire.getxOne())/cellWidth);
            int rw = (int)Math.floor(toYCoord(theWire.getyOne())/cellWidth);
            deletePointersTo(cells.get(clm, rw));
            wires.remove(index);
        }
    }

    private void redo() {
        changeMessage message = toRedo.pop();
        if(message instanceof cellMessage) {
            int xPoint = ((cellMessage) message).getPoint().x;
            int yPoint = ((cellMessage) message).getPoint().y;
            cellMessage undoMessage = new cellMessage(cells.get(xPoint, yPoint), ((cellMessage) message).getPoint());
            toUndo.push(undoMessage);
            cells.set(xPoint, yPoint, ((cellMessage) message).getOperator());
            if(!buffer.isEmpty()) {
                Wire theWire = buffer.pop().getWire();
                int clm1 = (int)Math.floor(toXCoord(theWire.getxOne())/cellWidth);
                int rw1 = (int)Math.floor(toYCoord(theWire.getyOne())/cellWidth);
                int clm2 = (int)Math.floor(toXCoord(theWire.getxTwo())/cellWidth);
                int rw2 = (int)Math.floor(toYCoord(theWire.getyTwo())/cellWidth);
                Operator cell2 = cells.get(clm2, rw2);
                if(cell2 instanceof Operator2I) {
                    if(cell2.getPrev1().getRow() == clm1 && cell2.getPrev1().getCol() == rw1) {
                        cell2.setPrev1(null);
                    }
                    if(((Operator2I) cell2).getPrev2().getRow() == clm1 && ((Operator2I) cell2).getPrev2().getCol() == rw1) {
                        cell2.setPrev1(null);
                    }
                }
                else {
                    if(cell2.getPrev1().getRow() == clm1 && cell2.getPrev1().getCol() == rw1) {
                        cell2.setPrev1(null);
                    }
                }
            }
        }
        else if(message instanceof wireMessage) {
            Wire theWire = ((wireMessage) message).getWire();
            wires.add(theWire);
            int clm1 = (int)Math.floor(toXCoord(theWire.getxOne())/cellWidth);
            int rw1 = (int)Math.floor(toYCoord(theWire.getyOne())/cellWidth);
            int clm2 = (int)Math.floor(toXCoord(theWire.getxTwo())/cellWidth);
            int rw2 = (int)Math.floor(toYCoord(theWire.getyTwo())/cellWidth);
            if(cells.get(clm2, rw2) instanceof Operator2I) {
                if(cells.get(clm2, rw2).getPrev1() == null) {
                    cells.get(clm2, rw2).setPrev1(cells.get(clm1, rw1));
                }
                else {
                    ((Operator2I) cells.get(clm2, rw2)).setPrev2(cells.get(clm1, rw1));
                }
            }
            else {
                cells.get(clm2, rw2).setPrev1(cells.get(clm1, rw1));
            }

            wireMessage undoMessage = new wireMessage(theWire);
            toUndo.push(undoMessage);



            //still need to handle when wire color is changed and when wires are deleted !!!!!!!! also still some bugs
        }
    }


    private void drawTriangle(Graphics2D g, int x1, int y1, int x2, int y2){
        double angle = Math.atan2(y2-y1, x2-x1);
        double xMid = (x1+x2)/2.0;
        double yMid = (y1+y2)/2.0;
        double x0 = xMid - 15 * Math.cos(angle);
        double y0 = yMid - 15 * Math.sin(angle);
        double angle2 = Math.PI / 2 - angle;
        double xt = x0 - 10 * Math.cos(angle2);
        double yt = y0 + 10 * Math.sin(angle2);
        double xb = x0 + 10 * Math.cos(angle2);
        double yb = y0 - 10 * Math.sin(angle2);
        g.fillPolygon(new int[]{(int)xMid, (int)xt, (int)xb},new int[]{(int)yMid, (int)yt, (int)yb},3);
    }

    private void selectWire(int select) {
        if(selectedWire == select) {
            //System.out.println("Wire at index "+selectedWire+" unselected!");
            wires.get(selectedWire).setSelected(false);
            selectedWire = -1;
            wireIsSelected = false;
        }
        else {
            selectedWire = select;
            //System.out.println("Wire at index "+selectedWire+" selected!");

            for(Wire w : wires) {
                w.setSelected(wires.indexOf(w) == selectedWire);
            }

            wireIsSelected = true;
        }
        repaint();
    }

    private void deletePointersTo(Operator n) {
        for(Operator temp1 : cells) {
            if (temp1 != null) {
                if (temp1 instanceof Custom) {
                    for (Operator temp : (((Custom) temp1).getInputs())) {
                        if (temp.getPrev1() == n) {
                            temp.setPrev1(null);
                        }
                    }
                } else {
                    if (temp1.getPrev1() == n) {
                        temp1.setPrev1(null);
                    }
                    if (temp1 instanceof Operator2I && ((Operator2I) temp1).getPrev2() == n) {
                        ((Operator2I) temp1).setPrev2(null);
                    }
                }
            }
        }
    }

    public SparseMatrix<Operator> getCells(){
        return cells;
    }

    private void createWire(int c1, int r1, int c2, int r2) {
        Wire wire = new Wire(c1, r1, c2, r2, currentWireColor);
        wires.add(wire);
        wireMessage message = new wireMessage(wire);
        toUndo.push(message);
    }

    public int getCellWidth() {
        return cellWidth;
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

        cursorX = e.getX();
        cursorY = e.getY();

        mouseX = (int)Math.floor(toXCoord(e.getX())/cellWidth);
        mouseY = (int)Math.floor(toYCoord(e.getY())/cellWidth);

        updateWireToCursor();

        if(!wires.isEmpty()) {
            for(Wire w : wires) {
                mouseOverWire = w.contains(cursorX, cursorY);
                w.setMouseCovering(w.contains(cursorX, cursorY));
            }
        }

        repaint();
    }

    private void updateWireToCursor() {
        if(firstInput != null) { //draws wire to cursor
            wireToCursor = new Wire((int)firstInput.getX(), (int)firstInput.getY(), cursorX, cursorY, currentWireColor);
        }
        else {
            wireToCursor = null;
        }
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
    }

    public void processUserInput(int k) { //k is key input from kb
        switch (k) {
            case KeyEvent.VK_S -> {
                if (Driver.isControl) {
                    FileExplorer fileExplorer = new FileExplorer();
                    File file = fileExplorer.selectFile(false);
                    if (file.getName().isEmpty()) {
                        return;
                    }
                    String image = "/" + file.getPath().replace("\\", "/");

                    try {
                        Manager.writeToFile(cells, "Saves/untitled.txt", image);
                    } catch (Exception ignored) {}
                }
            }
            case KeyEvent.VK_L -> {
                try {
                    if (Driver.isControl) {
                        FileExplorer fileExplorer = new FileExplorer();
                        File file = fileExplorer.selectFile(true);
                        if (file.getName().isEmpty()) {
                            return;
                        }
                        cells = Manager.readFile("Saves/" + file.getName());
                        for (Operator n : cells) {
                            if (n instanceof Custom) {
                                for (Operator input : ((Custom) n).getInputs()) {
                                    if (input.getPrev1() != null) {
                                        wires.add(new Wire(input.getPrev1().getCol(), input.getPrev1().getRow(), (int) n.getCol(), (int) n.getRow(), input.getColor()));
                                    }//wires.add(new Wire(c1, r1, c2, r2, currentWireColor)
                                }
                            } else {
                                if (n.getPrev1() != null) {
                                    wires.add(new Wire(n.getPrev1().getCol(), n.getPrev1().getRow(), (int) n.getCol(), (int) n.getRow(), n.getColor()));
                                }
                                if (n instanceof Operator2I && ((Operator2I) n).getPrev2() != null) {
                                    wires.add(new Wire(((Operator2I) n).getPrev2().getCol(), ((Operator2I) n).getPrev2().getRow(), (int) n.getCol(), (int) n.getRow(), ((Operator2I) n).getColor2()));
                                }
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case KeyEvent.VK_Z -> {
                if(Driver.isControl && !toUndo.isEmpty()) {
                    undo();
                }
            }
            case KeyEvent.VK_Y -> {
                if(Driver.isControl && !toRedo.isEmpty()) {
                    redo();
                }
            }
        }
        repaint();
    }

}


