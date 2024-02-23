import java.awt.*;
import java.sql.Array;
import java.text.ParsePosition;
import java.util.*;
import java.io.*;

public class Manager {
    /** Returns the size of the inputed file.
     * @param fileName    String of name of file to find size of
     * @return    Returns size of fileName
     */
    public static int getFileSize(String fileName)throws IOException
    {
        Scanner input = new Scanner(new FileReader(fileName));
        int size=0;
        while (input.hasNextLine())				//while there is another line in the file
        {
            size++;										//add to the size
            input.nextLine();							//go to the next line in the file
        }
        input.close();									//always close the files when you are done
        return size;
    }

    public static String getImage(String fileName) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(new FileReader(fileName));
        return input.nextLine();
    }

    public static SparseMatrix<Operator> readFile(String fileName) throws IOException, ClassNotFoundException { //returns array of operators that have their connections
        SparseMatrix<Operator> arr = new SparseMatrix<>();
        Scanner input = new Scanner(new FileReader(fileName));
        HashMap<Point, LinkedList<Point>> inputs = new HashMap<>(); //key is starting point, value is list of previous
        input.nextLine();
        while (input.hasNextLine()){
            String[] parsed = input.nextLine().split(",");
            String name = parsed[0].trim();
            String wire1 = parsed[1].trim();
            String wire2 = parsed[2].trim();
            int col = Integer.parseInt(parsed[3].trim());
            int row = Integer.parseInt(parsed[4].trim());
            inputs.put(new Point(col, row), new LinkedList<>());
            for (int i = 5; i < parsed.length; i++){
                String[] temp = parsed[i].split("\\.");
                inputs.get(new Point(col, row)).add(new Point(Integer.parseInt(temp[0].trim().substring(1)), Integer.parseInt(temp[1].trim().substring(0, 1)))); //adds new input coord do map
            }
            if (name.contains("(")){ //custom
                String customname = name.substring(name.indexOf("(")+1,name.indexOf(")"));
                arr.set(col, row, new Custom(row, col, FileManager.readFile("Saves/" + customname + ".txt"), customname));//deal with colors
            }
            else {
                switch (name) {
                    case "NotBlock" ->  arr.set(col, row, new NotBlock(row, col, null, wire1));
                    case "AndBlock" ->  arr.set(col, row, new AndBlock(row, col, null, null, wire1, wire2));
                    case "OnBlock" ->   arr.set(col, row, new OnBlock(row, col));
                    case "Light" ->     arr.set(col, row, new Light(row, col, null, wire1));
                    case "Switch" ->    arr.set(col, row, new Switch(row, col));
                    case "Input" ->     arr.set(col, row, new Input(row, col, wire1));
                    case "Output" ->    arr.set(col, row, new Output(row, col, null, wire1));
                }
            }
        }
        for (Operator operator : arr) { //buffer for inputs
            LinkedList<Point> temp = inputs.get(new Point(operator.getCol(), operator.getRow())); //list of inputs for particular operator
            if (operator instanceof Custom){
                for (Point p : temp){
                    ((Custom) operator).getFirstEmpty().setPrev1(arr.get((int) p.getX(), (int) p.getY()));
                }
            }
            else {
                if (!temp.isEmpty() && temp.getFirst() != null) {
                    operator.setPrev1(arr.get((int) temp.getFirst().getX(), (int) temp.getFirst().getY()));
                }
                if (temp.size() > 1 && temp.get(1) != null) { //basically asking is there 2 inputs
                    ((Operator2I) operator).setPrev2(arr.get((int) temp.get(1).getX(), (int) temp.get(1).getY()));
                }
            }
        }
        input.close();
        return arr;
    }

    public static void writeToFile(SparseMatrix<Operator> n, String filename) throws IOException
    {
        System.setOut(new PrintStream(new FileOutputStream(filename)));
        for(Operator temp : n) {
            String line = temp.toString();

            if (temp instanceof Custom){
                for (Operator inputs : ((Custom) temp).getInputs()){
                    line += ", (" + inputs.getCol() + ". " + inputs.getRow() + ")";
                }
            }
            else {
                if (temp.getPrev1() != null) {
                    line += ", (" + temp.getPrev1().getCol() + ". " + temp.getPrev1().getRow() + ")"; //periods in place of commas so that it won't split up the coordinates
                }
                if (temp instanceof Operator2I && ((Operator2I) temp).getPrev2() != null) {
                    line += ", (" + ((Operator2I) temp).getPrev2().getCol() + ". " + ((Operator2I) temp).getPrev2().getRow() + ")";
                }
            }
            System.out.println(line);
        }
        System.out.flush();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
