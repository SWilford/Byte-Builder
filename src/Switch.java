public class Switch extends Operator{
    private boolean state; //holds a boolean on itself rather doing calculations from previous operators

    public Switch(int r, int c){
        super(r, c,null);
        state = false; //switch default to false
    }
    public boolean getOutput(){
        return state;
    }
    public void switchInput(){ //switches what the signal will be
        state = !state;
    }
}
