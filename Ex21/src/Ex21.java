// Java program to find out maximum value
import java.io.*;
import java.util.Scanner;

class Ex21{
    final static int INF = 99999;

    public static int solve(int[] A) {
        int[][] MV = new int[A.length][A.length];
        String[][] table = initTable(A.length);

        for (int interval = 0; interval < A.length; interval++) {
            for (int i = 0, j = interval; j < A.length; i++, j++) {
                int a, b, c;
                if (i + 2 <= j) {
                    a = MV[i + 2][j];
                } else {
                    a = 0;
                }
                //////////////////////////////////
                if (i + 1 <= j - 1) {
                    b = MV[i + 1][j - 1];
                } else {
                    b = 0;
                }
                //////////////////////////////////
                if (i <= j - 2) {
                    c = MV[i][j - 2];
                } else {
                    c = 0;
                }
                //////////////////////////////////
                MV[i][j] = Math
                        .max(A[i] + Math.min(a, b), A[j] + Math.min(b, c));
                int F = A[i] + Math.min(a, b);
                int L = A[j] + Math.min(b, c);
                if (F > L) {
                    table[i][j] = MV[i][j] + " (F)";
                } else if (F == L) {
                    table[i][j] = MV[i][j] + " (*)";
                } else {
                    table[i][j] = MV[i][j] + " (L)";
                }
            }
        }
        printTable(table);
        return MV[0][A.length - 1];
    }
    private static String[][] initTable(int size) {
        String[][] table = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = "0 (-)";
            }
        }
        return table;
    }
    private static void printTable(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            if (i == 0) {
                System.out.printf("%5s", "");
                for (int k = 0; k < table.length; k++) {
                    System.out.printf("%10d ", (k + 1));
                }
                System.out.println();
            }
            System.out.printf("%5d %s", (i + 1), "|");
            for (int j = 0; j < table.length; j++) {
                System.out.printf("%11s", table[i][j]);
            }
            System.out.println("\n");
        }
    }
    private static int[] getUserInput() {
        List<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter some integers, separated by a space or comma, press enter twice to continue.");
        String[] next = scanner.nextLine().split("[\\s,]");
        int[] numArray = new int[next.length];
        for (int i = 0; i < next.length; i++) {
            numArray[i] = Integer.parseInt(next[i]);
        }
        return numArray;
    }

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
