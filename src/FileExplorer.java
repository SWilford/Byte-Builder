
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileExplorer {



    private File file;


    public FileExplorer() {

    }

    public void selectFile() {

        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        File[] f = fd.getFiles();
        if(f.length > 0){
            System.out.println(fd.getFiles()[0].getAbsolutePath());
        }

    }

    public File getFile() {
        return file;
    }






}
