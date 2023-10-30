import java.awt.*;
import java.util.*;
import java.io.*;
public class FileManager {
    /** Returns the size of the inputed file.
     * @param fileName    String of name of file to find size of
     * @return    Returns size of fileName
     */
    private static ArrayList<String> colors;
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
        colors = new ArrayList<>();
        LinkedList<Operator> arr = new LinkedList<>();
        LinkedList<Integer> inputs = new LinkedList<>();
        Scanner input = new Scanner(new FileReader(fileName));
        while (input.hasNextLine()){
            String[] thing = input.nextLine().split(",");
            int col = Integer.parseInt(thing[3].trim());
            System.out.println(col);
            int row = Integer.parseInt(thing[2].trim());
            if (thing.length > 4){
                inputs.add(Integer.parseInt(thing[4].trim()));
                if (thing.length > 5){
                    inputs.add(Integer.parseInt(thing[5].trim()));
                }
            }
            if (!thing[1].trim().equals("null")){
                for (int i = 4; i < thing.length; i++)
                    colors.add(thing[1].trim());
            }
            switch (thing[0].trim()){
                case "NotBlock" ->  arr.add(new NotBlock(row, col, null));
                case "AndBlock" ->  arr.add(new AndBlock(row, col, null, null));
                case "OnBlock" ->   arr.add(new OnBlock(row, col));
                case "Light" ->     arr.add(new Light(row, col, null));
                case "Switch" ->    arr.add(new Switch(row, col));
                case "Input" ->     arr.add(new Input(row, col));
                case "Output" ->    arr.add(new Output(row, col, null));
            }
        }
        for (Operator operator : arr) {
            if (!(operator instanceof Input || operator instanceof Switch)) {
                operator.setPrev1(arr.get(inputs.removeFirst()));
                if (operator instanceof Operator2I) {
                    ((Operator2I) operator).setPrev2(arr.get(inputs.removeFirst()));
                }
            }
        }
        input.close();
        return arr;
    }

    public static ArrayList<String> getColors(){
        return colors;
    }
}
