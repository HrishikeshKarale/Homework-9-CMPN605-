/**
* Mandelbort.java
*
* @version   $Id: Mandelbort.java,v_1.5 2014/10/26 11:20:00
*
* @author    hhk9433 (Hrishikesh Karale)
* @author    ap8185 (Atir Petkar)
*
* Revisions:
*      Initial revision
*/
import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * Mandelbort implements Jframe and this class is used to draw some figure
 * we have also created an inner class which implements Runnable and over-rides
 * run method to implement thread.
 */
public class Mandelbrot extends JFrame {
 
    final int MAX = 5000;
    final int LENGTH   = 800;
    final double ZOOM  = 1000;
    static BufferedImage I;
    double zx, zy, cX, cY, tmp;
    int[] colors = new int[MAX];
 
    /**
     * default Constructor
     */
    public Mandelbrot() {
        super("Mandelbrot Set");
	
	initColors();
        setBounds(100, 100, LENGTH, LENGTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * this method calls thread start method and starts the thread
     */
    public void createSet()	{
       Mandelbrot.I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	
            		new Thread(new DrawIt(x,y)).start();
            		repaint();
            }
        }
    }
    
    /**
     * fills colors 
     */
    public void initColors() {
        for (int index = 0; index < MAX; index++) {
            colors[index] = Color.HSBtoRGB(index/256f, 1, index/(index+8f));
        }
    }
 
    /* (non-Javadoc)
     * @see java.awt.Window#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
    
    /**
     * This class is Implements Runnable interface and over-rides run method 
     * to run thread 
     */
    class DrawIt extends Mandelbrot implements Runnable{
    	//inner class variables
    	int x, y;
    	
    	/**
    	 * parameterized Constructor which initializes inner class variables
    	 * @param x
    	 * @param y
    	 */
    	DrawIt(int x, int y)
    	{
    		this.x = x;
    		this.y = y;
    	}
    	
    	/**
    	 *predefined code
    	 *@Override
    	 */
    	public void run()
    	{
    		zx = 0;
    		zy = 0;
    		cX = (x - LENGTH) / ZOOM;
    		cY = (y - LENGTH) / ZOOM;
    		int iter = 0;
    		while ( (zx * zx + zy * zy < 4 ) && ( iter < MAX - 1 ) ) {
    	    	tmp = zx * zx - zy * zy + cX;
    	    	zy = 2.0 * zx * zy + cY;
    	        zx = tmp;
    	        iter++;
    	    }
    	    if ( iter > 0 )
    	    	Mandelbrot.I.setRGB(x, y, colors[iter]);
    	    else
    	    	Mandelbrot.I.setRGB(x, y, iter | (iter << 8));
    	    repaint();
    	}
    }
 
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Mandelbrot aMandelbrot = new Mandelbrot();
	aMandelbrot.setVisible(true);
	aMandelbrot.createSet();
    }
}