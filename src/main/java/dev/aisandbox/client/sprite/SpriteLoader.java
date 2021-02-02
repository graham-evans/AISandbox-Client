package dev.aisandbox.client.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * SpriteLoader class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Component
public class SpriteLoader {

  private static final Logger LOG = LoggerFactory.getLogger(SpriteLoader.class.getName());

  private final boolean licencedAvailable;

  /**
   * Constructor for SpriteLoader.
   */
  public SpriteLoader() {
    LOG.debug("Initialising sprite loader");
    licencedAvailable = false;
  }

  /**
   * loadSprites.
   *
   * @param path   a {@link java.lang.String} object.
   * @param width  a int.
   * @param height a int.
   * @return a {@link java.util.List} object.
   */
  public List<BufferedImage> loadSprites(String path, int width, int height) {
    try {
      return loadSpritesFromResources(path, width, height);
    } catch (IOException e) {
      LOG.error("Error loading sprites for {}", path, e);
      return new ArrayList<>();
    }
  }

  /**
   * loadSpritesFromResources.
   *
   * @param path   a {@link java.lang.String} object.
   * @param width  a int.
   * @param height a int.
   * @return a {@link java.util.List} object.
   * @throws java.io.IOException if any.
   */
  public static List<BufferedImage> loadSpritesFromResources(String path, int width, int height)
      throws IOException {
    BufferedImage sheet = ImageIO.read(SpriteLoader.class.getResourceAsStream(path));
    List<BufferedImage> images = new ArrayList<>();
    int x = 0;
    int y = 0;
    while (y < sheet.getHeight()) {
      while (x < sheet.getWidth()) {
        images.add(sheet.getSubimage(x, y, width, height));
        x += width;
      }
      x = 0;
      y += height;
    }
    return images;
  }

  /**
   * Load a sprite grid from a resource.
   *
   * @param path   the path reference for the image to load
   * @param width  the width of the sprites to extract
   * @param height the height of the sprites to extract
   * @return a two dimensional array of {@link java.awt.image.BufferedImage}
   * @throws IOException if the image can't be found or loaded.
   */
  public static BufferedImage[][] loadSpriteGridFromResources(String path, int width, int height)
      throws IOException {
    BufferedImage image = ImageIO.read(SpriteLoader.class.getResourceAsStream(path));
    int cx = image.getWidth() / width;
    int cy = image.getHeight() / height;
    BufferedImage[][] result = new BufferedImage[cx][cy];
    for (int x = 0; x < cx; x++) {
      for (int y = 0; y < cy; y++) {
        result[x][y] = image.getSubimage(x * width, y * height, width, height);
      }
    }
    return result;
  }
}
