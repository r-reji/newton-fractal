/*
 * This file contains a template for the class Newton. 
 *
 * This class creates a basic Java object responsible for
 * performing the Newton-Raphson root finding method on a given polynomial
 * f(z) with complex coefficients.
 *
 * There are some simple manual tests at the bottom of the file.
 * Roots can be checked by manual comparison with a complex function
 * plotter. 
 * 
 * Recommend the use of: https://samuelj.li/complex-function-plotter/#z
 */

import javax.management.openmbean.OpenMBeanAttributeInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

class Newton {
    /**
     * The maximum number of iterations that should be used when applying
     * Newton-Raphson. Value has been kept *small* to aid performance.
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance that will be used throughout the Newton-Raphson method.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Newton-Raphson method to.
     */
    private Polynomial f;

    /**
     * The derivative of the the polynomial f.
     */
    private Polynomial fp;

    /**
     * A root of the polynomial f corresponding to the root found by the
     * Newton-Raphson iterate() function below.
     */
    private Complex root;

    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An integer that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   =  0: Nothing went wrong.
     *   = -1: Derivative went to zero during the algorithm.
     *   = -2: Reached MAXITER iterations.
     */
    private int err;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor to et our polynomial f and its derivative fp.
     *
     * @param p  The polynomial used for Newton-Raphson.
     */
    public Newton(Polynomial p) {
        this.f = new Polynomial(p.coeff);
        this.fp = p.derivative();
    }

    // ========================================================
    // Accessor methods.
    // ========================================================

    /**
     * Method to return the error variable.
     * 
     * @return The current value of the err instance variable.
     */
    public int getError() {
        return this.err;
    }

    /**
     * Method to return the number of iterations that have been calculated.
     * 
     * @return The current value of the numIterations instance variable.
     */
    public int getNumIterations() {
        return this.numIterations;
    }

    /**
     * Method to return the root value of the current iteration.
     * 
     * @returns The current value of the root instance variable.
     */
    public Complex getRoot() {
        return this.root;
    }

    /**
     * Method to return the polynomial.
     * 
     * @return The polynomial.
     */
    public Polynomial getF() {
        return this.f;
    }

    /**
     * Method to return the derivative of the polynomial.
     * 
     * @return The derivative of the polynomial.
     */
    public Polynomial getFp() {
        return this.fp;
    }

    // ========================================================
    // Newton-Rapshon method
    // ========================================================

    /**
     * Given a complex number z0, apply Newton-Raphson to the polynomial f in
     * order to find a root within tolerance TOL. This uses the formula
     * z(n+1) = zn - f(zn)/f'(zn) until a root is calculated.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to 0.
     *   - At some point the derivative of f becomes zero. In this case, set err
     *     to -1 and return.
     *   - After MAXITER iterations the algorithm has not converged. In this
     *     case set err to -2 and return.
     *
     * @param z0  The initial starting point for the root finding algorithm.
     */

    public void iterate(Complex z0) {
        this.numIterations = 0;
        Complex oldRoot = new Complex();
        root = new Complex();
        Complex num;
        Complex denom;
        Complex fraction;
        Complex delta;
        for (int i = 0; i < MAXITER; i++){
            oldRoot.setReal(z0.getReal());
            oldRoot.setImag(z0.getImag());
            num = f.evaluate(z0);
            denom = fp.evaluate(z0);
            // Check that f(zn)/f'(zn) is valid
            if (denom.getReal() == 0 && denom.getImag() == 0){
                err = -1;
                numIterations = numIterations + 1;
                return;
            }
            fraction = num.divide(denom);
            z0 = z0.add(fraction.minus());
            numIterations = numIterations + 1;
            // Checking that the root is within tolerance
            delta = z0.add(oldRoot.minus());
            if (delta.abs() < TOL){
                root.setReal(z0.getReal());
                root.setImag(z0.getImag());
                err = 0;
                return;
            }
        }
        err = -2;
    }



    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Basic test: find a root of f(z) = z^3-1 from the starting point
        // z_0 = 1+i - compare with plotter mentioned on Line 12.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(),
                new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        Newton     n    = new Newton(p);
        n.iterate(new Complex(1.0, 1.0));
        System.out.println(n.root);
    }
}