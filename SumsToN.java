// Java program to print all the permutations
// of the given string

// Java Program to generate all permutations of a given set of
// numbers.
import java.util.Scanner;

public class SumsToN {


        // Function counts the total number of digits in a number.
        static int countHowManyDigits(int N)
        {
            int count = 0;
            while (N>0) {
                count++;
                N = N / 10;
            }
            return count;
        }

        // Function to generate permutations of a number
        static void permutation(int N)
        {
            int num = N;
            int n = countHowManyDigits(N);

            while (true) {
                System.out.println(num);

                // generates a circular permutation of a number.
                int rem = num % 10;
                int dev = num / 10;
                num = (int)((Math.pow(10, n - 1)) *
                        rem + dev);

                // If all the permutations are
                // checked and we obtain original
                // number, exit from loop.
                if (num == N)
                    break;
            }
        }

        // Driver Program
        public static void main (String[] args) {
            Scanner inputStr = new Scanner(System.in);
            System.out.print("Enter a number to produce a list of all the possibilities:");
            int N = inputStr.nextInt(); // Read user input
            inputStr.close();
            //int N = 5674;
            permutation(N);
        }


    /* This code is contributed by Mr. Somesh Awasthi */

}
//
//    // Function to print all the permutations of str
//    static void printPermutn(String str, String ans)
//    {
//
//        // If string is empty
//        if (str.length() == 0) {
//            System.out.print(ans + " ");
//            return;
//        }
//
//        for (int i = 0; i < str.length(); i++) {
//
//            // ith character of str
//            int ch = str.charAt(i);
//
//            // Rest of the string after excluding
//            // the ith character
//            int ros = str.substring(0, i) +
//                    str.substring(i + 1);
//
//            // Recurvise call
//            printPermutn(ros, ans + ch);
//        }
//    }

    // Driver code
//    public static void main(String[] args) {
//        Scanner inputStr = new Scanner(System.in);
//        System.out.println("Enter a number to produce a list of all the possibilities:");
//        String n = inputStr.nextLine(); // Read user input
//        inputStr.close();
//        //String s = inputStr;
//        printPermutn(n, "");
//    }
//}
