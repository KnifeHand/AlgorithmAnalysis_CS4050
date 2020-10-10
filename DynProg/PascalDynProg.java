import java.util.Scanner;
import java.math.BigInteger;

public class PascalDynProg {

   private static int count = 0;

   private static BigInteger[][] chart;

   public static void main(String[] args) {
      Scanner keys = new Scanner( System.in );
      System.out.print("enter n: ");
      int n = keys.nextInt();
      System.out.print("enter k: ");
      int k = keys.nextInt();

      // build empty chart for size of main problem:
      chart = new BigInteger[ n+1 ][ k+1 ];

      System.out.println("n choose k is " + pascal(n,k) );
      System.out.println("(took " + count + " calls)");
   }
   
   private static BigInteger pascal(int n, int k) {
      count++;

      if ( k==0 || k==n ) {
         return BigInteger.ONE;
      }
      else {
         // if desired answer is in chart, use it
         BigInteger temp = chart[ n ][ k ];
         if ( temp != null )
            return temp;
        
         chart[ n ][ k ] = 
                    pascal(n-1,k-1).add( pascal(n-1,k));
         return chart[ n ][ k ];
      }
   }
}
