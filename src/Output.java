public class Output extends Operator{ //end of custom block segment
    public Output(Operator n){
        super(n);
    }
    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
