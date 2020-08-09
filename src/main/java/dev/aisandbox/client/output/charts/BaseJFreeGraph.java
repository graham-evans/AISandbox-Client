package dev.aisandbox.client.output.charts;

import java.awt.Font;
import lombok.Setter;

public abstract class BaseJFreeGraph implements OutputGraph {

  private static final String BASE_FONT = "Helvetica";

  @Setter String title = "Average Reward";
  @Setter String xaxisHeader = "Average Reward";
  @Setter String yaxisHeader = "Steps";
  @Setter int graphWidth = 500;
  @Setter int graphHeight = 400;
  @Setter private Font titleFont = new Font(BASE_FONT, Font.BOLD, 32);
  @Setter private Font axisFont = new Font(BASE_FONT, Font.PLAIN, 14);
  @Setter private Font valueFont = new Font(BASE_FONT, Font.PLAIN, 12);
}
