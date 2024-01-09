import java.util.*;

public class SparseMatrix<anyType> implements Matrixable<anyType>{
    private LinkedList<Cell<anyType>> list;
    private int numRows, numCols;
    private char blankChar;

    public SparseMatrix(int r, int c){
        list = new LinkedList();
        numRows = r;
        numCols = c;
        blankChar = '-';
    }
    /** returns a value corresponding to row and col
     *   @param  r    the row location; r >=0 and r is less than the number of rows.
     *   @param  c    the column location; c >=0 and c is less than the number of columns.
     *  @return the "key" given to the corresponding row r and column c
     */
    public int getKey(int r, int c){
        return (r * numCols) + c;
    }

    /** returns the element at row r, col c.
     *   @param  r    the row location; r >=0 and r is less than the number of rows.
     *   @param  c    the column location; c >=0 and c is less than the number of columns.
     *   @return the element at row r, column c, return null if r or c are invalid.
     */
    public anyType get(int r, int c){
        if ((r >= 0 && r < numRows) && (c >= 0 && c < numCols)){
            for (int i = 0; i < list.size() && list.get(i).getRow() <= r; i++){
                if (list.get(i).equals(r, c)){
                    return (anyType) list.get(i).getValue();
                }
            }
        }
        return null;
    }

    /** changes the element at row r, col c to a new value, returning the old value that was there.
     *   @param  r    the row location; r >=0 and r is less than the number of rows.
     *   @param  c    the column location; c >=0 and c is less than the number of columns.
     *   @param  x    a non-null anyType object
     *   @return the element that was at row r, column c before it was changed to x, return null if r or c are invalid.
     */
    public anyType set(int r, int c, anyType x){
        if ((r >= 0 && r < numRows) && (c >= 0 && c < numCols) && (x != null)){
            for (int i = 0; i < list.size() && list.get(i).getRow() <= r; i++){
                if (list.get(i).equals(r, c)){
                    return (anyType) list.set(i, new Cell(r, c, x)).getValue();
                }
            }
        }
        return null;
    }

    /** adds a new element at row r, col c.
     *   @param  r    the row location; r >=0 and r is less than the number of rows.
     *   @param  c    the column location; c >=0 and c is less than the number of columns.
     *   @param  x    a non-null anyType object
     *   @return true if r and c are valid and the item was added, false if r or c are out-of-bounds with no item added
     */
    public boolean add(int r, int c, anyType x){
        if ((r >= 0 && r < numRows) && (c >= 0 && c < numCols) && (x != null)){
            int key = getKey(r, c);
            if (isEmpty() || key > getKey(list.getLast().getRow(), list.getLast().getCol())){
                return list.add(new Cell(r, c, x));
            }
            else{
                for (int i = 0; i < list.size(); i++){
                    int loopKey = getKey(list.get(i).getRow(), list.get(i).getCol());
                    if (loopKey == key){  //replaces element with the same row and column
                        set(r, c, x);
                        return true;
                    }
                    else if (loopKey > key){
                        list.add(i, new Cell(r, c, x));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** removes and returns the element at row r, col c.
     *   @param  r    the row location; r >=0 and r is less than the number of rows.
     *   @param  c    the column location; c >=0 and c is less than the number of columns.
     *   @return the element that was at row r, column c before it was removed, return null if r or c are invalid.
     */
    public anyType remove(int r, int c){
        if ((r >= 0 && r < numRows) && (c >= 0 && c < numCols)){
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).equals(r, c)){
                    return (anyType) list.remove(i).getValue();
                }
            }
        }
        return null;
    }

    /** returns the number of logical elements added to the matrix.
     *   @return the number of logical elements added to the matrix.
     */
    public int size(){			//returns # actual elements stored
        return list.size();
    }

    /** returns the number of rows in the matrix.
     *   @return the number of rows in the matrix.
     */
    public int numRows(){		//returns # rows set in constructor
        return numRows;
    }

    /** returns the number of columns in the matrix.
     *   @return the number of columns in the matrix.
     */
    public int numColumns(){	//returns # cols set in constructor
        return numCols;
    }

    /** returns the Sparse Matrix as a String readable to the client
     *  @return the Sparse Matrix as a String
     */
    public String toString(){
        String str = "";
        for (int r = 0; r < numRows; r++){
            for (int c = 0; c < numCols; c++){
                if (get(r, c) == null){
                    str += blankChar + " ";
                }
                else{
                    str += get(r, c) + " ";
                }
            }
            str += "\n";
        }
        return str;
    }
    /** returns true or false whether x is in the matrix
     *  @param x    being the value to search for
     *  @return  true if x is in list, false otherwise
     */
    public boolean contains(anyType x){
        return list.contains(x);
    }

    /** returns the location of x in the sparse matrix
     *  @param x    being the value to find row and col of
     *  @return  row and col as a integer array
     */
    public int[] getLocation(anyType x){	//returns location [r,c] of where x exists in list, null otherwise
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(x)){
                return new int[]{list.get(i).getRow(), list.get(i).getCol()};
            }
        }
        return null;
    }

    /** returns the matrix as a 2D array
     *  @return  the matrix as a 2D array
     */
    public Object[][] toArray(){			//returns equivalent structure in 2-D array form
        Object[][] arr = new Object[numRows][numCols];
        for (int r = 0; r < numRows; r++){
            for (int c = 0; c < numCols; c++){
                arr[r][c] = get(r, c);
            }
        }
        return arr;
    }

    /** returns true or false whether the matrix is empty
     *  @return  true if the matrix has no elements, false otherwise
     */
    public boolean isEmpty(){				//returns true if there are no actual elements stored
        return (list.size() == 0);
    }

    /** clears all elements out of the matrix and retains dimensions
     */
    public void clear(){						//clears all elements out of the list
        list = new LinkedList();
    }
    /** sets the blank character in the client of the matrix to said char
     *  @param blank   char that determines the symbol for nothing when doing toString of matrix
     */
    public void setBlank(char blank){	   //allows the client to set the character that a blank spot in the array is
        blankChar = blank;                  //represented by in String toString()
    }
}