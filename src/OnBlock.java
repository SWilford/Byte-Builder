public class OnBlock extends Operator{
    public OnBlock(int r, int c){
        super(r, c, null);
    }
    public boolean getOutput(){ //will always return an on signal
        return true;
    }
}
