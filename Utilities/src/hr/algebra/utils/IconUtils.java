package hr.algebra.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class IconUtils {

    public static Optional<ImageIcon> createIcon(String path, int width, int height) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new File(path));

        if (bufferedImage != null) {
            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return Optional.of(new ImageIcon(image));
        }

        return Optional.empty();
    }
}
