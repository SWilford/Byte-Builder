public abstract class Operator {
    protected Operator leadsTo; //block thing only has 1 output
    protected Operator previous1;

    public Operator getNext() {
        return leadsTo;
    }
    public Operator getPrev1() {
        return previous1;
    }
    public void setNext(Operator n){
        leadsTo = n;
    }
    public void setPrev1(Operator n){
        previous1 = n;
    }
    public abstract boolean getOutput();
}
