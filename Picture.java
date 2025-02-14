import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu & Katie Wang
 * @since  February 3rd, 2025 
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method to set the red and green to 0 */
  public void keepOnlyBlue()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    } 
  }
  
  /** Method to negate all colors */
  public void negate()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    } 
  }
  
  /** Method to make grayscale */
  public void grayscale()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen())/3);
        pixelObj.setGreen((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen())/3);
        pixelObj.setBlue((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen())/3);
      }
    } 
  }

  /** Method to modify the pixel colors to make the fish easier to see*/
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		  if (pixelObj.getBlue() > pixelObj.getGreen() 
		      && pixelObj.getBlue() > pixelObj.getRed())
			  pixelObj.setBlue(255);
      }
    }
  }  

  /** Method to add watermark to image*/
  public void addWatermark()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        Pixel pixelObj = pixels[i][j];
        if ((i/40 + j/40) % 2 == 0) {
          pixelObj.setRed(pixelObj.getRed() + 25);
          pixelObj.setGreen(pixelObj.getGreen() + 25);
          pixelObj.setBlue(pixelObj.getBlue() + 25);
        }
      }
	  }				
  }

  /** To pixelate by dividing area into size x size.
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
  
  /** Helper method to pixelate 
   * @param starting row, starting column, size of grid to pixelate
   */
  private void pixelateBlock(int startRow, int startCol, int size){
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
	  
	  if (count == 0) return;
	  
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
  
  /** Method that blurs the picture
  * @param size Blur size, greater is more blur
  * @return Blurred picture
  */
  public Picture blur(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++) {
		for (int j = 0; j < pixels[0].length; j++) {
			double avgRed = 0; double avgGreen = 0; double avgBlue = 0;
			int counter = 0;
			for (int a = i - size/2; a < i + size/2; a++) {
				for (int b = j - size/2; b < j + size/2; b++) {
					if ((a >= 0 && a < pixels.length) && (b >= 0 && b < pixels[0].length)) {
						avgRed += pixels[a][b].getRed();
						avgGreen += pixels[a][b].getGreen();
						avgBlue += pixels[a][b].getBlue();
						counter++;
					}
				}
			}
			resultPixels[i][j].setRed((int)(avgRed/counter));
			resultPixels[i][j].setGreen((int)(avgGreen/counter));
			resultPixels[i][j].setBlue((int)(avgBlue/counter));
		}
	}
	
	return result;	
  }

  /** Method that enhances a picture by getting average Color around
   * a pixel then applies the following formula:
   *
   * pixelColor <- 2 * currentValue - averageValue
   *
   * size is the area to sample for blur.
   *
   * @param size Larger means more area to average around pixel
   * and longer compute time.
   * @return enhanced picture
   */
  public Picture enhance(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        double avgRed = 0; double avgGreen = 0; double avgBlue = 0;
        int counter = 0;
        for (int a = i - size/2; a < i + size/2; a++) {
          for (int b = j - size/2; b < j + size/2; b++) {
            if ((a >= 0 && a < pixels.length) && (b >= 0 && b < pixels[0].length)) {
              avgRed += pixels[a][b].getRed();
              avgGreen += pixels[a][b].getGreen();
              avgBlue += pixels[a][b].getBlue();
              counter++;
            }
          }
        }
        resultPixels[i][j].setRed((int)(2 * pixels[i][j].getRed() - avgRed/counter));
        resultPixels[i][j].setGreen((int)(2 * pixels[i][j].getGreen() - avgGreen/counter));
        resultPixels[i][j].setBlue((int)(2 * pixels[i][j].getBlue() - avgBlue/counter));
      }
    }

    return result;
  }

  /** Method that swaps the left and right halves of the image by shifting 
  * each pixel half the width, wrapping around to the opposite side.
  * 
  * @return A new picture with the left and right halves swapped
  */
  public Picture swapLeftRight()
  {
  Pixel[][] pixels = this.getPixels2D();
  int height = pixels.length;
  int width = pixels[0].length;
  
  Picture shifted = new Picture(height, width);
  Pixel[][] resultPixels = shifted.getPixels2D();

  int shiftAmount = width / 2;
  
  for (int row = 0; row < height; row++)
  {
    for (int col = 0; col < width; col++)
    {
      int newCol = (col + shiftAmount) % width;
      
      resultPixels[row][newCol].setColor(pixels[row][col].getColor());
    }
  }
  return shifted;
  }

  /** Method that shifts the pixels in an image a set number with a stair step:
   *  each step is shifted another shiftCount pixels
   * 
   * @param shiftCount The number of pixels to shift to the right
   * @param steps The number of steps
   * @return The picture with pixels shifted in stair steps
   */
  public Picture stairStep(int shiftCount, int steps)
  {
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
public Picture liquify(int maxHeight) 
{
  Pixel[][] pixels = this.getPixels2D();
  int height = pixels.length;
  int width  = pixels[0].length;
  
  Picture result = new Picture(height, width);
  Pixel[][] resultPixels = result.getPixels2D();

  double bellWidth = 70.0;
  double centerRow = height / 2.0;
  
  for (int row = 0; row < height; row++)
  {
    double exponent = Math.pow(row - centerRow, 2.0) 
                      / (2.0 * Math.pow(bellWidth, 2.0));
    int rightShift = (int)(maxHeight * Math.exp(-exponent));
    
    for (int col = 0; col < width; col++)
    {
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
public Picture wavy(int amplitude)
{
  Pixel[][] pixels = this.getPixels2D();
  int height = pixels.length;
  int width  = pixels[0].length;
  
  Picture result = new Picture(height, width);
  Pixel[][] resultPixels = result.getPixels2D();
  
  double frequency = 0.011;
  double phase = 0.0; // Shift in radians
  // ---------------------------------------------
  for (int row = 0; row < height; row++)
  {
    double shiftValue = amplitude * Math.sin(2.0 * Math.PI * frequency * row + phase);
    int shift = (int)Math.round(shiftValue);
    for (int col = 0; col < width; col++)
    {
      int newCol = (col + shift + width) % width;
      resultPixels[row][newCol].setColor(pixels[row][col].getColor());
    }
  }
  return result;
}


  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("images/flower1.jpg");
    Picture flower2 = new Picture("images/flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
   /** Method that creates an edge detected black/white picture
   * @param threshold 	threshold as determined by Pixel’s colorDistance method
   * @return edge detected picture
   */
   public Picture edgeDetectionBelow(int threshold)
   {
	   Pixel[][] pixels = this.getPixels2D();
	   Picture result = new Picture(pixels.length, pixels[0].length);
	   Pixel[][] resultPixels = result.getPixels2D(); 
	   Pixel topPixel = null;
	   Pixel bottomPixel = null;
	   Color bottomColor = null;
	   for (int col = 0; col < pixels[0].length; col++) 
	   {
		   for (int row = 0; row < pixels.length-1; row++) 
		   { 
			   bottomPixel = pixels[row][col];
			   topPixel = pixels[row+1][col];
			   bottomColor = bottomPixel.getColor();
			   if (topPixel.colorDistance(bottomColor) > threshold)
					resultPixels[row+1][col].setColor(Color.BLACK);
			   else
					resultPixels[row+1][col].setColor(Color.WHITE);
			}
	   } 
	   return result;
   }
   
	/** Method that creates a green screen picture
	* @return green screen picture
	*/
	public Picture greenScreen()
	{
		// Get background picture
		Picture bkgnd = new Picture("greenScreenImages/IndoorHouseLibraryBackground.jpg");
		Pixel[][] bkgndPixels = bkgnd.getPixels2D();
		
		// Get cat picture
		Picture cat = new Picture("greenScreenImages/kitten1GreenScreen.jpg");
		Pixel[][] catPixels = cat.getPixels2D();
		
		// Get mouse picture
		Picture mouse = new Picture("greenScreenImages/mouse1GreenScreen.jpg");
		Pixel[][] mousePixels = mouse.getPixels2D(); 
		
		
		Pixel currPixel = null;
		Pixel newPixel = null;
		// loop through the columns
		for (int srcX=0, trgX=260;
         srcX<mouse.getWidth() && trgX<bkgnd.getWidth();
         srcX++, trgX++) {

		// loop through the rows
		for (int srcY=0, trgY=290;
           srcY<mouse.getHeight() && trgY<bkgnd.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = mouse.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getBlue() > currPixel.getGreen()) {
          bkgnd.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
    
    return bkgnd;
		
	}
	
	 /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void blueScreen(Picture target,
                        int targetX, int targetY) {

    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX=targetX;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getBlue() > currPixel.getGreen()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
