package dev.aisandbox.client.output.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseChart {

  double lowestX = 0.0;
  double highestX = 1.0;
  double lowestY = 0.0;
  double highestY = 1.0;
  @Getter @Setter int graphWidth = 500;
  @Getter @Setter int graphHeight = 350;
  @Getter @Setter private String title = "Graph Title";
  @Getter @Setter private String xaxisHeader = "X Axis";
  @Getter @Setter private String yaxisHeader = "Y Axis";
  @Getter @Setter private Color backgroundColour = Color.WHITE;
  @Getter @Setter private Color titlesColour = Color.BLACK;
  @Getter @Setter private Font titleFont = new Font("Helvetica", Font.BOLD, 32);
  @Getter @Setter private Font axisFont = new Font("Helvetica", Font.PLAIN, 14);
  @Getter @Setter private Font valueFont = new Font("Helvetica", Font.PLAIN, 12);
  private int tickLength = 3;
  private int tickMargin = 2;
  // holders for image as it's been drawn
  @Getter private BufferedImage image = null;
  Graphics2D graphics2D;
  // internal measurements - needed to extend the class
  int topMargin = 0;
  int leftMargin = 0;
  int rightMargin = 0;
  int bottomMargin = 0;
  double horizontalScale = 1.0;
  double verticalScale = 1.0;

  /**
   * Redraw the graph outline (headers & axis) based on the current lowestX, highestX, lowestY,
   * highestY.
   *
   * <p>Note: this may update the lowestY,highestY.
   */
  public void resetGraph() {
    // nudge min/max X/Y to avoid div/0
    if (highestY == lowestY) {
      highestY += 0.0001;
    }
    if (highestX == lowestX) {
      highestX += 0.0001;
    }
    // create new image and graphics context
    image = new BufferedImage(graphWidth, graphHeight, BufferedImage.TYPE_INT_RGB);
    graphics2D = image.createGraphics();
    // set background colour
    graphics2D.setColor(backgroundColour);
    graphics2D.fillRect(0, 0, graphWidth, graphHeight);
    // setup hints
    graphics2D.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    graphics2D.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    // track drawable rectangle
    topMargin = 0;
    leftMargin = 0;
    rightMargin = 0;
    bottomMargin = 0;
    // draw text
    if (title != null) {
      topMargin += drawTitle();
    }
    if (xaxisHeader != null) {
      bottomMargin += drawXAxisHeader();
    }
    if (yaxisHeader != null) {
      leftMargin += drawYAxisHeader();
    }
    // draw Y axis
    drawYAxis();
    // draw X axis
    drawXAxis();
    // DEBUG highlight drawable area
    // draw top title
    graphics2D.setColor(Color.LIGHT_GRAY);
    graphics2D.drawRect(
        leftMargin,
        topMargin,
        graphWidth - leftMargin - rightMargin,
        graphHeight - topMargin - bottomMargin);
    // work out scales

    horizontalScale = (graphWidth - leftMargin - rightMargin) / (highestX - lowestX);
  }

  private int drawTitle() {
    graphics2D.setColor(titlesColour);
    graphics2D.setFont(titleFont);
    // work out center + height + descent
    FontMetrics metrics = graphics2D.getFontMetrics(titleFont);
    // get the height of a line
    int fheight = metrics.getHeight();
    // get the width of the text
    int fwidth = metrics.stringWidth(title);
    // get descent info
    int descent = metrics.getDescent();
    graphics2D.drawString(title, (graphWidth - fwidth) / 2, fheight - descent);
    return fheight;
  }

  private int drawXAxisHeader() {
    graphics2D.setColor(titlesColour);
    graphics2D.setFont(axisFont);
    // work out center + height + descent
    FontMetrics metrics = graphics2D.getFontMetrics(axisFont);
    // get the height of a line
    int fheight = metrics.getHeight();
    // get the width of the text
    int fwidth = metrics.stringWidth(xaxisHeader);
    // get descent info
    int descent = metrics.getDescent();
    graphics2D.drawString(xaxisHeader, (graphWidth - fwidth) / 2, graphHeight - descent);
    return fheight;
  }

  private int drawYAxisHeader() {
    graphics2D.setColor(titlesColour);
    graphics2D.setFont(axisFont);
    // work out center + height + descent
    FontMetrics metrics = graphics2D.getFontMetrics(axisFont);
    graphics2D.setFont(axisFont);
    // get the height of a line
    int fheight = metrics.getHeight();
    // get the width of the text
    int fwidth = metrics.stringWidth(xaxisHeader);
    // get ascent info
    int ascent = metrics.getAscent();
    // save then rotate
    AffineTransform originalTransformation = graphics2D.getTransform();
    AffineTransform at = new AffineTransform();
    at.translate(ascent, graphHeight - (graphHeight - fwidth) / 2.0);

    at.rotate(Math.toRadians(-90));
    // at.setToRotation(Math.toRadians(-90), ascent, (height-fwidth)/2);
    graphics2D.setTransform(at);
    graphics2D.drawString(yaxisHeader, 0, 0);
    graphics2D.setTransform(originalTransformation);
    return fheight;
  }

  private void drawYAxis() {
    // work out the tick values
    List<Double> ticks = looseLabel(lowestY, highestY);
    List<String> labels = new ArrayList<>();
    ticks.stream().forEach(tick -> labels.add(toSignificantDigitString(tick, 3)));
    log.info("adding labels at {}", labels);
    // work out the maximum label size
    FontMetrics metrics = graphics2D.getFontMetrics(valueFont);
    graphics2D.setFont(valueFont);
    int labelSizeX = 0;
    int labelSizeY = metrics.getHeight();
    for (String label : labels) {
      labelSizeX = Math.max(labelSizeX, metrics.stringWidth(label));
    }
    // adjust the highest and lowest to match the tick values
    lowestY = Math.min(lowestY, ticks.get(0));
    highestY = Math.max(highestY, ticks.get(ticks.size() - 1));
    // we know that the x axis will be lifted up - do this now
    bottomMargin += labelSizeY;
    bottomMargin += tickLength;
    bottomMargin += tickMargin;
    // work out the scale
    verticalScale = (graphHeight - topMargin - bottomMargin) / (highestY - lowestY);
    // draw the ticks + labels
    for (int i = 0; i < ticks.size(); i++) {
      double value = ticks.get(i);
      String name = labels.get(i);
      int y = (int) (graphHeight - bottomMargin - (value - lowestY) * verticalScale);
      graphics2D.drawString(name, leftMargin, y + metrics.getAscent() / 2);
      graphics2D.drawLine(
          leftMargin + labelSizeX + tickMargin,
          y,
          leftMargin + labelSizeX + tickMargin + tickLength,
          y);
    }
    // move right
    leftMargin += labelSizeX;
    leftMargin += tickMargin;
    leftMargin += tickLength;
    // put the bottom margin back to where it was
    bottomMargin -= labelSizeY;
    bottomMargin -= tickLength;
    bottomMargin -= tickMargin;
  }

  private void drawXAxis() {
    // work out the tick values
    List<Double> ticks = tightLabel(lowestX, highestX);
    List<String> labels = new ArrayList<>();
    ticks.stream().forEach(tick -> labels.add(Integer.toString((int) Math.floor(tick))));
    log.info("adding labels at {}", labels);
    // work out the maximum label size
    FontMetrics metrics = graphics2D.getFontMetrics(valueFont);
    graphics2D.setFont(valueFont);
    int labelSizeX = 0;
    int labelSizeY = metrics.getHeight();
    for (String label : labels) {
      labelSizeX = Math.max(labelSizeX, metrics.stringWidth(label));
    }
    // move the right hand margin so we can see all of the last label
    rightMargin += labelSizeX / 2;
    // work out the scale
    horizontalScale = (graphWidth - leftMargin - rightMargin) / (highestX - lowestX);
    // draw the ticks + labels
    for (int i = 0; i < ticks.size(); i++) {
      double value = ticks.get(i);
      String name = labels.get(i);
      int x = (int) (leftMargin + (value - lowestX) * horizontalScale);
      int y = graphHeight - bottomMargin;
      graphics2D.drawString(name, x - metrics.stringWidth(name) / 2, y);
      graphics2D.drawLine(x, y - labelSizeY, x, y - labelSizeY - tickLength);
    }
    // lift up the axis above the labels
    bottomMargin += labelSizeY;
    bottomMargin += tickLength;
  }

  public static double niceNum(double x, NiceMode mode) {
    double exp = Math.floor(Math.log10(x)); // exponent of x
    double d = Math.pow(10, exp);
    if (d == 0.0) d = 1.0;
    double f = x / d;
    double nf;
    if (mode == NiceMode.ROUND) {
      if (f < 1.5) {
        nf = 1.0;
      } else if (f < 3.0) {
        nf = 2.0;
      } else if (f < 7.0) {
        nf = 5.0;
      } else {
        nf = 10.0;
      }
    } else {
      if (f <= 1.0) {
        nf = 1.0;
      } else if (f <= 2.0) {
        nf = 2.0;
      } else if (f <= 5.0) {
        nf = 5.0;
      } else {
        nf = 10.0;
      }
    }
    return nf * Math.pow(10, exp);
  }

  private static final int TICK_COUNT = 5;

  public static List<Double> looseLabel(double min, double max) {
    double range = niceNum(max - min, NiceMode.CEIL);
    double d = niceNum(range / (TICK_COUNT - 1), NiceMode.ROUND);
    double graphmin = Math.floor(min / d) * d;
    double graphmax = Math.ceil(max / d) * d;
    int nfrac = Math.max(0, (int) -Math.floor(Math.log10(d)));
    List<Double> tickMarks = new ArrayList<>();
    for (double value = graphmin; value <= (graphmax + 0.5 * d); value += d) {
      tickMarks.add(value);
    }
    log.debug("Generating loose labels between {} and {}, found {}", min, max, tickMarks);
    return tickMarks;
  }

  public static List<Double> tightLabel(double min, double max) {
    List<Double> tickMarks = new ArrayList<>();
    tickMarks.add(min);
    double range = niceNum(max - min, NiceMode.ROUND);
    double d = niceNum(range / (TICK_COUNT - 1), NiceMode.ROUND);
    double graphmin = Math.ceil(min / d) * d;
    double graphmax = Math.floor(max / d) * d;
    int nfrac = Math.max(0, (int) -Math.floor(Math.log10(d)));

    for (double value = graphmin; value <= (graphmax + 0.5 * d); value += d) {
      tickMarks.add(value);
    }
    tickMarks.add(max);
    log.info("Generating tight labels between {} and {}, found {}", min, max, tickMarks);
    return tickMarks;
  }

  public enum NiceMode {
    ROUND,
    CEIL
  }

  public static String toSignificantDigitString(double value, int significantDigits) {
    if (significantDigits < 0) throw new IllegalArgumentException();
    // this is more precise than simply doing "new BigDecimal(value);"
    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.round(new MathContext(significantDigits, RoundingMode.HALF_UP));
    final int precision = bd.precision();
    if (precision < significantDigits)
      bd = bd.setScale(bd.scale() + (significantDigits - precision));
    return bd.toPlainString();
  }
}
