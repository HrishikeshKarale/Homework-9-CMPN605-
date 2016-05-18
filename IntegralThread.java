
/**
* IntegralThread.java
*
* @version   $Id: IntegralThread.java,v_1.5 2014/10/27 21:20:00
*
* @author    ap8185 (Atir Petkar)
* @author    hhk9433 (Hrishikesh Karale)
*
* Revisions:
*      Initial revision
*/

/**
 * This class Extands Thread and over-rides its run method to find the double 
 * Integral of the given function. It uses Reimann's method to calculate the
 * Integration of the given function
 * @author ap8185
 *
 */
public class IntegralThread extends Thread 
{
 //Limits of function are initialized;
 final int minX = -1, maxX = 1, minY = -2, maxY = 2;
 //no of segments that the function is divided into
 int segment;
 //used to store volume of green and yellow bars of Reimann integral
 double green, yellow;
 
 
 /**
  * parameterized constructor that initializes segment variable.
 * @param segment
 */
IntegralThread(int segment)
 {
  this.segment = segment;
 }
 
 
 /**
  * This is the given function whose volume is to be calculated using reimann's
  * Integration.
 * @param x
 * @param y
 * @return Double
 */
public double function(double x, double y) 
 {
  return x * x + y + y;
 }
 
 /**
  * This method is used to calculate the volume of the yellow region, it take's
  * Xmax, Xmin, Ymax, Ymin and Segement as parameters and then calculates its volume.
 * @param a
 * @param b
 * @param c
 * @param d
 * @param segment
 * @return Double
 */
public double yellowRectangles(double a, double b, double c, double d, int segment)
 {
  // length of an x segment.
  double xsegmentize = Math.abs((b - a) / segment);
  //length of a y segment.
  double ysegmentize = Math.abs((d - c) / segment); 
  //volume under the surface.
  double volume = 0; 
  
  for (int i = 1; i < segment; i++)
  {
   for (int j = 1; j < segment; j++) 
   {
    // calculates the height for the given parametric values
	double height = function(a + (xsegmentize * i), c + (ysegmentize * j));
    //calculates Volume.
    volume += xsegmentize * ysegmentize * height;
   }
  }
  return volume;
 }
 
 /**
  *This method is used to calculate the volume of the green region, it take's
  * Xmax, Xmin, Ymax, Ymin and Segement as parameters and then calculates its volume.
 * @param a
 * @param b
 * @param c
 * @param d
 * @param segment
 * @return Double
 */
public double greenRectangles(double a, double b, double c, double d, int segment) 
 {
  // length of an x segment.
  double xsegmentize = (b - a) / segment; 
  //length of a y segment.
  double ysegmentize = (d - c) / segment; 
  //volume under the surface.
  double volume = 0; 
  
  for (int i = 1; i < segment; i++) 
  {
   for (int j = 1; j < segment; j++) 
   {
	// calculates the height for the given parametric values
	double height = function(a + (xsegmentize * (i - 1)), c + (ysegmentize * (j - 1)));
    xsegmentize = xsegmentize==0?1:xsegmentize;
    //calculates Volume.
    volume += xsegmentize * ysegmentize * height;
   }
  }
  return volume;
 }
 
 /**
  * This is the main method of our IntegralThread class which calls various 
  * methods and initializes thread and called start() and thread is implemented
 * @param args
 */
public static void main(String[] args) 
 {
  //counter to increment the segments every time by multiple of 2
  int i = 1;
  //calculated and stores volumes of green and yellow areas
  double greenArea , yellowArea;
  
  // runs if value of delta is grater than 0.01 as per the given standards
  do
  {   
   // segment into which our yellow and green rectangles are divided into 	  
   int segment = i*2;
   IntegralThread it = new IntegralThread(segment);
   //Thread is started
   it.start();
   //calculates volume of green rectangle ad stores it in this variable
   greenArea = it.greenRectangles(it.minX, it.maxX, it.minY, it.maxY, segment);
   //calculates volume of yellow rectangle ad stores it in this variable
   yellowArea = it.yellowRectangles(it.minX, it.maxX, it.minY, it.maxY, segment);
   i++;
  }
  while((yellowArea - greenArea) > 0.01);
  
  System.out.println("yellow area = " + yellowArea);
  System.out.println("green area = " + greenArea);
  System.out.println("Delta = " + (yellowArea - greenArea));
 }

 /* (non-Javadoc)
 * @see java.lang.Thread#run()
 */
@Override
 public void run() 
 {
  green = greenRectangles(minX, maxX, minY, maxY, segment);
  yellow = yellowRectangles(minX, maxX, minY, maxY, segment);
 }
 }
