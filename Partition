/******************************************************************************
 *  Compilation:  javac Partition
 *  Execution:    java Partition N
 *
 *  Print out all partitions of a positive integer N. In number theory,
 *  a partition of N is a way to write it as a sum of positive integers.
 *  Two sums that differ only in the order of their terms are considered
 *  the same partition.
 *
 *  % java Partition 4
 *  4
 *  3 1
 *  2 2
 *  2 1 1
 *  1 1 1 1
 *
 *  % java Partition 6
 *  6
 *  5 1
 *  4 2
 *  4 1 1
 *  3 3
 *  3 2 1
 *  3 1 1 1
 *  2 2 2
 *  2 2 1 1
 *  2 1 1 1 1
 *  1 1 1 1 1 1
 *
 ******************************************************************************/

public class Partition { 

    public static void partition(int n) {
        partition(n, n, "");
    }
    public static void partition(int n, int max, String prefix) {
        if (n == 0) {
            StdOut.println(prefix);
            return;
        }
  
        for (int i = Math.min(max, n); i >= 1; i--) {
            partition(n-i, i, prefix + " " + i);
        }
    }


    public static void main(String[] args) { 
        int n = Integer.parseInt(args[0]);
        partition(n);
    }

}
