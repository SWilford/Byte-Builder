import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

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
        frame.addKeyListener(new listen());		//Get input from the keyboard
    }

    public static class listen implements KeyListener{ //keyboard stuff
        public void keyTyped(KeyEvent e){}
        public void keyPressed(KeyEvent e){
            screen.processUserInput(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e){}
    }
}
