package dev.aisandbox.client.output.charts;

import java.awt.Font;
import lombok.Setter;

public abstract class BaseJFreeGraph implements OutputGraph {

  @Setter String title = "Average Reward";
  @Setter String xaxisHeader = "Average Reward";
  @Setter String yaxisHeader = "Steps";
  @Setter int graphWidth = 500;
  @Setter int graphHeight = 400;
  @Setter private Font titleFont = new Font("Helvetica", Font.BOLD, 32);
  @Setter private Font axisFont = new Font("Helvetica", Font.PLAIN, 14);
  @Setter private Font valueFont = new Font("Helvetica", Font.PLAIN, 12);
}
