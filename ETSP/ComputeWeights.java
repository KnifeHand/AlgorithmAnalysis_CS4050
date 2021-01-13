/* 
  read data file and compute and
  display all pairwise distances
  on-screen,

  and produce pictex code to plot the points

  then type in a tour and compute its cost
*/

import java.io.*;
import java.util.*;

public class ComputeWeights
{
  // special for TSP format:
  private static double[][] pts;

  public static void main(String[] args) throws Exception {
    PrintWriter texout = new PrintWriter( new File( "pic.tex" ) );

    Scanner in = new Scanner( new File( args[0] ) );
    Scanner keys = new Scanner( System.in );

    int n = in.nextInt(); in.nextLine();

    in.nextLine();  // read and toss the points line
   
    pts = new double[ n ][2];

    texout.println("% points to plot:");

    for( int k=0; k<n; k++ ) {
      pts[k][0] = in.nextDouble();
      pts[k][1] = in.nextDouble();

      texout.println("\\put {$\\bullet$} at " + pts[k][0] + " " + pts[k][1] );
      texout.println("\\put {$\\scriptscriptstyle " + (k+1) + "$} [lt] at " + (pts[k][0]+1) + " " + (pts[k][1]+1) );
    }

    System.out.print("   ");
    for( int col=2; col<=n; col++ )
      System.out.print( String.format("%8d", col ) );
    System.out.println();

    double[][] weights = new double[n][n];

    for( int row=0; row<n-1; row++ ) {
      System.out.print( String.format("%2d:", (row+1) ) );
      for( int col=1; col<n; col++ ) {
         if( col < row+1 )
           System.out.print("        ");
         else {
           double d = dist(pts,row,col);
           System.out.print( String.format("%8.2f", d ) );        
           weights[row][col] = d;
           weights[col][row] = d;
         }
      }
      System.out.println();
    }

    int x, y;

    texout.println("\\input pictex");
    texout.println("\\beginpicture");
    texout.println("\\setcoordinatesystem units <12 mm,6true mm>");

    texout.println("% draw the row labels:");
    for ( y=1; y<n; y++) {
       texout.println("\\put {$" + y + "$} [r] at " + (y-1.5) + " " + (n-y+0.5) );
    }
   
    texout.println("% draw the horizontal lines:");

    texout.println("\\putrule from 0 " + n + " to " + (n-1) + " " + n );  // top horizontal line
    x = n-1;  // start of solid part of line at height y
    for ( y=1; y<n; y++) {// draw line at height y
//       texout.println("\\setdots <2true mm>");
//       texout.println("\\putrule from 0 " + y + " to " + (x-1) + " " + y );
//       texout.println("\\setsolid");
       texout.println("\\putrule from " + (x-1) + " " + y + " to " + (n-1) + " " + y );
       x--;
    }
 
    texout.println("% draw the column labels:");
    for ( x=2; x<=n; x++) {
       texout.println("\\put {$" + x + "$} [b] at " + (x-1.5) + " " + (n+0.5) );
    }

    texout.println("% draw the vertical lines:");

    y = n-1;  // lower y coord of vertical line
    for (x=0; x<n-1; x++) {
       texout.println("\\putrule from " + x + " " + n + " to " + x + " " + y );
       y--;
    }
    texout.println("\\putrule from " + (n-1) + " " + n + " to " + (n-1) + " " + 1 );

    texout.println("% draw the weights:");

    for (int j=1; j<n; j++) {// cost of vertex j
       for (int k=j+1; k<=n; k++) {// to vertex k
          texout.println("\\put {$\\scriptstyle" + String.format("%8.2f", weights[ j-1 ][ k-1 ] ) + "$} at " +
                              (k-1.5) + " " + (n-j+0.5) );
       }
    }
   
    texout.println("\\endpicture");

    texout.close();

    int[] tour = new int[n+1];

    System.out.println("enter tour (like 1 3 2 7 ...):");
    for (int k=0; k<n; k++) {
       System.out.print("enter the " + (k+1) + "th vertex number in tour: ");
       tour[k] = keys.nextInt();
    }
    tour[n] = tour[0];

    System.out.println("cost of tour:");
    double total = 0;
    for (int k=0; k<n; k++ ) {
        double w = weights[ tour[k]-1 ][ tour[k+1]-1 ];
       
       System.out.println("edge from " + tour[k] + " to " + tour[k+1] + " is " + w );
       total += w;
    }
    System.out.println("    total tour cost is " + total );

  }// main

  private static double dist( double[][] pts, int row, int col ) {
    double x1 = pts[row][0], y1 = pts[row][1];
    double x2 = pts[col][0], y2 = pts[col][1];

    return Math.sqrt( Math.pow( x1-x2, 2) + Math.pow( y1-y2, 2 ) );
  }

}
