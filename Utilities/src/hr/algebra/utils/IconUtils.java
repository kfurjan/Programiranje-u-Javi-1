package hr.algebra.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconUtils {

    public static ImageIcon createIcon(String path, int width, int height) throws IOException {

        System.out.println(path);
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        System.out.println(bufferedImage);
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        System.out.println(image);
        return new ImageIcon(image);
    }

}
