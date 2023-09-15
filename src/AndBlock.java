public class AndBlock extends Operator2I{

    public AndBlock(Operator n1, Operator n2){
        previous1 = n1; // set outputs of previous to this block
        previous1.setNext(this); //sets the previous block's next to this object
        previous2 = n2;
        previous2.setNext(this);

    }
    public boolean getOutput(){
        return previous1.getOutput() && previous2.getOutput();
    }
}
