//takes two inputs rather than default 1
public abstract class Operator2I extends Operator{
    protected Operator previous2;

    public Operator getPrev2(){
        return previous2;
    }
    public void setPrev2(Operator n){
        previous2 = n;
    }
    //This and Operator shouldn't be existing objects in the first place so this shouldn't run
    public abstract boolean getOutput();
}
