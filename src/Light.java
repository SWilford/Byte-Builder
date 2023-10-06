public class Light extends Operator{

    public Light(Operator n){
        super(n);
    }
    public boolean getOutput() {
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput(); //merely returns the signal it is given, used to show the signal in the game, it can also be passed to later operators if need be
    }
}
