/*
  an instance of this class is an
  ordered pair (x,y) where x and y
  are integers mod n, with n shared
  by all points in a given usage

  Note that PointECC objects are
  immutable, and there is just one
  zero object
*/

import java.math.BigInteger;
import java.util.Scanner;

public class PointECC {

  private static boolean debug = true;

  private static BigInteger two = new BigInteger("2");
  private static BigInteger three = new BigInteger("3");

  private static BigInteger a, b;  // these guys determine the
                                   // particular elliptic curve
                                   // being used
  private static BigInteger n;  // everything happens mod n
                                // note that n should be prime,
                                // and carefully chosen to give
                                // a large order

  public static PointECC zero;  // the one and only zero point

  private boolean isZero;  // is this point 0
  private BigInteger x,y;  // (x,y) (reduced mod n)  is a "point"

  private static void debug( String s ) {
     if ( debug )
        out( s );
  }

  private static void out( String s ) {
     System.out.println( s );
  }

  // initialize the class to use mod n and
  // the elliptic curve y^2 = x^3 + ax + b
  public static void init( String nString, String aString, String bString ) {
     n = new BigInteger( nString );
     a = new BigInteger( aString );
     b = new BigInteger( bString );
     zero = new PointECC();
  }

  // initialize the class to use mod n and
  // the elliptic curve y^2 = x^3 + ax + b
  public static void init( BigInteger nIn, BigInteger aIn, BigInteger bIn ) {
     n = nIn;
     a = aIn;
     b = bIn;
     zero = new PointECC();
  }

  // construct the one and only zero object
  public PointECC() {
     isZero = true;
  }

  // construct a non-zero point from strings for both parts
  public PointECC( String xString, String yString ) {
     isZero = false;
     x = new BigInteger( xString );
     y = new BigInteger( yString );
  }

  // construct a non-zero point from BigInteger's for both parts
  public PointECC( BigInteger a, BigInteger b ) {
     isZero = false;
     x = a;
     y = b;
  }

  // construct a copy of point p
  // (refuses to copy zero)
  public PointECC( PointECC p ) {
     if ( p.isZero ) {
        System.out.println("Programming error---don't want more than one 0");
        System.exit(1);
     }

     isZero = false;
     x = p.x;
     y = p.y;
  }

  public BigInteger getX() {
     return x;
  }

  public BigInteger getY() {
     return y;
  }

  // efficiently compute m times this point
  // (note:  the efficient algorithm (uses divide and conquer)
  //  is very much like modular exponentiation in RSA)
  //   Notation:  this point is named P in the comments/documentation
  // (note:  unless m is zero, won't produce zero)
  public PointECC scalarMult( BigInteger m ) {

     // if this point is 0, return 0
     if (this == zero )
        return zero;  // any multiple of zero is zero
     // if m is 0, return 0
     if ( m.equals( BigInteger.ZERO ) )
        return zero;

     BigInteger current = m;  // m divided by powers of 2
     PointECC mult = new PointECC(this);  // = 1P
                      // current power of 2 multiple of this point
     PointECC sum = zero;  // sum of power of 2 multiples of this point that
                    // are added up to form m

     // at start m >= 1 because m=0 case is handled earlier
     do {

        // depending on this bit in m, add to sum or not
        if ( ! current.mod(two).equals(BigInteger.ZERO) ) {
           sum = sum.add( mult );
        }

        // make a table of values
        // System.out.print( current + " " + mult + " " + sum );
        
        // divide current by 2
        current = current.divide( two );

        // compute the next power of 2 times this point
        mult = mult.add( mult );  // mult = mult + mult        

     } while ( ! current.equals( BigInteger.ZERO ) );
     
     return sum;

  }// scalarMult

