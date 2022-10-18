/*
 * This file contains a template for the class NewtonFractal. 
 *
 * The functions in this class are designed to create a Newton Fractal and
 * produce a graphical representation of it.
 *
 * Note: Passing 'true' to colorIterations will darken the colours of the fractal
 * with respect to the number of iterations needed to converge ie. the darker the
 * colour, the more iterations were needed to converge.
 */

import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;

class NewtonFractal {
    /**
     * A reference to the Newton-Raphson iterator object.
     */
    private Newton iterator;

    /**
     * The top-left corner of the square in the complex plane to examine.
     */
    private Complex origin;

    /**
     * The width of the square in the complex plane to examine.
     */
    private double width;

    /**
     * A list of roots of the polynomial.
     */
    private ArrayList<Complex> roots;

    /**
     * A two dimensional array holding the colours of the plot.
     */
    private Color[][] colors;

    /**
     * A flag indicating the type of plot to generate. If true, we choose
     * darker colours if a particular root takes longer to converge.
     */
    private boolean colorIterations;

    /**
     * A standard Java object which allows us to store a simple image in
     * memory. This will be set up by setupFractal.
     */
    private BufferedImage fractal;

    /**
     * This is a standard Java object which allows us to perform basic 
     * graphical operations on the BufferedImage. This will be set up 
     * by setupFractal.
     */
    private Graphics2D g2;

    /**
     * Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image.
     */
    public static final int NUMPIXELS = 400;

    // ========================================================
    // Constructor function.
    // ========================================================

    /**
     * Constructor function which initialises the instance variables
     * above.
     *
     * @param p       The polynomial to generate the fractal of.
     * @param origin  The top-left corner of the square to image.
     * @param width   The width of the square to image.
     */
    public NewtonFractal(Polynomial p, Complex origin, double width) {
        this.roots = new ArrayList<Complex>();
        this.iterator = new Newton(p);
        this.origin = new Complex(origin.getReal(), origin.getImag());
        this.width = width;
        setupFractal();
    }

    // ========================================================
    // Basic operations.
    // ========================================================

    /**
     * Print out all of the roots found so far, which are contained in the
     * roots ArrayList.
     */
    public void printRoots() {
        Complex num;
        String rootString = "";
        for (int i = 0; i < this.roots.size(); i++){
            num = this.roots.get(i);
            rootString += num.toString() + " ";
        }
        System.out.println(rootString);
    }

    /**
     * Check to see if root is in the roots ArrayList by comparing with a tolerance
     * value. 
     *
     * @param root  Root to find in this.roots.
     * @return      Index of root in this.roots, or -1 if not found.
     */

    public int findRoot(Complex root) {
        for (int i = 0; i < this.roots.size(); i++) {
            Complex x = this.roots.get(i);
            Complex y = x.add(root.minus());
            if (y.abs() < Newton.TOL){
                return i;
            }
        }
        return -1;
    }

    /**
     * Convert from pixel indices (i,j) to the complex number (origin.real +
     * i*dz, origin.imaginary - j*dz).
     *
     * @param i  x-axis co-ordinate of the pixel located at (i,j)
     * @param j  y-axis co-ordinate of the pixel located at (i,j)
     */
    public Complex pixelToComplex(int i, int j) {
        double dz = width/NUMPIXELS;
        double a = origin.getReal()+i*dz;
        double b = origin.getImag()-j*dz;
        return new Complex(a,b);
    }

    // ========================================================
    // Fractal generating function.
    // ========================================================

    /**
     * Generate the fractal image.
     */
    public void createFractal(boolean colorIterations) {
        this.colorIterations = colorIterations;
        for (int i = 0; i < NUMPIXELS; i++){
            for (int j = 0; j < NUMPIXELS; j++){
                Complex z = pixelToComplex(i, j);
                iterator.iterate(z);
                if (iterator.getError() == 0){
                    Complex root = iterator.getRoot();
                    int rootIndex = findRoot(root);
                    if (rootIndex == -1){
                        this.roots.add(root);
                        colorPixel(i, j, this.roots.size()-1, iterator.getNumIterations());
                    }
                    else{
                        colorPixel(i, j, rootIndex, iterator.getNumIterations());
                    }
                }
            }
        }
    }

    /**
     * Sets up all the fractal image.
     */
    private void setupFractal()
    {
        int i, j;
        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 5)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive.");
        this.colors       = new Color[5][Newton.MAXITER];
        this.colors[0][0] = Color.RED;
        this.colors[1][0] = Color.GREEN;
        this.colors[2][0] = Color.BLUE;
        this.colors[3][0] = Color.CYAN;
        this.colors[4][0] = Color.MAGENTA;
        for (i = 0; i < 5; i++) {
            float[] components = colors[i][0].getRGBComponents(null);
            float[] delta      = new float[3];
            for (j = 0; j < 3; j++)
                delta[j] = 0.8f*components[j]/Newton.MAXITER;
            for (j = 1; j < Newton.MAXITER; j++) {
                float[] tmp  = colors[i][j-1].getRGBComponents(null);
                colors[i][j] = new Color(tmp[0]-delta[0], tmp[1]-delta[1],
                        tmp[2]-delta[2]);
            }
        }
        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2      = fractal.createGraphics();
    }

    /**
     * Colours a pixel in the image.
     *
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColor  An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.
     */
    private void colorPixel(int i, int j, int rootColor, int numIter)
    {
        if (colorIterations)
            g2.setColor(colors[rootColor][numIter-1]);
        else
            g2.setColor(colors[rootColor][0]);
        g2.fillRect(i,j,1,1);
    }

    /**
     * Saves the fractal image to a file.
     *
     * @param fileName  Filename for the .png file.
     */
    public void saveFractal(String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Encountered an error trying to save file.");
        }
    }

    public static void main(String[] args) {
        // Here is some example code which generates a Newton fractal for the function
        // f(z) = z^4 - 1.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0), new Complex(), 
            new Complex(), new Complex(), new Complex(1,0)};
        Polynomial p = new Polynomial(coeff);
        NewtonFractal f = new NewtonFractal(p, new Complex(-4.0,4.0), 8.0);
        f.createFractal(false);
        f.saveFractal("fractal-light.png");
        f.createFractal(true);
        f.saveFractal("fractal-dark.png");
    }
}