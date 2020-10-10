/*
  plot (in pictex) all
  points on Elliptic Curve (EC)
  for small n

Since we want a picture of the points on the curve,
n will be small, so just use int values (not BigInteger)
*/

import java.util.Scanner;
import java.math.BigInteger;
import java.io.*;

public class PlotEC {

   private static int unitSize = 8;

   public static void main(String[] args) throws Exception {
      Scanner keys = new Scanner( System.in );
      System.out.print("Enter p: ");
      int n = keys.nextInt();
      System.out.print("Enter a: ");
      int a = keys.nextInt();
      System.out.print("Enter b: ");
      int b = keys.nextInt();
      System.out.print("Enter P_x: ");
      int px = keys.nextInt();
      System.out.print("Enter P_y: ");
      int py = keys.nextInt();
      
      PointECC.init( ""+n, ""+a, ""+b );
      PointECC p = new PointECC( ""+px, ""+py );

      PrintWriter out = new PrintWriter( new File( "graph.tex" ) );
      out.println("\\advance \\hoffset -0.75true in");
      out.println("\\advance \\hsize 1.5true in");
      out.println("\\input pictex");
      out.println("\\beginpicture");
      out.println("\\setcoordinatesystem units <" + unitSize + "true mm," + 
                        unitSize + "true mm>");
      out.println("\\setplotarea x from 0 to " + (n-1) + ", " +
                    "y from 0 to " + (n-1) );

      out.println("\\setdots <1true mm>");
      out.println("\\grid " + (n-1) + " " + (n-1) );
      out.println("\\setsolid");

      // iterate through 0..n-1 and square each
      int[] squares = new int[n];
      for (int k=0; k<n; k++ ) {
         squares[ k ] = (k*k) % n;
         System.out.println( k + " " + squares[k] );
      }

      // plot all the points on the EC
      for (int x=0; x<n; x++ ) {
         // find first guy x such that x^3 + ax + b is a square
         // and plot the two points

         int y2 = (x*x*x + a*x + b) % n;

         // look for y that squares to give y2
         boolean found = false;
         for (int y=0; y<n && !found; y++ ) {
            if ( ((y*y)%n) == y2 ) {
               found = true;
               // plot two points---(x,y) and (x,-y)
               out.println("\\put {$\\bullet$} at " + x + " " + y);
               int minusY = (n-y) % n;
               out.println("\\put {$\\bullet$} at " + x + " " + minusY);
            }
         }
      }

      // label the x axis
      for (int k=0; k<n; k++ ) {
         out.println("\\putrule from " + k + " " + 0 + " to " + k + " -0.5" );
         out.println("\\put {$\\scriptscriptstyle " + k + "$} [t] at " + k + " -0.6" );
      }

      // label the y axis   
      for (int k=0; k<n; k++ ) {
         out.println("\\putrule from " + 0 + " " + k + " to " + 
                          (-0.5) + " " + k );
         out.println("\\put {$\\scriptscriptstyle " + k + "$} [r] at -0.6 " + k );
      }

      // starting from P, label all the points in order of scalar multiple
      // quitting when hit 0
      PointECC q = new PointECC( p );
      int mult = 1;

      do {
         out.println("\\put {$\\scriptscriptstyle " + mult + "$} [b] at " +
                    ( q.getX().intValue() ) + 
              " " + ( q.getY().intValue() + 0.25 ) );
         mult++;
         q = q.add( p );
         System.out.println( mult + ": " + q );
      } while( q != PointECC.zero );

      out.println("\\endpicture");
      out.println("\\vfil\\eject\\bye");
      out.close();
   }

}
