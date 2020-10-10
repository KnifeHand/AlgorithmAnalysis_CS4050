/*
 Java program to generate all unique
 partitions of an integer
 @author: Matt Hurt
*/

import java.io.*;
import java.util.Scanner;

class SumsToN
{
    // Function to print an array array[] of size n
    static void printArray(int array[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }

    // Function to generate all unique partitions of an integer
    static void generatePartition(int n)
    {
        int[] partition = new int[n]; // Array to store a partition
        int indexOfLastElementInPartition = 0; // Index of last element in a partition
        partition[indexOfLastElementInPartition] = n; // Initialize first partition as n itself

        // This loop first prints current partition then generates next
        // partition. The loop stops when the current partition has all 1s
        while (true)
        {
            // print current partition
            printArray(partition, indexOfLastElementInPartition + 1);

            // Generate next partition

            // Find the rightmost value that isn't 1 in array[]. Also, update the
            // remainingValue so that we know how much value can be accommodated
            int remainingValue = 0;
            while (indexOfLastElementInPartition >= 0 && partition[indexOfLastElementInPartition] == 1)
            {
                remainingValue += partition[indexOfLastElementInPartition];
                indexOfLastElementInPartition--;
            }

            // if indexOfLastElementInPartition < 0, all the values are 1 and there are no more partitions
            if (indexOfLastElementInPartition < 0) return;

            // Decrease the partition[indexOfLastElementInPartition] found above and adjust the remainingValue
            partition[indexOfLastElementInPartition]--;
            remainingValue++;


            // If remainingValue is more, then the sorted order is violated. Divide
            // remainingValue in different values of size partition[indexOfLastElementInPartition] and copy these values at
            // different positions after partition[indexOfLastElementInPartition]
            while (remainingValue > partition[indexOfLastElementInPartition])
            {
                partition[indexOfLastElementInPartition + 1] = partition[indexOfLastElementInPartition];
                remainingValue = remainingValue - partition[indexOfLastElementInPartition];
                indexOfLastElementInPartition++;
            }

            // Copy remainingValue to next position and increment position
            partition[indexOfLastElementInPartition + 1] = remainingValue;
            indexOfLastElementInPartition++;
        }
    }

    // Driver program
    public static void main (String[] args)
    {
        // Print space
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        // Get user input
        Scanner inputString = new Scanner(System.in);
        System.out.print("Enter a number to produce a list of all the possible combinations of sums: ");
        int N = inputString.nextInt();
        inputString.close();

        System.out.println("All Unique Partitions of " + N);
        generatePartition(N);

    }
}

