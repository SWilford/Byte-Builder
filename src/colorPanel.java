import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class colorPanel extends JPanel {

    private final ImageIcon RedWire = new ImageIcon("Images/RedWire.png");
    private final ImageIcon GreenWire = new ImageIcon("Images/GreenWire.png");
    private final ImageIcon BlueWire = new ImageIcon("Images/BlueWire.png");
    private final ImageIcon OrangeWire = new ImageIcon("Images/OrangeWire.png");
    private final ImageIcon YellowWire = new ImageIcon("Images/YellowWire.png");
    private final ImageIcon WhiteWire = new ImageIcon("Images/WhiteWire.png");

    ArrayList<Button> wireColors = new ArrayList<>();

    public colorPanel() {

        this.setLayout(new GridLayout());

        //createWireColors();
    }

    private void createWireColors() { //Initializes buttons for selecting wire color
        Shape s = new Rectangle(0, 0, 40, 40);
        Button b = new Button(s, "red", RedWire);
        wireColors.add(b);
        Shape s2 = new Rectangle(40, 0, 40, 40);
        Button b2 = new Button(s2, "green", GreenWire);
        wireColors.add(b2);
        Shape s3 = new Rectangle(0, 40, 40, 40);
        Button b3 = new Button(s3, "blue", BlueWire);
        wireColors.add(b3);
        Shape s4 = new Rectangle(40, 40, 40, 40);
        Button b4 = new Button(s4, "orange", OrangeWire);
        wireColors.add(b4);
        Shape s5 = new Rectangle(0, 80, 40, 40);
        Button b5 = new Button(s5, "yellow", YellowWire);
        wireColors.add(b5);
        Shape s6 = new Rectangle(40, 80, 40, 40);
        Button b6 = new Button(s6, "white", WhiteWire);
        wireColors.add(b6);
    }

    public ArrayList<Button> getWireColors () {
        return wireColors;
    }


}
