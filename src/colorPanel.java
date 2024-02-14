import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class colorPanel extends JPanel {

    ArrayList<colorPanelButton> things = new ArrayList<>();

    public colorPanel(Toolbar to) {

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(new Color(48, 48, 48));

        things.add(new colorPanelButton("red", to, this));
        things.add(new colorPanelButton("green", to, this));
        things.add(new colorPanelButton("blue", to, this));
        things.add(new colorPanelButton("orange", to, this));
        things.add(new colorPanelButton("yellow", to, this));
        things.add(new colorPanelButton("white", to, this));

        for(colorPanelButton c : things) {
            this.add(c);
        }

    }

    public ArrayList<colorPanelButton> getThings() {
        return things;
    }






}
