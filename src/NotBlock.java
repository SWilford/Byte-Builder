public class NotBlock extends Operator{

    public NotBlock(Operator n){
        previous1 = n;
    }
    public boolean getOutput(){ //returns opposite of previous output
        return !previous1.getOutput();
    }
}