  // add point q to this point
  // Note:  if q is this point, add using lambda from tangent line slope,
  //        otherwise use lambda as slope between this point and q
  // Note:  if this point is zero, or q is zero, return the other
  public PointECC add( PointECC q ) {
     
// System.out.println("adding " + this + " and " + q );

     if ( q == zero ) {
        return new PointECC( this );
     }
     else if ( this == zero ) {
        return q;
     }

     if ( this.x.equals(q.x) && this.y.add( q.y ).equals(BigInteger.ZERO) ) {
        // adding opposites
        return zero;
     }

     BigInteger lambda;  // the slope

     if ( this.equals( q ) ) {// this and q are same, use tangency slope
        BigInteger top = x.multiply(x).mod(n);
        top = top.multiply(three).mod(n);
        top = top.add(a).mod(n);
        BigInteger bottom = two.multiply( y ).mod(n);
        BigInteger bottomInverse = bottom.modInverse( n );
        lambda = top.multiply( bottomInverse ).mod( n );
     }// this == q, use tangency slope
     else {// this != q, use actual slope
        if ( this.x.equals( q.x ) ) {// should be P + -P = 0
           if( this.y.add( q.y ).mod(n).equals( BigInteger.ZERO ) ) {
              return zero;
           }
           else {
              System.out.println("oops, strange error--- P != Q, but x's equal?");
              System.exit(1);
              return null;
           }
        }

        BigInteger yDiff = this.y.subtract( q.y ).mod( n );
        BigInteger xDiff = this.x.subtract( q.x ).mod( n );
        // compute inverse of xDiff in Z_n
        BigInteger xDiffInverse = xDiff.modInverse( n );
        lambda = yDiff.multiply( xDiffInverse ).mod( n );
     }// this != q, use actual slope

// System.out.println("lambda: " + lambda );

     // now have lambda, proceed to get (xs,ys) 
     BigInteger lambda2 = lambda.multiply( lambda ).mod( n );
     BigInteger xpPlusXQ = this.x.add(q.x).mod( n );
     BigInteger xs = lambda2.subtract( xpPlusXQ ).mod( n );
     BigInteger xsMinusXP = xs.subtract( this.x ).mod( n );
     BigInteger ys = 
         this.y.add( lambda.multiply( xsMinusXP ).mod( n ) ).mod(n);

     // reflect ys about x axis to obtain point R = P + Q and return the point
     PointECC r =  new PointECC( xs, BigInteger.ZERO.subtract( ys ).mod(n) );

      return r;
     
  }// add

  public boolean equals( PointECC other ) {
     if ( this == zero && other == zero )
        return true;
     else
       return x.equals(other.x) && y.equals(other.y);
  }

  public String toString() {
     if ( this == zero ) 
        return "0";
     else
        return "(" + x + "," + y + ")";
  }

  public static void main1( String[] args ) {
     // test stuff
     Scanner keys = new Scanner( System.in );

     debug("unit tests with n=97, y^2 = x^3-7x+10");

     PointECC.init( "97", "-7", "10" );


/*
     System.out.print("Enter x for starting point P: ");
     String xString = keys.nextLine();
     System.out.print("Enter y for starting point P: ");
     String yString = keys.nextLine();
     PointECC p = new PointECC( xString, yString );

     System.out.print("Enter desired scalar multiple of P: ");
     BigInteger scalar = new BigInteger( keys.nextLine() );

     PointECC p5 = p.scalarMult( scalar );
     debug( scalar + "P = " + p5 );
*/
     
  }// main1

  public static void main( String[] args ) {

     Scanner keys = new Scanner( System.in );

     System.out.print("Enter p (for Z_p): ");
     String pString = keys.nextLine();
     System.out.print("Enter a: ");
     String aString = keys.nextLine();
     System.out.print("Enter b: ");
     String bString = keys.nextLine();
     PointECC.init( pString, aString, bString );

     out("Enter x for starting point P: ");
     String xString = keys.nextLine();
     out("Enter y for starting point P: ");
     String yString = keys.nextLine();
     PointECC p = new PointECC( xString, yString );
     PointECC q = zero;
     PointECC q1;

     BigInteger m = BigInteger.ZERO;

     do { 
        q = q.add(p);
            m = m.add( BigInteger.ONE );
            q1 = p.scalarMult( m );
        out("by repeated addings, " + m + "P= " + q );
        out("by scalar mult           " + q1 );
        out("");
     } while ( ! q.equals( zero ) );

  }// main

}// PointECC
