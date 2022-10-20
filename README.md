# newtonFractal

This repository contains all the code that was written for a Java programming module I took in 2018-2019. Assuming you have all the necessary dependancies installed, you should be able to run [NewtonFractal.java](https://github.com/r-reji/newtonFractal/blob/main/src/NewtonFractal.java).

### [NewtonFractal.java](https://github.com/r-reji/newtonFractal/blob/main/src/NewtonFractal.java)

This is the main file for this project. It will generate a graphical representation of a Newton fractal derived from root finding algorithms on a complex polynomial. By default generated images will be saved in the same folder.

You can produce your own Newton fractals by adding the necessary code to the main method of this file - an example has been shown below.

Note: the fractal generation takes a boolean operator `colorIterations` which, when passed `true`, will set the colours of the fractal as a function of convergence time. ie. the longer a point in your domain takes to converge to a root, the darker the colour of the point will be. See below for examples of both image types.

![fractal-light](https://user-images.githubusercontent.com/112977394/196665704-14e80b3b-79a9-4104-9b57-28e05389a5ef.png)
![fractal-dark](https://user-images.githubusercontent.com/112977394/196665783-7d45a913-b099-450b-9712-83474c0f3ebe.png)

```
// Here is some example code which generates the Newton fractals for the function P(z) = z^4 - 1 shown above

// Define an array of complex coeeficiants in order to initialise a complex polynomial
Complex[] coeff = new Complex[] { new Complex(-1.0,0), new Complex(), new Complex(), new Complex(), new Complex(1,0)};
Polynomial p = new Polynomial(coeff);

// Define the fractal with parameters for the start grid (top left corner, size)
NewtonFractal f = new NewtonFractal(p, new Complex(-4.0,4.0), 8.0);

// Generate a Newton fractal with colorIterations false then true
f.createFractal(false);
f.saveFractal("fractal-light.png");
f.createFractal(true);
f.saveFractal("fractal-dark.png");

```

The other files in this repository are necessary for the project to run. They have been tested manually and you should not need to change anything. More information on each file below:

 - [Complex.java](https://github.com/r-reji/newtonFractal/blob/main/src/Complex.java) provides a template for a complex number object and all necessary associated methods.
 - [Polynomial.java](https://github.com/r-reji/newtonFractal/blob/main/src/Polynomial.java) provides a template for a complex polynomial and all necessary associated methods.
 - [Newton.java](https://github.com/r-reji/newtonFractal/blob/main/src/Newton.java) implements a Newton - Raphson root finding algorithm on a complex polynomial with a defined set of tolerances on convergence.
