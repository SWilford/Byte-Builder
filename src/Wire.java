import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.ArrayList;

public class Wire {
    private int x1;
    private int y1;
    private final int x2;
    private final int y2;
    private int xOne, yOne, xTwo, yTwo;
    private String color;

    public boolean selected;

    private boolean mouseCovering;

    private Color clr, baseColor;
    private Color highlightColor, highlightColor2;

    public boolean wireColored;

    public Wire(int a, int b, int c, int d, String cl){
        x1 = a;
        y1 = b;
        x2 = c;
        y2 = d;
        xOne = a;
        yOne = b;
        xTwo = c;
        yTwo = d;
        color = cl;
        baseColor = this.getColor();
        clr = baseColor;
        selected = false;

        int hR = baseColor.getRed()-50;
        int hG = baseColor.getGreen()-50;
        int hB = baseColor.getBlue()-50;
        if(hR < 0 ) {
            hR = 0;
        }
        if(hG < 0) {
            hG = 0;
        }
        if(hB < 0) {
            hB = 0;
        }
        highlightColor = new Color(hR, hG, hB);
        int hR2 = baseColor.getRed()-100;
        int hG2 = baseColor.getGreen()-100;
        int hB2 = baseColor.getBlue()-100;
        if(hR2 < 0 ) {
            hR2 = 0;
        }
        if(hG2 < 0) {
            hG2 = 0;
        }
        if(hB2 < 0) {
            hB2 = 0;
        }
        highlightColor2 = new Color(hR2, hG2, hB2);

        wireColored = false;
        mouseCovering = false;
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
    public Color getColor() {
        switch (color) {
            case "red" -> {return Color.RED;}
            case "green" -> {return Color.GREEN;}
            case "blue" -> {return Color.BLUE;}
            case "orange" -> {return Color.ORANGE;}
            case "yellow" -> {return Color.YELLOW;}
            case "white" -> {return Color.WHITE;}
        }
        return null;
    }

    public void setColor(String s) {
        color = s;
        baseColor = this.getColor();
        clr = baseColor;
        int hR = baseColor.getRed()-50;
        int hG = baseColor.getGreen()-50;
        int hB = baseColor.getBlue()-50;
        if(hR < 0 ) {
            hR = 0;
        }
        if(hG < 0) {
            hG = 0;
        }
        if(hB < 0) {
            hB = 0;
        }
        highlightColor = new Color(hR, hG, hB);
        int hR2 = baseColor.getRed()-100;
        int hG2 = baseColor.getGreen()-100;
        int hB2 = baseColor.getBlue()-100;
        if(hR2 < 0 ) {
            hR2 = 0;
        }
        if(hG2 < 0) {
            hG2 = 0;
        }
        if(hB2 < 0) {
            hB2 = 0;
        }
        highlightColor2 = new Color(hR2, hG2, hB2);
    }

    public Color getClr() {
        return clr;
    }

    public void highlight() {
        clr = highlightColor;
    }

    public void unHighlight() {
        clr = baseColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean b) {
        selected = b;
    }

    public boolean isWireColored() {
        return wireColored;
    }

    public void resetWireColor() {
        baseColor = this.getColor();
        clr = baseColor;
        wireColored = false;
    }

    public void selectionHighlight() {
        baseColor = highlightColor2;
        clr = baseColor;
        wireColored = true;
    }

    public void drawWire(Graphics g, Grid grid) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke wireStroke;
        wireStroke = new BasicStroke(3);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(wireStroke);
        //g2.setColor(this.getColor());
        int cellWidth = grid.getCellWidth();
        int max = grid.toXPosOnWindow(cellWidth + grid.getEks());
        x1 = grid.toXPosOnWindow(x1 * cellWidth)+(max/5*4);
        y1 = grid.toYPosOnWindow(y1 * cellWidth)+max/2;
        g2.drawLine(x1, y1, x2, y2);
    }

    public void drawWire(Graphics g, Grid grid, SparseMatrix<Operator> cells) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke wireStroke;
        if(mouseCovering) {
            wireStroke = new BasicStroke(5);
        }
        else {
            wireStroke = new BasicStroke(3);
        }
        //wireStroke = new BasicStroke(3);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int cellWidth = grid.getCellWidth();
        int max = grid.toXPosOnWindow(cellWidth + grid.getEks());

        xOne = grid.toXPosOnWindow(x1 * cellWidth)+(max/5*4);
        xTwo = grid.toXPosOnWindow(x2 * cellWidth)+max/5;
        yOne = grid.toYPosOnWindow(y1 * cellWidth)+max/2;
        yTwo = grid.toYPosOnWindow(y2 * cellWidth)+max/2;

        if(cells.get(x2, y2) instanceof Operator2I) {
            if(cells.get(x2, y2).getPrev1() == cells.get(x1, y1)) { //First input
                yTwo = grid.toYPosOnWindow(y2* cellWidth)+(max/3);
            }
            else { //Second input
                yTwo = grid.toYPosOnWindow(y2* cellWidth)+(max/3*2);
            }
        }
        else if(cells.get(x2, y2) instanceof Custom) {
            //else if Custom, find the amount of inputs and put it in the first open one
            ArrayList<Operator> temp = ((Custom) cells.get(x2, y2)).getInputs();
            for (int i = 0; i < temp.size(); i++){
                if (temp.get(i).getPrev1() == cells.get(x1, y1)){
                    yTwo = grid.toYPosOnWindow(y2* cellWidth)+(max/(temp.size()+1) * (i + 1));
                }
            }
        }

        //eventually will need to have outputs if blocks have multiple outputs
        if(selected) {
            Stroke selectedStroke = new BasicStroke(10);
            g2.setColor(Color.BLACK);
            g2.setStroke(selectedStroke);
            g2.drawLine(xOne, yOne, xTwo, yTwo);
        }
        g2.setStroke(wireStroke);
        g2.setColor(this.getColor());
        g2.drawLine(xOne, yOne, xTwo, yTwo);

    }

    public int getxOne() {
        return xOne;
    }

    public int getyOne() {
        return yOne;
    }

    public int getxTwo() {
        return xTwo;
    }

    public int getyTwo() {
        return yTwo;
    }

    public boolean contains(int x, int y) {
        return Line2D.ptSegDist(xOne, yOne, xTwo, yTwo, x, y) < 7;
    }

    public void setMouseCovering(Boolean b) {
        mouseCovering = b;
    }


}
