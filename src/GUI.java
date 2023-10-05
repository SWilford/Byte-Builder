import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class GUI extends JPanel implements MouseListener, MouseMotionListener {

    private final ArrayList<Button> buttons = new ArrayList<>(); //stores buttons in the grid
    private final Operator [][] operators = new Operator[25][25]; //the actual array of operators

    public String toolHeld = ""; //the title of the tool which is currently selected
    public boolean toolSelected; //If a tool has been selected or not (any tool)
    public ArrayList<Button> toolButtons = new ArrayList<>(); //Stores buttons in the toolbar
    public ArrayList<Wire> wires = new ArrayList<>(); // stores wires to be drawn

    public Wire wireToCursor; //The wire being generated when wire tool is selected

    private static int mouseX; //X position of cursor
    private static int mouseY; //Y position of cursor

    private static Point firstInput; //variable for first component clicked for 2, used for making wiring
    // Image Icons

    private final ImageIcon gridSquareImg = new ImageIcon("Images/gridSquare.png");
    private final ImageIcon wireToolImg = new ImageIcon("Images/copwire.png");
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");
    private final ImageIcon trashImage = new ImageIcon("Images/Trash.png");
    private final ImageIcon onImage = new ImageIcon("Images/On.png");
    private final ImageIcon lightOn = new ImageIcon("Images/LightOn.png");
    private final ImageIcon lightOff = new ImageIcon("Images/LightOff.png");
    private final ImageIcon switchOn = new ImageIcon("Images/LeverOn.png");
    private final ImageIcon switchOff = new ImageIcon("Images/LeverOff.png");

    //End of Image Icons

    public GUI() {
        this.setPreferredSize(new Dimension(1440, 1200)); //Manually sets size of JFrame
        addMouseListener(this); //Listeners
        addMouseMotionListener(this);
        mouseX = 0; //Initializes mouse position to 0, 0
        mouseY = 0;
        firstInput = null;
        createGridButtons();
        createToolbar();
        toolHeld = ""; //No tool held at start
        toolSelected = false; //No tool selected at start
        wireToCursor = null;
    }

    public void display(Graphics g) { //Draws buttons
        for(Button b:buttons) {
            b.drawButton(g);
        }
        for(Button b:toolButtons) {
            b.drawButton(g);
        }
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.RED);
        Stroke wireStroke = new BasicStroke(3);
        g2.setStroke(wireStroke);
        ArrayList<Wire> wiresDelete = new ArrayList<>();
        for (Wire w:wires){
            if(operators[(w.getY1()-24)/48][(w.getX1()-240-36)/48]==null||operators[(w.getY2()-24)/48][(w.getX2()-240-12)/48]==null) {
                wiresDelete.add(w);
            }
            else {
                g2.drawLine(w.getX1(), w.getY1(), w.getX2(), w.getY2());
            }
        }
        for(Wire w:wiresDelete) {
            wires.remove(w);
        }
        if(wireToCursor != null) {
            g2.drawLine(wireToCursor.getX1()*48+240+36, wireToCursor.getY1()*48+24, wireToCursor.getX2(), wireToCursor.getY2());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        display(g);
    }

    private void createGridButtons() { //Initializes the grid's buttons
        int row = 0;
        for(int x = 240; x < 1440; x+=48) {
            int col = 0;
            for(int y = 0; y < 1200; y+=48) {
                Shape gridShape = new Rectangle(x, y, 48, 48);
                Button button = new Button(gridShape, "gridSquare", gridSquareImg, row, col);
                buttons.add(button);
                col++;
            }
            row++;
        }
    }

    private void createToolbar() { //Creates each toolbar button, should eventually become a method
        Shape toolSquare1 = new Rectangle(0, 0, 120, 120);
        Button wireButton = new Button(toolSquare1, "wire", wireToolImg);
        toolButtons.add(wireButton);
        Shape toolSquare2 = new Rectangle(120, 0, 120, 120);
        Button trashButton = new Button(toolSquare2, "trash", trashImage);
        toolButtons.add(trashButton);
        Shape toolSquare3 = new Rectangle(0, 120, 120, 120);
        Button notButton = new Button(toolSquare3, "not", notImage);
        toolButtons.add(notButton);
        Shape toolSquare4 = new Rectangle(120, 120, 120, 120);
        Button andButton = new Button(toolSquare4, "and", andImage);
        toolButtons.add(andButton);
        Shape toolSquare5 = new Rectangle(0, 240, 120, 120);
        Button onButton = new Button(toolSquare5, "on", onImage);
        toolButtons.add(onButton);
        Shape toolSquare6 = new Rectangle(120, 240, 120, 120);
        Button lightButton = new Button(toolSquare6, "light", lightOn);
        toolButtons.add(lightButton);
        Shape toolSquare7 = new Rectangle(0, 360, 120, 120);
        Button switchButton = new Button(toolSquare7, "switch", switchOff);
        toolButtons.add(switchButton);

    }

    private void toolButtonHelper(String s) { //Helper method for when a tool is selected
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
        if(!s.equals("wire")) {
            firstInput = null;
        }
    }

    public void mouseClicked(MouseEvent e) { //What happens when the mouse is clicked
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) {
            for (Button b : toolButtons) { //When mouse is clicked on a tool button
                if (b.getShape().contains(mouseX, mouseY)) {
                    toolButtonHelper(b.getTitle());
                }
            }
            for (Button b : buttons) { //When mouse is clicked on a grid button
                if (b.getShape().contains(mouseX, mouseY)) {
                    int tempCol = b.getRow();
                    int tempRow = b.getCol();
                    switch (toolHeld) {
                        case "" -> {
                            if(operators[tempRow][tempCol] instanceof Switch) {
                                ((Switch) operators[tempRow][tempCol]).switchInput();
                            }
                        }
                        case "wire" -> { //do wiring
                            if (operators[tempRow][tempCol] != null) {
                                if (firstInput == null) {
                                    firstInput = new Point(tempCol, tempRow);
                                } else {
                                    if(operators[tempRow][tempCol] instanceof Operator2I && operators[tempRow][tempCol].getPrev1() != null){
                                        ((Operator2I) operators[tempRow][tempCol]).setPrev2(operators[(int) firstInput.getY()][(int) firstInput.getX()]);
                                    }
                                    else{
                                        operators[tempRow][tempCol].setPrev1(operators[(int) firstInput.getY()][(int) firstInput.getX()]); //set 2nd operator's previous to 1st operator
                                    }
                                    getWireCoords((int)firstInput.getX(), (int)firstInput.getY(), tempCol,tempRow);
                                    firstInput = null; //reset
                                }
                            }
                        }
                        case "not" -> { //creates not block
                            Operator notBlock = new NotBlock(null);
                            operators[tempRow][tempCol] = notBlock;
                            b.setRegularImage(notImage);
                        }
                        case "and" -> { //creates and block
                            Operator andBlock = new AndBlock(null, null);
                            operators[tempRow][tempCol] = andBlock;
                            b.setRegularImage(andImage);
                        }
                        case "trash" -> { //deletes block
                            deletePointersTo(operators[tempRow][tempCol]);
                            operators[tempRow][tempCol] = null;
                            b.setRegularImage(gridSquareImg);
                        }
                        case "on" -> {
                            Operator onBlock = new OnBlock();
                            operators[tempRow][tempCol] = onBlock;
                            b.setRegularImage(onImage);
                        }
                        case "light" -> {
                            Operator lt = new Light(null);
                            operators[tempRow][tempCol] = lt;
                            b.setRegularImage(lightOff);
                        }
                        case "switch" -> {
                            Operator swi = new Switch();
                            operators[tempRow][tempCol] = swi;
                            b.setRegularImage(switchOff);
                        }
                    }

                }
            }
        }
        updateHighlighting();
    }

    private void deletePointersTo(Operator n){
        for (int r = 0; r < operators.length; r++){
            for (int c = 0; c < operators[0].length; c++){
                if (operators[r][c] != null) {
                    if (operators[r][c].getPrev1() == n) {
                        operators[r][c].setPrev1(null);
                    }
                    if (operators[r][c] instanceof Operator2I && ((Operator2I) operators[r][c]).getPrev2() == n) {
                        operators[r][c].setPrev1(null);
                    }
                }
            }
        }
    }

    private void getWireCoords(int c1, int r1, int c2, int r2){
        Wire temp = new Wire(c1*48 + 36 + 240, r1*48 + 24, c2*48 + 12 + 240, r2*48 + 24);
        wires.add(temp);
    }
    public class Wire{
        private final int x1, y1, x2, y2;

        public Wire(int a, int b, int c, int d){
            x1 = a;
            y1 = b;
            x2 = c;
            y2 = d;
        }

        public int getX1() {
            return x1;
        }
        public int getY1() {
            return y1;
        }
        public int getX2() {
            return x2;
        }
        public int getY2() {
            return y2;
        }


    }

    private void updateHighlighting(){
        for(Button b: buttons) { //When mouse enters a grid button, it is highlighted
            if(b.getShape().contains(mouseX, mouseY)) {
                b.highlight();
            }
            else {
                b.unHighlight();
            }
            int tempCol = b.getRow();
            int tempRow = b.getCol();
            if(operators[tempRow][tempCol] instanceof Light) {
                if(operators[tempRow][tempCol].getPrev1() != null && operators[tempRow][tempCol].getOutput()) {
                    b.setRegularImage(lightOn);
                }
                else {
                    b.setRegularImage(lightOff);
                }
            }
            if(operators[tempRow][tempCol] instanceof Switch) {
                if(operators[tempRow][tempCol].getOutput()) {
                    b.setRegularImage(switchOn);
                }
                else {
                    b.setRegularImage(switchOff);
                }
            }


        }
        for(Button b: toolButtons) {
            if(toolHeld.equals(b.getTitle()) && !b.isToolbarColored()) { //when a tool is selected primary color becomes the selection color
                b.toolbarHighlight();
            }
            else if(b.isToolbarColored() && !toolHeld.equals(b.getTitle())) { //when a tool is not selected the primary color becomes base color
                b.resetToolbarColor();
            }
            if(b.getShape().contains(mouseX, mouseY)) { //when mouse enters a tool button it is highlighted
                b.highlight();
            }
            else {
                b.unHighlight();
            }
        }
        if(firstInput != null) {
            wireToCursor = new Wire((int)firstInput.getX(), (int)firstInput.getY(), mouseX, mouseY);
        }
        else {
            wireToCursor = null;
        }
        repaint();
    }

    public void mouseMoved(MouseEvent e) { //When mouse is moved, highlighting is updated
        mouseX = e.getX();
        mouseY = e.getY();
        updateHighlighting();
    }

    public void processUserInput(int k){ //k is key input from kb
        switch (k){
            case KeyEvent.VK_ESCAPE -> {
                if (firstInput != null){
                    firstInput = null;
                }
            }
            case KeyEvent.VK_1 -> {
                toolButtonHelper("wire");
            }
            case KeyEvent.VK_2 -> {
                toolButtonHelper("trash");
            }
            case KeyEvent.VK_3 -> {
                toolButtonHelper("not");
            }
            case KeyEvent.VK_4 -> {
                toolButtonHelper("and");
            }
            case KeyEvent.VK_5 -> {
                toolButtonHelper("on");
            }
            case KeyEvent.VK_6 -> {
                toolButtonHelper("light");
            }
            case KeyEvent.VK_7 -> {
                toolButtonHelper("switch");
            }
        }
        updateHighlighting();
    }

    public void mousePressed(MouseEvent e) {
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

}
