import java.awt.*;
import java.util.*;
import java.io.*;
public class FileManager {
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

    public static LinkedList<Operator> readFile(String fileName) throws IOException, ClassNotFoundException { //returns array of operators that have their connections
        LinkedList<Operator> arr = new LinkedList<>();
        Scanner input = new Scanner(new FileReader(fileName));
        while (input.hasNextLine()){
            String[] thing = input.nextLine().split(",");
            int col = Integer.parseInt(thing[2].trim());
            System.out.println(col);
            int row = Integer.parseInt(thing[1].trim());
            Operator input1 = null;
            Operator input2 = null;
            if (thing.length > 3){
                input1 = arr.get(Integer.parseInt(thing[3].trim()));
                if (thing.length > 4){
                    input2 = arr.get(Integer.parseInt(thing[4].trim()));
                }
            }
            switch (thing[0].trim()){
                case "NotBlock" ->  arr.add(new NotBlock(row, col, input1));
                case "AndBlock" ->  arr.add(new AndBlock(row, col, input1, input2));
                case "OnBlock" ->   arr.add(new OnBlock(row, col));
                case "Light" ->     arr.add(new Light(row, col, input1));
                case "Switch" ->    arr.add(new Switch(row, col));
                case "Input" ->     arr.add(new Input(row, col));
                case "Output" ->    arr.add(new Output(row, col, input1));
            }
        }
        input.close();
        return arr;
    }
}
