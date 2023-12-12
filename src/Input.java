public class Input extends Operator{ //end of custom block segment
    public Input(int r, int c){
        super(r, c, null);
    }

    public Input(int row, int col, String color) {
        super(row, col, null, color);
    }

    public boolean getOutput(){
        if (previous1 == null){
            return false;
        }
        return previous1.getOutput();
    }
}
