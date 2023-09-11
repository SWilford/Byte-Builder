public class AndBlock extends Operator{
    public boolean getOutput(){
        return previous1.getOutput() && previous2.getOutput();
        //todo make a source class?
    }
}
