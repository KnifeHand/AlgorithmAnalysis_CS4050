// Java program to find out maximum value
import java.io.*;
import java.util.Scanner;

class Ex21{
    final static int INF = 99999;
    static int optimalStrategy(int[] arr, int n)
    {
        // Create a table F to store solutions of subproblems
        int F[][] = new int[n][n];
        int gap, i, j, x, y, z;

        // Fill F using above recursive formula.
        // Note that the F is filled in diagonal fashion.
        for (gap = 0; gap < n; ++gap) {
            for (i = 0, j = gap; j < n; ++i, ++j) {

                // Here x is value of F(i+2, j),
                // y is F(i+1, j-1) and z is
                // F(i, j-2) in above recursive formula
                x = ((i + 2) <= j)? F[i + 2][j]: 0;
                y = ((i + 1) <= (j - 1))? F[i + 1][j - 1]: 0;
                z = (i <= (j - 2))? F[i][j - 2]: 0;

                F[i][j] = Math.max(arr[i] + Math.min(x, y), arr[j] + Math.min(y, z));
                //System.out.println(F[i][j]);
            }
        }
        printSolution(F);

        return F[0][n - 1];

    }

    static void printSolution(int F[][])
    {
        System.out.println("Order of choices: ");
        int n;
        for (int i = 0; i<F.length; ++i)
        {
            for (int j=0; j<F.length; ++j)
            {
                if (F[i][j]==INF)
                    System.out.print("INF ");
                else
                    System.out.print(F[i][j]+" ");
            }
            System.out.println("\n");
        }
    }



    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how many numbers you have to pick from ");
        int length = scanner.nextInt(); // n
        int n = length;
        int [] arr = new int[length];

        System.out.println("Please enter the numbers");
        for (int i=0; i < length; i++){
            int userInput = scanner.nextInt();
            arr[i] = userInput;
        }

        System.out.println("The optimal score is: " + optimalStrategy(arr, length));
        //printSolution(F);
    }
}