public class Cell<anyType>{
    private int row, col;
    private anyType value;

    public Cell(int r, int c, anyType v){
        row = r;
        col = c;
        value = v;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public anyType getValue(){
        return (anyType) value;
    }

    public boolean equals(int r, int c){
        return (r == row && c == col);
    }

    public String toString(){
        return "" + value + "("+row+", "+col+")";
    }
}