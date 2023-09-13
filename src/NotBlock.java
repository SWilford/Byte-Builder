public class NotBlock extends Operator{
    public boolean getOutput(){
        return !previous1.getOutput();
    }
}
