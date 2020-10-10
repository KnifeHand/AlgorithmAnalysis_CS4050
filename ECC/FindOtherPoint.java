/* 
*/

import java.util.Scanner;

public class FindOtherPoint{

  private static double a = -7;
  private static double b = 3;

  public static void main(String[] args){
     System.out.print("enter xp: ");
     Scanner keys = new Scanner( System.in );
     double xp = keys.nextDouble();    
     System.out.print("enter xq: ");
     double xq = keys.nextDouble();    

     double yp = curve( xp );
     double yq = curve( xq );
     
     System.out.println("Known points are (" + nice(xp) + "," +
      nice(yp) + ") and (" + nice(xq) + "," + nice(yq) + ")" );

     double lam = (yp - yq ) / (xp - xq);
     System.out.println("slope is " + nice(lam) );

     double xs = lam*lam - xp - xq;
     double ys = yp + lam*(xs-xp);

     System.out.println("Third point is (" + nice(xs) + "," +
                           nice(ys) + ")" );
     
  }

  private static double curve( double x ) {
     return Math.sqrt( x*x*x + a*x + b );     
  }

  public static String nice( double x ){
    return String.format("%10.6f ", x );
  }

}
