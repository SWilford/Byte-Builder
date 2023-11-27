import java.awt.*;

//takes two inputs rather than default 1
public abstract class Operator2I extends Operator{
    protected Operator previous2;

    protected String color2;


    public Operator2I(int r, int c, Operator n1, Operator n2){
        super(r, c, n1);
        previous2 = n2;
        color2 = "red";
    }

    public Operator2I(int r, int c, Operator n1, Operator n2, String clr, String colour){
        super(r, c, n1, clr);
        previous2 = n2;
        color2 = colour;
    }

    public Operator getPrev2(){
        return previous2;
    }
    public boolean isFull(){
        return (previous1 != null && previous2 != null);
    }
    public void setPrev2(Operator n){
        previous2 = n;
    }
    public abstract boolean getOutput();

    public void setColor2 (String c) {
        color2 = c;
    }

    public String getColor2() {
        return color2;
    }
}
