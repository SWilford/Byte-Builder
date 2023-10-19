import java.awt.*;
import java.awt.geom.Line2D;

public class Wire {
    private final int x1, y1, x2, y2;
    private final String color;

    public boolean selected;

    private Color clr, baseColor;
    private final Color highlightColor, highlightColor2;

    public boolean wireColored;

    public Wire(int a, int b, int c, int d, String cl){
        x1 = a;
        y1 = b;
        x2 = c;
        y2 = d;
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
        int hR2 = baseColor.getRed()+50;
        int hG2 = baseColor.getGreen()+50;
        int hB2 = baseColor.getBlue()+50;
        if(hR2 > 255 ) {
            hR2 = 255;
        }
        if(hG2 > 255) {
            hG2 = 255;
        }
        if(hB2 > 255) {
            hB2 = 255;
        }
        highlightColor2 = new Color(hR2, hG2, hB2);

        wireColored = false;

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

    public void drawWire(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke wireStroke = new BasicStroke(3);
        g2.setColor(clr);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(wireStroke);
        g2.setColor(this.clr);
        g2.drawLine(x1, y1, x2, y2);
    }

    public boolean contains(int x, int y) {
        return new Line2D.Double(x1,y1,x2,y2).ptLineDist(x, y)<=1.5;
    }


}