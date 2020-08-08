package dev.aisandbox.client.scenarios;

import dev.aisandbox.client.profiler.ProfileStep;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class RuntimeResponse {

  @Getter private final List<BufferedImage> images = new ArrayList<>();
  @Getter private final ProfileStep profileStep;

  public RuntimeResponse(ProfileStep profileStep, BufferedImage image) {
    this.profileStep = profileStep;
    if (image != null) {
      this.images.add(image);
    }
  }

  public RuntimeResponse(ProfileStep profileStep, List<BufferedImage> images) {
    this.profileStep = profileStep;
    this.images.addAll(images);
  }

  public RuntimeResponse(BufferedImage image) {
    this.profileStep = null;
    this.images.add(image);
  }
}
