/*
  plot (in pictex) enough
  points for a nice picture of
  an EC

  a, b, and other things are hard-coded
*/

import java.util.Scanner;
import java.math.BigInteger;
import java.io.*;

public class PlotRealEC {

   public static void main(String[] args) throws Exception {

      double a = -5;
      double b = 4;

      double left = -3, right = 3;
      double bottom = -4, top = 4;

      PrintWriter out = new PrintWriter( new File( "graph.tex" ) );

      out.println("\\beginpicture");
      out.println("\\setcoordinatesystem units <20true mm,20true mm>");
      out.println("\\setplotarea x from " + left + " to " + right + ", y from "
                       + bottom + " to " + top );
      int n = 1000;
      double dx = (right-left)/n;

      // plot upper half

      double x = left;
      out.println("\\plot");
      for (int k=1; k<=n; k++) {

         double y2 = (x*x*x + a*x + b);

         if ( y2 >= 0 ) {
            double y = Math.sqrt( y2 );
            String xs = nice(x);
            out.println( xs + " " + nice(y) );
         }

         x += dx;
      }
      out.println("/");

      // plot lower half

      x = left;
      out.println("\\plot");
      for (int k=1; k<=n; k++) {

         double y2 = (x*x*x + a*x + b);

         if ( y2 >= 0 ) {
            double y = Math.sqrt( y2 );
            String xs = nice(x);
            out.println( xs + " " + nice(-y) );
         }

         x += dx;
      }
      out.println("/");

      out.println("\\endpicture");
      out.println("\\vfil\\eject\\bye");
      out.close();
   }

   private static String nice( double x ) {
      return String.format("%10.5f", x );
   }

}
