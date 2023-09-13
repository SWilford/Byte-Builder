public class NotBlock extends Operator{

    public NotBlock(Operator n){
        previous1 = n;
    }
    public boolean getOutput(){
        return !previous1.getOutput();
    }
}
