/*
   hard-code where line through two
points hits other points
*/

import java.util.Scanner;
import java.math.BigInteger;
import java.io.*;

public class LineTool {

   public static void main(String[] args) throws Exception {

      double[] p = { -.996, 2.827 };
      double[] q = { .504, -1.26808};

      double lambda = // (q[1] - p[1]) / (q[0]-p[0]);
                      (3*p[0]*p[0] - 5 ) / (2 * p[1] );
 
      System.out.println("At x= " + p[0] + ", y= " + p[1] );
      System.out.println("At x= " + q[0] + ", y= " + q[1] );
      System.out.println("At x=3.5, y = " + ( q[1] + lambda*(3.5-q[0])));
      System.out.println("At x=-2, y = " + ( q[1] + lambda*(-2-q[0])));

      // use ECC stuff 
      //  (looks magical---given p,q on an elliptic curve, these formulas
      //   find where the line hits that curve---with absolutely no mention
      //   of the curve's a, b?)
//      double x = lambda*lambda - p[0] - q[0];
      double x = lambda*lambda - p[0] - p[0];
      double y = p[1] + lambda*(x-p[0]);
      System.out.println("line hits curve at " + x + " " + y );
   }

   private static String nice( double x ) {
      return String.format("%10.5f", x );
   }

}
