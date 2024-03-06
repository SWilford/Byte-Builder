
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileExplorer {

    public FileExplorer() {

    }

    public File selectFile(Boolean bool) {
        FileDialog fd;
        if(bool) {
            fd = new FileDialog(new JFrame(), "Load Custom Component");
            fd.setDirectory("Saves\\");
            fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
        }
        else {
            fd = new FileDialog(new JFrame(), "Select Image for Custom Component", FileDialog.LOAD);
            fd.setDirectory("ImageSaves\\");
            fd.setFilenameFilter((dir, name) -> name.endsWith(".png"));

        }
        fd.setMode(FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile() == null) {
            return new File("");
        }
        else if(bool) {
            if(fd.getFile().endsWith(".txt")) {
                return new File("Saves/" + fd.getFile());
            }
            else {
                JOptionPane.showMessageDialog(null, "Unsupported file type. \".txt\" file type is supported", "Error", JOptionPane.ERROR_MESSAGE);
                selectFile(true);
            }

        }
        else {
            if(fd.getFile().endsWith(".png")) {
                return new File("ImageSaves/" + fd.getFile());
            }
            else {
                JOptionPane.showMessageDialog(null, "Unsupported file type. \".png\" file type is supported", "Error", JOptionPane.ERROR_MESSAGE);
                selectFile(false);
            }
        }
        return new File("");
    }

    public String saveFile(String filename) {
        FileDialog fd = new FileDialog(new JFrame(), "Save Custom Component");
        fd.setDirectory("Saves\\");
        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
        fd.setFile(filename);
        fd.setMode(FileDialog.SAVE);
        fd.setVisible(true);
        if (fd.getFile() != null) {
            File file = new File(fd.getDirectory(), fd.getFile());
            if(!file.getPath().endsWith(".txt")) {
                return file.getPath() + ".txt";
            }
            else return file.getPath();
        }
        return null;
    }








}
