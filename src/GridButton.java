import javax.swing.*;
import java.awt.*;

public class GridButton extends Button {
    private Operator operator;

    public GridButton(Shape s, String t, ImageIcon i, int r, int c, Operator o) {
        super(s, t, i, r, c);
        operator = o;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
