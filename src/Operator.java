
public abstract class Operator {
    protected Operator previous1;
    protected int row, col;

    protected String color;

    public Operator(int r, int c, Operator n){
        previous1 = n;
        row = r;
        col = c;
        color = "red";
    } //add

    public Operator(int r, int c, Operator n, String colour) {
        previous1 = n;
        row = r;
        col = c;
        color = colour;
    }

    public Operator getPrev1() {
        return previous1;
    }
    public boolean isFull(){
        return (previous1 != null);
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }


    public void setPrev1(Operator n){
        previous1 = n;
    }
    public abstract boolean getOutput();

    public String toString(){
        return this.getClass().getName() + ", " + color + ", null, " + col + ", " + row;
    }

    public void setColor(String c) {
        color = c;
    }

    public String getColor() {
        return color;
    }

}
