//takes two inputs rather than default 1
public abstract class Operator2I extends Operator{
    protected Operator previous2;

    public Operator2I(Operator n1, Operator n2){
        super(n1);
        previous2 = n2;
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
}
