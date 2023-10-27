public class Input extends Operator{ //end of custom block segment
    public Input(int r, int c){
        super(r, c, null);
    }
    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
