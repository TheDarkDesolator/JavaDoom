package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.environment.Ray;
import com.impalah.JDoomK1.model.environment.Wall;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureMapper {

    public BufferedImage loadWall() throws IOException {
        return ImageIO.read(getClass().getResource("/textures/wall.jpg"));
    }

    public BufferedImage mapToRay(BufferedImage image, Ray r){
        return null;
    }
}
