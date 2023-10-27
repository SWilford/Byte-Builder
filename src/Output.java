public class Output extends Operator{ //end of custom block segment
    public Output(int r, int c, Operator n){
        super(r, c, n);
    }
    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
