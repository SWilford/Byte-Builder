public class AndBlock extends Operator2I{

    public AndBlock(int r, int c, Operator n1, Operator n2){
        super(r, c, n1, n2);
    }
    public boolean getOutput(){
        if (previous1 == null || previous2 == null){
            return false;
        }
        return previous1.getOutput() && previous2.getOutput();
    }
}
