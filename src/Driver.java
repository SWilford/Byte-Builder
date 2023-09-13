import javax.swing.*;

public class Driver {

    public static GUI screen;
    public static void main(String[] args) {
        screen = new GUI();
        JFrame frame = new JFrame("Byte Builder");
        frame.setSize(1454,1237);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(screen);
        frame.setVisible(true);
    }
}
