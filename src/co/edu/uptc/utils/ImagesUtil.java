package co.edu.uptc.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImagesUtil {
    public static BufferedImage alienImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources\\alien.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage canonImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources\\canon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
