
/*  Codsoft Java Internship

    Task 1 (Number Game)

    Create a game where the user guesses a randomly generated number between 1 and 100, receiving feedback on whether their guess is correct, 
    too high, or too low, with a limited number of attempts per round, an option to play multiple rounds, and a scoring system based on performance.
    
    By Mrunali Kamerikar
*/

import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("\nWelcome to the Number Guessing Game!");
        System.out.println("Let's kick off the fun.");

        int rounds = 1;
        int total_Score = 0;

        while (true) {
            System.out.println("\nRound " + rounds);
            int number = random.nextInt(100) + 1;
            int attempts = 0;
            final int max_Attempts = 10;
            boolean guessed = false;

            while (attempts < max_Attempts) {
                System.out.print("\nAttempt " + (attempts + 1) + "/" + max_Attempts + ":        Enter your guess (1-100): ");
                int guess = 0;

                try {
                    guess = Integer.parseInt(scanner.nextLine());

                    if (guess < 1 || guess > 100) {
                        System.out.println("Please enter a number between 1 and 100.");
                        continue;
                    }

                    attempts++;

                    if (guess == number) {
                        System.out.println("\nFantastic! You nailed the correct number!");
                        guessed = true;
                        break;
                    } else if (guess < number) {
                        System.out.println("Your guess is too low. Give it another shot.");
                    } else {
                        System.out.println("Your guess is too high. Give it another shot.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            if (!guessed) {
                System.out.println("\nSorry, you're out of attempts. The number you were looking for was " + number + ".");
            }

            int score = max_Attempts - attempts;
            total_Score += score;
            System.out.println("\nYou earned " + score + " points in this round, bringing your total to " + total_Score);

            System.out.print("\nWould you like to play another round? (yes/no): ");
            String play_Again = scanner.nextLine().trim().toLowerCase();

            if (!play_Again.equals("yes")) {
                break;
            }

            rounds++;
        }

        System.out.println("\nThank you for playing! Your final score is: " + total_Score);
        scanner.close();
    }
}
