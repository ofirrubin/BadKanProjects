/**********************************************************
 *  Copyright 2020 Ofir Rubin
 * ID: 212831879
 **********************************************************/
//package com.ofirrubin;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Ex1 {

    // Test input
    public static final int[][] inputsPairs = new int[][]{
            new int[]{1, 1}, // Minimum
            new int[]{2, 4},
            new int[]{2, 5},
            new int[]{3, 9},
            new int[]{31, 90},
            new int[]{62, 3},
            new int[]{70, 140},
            new int[]{71, 142},
            new int[]{76, 152},
            new int[]{15353, 1611364},
            new int[]{6126136, 9816161},
            new int[]{6 * 57 * 5 * 81, 321 * 38 * 17}, // Given test program pair
            new int[]{2 * 31 * 5 * 7 * 8 * 3 * 38, 7 * 19 * 6 * 31 * 32 * 17}, // Given test program pair
            new int[]{2147483647, 2147483647}};// Maximum

    /**
     * The program will receive two natural numbers and return their greatest common prime divider.
     * Might not be the most efficient way but I just looked at java similarities to C#
     * @param args unused
     */
    public static void main(String[] args) {
        int n1 = 0, n2 = 0;
        if (args == null || args.length == 0) {
            // --------------------------- Getting user input ---------------------
            Scanner scanner = new Scanner(System.in); // stdin
            System.out.println("The program will look for the greatest common prime divider.\n" +
                    "Numbers range: 1 - 2147483647");

            do {
                if (n1 != 0 || n2 != 0) {
                    System.out.println("At least one of the numbers you entered is not a natural number. Please enter numbers above 0.");
                }
                System.out.println("Finding the greatest common prime number of two numbers.");
                try {
                    System.out.print("Please enter the first number: ");
                    n1 = scanner.nextInt();
                    System.out.print("Please enter the second number: ");
                    n2 = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter 2 natural numbers in range of 0 - 2147483647");
                    scanner.nextLine();
                }
            } while (n1 < 1 || n2 < 1);
            scanner.close(); // Closing the pipe
            // --------------------------------------------------------------------
        }
        else{
            String helpMessage = """
                    usage: fileName -jar [<num1> <num2>] \\ <--a> <--h> <help>
                    Prints the GCPD of two numbers to STDOUT.
                    Run the program with one of the following combinations:\s
                    <num1> <num2>     Two natural numbers in range of 1 - 2147483647.
                    --a               Run all test inputs available.
                    -- h / help       Show this help message.
                    Leave empty for CLI input.""";
            if (args.length == 1 && (args[0].toLowerCase().equals("help") || args[0].toLowerCase().equals("--h")))
            {
                System.out.println(helpMessage);
                System.exit(0);
            }
            else if (args.length == 1 && args[0].toLowerCase().equals("--a")){
                long total = System.currentTimeMillis();
                for(int[] input: inputsPairs){
                    GCPN_time_print(input[0], input[1]);
                    System.out.println("---------------------------------------------------------------------");
                }
                total = System.currentTimeMillis() - total;
                System.out.println("Calculating " + inputsPairs.length + " pairs took total of: " + total + "ms");
                System.exit(0);
            }
            else if (args.length != 2) {
                System.out.println(helpMessage + "\nInvalid input. Unknown arguments.");
                System.exit(-1);
            }
            else{
                    try {
                        n1 = Integer.parseInt(args[0]);
                        n2 = Integer.parseInt(args[1]);
                        if (n1 < 1 || n2 < 1)
                            throw new NumberFormatException("Unnatural");
                    } catch (NumberFormatException e) {
                        System.out.println(helpMessage +
                                "\nReceived at least unnatural numbers or unknown characters.\nPlease note that the " +
                                "numbers must be in range of 1 - 2147483647");
                        System.exit(-1);
                    }
                }
        }
        // Running the calculation and messuring run time.
        GCPN_time_print(n1, n2);
    }

    // Used in order to count function time and printing the result.
    public static void GCPN_time_print(int n1, int n2){
        long startTime = System.nanoTime(); // Messuring start time
        int ans = getGCPN(n1, n2);
        long endTime = System.nanoTime(); // Messuring end time
        // User output
        System.out.println("The greatest common prime divider of " + n1 + " and " + n2 +
                (ans != 1 ? " is " + ans : " has not been found") + "\n" +
                "Looking for this number took: " + ((endTime - startTime)) + " ns = ~ "+
                ((endTime - startTime) /1000000) + " ms = ~ " + (endTime - startTime) / 1000000000 + " sec");
    }
    /**
     * The function will take two natural numbers n1, n2 and find their greatest common prime divider if it has one.
     * @param n1 (int) natural number
     * @param n2 (int) natural number
     * @return (int) greatest common prime divider, 1 if not found.
     **/
    public static int getGCPN(int n1, int n2){

        int temp; // Used to replace n1 and n2 if needed - temporary.

        // According to Noam, This should give us the GCD.
        // thus, the code at readme.md (what I meant to do here) is no longer needed.
        while (n1 % n2 != 0){
            if (n1 < n2) {
                temp = n1;
                n1 = n2;
                n2 = temp;
            }
            n1 %= n2;
        }
        for(int i = n2; i > 1; i--){
            if (n2 % i == 0 && n1 % i == 0 && isPrime(i)) // Checking common divider and prime number
                return i; // Found
        }
        return 1; // Not found
    }

    /**
     * The function is going through every possible divider of num and returns if num is a prime number
     * @param num a number we want to check if is a prime number
     * @return boolean - is the number prime
     */
    public static boolean isPrime(int num){
        if (num == 2) return true; // We want to filter even numbers but 2 is a prime number.
        if (num % 2 == 0) return false; // If the number divisible by 2 and not equal to 2, it's not a prime number.
        int max = (int)Math.floor(Math.sqrt(num)); // Biggest possible divider, done after other filters in case it's rounded to even.
        // In case the number was rounded to even number it is a prime number
        for(int i = 3; i <= max; i += 2){ // looking for the number divider
            if (num % i == 0) // if found, than it's not a prime number.
                return false; // return false
        }
        return true; // we didn't find any divider so it's a prime number.
    }
}
