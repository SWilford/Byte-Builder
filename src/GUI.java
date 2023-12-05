import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;

public class GUI extends JPanel implements MouseListener, MouseMotionListener {

    private final ArrayList<Button> buttons = new ArrayList<>(); //stores buttons in the grid
    private final Operator [][] operators = new Operator[25][25]; //the actual array of operators

    public String toolHeld = ""; //the title of the tool which is currently selected
    public boolean toolSelected; //If a tool has been selected or not (any tool)
    public ArrayList<Button> toolButtons = new ArrayList<>(); //Stores buttons in the toolbar
    public ArrayList<Wire> wires = new ArrayList<>(); // stores wires to be drawn
    private int toSel;
    public ArrayList<Button> wireColors = new ArrayList<>();
    public String currentWireColor;

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
    private final ImageIcon inImage = new ImageIcon("Images/Input.png");
    private final ImageIcon outImage = new ImageIcon("Images/Output.png");
    private final ImageIcon RedWire = new ImageIcon("Images/RedWire.png");
    private final ImageIcon GreenWire = new ImageIcon("Images/GreenWire.png");
    private final ImageIcon BlueWire = new ImageIcon("Images/BlueWire.png");
    private final ImageIcon OrangeWire = new ImageIcon("Images/OrangeWire.png");
    private final ImageIcon YellowWire = new ImageIcon("Images/YellowWire.png");
    private final ImageIcon WhiteWire = new ImageIcon("Images/WhiteWire.png");

    //End of Image Icons

    public GUI() {
        toSel = -1;
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
        createWireColors();
        currentWireColor = "red";
    }

