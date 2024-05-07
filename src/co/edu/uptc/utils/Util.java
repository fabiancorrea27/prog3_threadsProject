package co.edu.uptc.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import co.edu.uptc.views.DirectEnum;

public class Util {
    
    public static DirectEnum randomDirection(){
        DirectEnum[] directEnums = DirectEnum.values();
        return directEnums[(int) (Math.random() * directEnums.length)];
    }

    public static BufferedImage alienImage(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources\\alien.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
