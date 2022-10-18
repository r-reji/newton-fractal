/*
 * This file contains a template for the class Polynomial. 
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex coefficients. Coefficients are assumed to be ordered and of the
 * form:
 * 
 * a = x + iy such that P(X) = a0 + a1*X + a2*X^2 + ... + an*X^n
 *
 * There are some simple manual tests at the bottom of the file.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Polynomial {
    /**
     * An array storing the complex coefficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * coefficients.
     *
     * @param coeff  The coefficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        int degree = coeff.length;
        for (int i = degree - 1; i > -1; i--){
            if (coeff[i].getReal() == 0 && coeff[i].getImag() == 0){
                degree--;
            }
            else{
                break;
            }
        }
        this.coeff = new Complex[degree];
        for (int i = 0; i < degree; i++){
            this.coeff[i] = coeff[i];
        }
    }

    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        this.coeff = new Complex[] {new Complex()};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Create a string representation of the polynomial.
     *
     * @return String representation of the polynomial (see Line 8)
     */
    public String toString() {
        String s = "";
        if(this.coeff[0].getReal() != 0 || this.coeff[0].getImag() !=0) {
            s = ("(" + this.coeff[0] + ")");
        }
        for(int i = 1; i < this.coeff.length; i++){
            if(this.coeff[i].getImag() == 0 && this.coeff[i].getReal() == 0){
                continue;
            }
            s = s+("+"+"("+this.coeff[i]+")"+"x^"+i);
        }
        return s;
    }

    /**
     * Returns the degree of this polynomial.
     * 
     * @return Polynomial degree
     */
    public int degree() {
        return this.coeff.length-1;
    }

    /**
     * Evaluates the polynomial at a given point z. By using the following
     * relation:
     * 
     * P(z) = a0 + a1*z + a2*z^2 + ... + an*z^n = a0 + z(a1 + z(a2 + ... + z(an-1 + z*an)...))
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex result = new Complex();
        result.setReal(this.coeff[this.coeff.length-1].getReal());
        result.setImag(this.coeff[this.coeff.length-1].getImag());
        for (int i = this.coeff.length-2; i >-1; i--){
            Complex num = result.multiply(z);
            num = num.add(this.coeff[i]);
            result.setReal(num.getReal());
            result.setImag(num.getImag());
        }
        return result;
    }

    /**
     * Calculate and returns the derivative of this polynomial.
     *
     * @return The derivative of this polynomial.
     */
    public Polynomial derivative() {
        Complex[] derCoeff = new Complex[this.coeff.length-1];
        for (int i = 1; i < this.coeff.length; i++){
            derCoeff[i-1] = this.coeff[i].multiply(i);
        }
        return new Polynomial(derCoeff);
    }

    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Complex[] test = new Complex[10];
        for(int i = 0; i < test.length; i++){
            test[i] = new Complex(i+1,i+1);
        }
        Polynomial p = new Polynomial(test);
        System.out.println("The polynomial is");
        System.out.println(p.toString());
        System.out.println("Of degree:");
        System.out.println(p.degree());
        Complex c = new Complex(1,0);
        System.out.println("The polynomial evaluated at the point ("+c+") is:");
        System.out.println(p.evaluate(c));
        System.out.println("The derivative is:");
        System.out.println(p.derivative());
        System.out.println();
        System.out.println("Testing the copying of zeroes: ");
        Complex[] zeroes = new Complex[] {new Complex(-1,1), new Complex(), new Complex(), new Complex(1,2), new Complex(), new Complex(1,3)};
        Polynomial t = new Polynomial(zeroes);
        System.out.println(t);
        System.out.println();
        System.out.println("Testing the Newton polynomial: ");
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(), new Complex(), new Complex(1.0,0.0) };
        System.out.println("Length of Newton polynomial array: "+coeff.length);
        System.out.println(coeff[0]);
        System.out.println(coeff[1]);
        System.out.println(coeff[2]);
        System.out.println(coeff[3]);
        Polynomial z    = new Polynomial(coeff);
        System.out.println(z);
    }
}