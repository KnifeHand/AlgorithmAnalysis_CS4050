/*  
   automate (somewhat) the heuristic TSP algorithm
   (user still has to enter cuts, branches, and notice tours)

   This "text" version tosses all the graphical stuff and
textually reports the node currently being processed at
each step, and gets a command
(cut, branch, or tour)

*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;

import java.util.*;
import java.io.*;

public class AutoTSPtext
{

//==================================================================================================

  public static final double tiny = 0.0000001;

  public static double[][] points;  // points for problem, globally known

  public static Scanner keys = new Scanner( System.in );
//==================================================================================================

  public static void main(String[] args) throws Exception {

     Scanner input = new Scanner( new File( args[0] ) );

     int n = input.nextInt();   input.nextLine();

     // read and ignore the line that says "points" for compatibility
     // with HeuristicTSP which has some options not supported here
     input.nextLine();

     points = new double[ n ][2];

     for ( int k=0; k<n; k++ ) {
        points[k][0] = input.nextDouble();
        points[k][1] = input.nextDouble();
     }

     node = new Node();
     node.solve();
     System.out.println("\nInitial node: " + node );

     bestNode = null;

     pq = new ArrayList<Node>();  // is empty, node is current node

     do {

        node.display();  // replaces the graphical display of the current node

        System.out.println("Please enter a command line (c, b, t, q):");
        String command = keys.nextLine();

        // note: out of laziness, I'm assuming user will enter a valid command
        //  like  "c 2 12 17 8"  or "b 13" or "t" or "q"

        if ( command.length() > 2 &&
             command.substring(0,2).equals("c ") ) {// process cut command

           if( node.addCut( command ) ) {// is okay cut, was added to node, 
                                         // re-solve this node with no change to pq
              node.solve();
              System.out.println("\n------------------");
              System.out.println("after cut, current node is " + node );
              System.out.println("------------------");

           }// cut okay
           else {
              System.out.println("[" + command + "] is an invalid command");
           }

        }// cut 

        else if ( command.length() > 2 &&
                  command.substring(0,2).equals("b ") ) {// process branch command

           Node[] branchNodes = node.branch( command );
           if ( branchNodes != null ) {

              pq.add( branchNodes[0] );
              pq.add( branchNodes[1] );

              branchAndBoundStep();

           }
        
        }// branch

        else if ( command.equals("t") ) {// process tour command

           // update best node using current node
           // based on user's claim that this node
           // represents a tour
        
           if ( bestNode == null || bestNode.score > node.score ) { 
              bestNode = node;  // will never be changed again, so okay
              System.out.println("Updating best node to " + node );
           }
        
           branchAndBoundStep();

        }// tour
        
        else if ( command.equals("q") ) {// process quit command
           System.exit(0);
        }// quit

     } while (true);

  }// main

  private static Node node;  // the current node
  private static Tableau table;  // the tableau currently being used
  private static ArrayList<Node> pq;  // the priority queue (implemented crudely) 
                               // of all unexplored nodes

  private static Node bestNode;

  // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

  // do a step of the main branch and bound algorithm
  private static void branchAndBoundStep()
  {
    System.out.println("\n-------------------- priority queue in branch and bound step ----------");

    // remove all nodes with score worse than bestNode
    if( bestNode != null )
    {
      for( int k=pq.size()-1; k>=0; k-- )
      {
        Node current = pq.get(k);
        if( current.score  > bestNode.score )
        {
          System.out.println("pruning node " + current.id + " with score " + 
                               current.score );
          pq.remove(k);
        }
      }
    }

    if( pq.size() == 0 )
    {
      System.out.println("Priority queue is empty of nodes");
      if( bestNode == null )
      {
        System.out.println("Never found any tour???");
        System.exit(0);
      }
      else
      {
        node = bestNode;
        System.out.println("Best node: " + node );
        System.exit(0);
      }
    }
    else
    {// pq is not empty

      System.out.println( pq.get(0) );

      // remove best node
      int best = 0;
      for( int k=1; k<pq.size(); k++ )
      {
        System.out.println( pq.get(k) );
        if( pq.get(best).score > pq.get(k).score )
          best = k;
      }

      node = pq.get(best);
      System.out.println("Explore node <<< " + node.id + " >>> and remove from PQ");
      pq.remove( best );
    }

  }// branchAndBoundStep

}
