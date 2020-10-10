import java.util.Scanner;
import java.math.BigInteger;

public class Pascal {

   private static int count = 0;

   public static void main(String[] args) {
      Scanner keys = new Scanner( System.in );
      System.out.print("enter n: ");
      int n = keys.nextInt();
      System.out.print("enter k: ");
      int k = keys.nextInt();

      System.out.println("n choose k is " + pascal(n,k) );
      System.out.println("(took " + count + " calls)");
   }
   
   private static BigInteger pascal(int n, int k) {
      count++;

      if ( k==0 || k==n ) {
         return BigInteger.ONE;
      }
      else {
         return pascal(n-1,k-1).add( pascal(n-1,k));
      }
   }
}
