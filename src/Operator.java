public abstract class Operator {
    protected Operator leadsTo; //block thing only has 1 output
    protected Operator previous1;

    public Operator(Operator n){
        previous1 = n;
        if (previous1 != null)
            previous1.setNext(this);
    }
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
        if (previous1 != null){
            n.setNext(this);
        }
    }
    public abstract boolean getOutput();
}
