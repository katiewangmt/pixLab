/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test keepOnlyBlue */
  public static void testKeepOnlyBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testNegate()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.negate();
    beach.explore();
  }
  
  /** Method to test grayscale */
  public static void testGrayscale()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.grayscale();
    beach.explore();
  }

  /** Method to test fixUnderwater */
  public static void testFixUnderwater()
  {
    Picture water = new Picture("images/water.jpg");
    water.explore();
    water.fixUnderwater();
    water.explore();
  }

  /** Method to test addWatermark */
  public static void testAddWatermark()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.addWatermark();
    beach.explore();
  }

  /** Method to test pixelate */
  public static void testPixelate(int size)
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.pixelate(size);
    beach.explore();
  }

  /** Method to test blur */
  public static void testBlur(int size)
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.blur(size).explore();
  }

  /** Method to test enhance */
  public static void testEnhance(int size)
  {
    Picture beach = new Picture("images/water.jpg");
    beach.explore();
    beach.enhance(size).explore();
  }

  /** Method to test shiftHalfWidth */
  public static void testSwapLeftRight()
  {
    // Load an original picture
    Picture beach = new Picture("images/redMotorcycle.jpg");
    beach.explore(); 
    beach.swapLeftRight().explore();
  }

  /** Method to test stairStep */
  public static void testStairStep(int shiftCount, int steps)
  {
    Picture motorcycle = new Picture("images/redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.stairStep(shiftCount, steps).explore();
  }

  /** Method to test liquify */
  public static void testLiquify(int maxHeight)
  {
    // 1. Load a picture you want to liquify
    Picture insideOut = new Picture("images/temple.jpg");
    insideOut.explore();
    insideOut.liquify(maxHeight).explore();
  }

  /** Method to test wavy */
  public static void testWavy(int amplitude)
  {
    Picture stillLife = new Picture("images/gorge.jpg");
    stillLife.explore(); 
    stillLife.wavy(amplitude).explore();
  }

  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("images/caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("images/temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("images/640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Method to test edgeDetectionBelow */
  public static void testEdgeDetectionBelow(int threshold)
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.edgeDetectionBelow(threshold).explore();
  }
  
  /** Method to test greenScreen */
  public static void testGreenScreen()
  {
	// choose any picture to start since it will *not* be used
	Picture pic = new Picture("images/beach.jpg");
	Picture gScreen = pic.greenScreen();
	gScreen.explore();
  } 
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
	//testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testPixelate(5);
    //testBlur(20);
    //testEnhance(20);
    //testFixUnderwater();
    //testAddWatermark();
    //testSwapLeftRight();
    //testStairStep(1, 400);
    //testLiquify(100);
    //testWavy(20);
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetectionBelow(10);
    testGreenScreen();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}
