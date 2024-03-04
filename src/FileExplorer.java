
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileExplorer {

    public FileExplorer() {

    }

    public File selectFile(Boolean bool) {
        FileDialog fd;
        if(bool) {
            fd = new FileDialog(new JFrame(), "Load Custom Component", FileDialog.LOAD);
            fd.setDirectory("Saves/");
        }
        else {
            fd = new FileDialog(new JFrame(), "Select Image for Custom Component", FileDialog.LOAD);
            fd.setDirectory("ImageSaves/");
        }
        fd.setVisible(true);
        if(bool) {
            fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
            return new File("Saves/" + fd.getFile());
        }
        else {
            return new File("ImageSaves/" + fd.getFile());
        }
    }

    public String saveFile(String filename) {
        FileDialog fd = new FileDialog(new JFrame(), "Save Custom Component", FileDialog.SAVE);
        //fd.setDirectory("Saves/");
        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
        fd.setFile(filename);
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
