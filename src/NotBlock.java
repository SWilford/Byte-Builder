public class NotBlock extends Operator{

    public NotBlock(Operator n){
        previous1 = n;
        previous1.setNext(this);
    }
    public boolean getOutput(){ //returns opposite of previous output
        return !previous1.getOutput();
    }
}
