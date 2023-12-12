public class NotBlock extends Operator{

    public NotBlock(int r, int c, Operator n){
        super(r, c, n);
    }

    public NotBlock(int row, int col, Operator n, String color) {
        super(row, col, n, color);
    }

    public boolean getOutput(){ //returns opposite of previous output
        if (previous1 == null){
            return false;
        }
        return !previous1.getOutput();
    }
}
