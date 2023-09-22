public class OnBlock extends Operator{
    public OnBlock(){
        super(null);
    }
    public boolean getOutput(){ //will always return an on signal
        return true;
    }
}
