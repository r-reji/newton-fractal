/*
 * This file contains a template for the class Complex. This class implements a new 
 * type for complex numbers and corresponding arithmetic operations. 
 *
 * I have included a simple main function which tests the basic functionality manually.
 */

public class Complex {
    /**
     * Real part x of the complex number x+iy.
     */
    private double x;
    
    /**
     * Imaginary part y of the complex number x+iy.
     */
    private double y;

    /**
     * Constructor: Initializes x, y.
     *
     * @param x  The initial value of the real component.
     * @param y  The initial value of the imaginary component.
     */

    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Real constructor - initialises with a real number.
     *
     * @param x  The initial real number to initialise to.
     */
    public Complex(double x) {
        this.x = x;
        this.y = 0;
    }

    /**
     * Default constructor - initialise x and y to zero.
     */
    public Complex() {
        this.x = 0;
        this.y = 0;
    }
    
    // ========================================================
    // Accessor and mutator methods.
    // ========================================================
    
    /**
     * Accessor Method: get real part of the complex number.
     * 
     * @return The real part of the complex number.
     */
    public double getReal() {
        return this.x;
    }

    /**
     * Accessor Method: get imaginary part of the complex number.
     *
     * @return The imaginary part of the complex number
     */
    public double getImag() {
        return this.y;
    }

    /**
     * Mutator method: set the real part of the complex number.
     *
     * @param x  The replacement real part of z.
     */
    public void setReal(double x) {
        this.x = x;
    }
    
    /**
     * Mutator method: set the imaginary part of the complex number.
     *
     * @param y  The replacement imaginary part of z.
     */
    public void setImag(double y) {
        this.y = y;
    }
    
    // ========================================================
    // Operations and functions with complex numbers.
    // ========================================================

    /**
     * Converts the complex number to a string. This is an important method as
     * it allows us to print complex numbers using System.out.println.
     *
     * @return A string describing the complex number.
     */
    public String toString() {
        if (y < 0.0)
            return x + "-" + Math.abs(y) + "i";
        else
            return x + "+" + Math.abs(y) + "i";
    }

    /**
     * Computes square of the absolute value (magnitude) of the complex number
     * (i.e. |z|^2).
     *
     * @return The square of the absolute value of this complex number.
     */
    public double abs2() {
        return Math.pow(this.x,2) + Math.pow(this.y,2);
    }

    /**
     * Computes absolute value (magnitude) of the complex number.
     *
     * @return The absolute value of the complex number.
     */
    public double abs() {
        return Math.sqrt(this.abs2());
    }
    
    /**
     * Calculates the conjugate of this complex number.
     *
     * @return A Complex number which is the conjugate.
     */
    public Complex conjugate() {
        return new Complex(this.x, -this.y);
    }

    /**
     * Adds a complex number to this one.
     *
     * @param b  The complex number to add to this one.
     * @return   The sum of this complex number with b.
     */
    public Complex add(Complex b) {
        Complex a = new Complex();
        a.setReal(this.x + b.getReal());
        a.setImag(this.y + b.getImag());
        return a;
    }
    
    /**
     * Calculates -z.
     *
     * @return The complex number -z = -x-iy
     */
    public Complex minus() {
        Complex a = new Complex();
        a.setReal(-this.x);
        a.setImag(-this.y);
        return a;
    }

    /**
     * Multiplies this complex number by a constant.
     *
     * @param alpha   The constant to multiply by.
     * @return        The complex number z = alpha*x + i*alpha*y.
     */
    public Complex multiply(double alpha) {
        Complex a = new Complex();
        a.setReal(alpha*this.x);
        a.setImag(alpha*this.y);
        return a;
    }
    
    /**
     * Multiplies this complex number by another complex number.
     *
     * @param b   The complex number to multiply by.
     * @return    The product of b with z.
     */
    public Complex multiply(Complex b) {
        Complex a = new Complex();
        a.setReal(this.x*b.getReal() - this.y*b.getImag());
        a.setImag(this.x*b.getImag() + this.y*b.getReal());
        return a;
    }

    /**
     * Divide this complex number by another.
     *
     * @param b  The complex number to divide by.
     * @return   The division z/a.
     */
    public Complex divide(Complex b) {
        Complex a = new Complex();
        double c = Math.pow(b.x,2)+Math.pow(b.y,2);
        a.setReal((this.x*b.getReal() + this.y*b.getImag())/c);
        a.setImag((this.y*b.getReal() - this.x*b.getImag())/c);
        return a;
    }
        
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Test all of the constructor functions. You can add anything you
        // want to this if you think it's necessary!
        Complex A = new Complex(1.0, 1.0);
        Complex B = new Complex(1.0);
        Complex C = new Complex();
        
        // Test the converters by printing out A, B and C.
        System.out.println("Constructor test:");
        System.out.println("A = "+A.toString());
        System.out.println("B = "+B.toString());
        System.out.println("C = "+C.toString());
        
        // Test accessor/mutators.
        System.out.println("\nSetting imag(C) = real(C) + 1:");
        C.setImag(C.getReal() + 1.0);
        System.out.println("C = "+C.toString());
        
        // Now test operations for both complex and real numbers
        System.out.println("\nTesting operators:");
        System.out.println("abs(A)   = "+A.abs());
        System.out.println("abs2(A)  = "+A.abs2());
        System.out.println("abs(B)   = "+B.abs());
        System.out.println("abs2(B)  = "+B.abs2());
        System.out.println("conj(A)  = "+A.conjugate());
        System.out.println("conj(B)  = "+B.conjugate());
        System.out.println("neg(A)   = "+A.minus());
        System.out.println("neg(B)   = "+B.minus());
        System.out.println("A+B      = "+A.add(B));
        System.out.println("A+C      = "+A.add(C));
        System.out.println("2*A      = "+A.multiply(2.0));
        System.out.println("A*C      = "+A.multiply(C));
        System.out.println("B*C      = "+B.multiply(C));
        System.out.println("A*A      = "+A.multiply(A));
        System.out.println("A/B      = "+A.divide(B));
        System.out.println("A/C      = "+A.divide(C));
        System.out.println("A/A      = "+A.divide(A));

        // Testing chained operations.
        System.out.println("\nChained operators:");
        System.out.println("B*(A+C)    = "+B.multiply(A.add(C)));
        System.out.println("-A+B*C     = "+A.minus().multiply(B.add(C)));
    }
}