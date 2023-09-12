public class NotBlock extends Operator{
    public boolean getOutPut(){
        return !previous1.getOutput();
    }
}
