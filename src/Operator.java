public class Operator {
    protected Operator leadsTo; //block thing only has 1 output
    protected Operator previous1;
    protected Operator previous2; //could be null if only takes 1 input
    protected boolean source;   //if prev 1 and 2 are null then its a source block
                                // and you check n for signal

    public Operator getNext() {
        return leadsTo;
    }
    public Operator getPrev1() {
        return previous1;
    }
    public Operator getPrev2(){
        return previous2;
    }
    public void setNext(Operator n){
        leadsTo = n;
    }
    public void setPrev1(Operator n){
        previous1 = n;
    }
    public void setPrev2(Operator n){
        previous2 = n;
    }
    public boolean getOutput(){ //should run when there is no more previous
            return source;
    }
}
