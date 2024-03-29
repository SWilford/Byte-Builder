import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Driver {

    public static BuilderGUI screen;
    public static void main(String[] args) {
        screen = new BuilderGUI();
        JFrame frame = new JFrame("Byte Builder");
        frame.setSize(1450,1250);
        frame.setMinimumSize(new Dimension(1000, 800));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(screen);
        frame.setVisible(true);
        frame.addKeyListener(new listen());		//Get input from the keyboard
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screen.componentResized(e);
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                screen.componentResized(e);
            }
        });
    }

    public static class listen implements KeyListener{ //keyboard stuff
        public void keyTyped(KeyEvent e){}
        public void keyPressed(KeyEvent e){
            screen.toolbar.processUserInput(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e){

        }
    }
}
