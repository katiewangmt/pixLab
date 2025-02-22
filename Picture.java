import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture. This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu & Katie Wang
 * @since February 3rd, 2025
 */
public class Picture extends SimplePicture {
  ///////////////////// constructors //////////////////////////////////

  /**
   * Constructor that takes no arguments
   */
  public Picture() {
    /*
     * not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor
     */
    super();
  }

  /**
   * Constructor that takes a file name and creates the picture
   * 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   * 
   * @param height the height of the desired picture
   * @param width  the width of the desired picture
   */
  public Picture(int height, int width) {
    // let the parent class handle this width and height
    super(width, height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   * 
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }

  /**
   * Constructor that takes a buffered image
   * 
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }

  ////////////////////// methods ///////////////////////////////////////

  /**
   * Method to return a string with information about this picture.
   * 
   * @return a string with information about the picture such as fileName,
   *         height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() +
        " height " + getHeight()
        + " width " + getWidth();
    return output;

  }

  /** Method to set the blue to 0 */
  public void zeroBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setBlue(0);
      }
    }
  }

  /** Method to set the red and green to 0 */
  public void keepOnlyBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }

  /** Method to negate all colors */
  public void negate() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }

  /** Method to make grayscale */
  public void grayscale() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setRed((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
        pixelObj.setGreen((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
        pixelObj.setBlue((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
      }
    }
  }

  /** Method to modify the pixel colors to make the fish easier to see */
  public void fixUnderwater() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        if (pixelObj.getBlue() > pixelObj.getGreen()
            && pixelObj.getBlue() > pixelObj.getRed())
          pixelObj.setBlue(255);
      }
    }
  }

  /** Method to add watermark to image */
  public void addWatermark() {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        Pixel pixelObj = pixels[i][j];
        if ((i / 40 + j / 40) % 2 == 0) {
          pixelObj.setRed(pixelObj.getRed() + 25);
          pixelObj.setGreen(pixelObj.getGreen() + 25);
          pixelObj.setBlue(pixelObj.getBlue() + 25);
        }
      }
    }
  }

  /**
   * To pixelate by dividing area into size x size.
   * 
   * @param size Side length of square area to pixelate.
   */
  public void pixelate(int size) {
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;

    for (int row = 0; row < height; row += size) {
      for (int col = 0; col < width; col += size) {
        pixelateBlock(row, col, size);
      }
    }
  }

  /**
   * Helper method to pixelate
   * 
   * @param starting row, starting column, size of grid to pixelate
   */
  private void pixelateBlock(int startRow, int startCol, int size) {
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;

    int redSum = 0, greenSum = 0, blueSum = 0;
    int count = 0;

    for (int row = startRow; row < startRow + size && row < height; row++) {
      for (int col = startCol; col < startCol + size && col < width; col++) {
        redSum += pixels[row][col].getRed();
        greenSum += pixels[row][col].getGreen();
        blueSum += pixels[row][col].getBlue();
        count++;
      }
    }

    if (count == 0)
      return;

    int avgRed = redSum / count;
    int avgGreen = greenSum / count;
    int avgBlue = blueSum / count;

    for (int row = startRow; row < startRow + size && row < height; row++) {
      for (int col = startCol; col < startCol + size && col < width; col++) {
        pixels[row][col].setRed(avgRed);
        pixels[row][col].setGreen(avgGreen);
        pixels[row][col].setBlue(avgBlue);
      }
    }
  }

  /**
   * Method that blurs the picture
   * 
   * @param size Blur size, greater is more blur
   * @return Blurred picture
   */
  public Picture blur(int size) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        double avgRed = 0;
        double avgGreen = 0;
        double avgBlue = 0;
        int counter = 0;
        for (int a = i - size / 2; a < i + size / 2; a++) {
          for (int b = j - size / 2; b < j + size / 2; b++) {
            if ((a >= 0 && a < pixels.length) && (b >= 0 && b < pixels[0].length)) {
              avgRed += pixels[a][b].getRed();
              avgGreen += pixels[a][b].getGreen();
              avgBlue += pixels[a][b].getBlue();
              counter++;
            }
          }
        }
        resultPixels[i][j].setRed((int) (avgRed / counter));
        resultPixels[i][j].setGreen((int) (avgGreen / counter));
        resultPixels[i][j].setBlue((int) (avgBlue / counter));
      }
    }

    return result;
  }

  /**
   * Method that enhances a picture by getting average Color around
   * a pixel then applies the following formula:
   *
   * pixelColor <- 2 * currentValue - averageValue
   *
   * size is the area to sample for blur.
   *
   * @param size Larger means more area to average around pixel
   *             and longer compute time.
   * @return enhanced picture
   */
  public Picture enhance(int size) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        double avgRed = 0;
        double avgGreen = 0;
        double avgBlue = 0;
        int counter = 0;
        for (int a = i - size / 2; a < i + size / 2; a++) {
          for (int b = j - size / 2; b < j + size / 2; b++) {
            if ((a >= 0 && a < pixels.length) && (b >= 0 && b < pixels[0].length)) {
              avgRed += pixels[a][b].getRed();
              avgGreen += pixels[a][b].getGreen();
              avgBlue += pixels[a][b].getBlue();
              counter++;
            }
          }
        }
        resultPixels[i][j].setRed((int) (2 * pixels[i][j].getRed() - avgRed / counter));
        resultPixels[i][j].setGreen((int) (2 * pixels[i][j].getGreen() - avgGreen / counter));
        resultPixels[i][j].setBlue((int) (2 * pixels[i][j].getBlue() - avgBlue / counter));
      }
    }

    return result;
  }

  /**
   * Method that swaps the left and right halves of the image by shifting
   * each pixel half the width, wrapping around to the opposite side.
   * 
   * @return A new picture with the left and right halves swapped
   */
  public Picture swapLeftRight() {
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;

    Picture shifted = new Picture(height, width);
    Pixel[][] resultPixels = shifted.getPixels2D();

    int shiftAmount = width / 2;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int newCol = (col + shiftAmount) % width;

        resultPixels[row][newCol].setColor(pixels[row][col].getColor());
      }
    }
    return shifted;
  }

  /**
   * Method that shifts the pixels in an image a set number with a stair step:
   * each step is shifted another shiftCount pixels
   * 
   * @param shiftCount The number of pixels to shift to the right
   * @param steps      The number of steps
   * @return The picture with pixels shifted in stair steps
   */
  public Picture stairStep(int shiftCount, int steps) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        int newCol = j + shiftCount * (i / (getHeight() / steps));
        resultPixels[i][newCol % getWidth()].setColor(pixels[i][j].getColor());
      }
    }

    return result;
  }

  /**
   * Applies a "liquify" distortion by shifting each row horizontally
   * according to a Gaussian (bell-curve) function.
   *
   * @param maxFactor Max height (shift) of curve in pixels
   * @return Liquified picture
   */
  public Picture liquify(int maxHeight) {
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;

    Picture result = new Picture(height, width);
    Pixel[][] resultPixels = result.getPixels2D();

    double bellWidth = 70.0;
    double centerRow = height / 2.0;

    for (int row = 0; row < height; row++) {
      double exponent = Math.pow(row - centerRow, 2.0)
          / (2.0 * Math.pow(bellWidth, 2.0));
      int rightShift = (int) (maxHeight * Math.exp(-exponent));

      for (int col = 0; col < width; col++) {
        int newCol = (col + rightShift) % width;

        resultPixels[row][newCol].setColor(pixels[row][col].getColor());
      }
    }

    return result;
  }

  /**
   * Creates a "wavy" distortion effect by shifting each row left/right
   * according to a sine function.
   *
   * @param amplitude The maximum shift of pixels
   * @return Wavy picture
   */
  public Picture wavy(int amplitude) {
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;

    Picture result = new Picture(height, width);
    Pixel[][] resultPixels = result.getPixels2D();

    double frequency = 0.011;
    double phase = 0.0; // Shift in radians
    // ---------------------------------------------
    for (int row = 0; row < height; row++) {
      double shiftValue = amplitude * Math.sin(2.0 * Math.PI * frequency * row + phase);
      int shift = (int) Math.round(shiftValue);
      for (int col = 0; col < width; col++) {
        int newCol = (col + shift + width) % width;
        resultPixels[row][newCol].setColor(pixels[row][col].getColor());
      }
    }
    return result;
  }

  /**
   * Method that mirrors the picture around a
   * vertical mirror in the center of the picture
   * from left to right
   */
  public void mirrorVertical() {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < width / 2; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** Mirror just part of a picture of a temple */
  public void mirrorTemple() {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();

    // loop through the rows
    for (int row = 27; row < 97; row++) {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++) {

        leftPixel = pixels[row][col];
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /**
   * copy from the passed fromPic to the
   * specified startRow and startCol in the
   * current picture
   * 
   * @param fromPic  the picture to copy from
   * @param startRow the start row to copy to
   * @param startCol the start col to copy to
   */
  public void copy(Picture fromPic,
      int startRow, int startCol) {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length &&
        toRow < toPixels.length; fromRow++, toRow++) {
      for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length &&
          toCol < toPixels[0].length; fromCol++, toCol++) {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }
  }

  /** Method to create a collage of several pictures */
  public void createCollage() {
    Picture flower1 = new Picture("images/flower1.jpg");
    Picture flower2 = new Picture("images/flower2.jpg");
    this.copy(flower1, 0, 0);
    this.copy(flower2, 100, 0);
    this.copy(flower1, 200, 0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue, 300, 0);
    this.copy(flower1, 400, 0);
    this.copy(flower2, 500, 0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }

  /**
   * Method to show large changes in color
   * 
   * @param edgeDist the distance for finding edges
   */
  public void edgeDetection(int edgeDist) {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length - 1; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col + 1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }

  /**
   * Method that creates an edge detected black/white picture
   * 
   * @param threshold threshold as determined by Pixel’s colorDistance method
   * @return edge detected picture
   */
  public Picture edgeDetectionBelow(int threshold) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    Color bottomColor = null;
    for (int col = 0; col < pixels[0].length; col++) {
      for (int row = 0; row < pixels.length - 1; row++) {
        bottomPixel = pixels[row][col];
        topPixel = pixels[row + 1][col];
        bottomColor = bottomPixel.getColor();
        if (topPixel.colorDistance(bottomColor) > threshold)
          resultPixels[row + 1][col].setColor(Color.BLACK);
        else
          resultPixels[row + 1][col].setColor(Color.WHITE);
      }
    }
    return result;
  }

  /**
   * Method that creates a green screen picture
   * 
   * @return green screen picture
   */
  public Picture greenScreen() {

    // Get background picture
    Picture bkgnd = new Picture("greenScreenImages/IndoorHouseLibraryBackground.jpg");

    // Get cat picture
    Picture cat = new Picture("greenScreenImages/kitten1GreenScreen.jpg");

    // Get mouse picture
    Picture mouse = new Picture("greenScreenImages/mouse1GreenScreen.jpg");

    // Place the mouse at (260, 290), half-size
    placeSubject(bkgnd, mouse, 287, 349, 0.52);

    // Place the cat at (470, 300), 0.4 (40%) size
    placeSubject(bkgnd, cat, 500, 350, 0.60);

    return bkgnd;
  }

  /**
   * Places a subject onto a target background,
   * scaling the subject by scaleFactor, starting at (targetX, targetY).
   * 
   * @param background  the Picture onto which we place the subject
   * @param subject     the Picture (cat/mouse) with a chroma key color
   * @param targetX     x coordinate in the background where we start placing
   * @param targetY     y coordinate in the background where we start placing
   * @param scaleFactor how much to scale the subject (e.g., 0.5 = half size)
   */
  public void placeSubject(Picture background,
      Picture subject,
      int targetX,
      int targetY,
      double scaleFactor) {
    Pixel subjectPixel;

    // Loop over every (x,y)
    for (int srcX = 0; srcX < subject.getWidth(); srcX++) {
      for (int srcY = 0; srcY < subject.getHeight(); srcY++) {
        subjectPixel = subject.getPixel(srcX, srcY);

        if (subjectPixel.getRed() + subjectPixel.getBlue() - 50 > subjectPixel.getGreen()) {
          // Scaling
          int trgX = targetX + (int) (srcX * scaleFactor);
          int trgY = targetY + (int) (srcY * scaleFactor);

          // Making sure not to go out of bounds
          if (trgX >= 0 && trgX < background.getWidth() &&
              trgY >= 0 && trgY < background.getHeight()) {
            background.getPixel(trgX, trgY).setColor(subjectPixel.getColor());
          }
        }
      }
    }
  }

  /**
   * Rotates the current picture by the given degrees around its center,
   * returning a new Picture that is tightly cropped vertically but has
   * extra left/right margin for a nicer appearance.
   * 
   * @param degrees the angle in degrees (e.g., 30 or 45)
   * @return the rotated picture, cropped vertically but wider horizontally
   */
  public Picture rotate(double degrees) {
    // Convert degrees to radians
    double angle = Math.toRadians(degrees);

    // Dimensions of the current (original) picture
    int oldWidth = this.getWidth();
    int oldHeight = this.getHeight();

    // Calculate the center coordinates of the original picture
    double cx = oldWidth / 2.0;
    double cy = oldHeight / 2.0;

    // four corners of the original picture
    double[][] corners = {
        { 0, 0 },
        { oldWidth, 0 },
        { 0, oldHeight },
        { oldWidth, oldHeight }
    };

    // Use cos/sin for the rotation
    double cosA = Math.cos(angle);
    double sinA = Math.sin(angle);

    // Variables to track the min/max x,y after rotating corners
    double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
    double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;

    
    for (int i = 0; i < 4; i++) {
      // Original corner
      double x0 = corners[i][0];
      double y0 = corners[i][1];

      double xCentered = x0 - cx;
      double yCentered = y0 - cy;

      double xRot = (xCentered * cosA) - (yCentered * sinA);
      double yRot = (xCentered * sinA) + (yCentered * cosA);

      xRot += cx;
      yRot += cy;

      if (xRot < minX)
        minX = xRot;
      if (xRot > maxX)
        maxX = xRot;
      if (yRot < minY)
        minY = yRot;
      if (yRot > maxY)
        maxY = yRot;
    }

    // adding left and right padding
    double marginX = 70; 
    minX -= marginX;
    maxX += marginX;

    int newWidth = (int) Math.ceil(maxX - minX);
    int newHeight = (int) Math.ceil(maxY - minY);

    Picture rotatedPic = new Picture(newHeight, newWidth);

    Pixel[][] resultPixels = rotatedPic.getPixels2D();

    for (int nx = 0; nx < newWidth; nx++) {
      for (int ny = 0; ny < newHeight; ny++) {
        double xWorld = nx + minX;
        double yWorld = ny + minY;

        // Shift so center is (0,0), then rotate by -angle
        double xCentered = xWorld - cx;
        double yCentered = yWorld - cy;

        double oldX = (xCentered * cosA) + (yCentered * sinA);
        double oldY = (-xCentered * sinA) + (yCentered * cosA);

        oldX += cx;
        oldY += cy;

        int ox = (int) Math.round(oldX);
        int oy = (int) Math.round(oldY);

        if (ox >= 0 && ox < oldWidth && oy >= 0 && oy < oldHeight) {
          resultPixels[ny][nx].setColor(this.getPixel(ox, oy).getColor());
        } else {
          resultPixels[ny][nx].setColor(Color.WHITE);
        }
      }
    }

    // Return the resulting wide, rotated picture
    return rotatedPic;
  }

  /*
   * Main method for testing - each class in Java can have a main
   * method
   */
  public static void main(String[] args) {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }

} // this } is the end of class Picture, put all new methods before this