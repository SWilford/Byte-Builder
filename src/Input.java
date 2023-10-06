public class Input extends Operator{ //end of custom block segment
    public Input(){
        super(null);
    }
    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
