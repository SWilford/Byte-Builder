import java.awt.*;

public class wireMessage extends changeMessage{

    private final Wire wire;

    public wireMessage(Wire w) {
        wire = w;
    }


    public Wire getWire() {
        return wire;
    }
}
