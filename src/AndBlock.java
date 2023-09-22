public class AndBlock extends Operator2I{

    public AndBlock(Operator n1, Operator n2){
        super(n1, n2);
    }
    public boolean getOutput(){
        return previous1.getOutput() && previous2.getOutput();
    }
}
