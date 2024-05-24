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
        LinkedList<String[]> inputs = new LinkedList<>();
        Scanner input = new Scanner(new FileReader(fileName));
        while (input.hasNextLine()){
            String[] thing = input.nextLine().split(",");
            int col = Integer.parseInt(thing[4].trim());
            int row = Integer.parseInt(thing[3].trim());
            if (thing.length > 6){
                inputs.add(new String[]{thing[5].trim(), thing[6].trim()});
            } else if (thing.length == 6) {
                inputs.add(new String[]{thing[5].trim(), null});
            }
            else{
                inputs.add(new String[]{null, null});

            }

            String color = thing[1].trim();
            String color2 = thing[2].trim();

            if (thing[0].contains("(")){ //custom
                String name = thing[0].substring(thing[0].indexOf("(")+1,thing[0].indexOf(")"));
                arr.add(new Custom(row, col, Manager.readFile("Saves/" + name + ".txt"), name));
            }
            else {
                switch (thing[0].trim()) {
                    case "NotBlock" ->  arr.add(new NotBlock(row, col, null, color));
                    case "AndBlock" ->  arr.add(new AndBlock(row, col, null, null, color, color2));
                    case "OnBlock" ->   arr.add(new OnBlock(row, col));
                    case "Light" ->     arr.add(new Light(row, col, null, color));
                    case "Switch" ->    arr.add(new Switch(row, col));
                    case "Input" ->     arr.add(new Input(row, col, color));
                    case "Output" ->    arr.add(new Output(row, col, null, color));
                }
            }
        }
        for (Operator operator : arr) { //buffer for inputs
            String[] temp = inputs.removeFirst();
            if (operator instanceof Custom){
                if (temp[0] != null) {
                    ((Custom) operator).getFirstEmpty().setPrev1(arr.get(Integer.parseInt(temp[0])));
                }
                if (temp[1] != null) {
                    ((Custom) operator).getFirstEmpty().setPrev1(arr.get(Integer.parseInt(temp[1])));
                }
            }
            else {
                if (temp[0] != null) {
                    operator.setPrev1(arr.get(Integer.parseInt(temp[0])));
                }
                if (temp[1] != null) {
                    ((Operator2I) operator).setPrev2(arr.get(Integer.parseInt(temp[1])));
                }
            }
        }
        input.close();
        return arr;
    }

    public static void writeToFile(LinkedList<Operator> array, String filename) throws IOException
    {
        String imagePath = "/ImageSaves/OrImage.png"; //Will be actual path to image
        System.setOut(new PrintStream(new FileOutputStream(filename)));
        System.out.print(imagePath);
        for(int i = 0; i < array.size(); i++){
            Operator op = array.get(i);
            String n = op.getClass().getName();
            if (n.equals("Custom")){
                n += "("+ ((Custom)op).getName()+")";
            }
            String color = op.getColor();
            String color2 = "null";
            if (op instanceof Operator2I) {
                color2 = ((Operator2I) op).getColor2();
            }
            int col = op.getCol();
            int row = op.getRow();


            String line = n + ", " + color + ", " + color2 + ", " + col + ", " + row;

            if (op instanceof Custom){
                for (Operator temp : ((Custom) op).getInputs()){
                    line += ", " + array.indexOf(temp.getPrev1());
                }
            }
            else {
                if (op.getPrev1() != null) {
                    line += ", " + array.indexOf(op.getPrev1());
                }
                if (op instanceof Operator2I && ((Operator2I) op).getPrev2() != null) {
                    line += ", " + array.indexOf(((Operator2I) op).getPrev2());
                }
            }
            System.out.println(line);
        }
        System.out.flush();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
