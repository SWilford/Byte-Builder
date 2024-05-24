import java.lang.reflect.Array;
import java.util.*;
public class Custom extends Operator{
    private final ArrayList<Operator> inputs; //different way to handle inputs, only contains Input operators
    private final SparseMatrix<Operator> block;
    private final String name;

    public Custom(int r, int c, SparseMatrix<Operator> s, String n) {
        super(r, c, null);
        inputs = new ArrayList<Operator>();
        block = s;
        for (Operator b : block){
            if (b instanceof Input){
                inputs.add(b);
            }
        }
        name = n;
    }
    public int numInputs(){
        return inputs.size();
    }
    public ArrayList<Operator> getInputs(){
        return inputs;
    }
    public Operator getFirstEmpty(){
        for (Operator b : inputs){
            if (b.getPrev1() == null){
                return b;
            }
        }
        return null;
    }
    public boolean getOutput(){
        for (Operator b : block){
            if (b instanceof Output){
                return b.getOutput();
            }
        }
        return false;
    }

    public String toString(){
        return this.getClass().getName() + "(" + name + "), " + "red" + ", null, " + col + ", " + row;
    }

    public String getName() {
        return name;
    }
}