import java.math.BigInteger;
import java.util.Scanner;

public class Project20 {

  public static void main( String[] args ) {

     Scanner keys = new Scanner( System.in );

     // Building ECC for sepc256k1
     BigInteger p = new BigInteger( "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16 );
     BigInteger a = new BigInteger( "0" );
     BigInteger b = new BigInteger( "7" );
     BigInteger x = new BigInteger( "79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16 );
     BigInteger y = new BigInteger( "483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16 );

/*
     // building ECC for Project 19
     BigInteger p = new BigInteger( "52981" );
     BigInteger a = new BigInteger( "-17" );
     BigInteger b = new BigInteger( "31" );
     BigInteger x = new BigInteger( "107" );
     BigInteger y = new BigInteger( "391" );
*/
 
     // set the prime number for Z_p and the elliptic curve y^2 = x^3 + ax + b
     PointECC.init( p, a, b );

     // build the base point
     PointECC P = new PointECC( x, y );

     // publish p, a, b, P
     System.out.println("Public information for ECC: ");
     System.out.println("p= " + p );
     System.out.println("a= " + a );
     System.out.println("b= " + b );
     System.out.println("P= " + P );

/*
     System.out.println("Enter Alice's secret multiplier (m): " );
     BigInteger m = new BigInteger( keys.nextLine() );
*/
     // Alice foolishly uses 2^167 + 37 (a power of 2 plus a two-digit number as her m
     BigInteger m = (new BigInteger("2")).pow( 167 ).add(new BigInteger("37"));
     System.out.println("Alice has private key m= " + m );
     PointECC A = P.scalarMult( m );
     System.out.println("Alice publishes A= " + A );

/*
     System.out.println("Enter Bob's secret multiplier (n): " );
     BigInteger n = new BigInteger( keys.nextLine() );
*/
     // Bob uses a random largeish number
     BigInteger n = new BigInteger( "1379568427491600518906784291" );
     System.out.println("Bob has private key n= " + n );
     PointECC B = P.scalarMult( n );
     System.out.println("Bob publishes B= " + B );

     System.out.println("Alice computes the shared secret " + B.scalarMult( m ) );
     System.out.println("Bob computes the shared secret   " + A.scalarMult( n ) );

  }// main

}// Project20
