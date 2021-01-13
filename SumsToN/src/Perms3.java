import java.util.Collections;
import java.util.Vector;

public class Perms3 {


        // A utility for creating palindrome
        static int createPalindrome(int input, int isOdd)
        {
            int n = input;
            int palin = input;

            // checks if number of digits is odd or even
            // if odd then neglect the last digit of input in
            // finding reverse as in case of odd number of
            // digits middle element occur once
            if (isOdd % 2 == 1)
            {
                n /= 10;
            }

            // Creates palindrome by just appending revers
            // of number to itself
            while (n > 0)
            {
                palin = palin * 10 + (n % 10);
                n /= 10;
            }

            return palin;
        }

        // Function to generate palindromes
        static Vector<Integer> generatePalindromes(int N)
        {
            Vector<Integer> palindromes = new Vector<>();
            int number;

            // Run two times for odd and even
            // length palindromes
            for (int j = 0; j < 2; j++)
            {

                // Creates palindrome numbers with first half as i.
                // Value of j decides whether we need an odd length
                // or even length palindrome.
                int i = 1;
                while ((number = createPalindrome(i++, j)) <= N)
                {
                    palindromes.add(number);
                }
            }

            return palindromes;
        }

        static String reverse(String input)
        {
            char[] temparray = input.toCharArray();
            int left, right = 0;
            right = temparray.length - 1;

            for (left = 0; left < right; left++, right--)
            {
                // Swap values of left and right
                char temp = temparray[left];
                temparray[left] = temparray[right];
                temparray[right] = temp;
            }
            return String.valueOf(temparray);
        }

        // Function to find the minimum
        // number of palindromes required
        // to express N as a sum
        static int minimumNoOfPalindromes(int N)
        {
            // Checking if the number is a palindrome
            String a = String.valueOf(N);
            String b = String.valueOf(N);
            b = reverse(b);
            if (a.equals(b))
            {
                return 1;
            }

            // Checking if the number is a
            // sum of two palindromes
            // Getting the list of all palindromes upto N
            Vector<Integer> palindromes = generatePalindromes(N);

            // Sorting the list of palindromes
            Collections.sort(palindromes);

            int l = 0, r = palindromes.size() - 1;
            while (l < r)
            {
                if (palindromes.get(l) + palindromes.get(r) == N)
                {
                    return 2;
                }
                else if (palindromes.get(l) + palindromes.get(r) < N)
                {
                    ++l;
                }
                else
                {
                    --r;
                }
            }

            // The answer is three if the
            // control reaches till this point
            return 3;
        }

        // Driver code
        public static void main(String[] args)
        {
            int N = 35;
            System.out.println(minimumNoOfPalindromes(N));
        }
    }

// This code is contributed by Rajput-Ji

