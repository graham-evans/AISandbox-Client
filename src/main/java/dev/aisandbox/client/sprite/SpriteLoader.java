package dev.aisandbox.client.sprite;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SpriteLoader {

    private static final Logger LOG = LoggerFactory.getLogger(SpriteLoader.class.getName());

    private final boolean licencedAvailable;

    public SpriteLoader() {
        LOG.info("Initialising sprite loader");
        licencedAvailable = false;
    }

    public List<BufferedImage> loadSprites(String path,int width,int height) {
        try {
            return loadSpritesFromResources(path, width, height);
        } catch (IOException e) {
            LOG.error("Error loading sprites for {}",path,e);
            return new ArrayList<>();
        }
    }

    private List<BufferedImage> loadSpritesFromResources(String path,int width,int height) throws IOException {
        BufferedImage sheet = ImageIO.read(SpriteLoader.class.getResourceAsStream(path));
        List<BufferedImage> images = new ArrayList<>();
        int x=0;
        int y=0;
        while (y<sheet.getHeight()) {
            while (x<sheet.getWidth()) {
                images.add(sheet.getSubimage(x,y,width,height));
                x+=width;
            }
            x=0;
            y+=height;
        }
        return images;
    }

}
