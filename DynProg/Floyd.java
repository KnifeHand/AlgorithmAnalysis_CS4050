import java.io.*;

public class Floyd
{
  private final static int m = 10000;
  private final static double cellSize = 3;

  // toggle compact, highlighted form to one per page form
  // for exporting to Explain Everything
  private final static boolean compact = false;

  public static void main(String[] args) throws Exception
  {
   // video example March 2020
   int[][] d = {{0,1,7,6,3},
                {m,0,5,3,m},
                {m,m,0,2,m},
                {1,m,m,0,8},
                {m,4,2,m,0}};
   /* Example 1
    int[][] d = {{0,10,m,m,m},
                 {1,0,2,4,m},
                 {2,m,0,3,6},
                 {2,m,3,0,4},
                 {m,1,m,11,0}};
   */
   /* Example 2
    int[][] d = {{0,1,m,1,5},{9,0,3,2,m},
         {m,m,0,4,m},{m,m,2,0,3},{3,m,m,m,0}};
   */
/*
   // ex8
    int[][] d = {{0,5,8,12,15},
                 {m,0,5,8,m},
                 {m,m,0,2,4},
                 {m,m,m,0,1},
                 {6,m,m,m,0}};
*/

/*    // text example
    int[][] d= {{0,1,m,1,5},{9,0,3,2,m},{m,m,0,4,m},
                {m,m,2,0,3},{3,m,m,m,0}};
*/

/*
    int[][] d = { {0,m,5,m,m},
                  {m,0,4,m,m},
                  {m,m,0,2,6},
                  {m,3,m,0,m},
                  {8,m,m,m,0}};
*/
/*
    int[][] d = { {0,3,m,m},
                  {4,0,5,m},
                  {7,m,0,6},
                  {2,m,m,0} };
*/

/*
    // Test 2 problem:
    int[][] d = { {0,m,3,10,7},
                  {8,0,m,11,5},
                  {m,6,0,4,9},
                  {12,m,15,0,m},
                  {m,m,7,m,0} };
*/

/*
    // Exercise 7 test case, spring 14:
    int[][] d = { {0,12,5,m,m,m},
                  {m,0,m,17,m,m},
                  {m,5,0,13,6,m},
                  {m,m,m,0,m,20},
                  {m,m,m,1,0,10},
                  {8,m,m,m,m,0} };
*/

/*
  // Test 1 question, fall 15:
  int[][] d = { {0,m,15,12,9},
                {14,0,6,m,m},
                {m,19,0,m,21},
                {8,7,m,0,10},
                {5,2,10,m,0} };
*/

/*
  // Test 1 spring 15
    int[][] d = { {0,m,16,m,6},
                  {m,0,m,4,m},
                  {10,9,0,m,m},
                  {5,m,12,0,8},
                  {m,3,m,m,0} };
*/

/*
  // t1, spring 15
  int[][] d = {{0,m,15,12,9},
               {14,0,6,m,m},
               {m,19,0,m,21},
               {8,7,m,0,10},
               {5,2,10,m,0}};
*/

/*
    // t2, spring 15
    int[][] d = {{0,12,5,m,m,m},
                 {m,0,m,17,m,m},
                 {m,5,0,13,6,m},
                 {m,m,m,0,m,20},
                 {m,m,m,1,0,10},
                 {8,m,m,m,m,0}  };
*/

/*
    // test3, spring 15
    int[][] d = {{0,2,10,3,16},
                 {3,0,3,1,9},
                 {4,2,0,3,5},
                 {5,1,6,0,12},
                 {6,7,8,9,0}};
*/

/*
    int[][] d = {{0,3,6,11,17,m},
                 {m,0,2,m,m,m},
                 {m,m,0,4,m,m},
                 {m,m,m,0,5,m},
                 {m,m,m,m,0,8},
                 {2,m,m,m,m,0}
                };
*/

/*
    // secret test of Project 5, Fall 2017
    int[][] d = {{0,1,1,1,1},
                 {1,0,6,m,m},
                 {m,1,0,12,m},
                 {m,m,1,0,20},
                 {m,m,m,1,0}};
*/

/*
   // Test 2 problem
   int[][] d = {{0,4,m,m,5},
                {2,0,3,2,8},
                {2,m,0,m,1},
                {m,5,1,0,3},
                {9,m,4,m,0} };
*/
    
    int n = d.length;

    boolean[][] flag = new boolean[n][n];
    clear( flag );

    int[][] path = new int[n][n];

    initTeX();
    plain = new PrintWriter( new File( "plainCharts" ) );

    doTable( d, flag, 0, path );

    for( int k=0; k<n; k++ )
    {
      clear( flag );
      for( int i=0; i<n; i++ )
        for( int j=0; j<n; j++ )
        {
          if( i != k && j != k && i != j )
          {
            if( d[i][j] > d[k][j]+d[i][k] )
            {
              d[i][j] = d[k][j]+d[i][k];
              flag[i][j] = true;
              path[i][j] = (k+1);
            }
          }
        }
        doTable( d, flag, k+1, path );
    }
 
    finishTeX();
    plain.close();
  }

