import java.awt.*;

public class cellMessage extends changeMessage {
    private final Operator operator;
    private final Point point;

    public cellMessage(Operator op, Point p) {
        operator = op;
        point = p;
    }

    public Operator getOperator() {
        return operator;
    }

    public Point getPoint() {
        return point;
    }

}
