public class Output extends Operator{ //end of custom block segment
    public Output(int r, int c, Operator n){
        super(r, c, n);
    }

    public Output(int row, int col, Operator n, String color) {
        super(row, col, n, color);
    }

    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
