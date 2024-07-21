
/*  Codsoft Java Internship

    Task 2 (Student Grade Calculator)
    
    The program takes marks for each subject, calculates the total marks, computes the average percentage, assigns a grade based on this percentage, 
    and displays the total marks, average percentage, and corresponding grade to the user.

    By Mrunali Kamerikar
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class Student_Grade_Calculator {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("How many subjects are there? \nPlease enter the number:");
        int numofSubjects = readPostiveInt();
        int total_Marks = calculate_Total_Marks(numofSubjects);
        double average_Percentage = calculate_Average_Percentage(total_Marks,numofSubjects);

        String Grade = determine_Grade(average_Percentage);

        display_Results(total_Marks, average_Percentage, Grade);
        in.close();

    }

    private static double calculate_Average_Percentage(double total_Marks, int numofSubjects) {
        return total_Marks / numofSubjects;
    }

    private static int calculate_Total_Marks(int numofSubjects) {
        int total_Marks = 0;
        for (int i = 1; i<= numofSubjects; i++) {
            total_Marks += read_Valid_mark(i);
        }

        return total_Marks;
    }

    private static int read_Valid_mark(int subject_Number) {
        int marks;

        while (true) {
            try {
                System.out.println("Enter  the marks for the subject #" + subject_Number + "  (out of 100):");
                marks = in.nextInt();

                if (marks >= 0 && marks <=100)
                    return marks;
                else {
                    System.out.println("Error: Marks should be in the range of 0 to 100. PLease re-enter.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value.");
                in.next(); // Clear the invalid input
            }
        }
    }

    private static String determine_Grade(double average_Percentage) {
        if (average_Percentage >= 90) {
            return "A";
        } else if (average_Percentage >= 80) {
            return "B";
        } else if (average_Percentage >= 60) {
            return "C";
        } else if (average_Percentage >= 50) {
            return "D";
        } else if (average_Percentage >= 40) {
            return "E";
        } else {
            return "F";
        }
    }

    private static void display_Results( int total_Marks, double average_Percentage, String Grade) {
        System.out.println("");
        System.out.println("You scored a total of: " + total_Marks);
        System.out.println("Your average percentage is: " + average_Percentage);
        System.out.println("You have been assigned the grade: " + Grade);
        System.out.println("");
    }

    private static int readPostiveInt() {
        int number;
        while (true) {
            try {
                number = in.nextInt();
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Error: Number of subjects must be a positive number. Please re-enter.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter an integer value.");
                in.next(); // Clear the invalid input
            }
        }
    }
}
