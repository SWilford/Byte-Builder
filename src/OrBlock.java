public class OrBlock extends Operator2I{
    public OrBlock(Operator n1, Operator n2){
        previous1 = n1;
        previous1.setNext(this);
        previous2 = n2;
        previous2.setNext(this);
    }
    public boolean getOutput(){
        return (new NotBlock(new AndBlock(new NotBlock(previous1), new NotBlock(previous2)))).getOutput();
    }
}
