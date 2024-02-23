
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileExplorer {

    public FileExplorer() {

    }

    public File selectFile() {
        FileDialog fd = new FileDialog(new JFrame(), "Load", FileDialog.LOAD);
        fd.setDirectory("Saves/");
        fd.setVisible(true);
        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
        return new File("Saves/" + fd.getFile());
    }








}
