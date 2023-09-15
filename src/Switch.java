public class Switch extends Operator{
    private boolean state; //holds a boolean on itself rather doing calculations from previous operators

    public Switch(){
        state = false;
        previous1 = null; //no previous
    }
    public boolean getOutput(){
        return state;
    }
    public void switchInput(){ //switches what the signal will be
        state = !state;
    }
}
