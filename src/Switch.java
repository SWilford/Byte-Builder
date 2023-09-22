public class Switch extends Operator{
    private boolean state; //holds a boolean on itself rather doing calculations from previous operators

    public Switch(){
        super(null);
        state = false; //switch default to false
    }
    public boolean getOutput(){
        return state;
    }
    public void switchInput(){ //switches what the signal will be
        state = !state;
    }
}
