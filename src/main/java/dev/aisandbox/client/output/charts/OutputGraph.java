package dev.aisandbox.client.output.charts;

import java.awt.Font;
import java.awt.image.BufferedImage;

/** Interface for graphs that appear in the output of a simulation. */
public interface OutputGraph {

  public void setTitle(String title);

  public void setXaxisHeader(String header);

  public void setYaxisHeader(String header);

  public void setGraphWidth(int width);

  public void setGraphHeight(int height);

  public void setTitleFont(Font font);

  public void setAxisFont(Font axisFont);

  public void setValueFont(Font valueFont);

  public BufferedImage getImage();
}
