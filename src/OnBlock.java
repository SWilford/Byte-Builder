public class OnBlock extends Operator{
    public OnBlock(){
        previous1 = null; //no previous
    }
    public boolean getOutput(){ //will always return an on signal
        return true;
    }
}
