/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watersys.Utilities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author balis
 */
public class XImage {

    public static Image getAppIcon(){
        URL url = XImage.class.getResource("/watersys/Icon/logo.jpg");
        return new ImageIcon(url).getImage();
    }
    public static ImageIcon getAppIconf() {
        URL url = XImage.class.getResource("watersys/Icon/logo.jpg");
        return new ImageIcon(url);
    }

    public static void save(File src) {
        File dir = new File("logos", src.getName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            Path source = Paths.get(src.getAbsolutePath());
            Path destination = Paths.get(dir.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ImageIcon read(String filename) {
        File path = new File("logos", filename);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(350, 325, Image.SCALE_DEFAULT));
    }
}
