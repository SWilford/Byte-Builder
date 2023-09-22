import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class GUI extends JPanel implements MouseListener, MouseMotionListener {

    private final ArrayList<Button> buttons = new ArrayList<>();
    private final Operator [][] operators = new Operator[25][25];

    public String toolHeld = "";
    public boolean toolSelected;
    public ArrayList<Button> toolButtons = new ArrayList<>();

    private static int mouseX;
    private static int mouseY;

    // Image Icons

    private final ImageIcon gridSquareImg = new ImageIcon("Images/gridSquare.png");
    private final ImageIcon wireToolImg = new ImageIcon("Images/copwire.png");
    private final ImageIcon andImage = new ImageIcon("Images/AndGate.png");
    private final ImageIcon notImage = new ImageIcon("Images/NotGate.png");

    //End of Image Icons

    public GUI() {
        this.setPreferredSize(new Dimension(1440, 1200));
        addMouseListener(this);
        addMouseMotionListener(this);
        mouseX = 0;
        mouseY = 0;
        createGridButtons();
        createToolbar();
        toolHeld = "";
        toolSelected = false;
    }

    public void display(Graphics g) {
        for(Button b:buttons) {
            b.drawButton(g);
        }
        for(Button b:toolButtons) {
            b.drawButton(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        display(g);
    }

    private void createGridButtons() {
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

    private void createToolbar() {
        Shape toolSquare1 = new Rectangle(0, 0, 120, 120);
        Button wireButton = new Button(toolSquare1, "wire", wireToolImg);
        toolButtons.add(wireButton);
        Shape toolSquare2 = new Rectangle(120, 0, 120, 120);
        Button notButton = new Button(toolSquare2, "not", notImage);
        toolButtons.add(notButton);
        Shape toolSquare3 = new Rectangle(0, 120, 120, 120);
        Button andButton = new Button(toolSquare3, "and", andImage);
        toolButtons.add(andButton);
    }


    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        if(button == MouseEvent.BUTTON1) {
            for(Button b : toolButtons) {
                if(b.getShape().contains(mouseX, mouseY)) {
                    switch(b.getTitle()) {
                        case "wire" -> {
                            if(!toolSelected) {
                                toolSelected = true;
                                toolHeld = "wire";
                            }
                            else if(toolHeld.equals("wire")){
                                toolSelected = false;
                                toolHeld = "";
                            }
                            else {
                                toolHeld = "wire";
                            }
                        }
                        case "not" -> {
                            if(!toolSelected) {
                                toolSelected = true;
                                toolHeld = "not";
                            }
                            else if(toolHeld.equals("not")){
                                toolSelected = false;
                                toolHeld = "";
                            }
                            else {
                                toolHeld = "not";
                            }
                        }
                        case "and" -> {
                            if(!toolSelected) {
                                toolSelected = true;
                                toolHeld = "and";
                            }
                            else if(toolHeld.equals("and")){
                                toolSelected = false;
                                toolHeld = "";
                            }
                            else {
                                toolHeld = "and";
                            }
                        }
                    }
                }
            }
            for(Button b : buttons) {
                if(b.getShape().contains(mouseX, mouseY)) {
                    int tempRow = b.getRow();
                    int tempCol = b.getCol();
                    switch (toolHeld) {
                        case "not" -> {
                            NotBlock notBlock = new NotBlock(null);
                            operators[tempRow][tempCol] = notBlock;
                            b.setRegularImage(notImage);
                        }
                        case "and" -> {
                            AndBlock andBlock = new AndBlock(null, null);
                            operators[tempRow][tempCol] = andBlock;
                            b.setRegularImage(andImage);
                        }
                    }
                }
            }
        }

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
        /*for(Button b: toolButtons) {
            {
            if(b.getShape().contains(mouseX, mouseY)) {
                b.highlight();
            }
            else {
                b.unHighlight();
            }
        }*/
        for(Button b: toolButtons) {
            if(toolHeld.equals(b.getTitle()) && !b.isToolbarColored()) {
                b.toolbarHighlight();
            }
            else if(b.isToolbarColored() && !toolHeld.equals(b.getTitle())) {
                b.resetToolbarColor();
            }
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
