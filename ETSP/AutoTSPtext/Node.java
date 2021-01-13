/*
   a node of the branch and bound tree
   for AutoHeuristicTSP
*/

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Node
{
  private static int currentId = 0;

  public int id;
  public ArrayList<ArrayList<Integer>> cuts;
  public ArrayList<Pair> zeros;
  public ArrayList<Pair> ones;

  // remember info from Tableau
  public double score;
  public String[] variables;
  public double[] values;

  // build a node with no commands
  public Node()
  {
    currentId++;  id = currentId;

    cuts = new ArrayList<ArrayList<Integer>>();
    zeros = new ArrayList<Pair>();
    ones = new ArrayList<Pair>();

    solve();
  }

  public void solve()
  {
    Tableau table = new Tableau( this );
    table.doSimplexMethod();
    score = - table.getOptimalCost();
    variables = table.getBasicVariableNames();
    values = table.getBasicVariableValues();
  }

  // deep copy original node and depending on whether which is
  // "zero" or "one" add (k,j) to zeros or ones
  public Node( Node orig, String which, int k, int j )
  {
    currentId++;  id = currentId;

    cuts = new ArrayList<ArrayList<Integer>>();
    for( int r=0; r<orig.cuts.size(); r++ )
    {
      // make copy of orig list r
      ArrayList<Integer> copy = new ArrayList<Integer>();
      for( int c=0; c<orig.cuts.get(r).size(); c++ )
        copy.add( orig.cuts.get(r).get(c) );
      cuts.add( copy );
    }

    zeros = new ArrayList<Pair>();
    for( int m=0; m<orig.zeros.size(); m++ )
    {
      zeros.add( new Pair( orig.zeros.get( m ) ) );
    }

    ones = new ArrayList<Pair>();
    for( int m=0; m<orig.ones.size(); m++ )
    {
      ones.add( new Pair( orig.ones.get( m ) ) );
    }

    // now add the new zero or one command
    if( which.equals( "zero" ) )
      zeros.add( new Pair( k, j ) );
    else
      ones.add( new Pair( k, j ) );

    solve();
  }

  // process s into a cut command, if
  // is valid, and return true, otherwise
  // return false
  public boolean addCut( String s )
  {
    ArrayList<Integer> aCut = new ArrayList<Integer>();
    s = s.substring(2);  // toss the irritating "c " part

    StringTokenizer st = new StringTokenizer( s );
    while( st.hasMoreTokens() )
    {
      String w = st.nextToken();
      try
      {
        int x = Integer.parseInt( w );
        if( 1<=x && x<=AutoTSPtext.points.length )
          aCut.add( x );
        else
          return false;
      }
      catch(Exception e)
      {
        return false;
      }
    }

    // succeeded
    cuts.add( aCut );

    return true;

  }// addCut

  // process s into two new nodes, one with
  // a zero command and the other with a one
  // command, both for the two points coded up in s,
  // or return null if s is invalid
  public Node[] branch( String s )
  {
    Node[] nodes = new Node[2];

    s = s.substring(2);  // toss the irritating "b " part

    ArrayList<Integer> points = new ArrayList<Integer>();
    StringTokenizer st = new StringTokenizer( s );
    while( st.hasMoreTokens() )
    {
      String w = st.nextToken();
      try
      {
        int x = Integer.parseInt( w );
        if( 1<=x && x<=AutoTSPtext.points.length )
          points.add( x );
        else
          return null;
      }
      catch(Exception e)
      {
        return null;
      }
    }

    if( points.size() == 2 && points.get(0).compareTo(points.get(1)) != 0 )
    {// have two valid points coded up in s

      nodes[0] = new Node( this, "zero", points.get(0), points.get(1) );
      nodes[1] = new Node( this, "one", points.get(0), points.get(1) );

      return nodes;
    }
    else
      return null;

  }// addCut

  public String toString()
  {
    String r = "Id: " + id + " score: " + score + "\n";
    r += "Cuts:\n";
    for( int k=0; k<cuts.size(); k++ )
    {
      r += "[";
      for( int j=0; j<cuts.get(k).size(); j++ )
        r += cuts.get(k).get(j).toString() + " ";
      r += "] ";
   
    }
    r += "\n";

    r += "Zeros: ";
    for( int m=0; m<zeros.size(); m++ )
      r += zeros.get(m).first + "-" + zeros.get(m).second + " ";
    
    r += "  ";

    r += "Ones: ";
    for( int m=0; m<ones.size(); m++ )
      r += ones.get(m).first + "-" + ones.get(m).second + " ";
    
    r += "\n";

    return r;
  }

  // textually display info about this node, namely
  // its edges and their weights
  public void display() {
 
     double tiny = AutoTSPtext.tiny;

     // stub
/*
     for (int k=0; k<variables.length; k++) {
        System.out.println( k + ": " + variables[k] + " " + values[k] );
     }
*/

     // better stub:  obtain the edges by doing x12,15 --> (12,15)

     // determine all the weight 1 edges

     ArrayList<Pair> edges = new ArrayList<Pair>();

     for (int m=0; m < variables.length; m++ ) {
        if ( variables[m].charAt(0) == 'x' && values[m] > tiny ) {
           // pull out the indices

           String w = variables[m];
           int comma = w.indexOf( ',' );
           int k = Integer.parseInt( w.substring(1,comma) );
           int j = Integer.parseInt( w.substring(comma+1) );

/*
          System.out.print( "(" + k + "," + j + "): " );

           if( Math.abs( values[m] - 1 ) < tiny )
              System.out.println( 1 );
           else 
              System.out.println( values[m] );
*/

           if ( Math.abs( values[m] - 1 ) < tiny ) {
              edges.add( new Pair( k, j ) );
           }

        }// is an xj,k variable with positive value
     }// basic variable m

/*
     for (int m=0; m<edges.size(); m++) {
        System.out.println( edges.get(m) );
     }
*/

     // now find all the sequences (sequences of nodes of intensity near 1,
     // might be cycles)

     System.out.println("\nHere are all the connected sequences of vertices");
     System.out.println("on edges of intensity 1:\n");
     
     while ( edges.size() > 0 ) {

        // starting a new sequence
        ArrayList<Integer> seq = new ArrayList<Integer>();

        Pair edge = edges.get( 0 );  
//System.out.println("start new sequence with edge " + edge );

        edges.remove(0);
        int start = edge.first, stop = edge.second;
        seq.add( start );  seq.add( stop );

        // examine all edges to see if they extend the sequence
        // and if they do, remove them from list and add to seq
        //  (when start or stop is changed, have to search all
        //   remaining edges again)

        boolean found;  // did we find an extending edge

        do {
 
           found = false;

           // check edges until find one that extends or reach end
           for (int j=0; j<edges.size() && !found; j++) {

              Pair trial = edges.get(j);
     
 //       System.out.println("    trying to connect to " + trial );
     
              if ( trial.first == start ) {
                 seq.add( 0, trial.second );
                 start = trial.second;
                 edges.remove(j);
                 found = true;
              }
              else if ( trial.second == start ) {
                 seq.add( 0, trial.first );
                 start = trial.first;
                 edges.remove(j);
                 found = true;
              }
              else if ( trial.first == stop ) {
                 seq.add( trial.second );
                 stop = trial.second;
                 edges.remove(j);
                 found = true;
              }
              else if ( trial.second == stop ) {
                 seq.add( trial.first );
                 stop = trial.first;
                 edges.remove(j);
                 found = true;
              }
              else {
              }

         }// loop on j to find an extending edge
   
  // System.out.println("now seq is " + seq );
   
        } while ( found );  // keep trying until don't find one

        // have finished this sequence, display it
        for (int k=0; k<seq.size(); k++) {
           System.out.print( seq.get(k) + " " );
        }
        System.out.println();

        // report useful info about this sequence
        int first = seq.get(0), last = seq.get( seq.size()-1 );
        if ( first == last ) {
           System.out.println("   (this is a cycle)\n");
        }
        else if ( Math.abs( intensity( first, last ) - 0.5 ) < tiny ) {
           System.out.println("   (intensity between " + first + " and " +
                        last + " is 1/2)\n"); 
        }
        else if ( intensity(first,last)==-1 ||  // not basic
                  Math.abs( intensity( first, last ) - 0.0 ) < tiny ) {
           System.out.println("   (" + first + " and " + last + 
                        " are not directly connected)\n");
        }
        else {
           System.out.println("\n");
        }

     }// loop to get each sequence

     // report non 0-1 intensities for possible branching
     System.out.println("Here are the edges with intensities not 0 or 1:");

     for (int m=0; m<variables.length; m++) {
        if ( variables[m].charAt(0) == 'x' &&
             tiny < values[m] && values[m] < 1-tiny ) {
           System.out.printf("%8s = %6.3f\n", variables[m], values[m] );
        }
     }

     System.out.println("\n");

  }// display

   // use variables and values to find intensity of
   // edge between vertices k and j
   private double intensity( int k, int j ) {
      // make sure j < k:
      if ( k < j ) {
         int temp = k;
         k = j;
         j = temp;
      }

      // find in variables xjk and return its value

      for (int m=0; m < variables.length; m++ ) {
        if ( variables[m].equals( "x" + j + "," + k ) ) {
           return values[m];
        }
      }

      return -1;  // to satisfy compiler
   }// intensity

}// Node
