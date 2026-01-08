import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Student Grade Calculator ---");
        
        // Input Handling
        System.out.print("Enter the number of subjects: ");
        // Check if there is an integer input
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.close();
            return;
        }
        int numSubjects = scanner.nextInt();
        
        if (numSubjects <= 0) {
            System.out.println("Number of subjects must be greater than 0.");
            scanner.close();
            return;
        }

        int[] marks = new int[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer (0-100).");
                System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
                scanner.next(); // consume invalid
            }
            int mark = scanner.nextInt();
            
            // Basic validation
            if (mark < 0 || mark > 100) {
                System.out.println("Marks should be between 0 and 100. Please try again.");
                i--; // retry this subject
            } else {
                marks[i] = mark;
            }
        }

        // Calculations
        int totalMarks = calculateTotalMarks(marks);
        double averagePercentage = calculateAveragePercentage(totalMarks, numSubjects);
        String grade = calculateGrade(averagePercentage);

        // Display Results
        System.out.println("\n--- Results ---");
        System.out.println("Total Marks: " + totalMarks + " / " + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }

    public static int calculateTotalMarks(int[] marks) {
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return sum;
    }

    public static double calculateAveragePercentage(int totalMarks, int numSubjects) {
        return (double) totalMarks / numSubjects;
    }

    public static String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}
