package utils;

import java.util.Scanner;

public class InputValidater {
    private static final Scanner scan = new Scanner(System.in);

    public static int getValidInteger(){
        while (true) {
            System.out.print("Please enter a valid integer: ");// Consume the input to prevent input issues
            if (scan.hasNextInt()) {
                return scan.nextInt(); // Consume the valid integer
            } else {
                scan.next(); // Consume the invalid input
                System.out.println("Invalid input. Please input a valid integer");
            }
        }
    }
    public static String getValidString() {
        while (true) {
            String input = scan.nextLine().trim();
            // Check if the input is non-empty
            if (!input.isEmpty()) {
                return input; // Return the valid string
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
