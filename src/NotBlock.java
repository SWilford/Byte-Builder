public class NotBlock extends Operator{

    public NotBlock(Operator n){
        previous1 = n;
        if (previous1 != null)
            previous1.setNext(this);
    }
    public boolean getOutput(){ //returns opposite of previous output
        return !previous1.getOutput();
    }
}
