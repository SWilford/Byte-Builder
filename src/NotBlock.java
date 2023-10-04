public class NotBlock extends Operator{

    public NotBlock(Operator n){
        super(n);
    }
    public boolean getOutput(){ //returns opposite of previous output
        if (previous1 == null){
            return false;
        }
        return !previous1.getOutput();
    }
}