    public void display(Graphics g) { //Draws buttons
        for(Button b:buttons) {
            b.drawButton(g);
        }
        for(Button b:toolButtons) {
            b.drawButton(g);
        }
        Graphics2D g2 = (Graphics2D)g;
        Stroke borderStroke = new BasicStroke(1);
        g2.setColor(Color.BLACK);
        g2.setStroke(borderStroke);
        for(int x = 240; x < 1440; x+=48) {
            for(int y = 0; y < 1200; y += 48) {
                g2.drawLine(x, y, x, 1200);
                g2.drawLine(x, y, 1440, y);
            }
        }
        boolean cBool = false;
        for(Wire w : wires) {
            if (w.isSelected()) {
                cBool = true;
                break;
            }
        }
        if(toolHeld.equals("wire") || cBool) {
            for(Button b: wireColors) {
                b.drawButton(g);
            }
            g2.setStroke(borderStroke);
            g2.setColor(Color.BLACK);
            g2.drawLine(0, 1040, 240, 1040);
            g2.drawLine(0, 1120, 240, 1120);
            g2.drawLine(0, 1200, 240, 1200);
            g2.drawLine(0, 1040, 0, 1200);
            g2.drawLine(80, 1040, 80, 1200);
            g2.drawLine(160, 1040, 160, 1200);
            g2.drawLine(240, 1040, 240, 1200);
        }
        g2.setColor(Color.RED);
        Stroke wireStroke = new BasicStroke(3);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(wireStroke);
        ArrayList<Wire> wiresDelete = new ArrayList<>();
        for (Wire w:wires){
            if(operators[(w.getY1()-24)/48][(w.getX1()-240-36)/48]==null||operators[(w.getY2()-24)/48][(w.getX2()-240-12)/48]==null) {
                wiresDelete.add(w);
            }
            else {
                //g2.drawLine(w.getX1(), w.getY1(), w.getX2(), w.getY2());
                w.drawWire(g2);
                g2.setColor(w.getClr());
                drawTriangle(g2,w.getX1(), w.getY1(), w.getX2(), w.getY2());
            }
        }
        for(Wire w:wiresDelete) {
            wires.remove(w);
        }
        if(wireToCursor != null) {
            g2.setColor(wireToCursor.getColor());
            g2.drawLine(wireToCursor.getX1()*48+240+36, wireToCursor.getY1()*48+24, wireToCursor.getX2(), wireToCursor.getY2());
            drawTriangle(g2,wireToCursor.getX1()*48+240+36, wireToCursor.getY1()*48+24, wireToCursor.getX2(), wireToCursor.getY2());
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
    private void createWireColors() { //Initializes buttons for selecting wire color
        Shape s = new Rectangle(0, 1040, 80, 80);
        Button b = new Button(s, "red", RedWire);
        wireColors.add(b);
        Shape s2 = new Rectangle(80, 1040, 80, 80);
        Button b2 = new Button(s2, "green", GreenWire);
        wireColors.add(b2);
        Shape s3 = new Rectangle(160, 1040, 80, 80);
        Button b3 = new Button(s3, "blue", BlueWire);
        wireColors.add(b3);
        Shape s4 = new Rectangle(0, 1120, 80, 80);
        Button b4 = new Button(s4, "orange", OrangeWire);
        wireColors.add(b4);
        Shape s5 = new Rectangle(80, 1120, 80, 80);
        Button b5 = new Button(s5, "yellow", YellowWire);
        wireColors.add(b5);
        Shape s6 = new Rectangle(160, 1120, 80, 80);
        Button b6 = new Button(s6, "white", WhiteWire);
        wireColors.add(b6);
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
        Shape toolSquare8 = new Rectangle(120, 360, 120, 120);
        Button inButton = new Button(toolSquare8, "input", inImage);
        toolButtons.add(inButton);
        Shape toolSquare9 = new Rectangle(0, 480, 120, 120);
        Button outButton = new Button(toolSquare9, "output", outImage);
        toolButtons.add(outButton);
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
        if(!s.equals("wire") || toolHeld.isEmpty()) {
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
                                    if (operators[tempRow][tempCol] instanceof OnBlock || operators[tempRow][tempCol] instanceof Switch || operators[tempRow][tempCol].isFull()){ //do nothing since start
                                        break;
                                    }
                                    if (operators[tempRow][tempCol] instanceof Custom){ //custom block
                                        Operator temp = ((Custom) operators[tempRow][tempCol]).getFirstEmpty();
                                        if (temp != null){
                                            temp.setPrev1(operators[(int) firstInput.getY()][(int) firstInput.getX()]);
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    else if(operators[tempRow][tempCol] instanceof Operator2I && operators[tempRow][tempCol].getPrev1() != null){ //if it has two inputs and the first is taken
                                        ((Operator2I) operators[tempRow][tempCol]).setPrev2(operators[(int) firstInput.getY()][(int) firstInput.getX()]); //Go to the second slot
                                        ((Operator2I)operators[tempRow][tempCol]).setColor2(currentWireColor);
                                    }
                                    else{ //first slot
                                        operators[tempRow][tempCol].setPrev1(operators[(int) firstInput.getY()][(int) firstInput.getX()]); //set 2nd operator's previous to 1st operator
                                        operators[tempRow][tempCol].setColor(currentWireColor);
                                    }
                                    getWireCoords((int)firstInput.getX(), (int)firstInput.getY(), tempCol,tempRow);
                                    firstInput = null; //reset
                                }
                            }
                        }
                        case "not" -> { //creates not block
                            Operator notBlock = new NotBlock(tempRow, tempCol, null);
                            operators[tempRow][tempCol] = notBlock;
                            b.setRegularImage(notImage);
                        }
                        case "and" -> { //creates and block
                            Operator andBlock = new AndBlock(tempRow, tempCol,null, null);
                            operators[tempRow][tempCol] = andBlock;
                            b.setRegularImage(andImage);
                        }
                        case "trash" -> { //deletes block
                            deletePointersTo(operators[tempRow][tempCol]);
                            operators[tempRow][tempCol] = null;
                            b.setRegularImage(gridSquareImg);
                        }
                        case "on" -> {
                            Operator onBlock = new OnBlock(tempRow, tempCol);
                            operators[tempRow][tempCol] = onBlock;
                            b.setRegularImage(onImage);
                        }
                        case "light" -> {
                            Operator lt = new Light(tempRow, tempCol,null);
                            operators[tempRow][tempCol] = lt;
                            b.setRegularImage(lightOff);
                        }
                        case "switch" -> {
                            Operator swi = new Switch(tempRow, tempCol);
                            operators[tempRow][tempCol] = swi;
                            b.setRegularImage(switchOff);
                        }
                        case "input" -> {
                            Operator inBlock = new Input(tempRow, tempCol);
                            operators[tempRow][tempCol] = inBlock;
                            b.setRegularImage(inImage);
                        }
                        case "output" -> {
                            Operator outBlock = new Output(tempRow, tempCol,null);
                            operators[tempRow][tempCol] = outBlock;
                            b.setRegularImage(outImage);
                        }
                    }
                }
            }
            if(toolHeld.equals("wire")) {
                for (Button b : wireColors) { //when wireColor button is clicked
                    if(b.getShape().contains(mouseX, mouseY)) {
                        currentWireColor = b.getTitle();
                    }
                }
            }
            for(Wire w : wires) { //if a wire is selected you can change the color of that wire
                if(w.isSelected()) {
                    for(Button b : wireColors) {
                        if(b.getShape().contains(mouseX, mouseY)) {
                            w.setColor(b.getTitle());
                        }
                    }
                }
            }
        }
        updateHighlighting();
    }

    private void deletePointersTo(Operator n){
        for (int r = 0; r < operators.length; r++) {
            for (int c = 0; c < operators[0].length; c++) {
                if (operators[r][c] != null) {
                    if (operators[r][c] instanceof Custom){
                        for (Operator temp : ((Custom) operators[r][c]).getInputs()){
                            if (temp.getPrev1() == n){
                                temp.setPrev1(null);
                            }
                        }
                    }
                    else {
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
    }

    private void getWireCoords(int c1, int r1, int c2, int r2){
        Wire temp = new Wire(c1*48 + 36 + 240, r1*48 + 24, c2*48 + 12 + 240, r2*48 + 24, currentWireColor);
        wires.add(temp);
    }
    private void getWireCoords(int c1, int r1, int c2, int r2, String c){
        Wire temp = new Wire(c1*48 + 36 + 240, r1*48 + 24, c2*48 + 12 + 240, r2*48 + 24, c);
        wires.add(temp);
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
        if(firstInput != null) { //draws wire to cursor
            wireToCursor = new Wire((int)firstInput.getX(), (int)firstInput.getY(), mouseX, mouseY, currentWireColor);
        }
        else {
            wireToCursor = null;
        }
        /*for(Wire w : wires) { old wire highlighting
            if(w.isSelected() && !w.isWireColored()) {
                w.selectionHighlight();
            }
            else if(w.isWireColored() && !w.isSelected()) {
                w.resetWireColor();
            }
            if(w.contains(mouseX, mouseY)) {
                w.highlight();
            }
            else {
                w.unHighlight();
            }
        }*/
        repaint();
        for(Button b: wireColors) {
            if(currentWireColor.equals(b.getTitle()) && !b.isToolbarColored()) {
                b.toolbarHighlight();
            }
            else if(b.isToolbarColored() && !currentWireColor.equals(b.getTitle())) {
                b.resetToolbarColor();
            }
            if(b.getShape().contains(mouseX, mouseY)) {
                b.highlight();
            }
            else {
                b.unHighlight();
            }
        }
    }

    public int customIndex(int row, int col){
        return (row*25)+col;
    }

    public void insert(Operator n){
        Button b = buttons.get(customIndex(n.getRow(), n.getCol()));
        operators[b.getCol()][b.getRow()] = n;

        if (n instanceof Custom){
            for (Operator temp : ((Custom) n).getInputs()){
                if (temp.getPrev1() != null) {
                    getWireCoords(temp.getPrev1().getRow(), temp.getPrev1().getCol(), b.getRow(), b.getCol(), n.getColor());
                }
            }
        }
        else {
            if (n.getPrev1() != null) {
                getWireCoords(n.getPrev1().getRow(), n.getPrev1().getCol(), b.getRow(), b.getCol(), n.getColor());
            }
            if (n instanceof Operator2I && ((Operator2I) n).getPrev2() != null) {
                getWireCoords(((Operator2I) n).getPrev2().getRow(), ((Operator2I) n).getPrev2().getCol(), b.getRow(), b.getCol(), ((Operator2I) n).getColor2());
            }
        }
        if(n instanceof NotBlock){
            b.setRegularImage(notImage);
        }
        if (n instanceof AndBlock){
           b.setRegularImage(andImage);
        }
        if (n instanceof OnBlock){
            b.setRegularImage(onImage);
        }
        if (n instanceof Light){
            b.setRegularImage(lightOff);
        }
        if (n instanceof Switch){
            b.setRegularImage(switchOff);
        }
        if (n instanceof Input){
            b.setRegularImage(inImage);
        }
        if (n instanceof Output){
            b.setRegularImage(outImage);
        }
        if (n instanceof Custom){
            b.setRegularImage(GreenWire);
        }
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
            case KeyEvent.VK_1 -> toolButtonHelper("wire");
            case KeyEvent.VK_2 -> toolButtonHelper("trash");
            case KeyEvent.VK_3 -> toolButtonHelper("not");
            case KeyEvent.VK_4 -> toolButtonHelper("and");
            case KeyEvent.VK_5 -> toolButtonHelper("on");
            case KeyEvent.VK_6 -> toolButtonHelper("light");
            case KeyEvent.VK_7 -> toolButtonHelper("switch");
            case KeyEvent.VK_8 -> toolButtonHelper("input");
            case KeyEvent.VK_9 -> toolButtonHelper("output");
            case KeyEvent.VK_L -> {
                try {
                    LinkedList<Operator> blocks = FileManager.readFile("Saves/untitled.txt");
                    for (Operator block : blocks) {
                        insert(block);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("File does not exist!");
                }
            }
            case KeyEvent.VK_S -> {
                File n = new File("Saves/untitled.txt");
                if (!n.exists()){
                    try {
                        n.createNewFile();
                    } catch (IOException ignored) {}
                }
                if (n.exists()){
                    try {
                        FileManager.writeToFile(toList(), "Saves/untitled.txt");
                    } catch (IOException ignored) {}
                }
            }
            case KeyEvent.VK_T -> {
                try {
                    insert(new Custom(5, 5, FileManager.readFile("Saves/xor.txt"), "xor"));
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        updateHighlighting();
    }

    public LinkedList<Operator> toList(){
        LinkedList<Operator> arr = new LinkedList<>();
        for (Operator[] op: operators){
            for (Operator n: op){
                if (n != null){
                    arr.add(n);
                }
            }
        }
        return arr;
    }


    public void mousePressed(MouseEvent e) {
        repaint();
    }
    public void mouseReleased(MouseEvent e) {
        repaint();
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}