  private static void clear( boolean[][] flag )
  {
    int n = flag.length;
    for( int k=0; k<n; k++ )
      for( int j=0; j<n; j++ )
        flag[k][j] = false;
  }

  private static PrintWriter output, plain;

  private static void initTeX() throws Exception
  {
    output = new PrintWriter( new FileWriter( "floydCharts.tex" ) );
    output.println("\\input pictex");
    output.println("\\parindent 0in");
    output.println("\\nopagenumbers");
    output.println();

  }

  private static void finishTeX()
  {
    output.println("\\vfil\\eject\\bye");
    output.close();
  }

  private static void doTable( int[][] d, boolean[][] flag, int s, 
                               int[][] path ) throws Exception
  {
    int n = d.length; // number of vertices

    // output to plain text file
    plain.println("\n\nChart " + s + "\n" );
    // column labels
    plain.print("    ");  
    for (int col=1; col<=n; col++) {
       plain.printf("%12d", col );
    }
    plain.println("\n");

    // print all the rows
    for (int row=1; row<=n; row++) {
       plain.printf("%4d", row );
       for (int col=1; col<=n; col++) {
          if ( d[row-1][col-1] < m ) 
             plain.printf("%8d(%2d)", d[row-1][col-1], path[row-1][col-1] );
          else
             plain.printf("     inf(%2d)", path[row-1][col-1] );
       }
       plain.println("\n");
    }

    output.println("$\\displaystyle D^{(" + s + ")}$"); 
    output.println("\\bigskip");

    drawBlankTable( d.length );
   
    String value;
    double smidge = 0.2*cellSize;

    for( int k=0; k<n; k++ )
      for( int j=0; j<n; j++ )
      {
        if( d[k][j] >= m )
          value = "$\\infty$";
        else
          value = "" + d[k][j];

        if( flag[k][j] )
          output.println("\\put{\\bf " + value + "} [lt] at " +
                          (j*cellSize+smidge) + " " + ((n-k)*cellSize-smidge) );
        else
          output.println("\\put{" + value + "} [lt] at " +
                          (j*cellSize+smidge) + " " + ((n-k)*cellSize-smidge) );

        output.println("\\put{" + path[k][j] + "} [rb] at " +
                ((j+1)*cellSize-smidge) + " " + ((n-k-1)*cellSize+smidge) );
        
      }

    if( s < n && compact )
    {
      // highlight row and column k
      output.println("\\putrectangle corners at " + smidge/2 + " " + 
                        ((n-s-1)*cellSize+smidge/2) + " and " + 
            (n*cellSize-smidge/2) + " " + ((n-s)*cellSize-smidge/2) );
      output.println("\\putrectangle corners at " + (s*cellSize+smidge/2) +
              " " + (smidge/2) + " and " + 
              ((s+1)*cellSize-smidge/2) + " " + (n*cellSize-smidge/2) );
    }

    output.println("\\endpicture");
    output.println("\\bigskip");

    if ( !compact ) {
       output.println("\\vfil\\eject");
    }

  }

  private static void drawBlankTable( int n )
  {
    output.println("\\beginpicture");
    output.println("\\setcoordinatesystem units <12pt,12pt>");


    // draw vertical lines
    for( int k=0; k<=n; k++ )
    {
      output.println("  \\putrule from " + (k*cellSize) + " 0 to " +
                         (k*cellSize) + "  " + (n*cellSize) + " " );
      if( k<n )
        output.println(" \\put{$" + (k+1) + "$} [b] at " + ((k+0.5)*cellSize) +
                        " " + ((n+0.2)*cellSize) );
    }
    
    // draw horizontal lines
    for( int j=0; j<=n; j++ )
    {
      output.println("  \\putrule from 0 " + (j*cellSize) + " to " +
                          (n*cellSize) + " " + (j*cellSize) );
      if( j<n )
        output.println("  \\put{$" + (n-j) + "$} [r] at " + (-0.2*cellSize) +
                          " " + ((j+0.5)*cellSize) );
                       
    }

  }

}
