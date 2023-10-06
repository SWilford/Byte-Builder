public abstract class Operator {
    protected Operator previous1;

    public Operator(Operator n){
        previous1 = n;
    }

    public Operator getPrev1() {
        return previous1;
    }
    public boolean isFull(){
        return (previous1 != null);
    }

    public void setPrev1(Operator n){
        previous1 = n;
    }
    public abstract boolean getOutput();
}
