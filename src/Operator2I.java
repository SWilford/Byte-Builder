//takes two inputs rather than default 1
public class Operator2I extends Operator{
    protected Operator previous2;

    public Operator getPrev2(){
        return previous2;
    }
    public void setPrev2(Operator n){
        previous2 = n;
    }
    //This and Operator shouldn't be existing objects in the first place so this shouldn't run
    public boolean getOutput(){ //should run when there is no more previous
        if (previous1 == null){ //if previous1 is null then 2 is also null by default
            return source;      //and thus has no inputs, therefore taking source as output
        }
        else if (previous2 == null){ //previous1 only channel so just takes and outputs that
            return previous1.getOutput();
        }
        return false; //default operation false if nothing outputs to it
    }
}
