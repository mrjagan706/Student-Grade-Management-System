
import java.util.*;

class StudentDetails {

    private int id;
    private String name;
    private HashMap<String, Integer> grades;

    // Constructor
    public StudentDetails(int id, String name) {
        setId(id);
        setName(name);
        grades = new HashMap<>();
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "Student ID must be greater than 0."
            );
        }

        this.id = id;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Student name cannot be empty."
            );
        }

        this.name = name.trim();
    }

    // Add Marks
    public void addMarks(String subject, int marks) {

        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Subject name cannot be empty."
            );
        }

        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                "Marks must be between 0 and 100."
            );
        }

        grades.put(subject.trim(), marks);
    }

    // Calculate Average
    public double calculateAverage() {

        if (grades.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (int marks : grades.values()) {
            total += marks;
        }

        return (double) total / grades.size();
    }

    // Calculate GPA
    public double calculateGPA() {
        return calculateAverage() / 25.0;
    }

    // Generate Student Report
    public void generateReport() {

        System.out.println("\n==============================================");
        System.out.println("              STUDENT GRADE REPORT");
        System.out.println("==============================================");

        System.out.println(
            String.format("%-18s : %d", "Student ID", id)
        );

        System.out.println(
            String.format("%-18s : %s", "Student Name", name)
        );

        System.out.println("----------------------------------------------");

        System.out.println(
            String.format("%-25s %10s", "Subject", "Marks")
        );

        System.out.println("----------------------------------------------");

        String[] subjects = {
            "Math", "Science", "English", "Computer"
        };

        for (String subject : subjects) {

            if (grades.containsKey(subject)) {

                System.out.println(
                    String.format(
                        "%-25s %10d",
                        subject,
                        grades.get(subject)
                    )
                );
            }
        }

        System.out.println("----------------------------------------------");

        System.out.println(
            String.format(
                "%-25s %10.2f",
                "Average",
                calculateAverage()
            )
        );

        System.out.println(
            String.format(
                "%-25s %10.2f",
                "GPA",
                calculateGPA()
            )
        );

        System.out.println("==============================================");
    }
}

// MAIN CLASS
public class Student {

    static ArrayList<StudentDetails> students = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    static final String[] SUBJECTS = {
        "Math", "Science", "English", "Computer"
    };

    // Read Integer with Validation
    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Read Marks Between 0 and 100
    public static int readMarks(String subject) {

        while (true) {

            int marks = readInt(
                "Enter " + subject + " Marks (0-100): "
            );

            if (marks >= 0 && marks <= 100) {
                return marks;
            }

            System.out.println(
                "Invalid marks! Please enter between 0 and 100."
            );
        }
    }

    // Read Non-Empty String
    public static String readString(String message) {

        while (true) {

            System.out.print(message);

            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty!");
        }
    }

    // Find Student
    public static StudentDetails findStudent(int id) {

        for (StudentDetails s : students) {

            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    // Add Student with Manual Marks Input
    
public static void addStudent() {

    System.out.println("\n===== ADD 5 STUDENTS =====");

    for (int i = 1; i <= 5; i++) {

        System.out.println("\n----- STUDENT " + i + " -----");

        int id;

        while (true) {

            id = readInt("Enter Student ID: ");

            if (id <= 0) {
                System.out.println("ID must be greater than 0!");
            }
            else if (findStudent(id) != null) {
                System.out.println("Student ID already exists!");
            }
            else {
                break;
            }
        }

        String name = readString("Enter Student Name: ");

        StudentDetails s = new StudentDetails(id, name);

        for (String subject : SUBJECTS) {

            int marks = readMarks(subject);

            s.addMarks(subject, marks);
        }

        students.add(s);

        System.out.println("Student " + i + " added successfully!");

        System.out.println(
            String.format("Average: %.2f", s.calculateAverage())
        );

        System.out.println(
            String.format("GPA: %.2f", s.calculateGPA())
        );
    }

    System.out.println("\n5 Students Added Successfully!");
}

    // Record or Update Marks
    public static void recordMarks() {

        System.out.println("\n===== RECORD STUDENT MARKS =====");

        int id = readInt("Enter Student ID: ");

        StudentDetails s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\nAvailable Subjects:");

        for (int i = 0; i < SUBJECTS.length; i++) {
            System.out.println(
                (i + 1) + ". " + SUBJECTS[i]
            );
        }

        int choice = readInt("Select Subject (1-4): ");

        if (choice < 1 || choice > 4) {
            System.out.println("Invalid subject choice!");
            return;
        }

        String subject = SUBJECTS[choice - 1];

        int marks = readMarks(subject);

        s.addMarks(subject, marks);

        System.out.println("Marks updated successfully!");
    }

    // Calculate Class Average
    public static void calculateClassAverage() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        double total = 0;
        int count = 0;

        for (StudentDetails s : students) {

            total += s.calculateAverage();
            count++;
        }

        double average = total / count;

        System.out.println(
            String.format(
                "\nClass Average: %.2f",
                average
            )
        );
    }

    // Calculate Class Median
    public static void calculateClassMedian() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        ArrayList<Double> averages = new ArrayList<>();

        for (StudentDetails s : students) {
            averages.add(s.calculateAverage());
        }

        Collections.sort(averages);

        int n = averages.size();

        double median;

        if (n % 2 == 0) {

            median = (
                averages.get(n / 2 - 1)
                + averages.get(n / 2)
            ) / 2.0;

        } else {

            median = averages.get(n / 2);
        }

        System.out.println(
            String.format(
                "\nClass Median: %.2f",
                median
            )
        );
    }

    // Generate All Reports
    public static void generateAllReports() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        for (StudentDetails s : students) {
            s.generateReport();
        }
    }

    // Main Method
    public static void main(String[] args) {

        int choice;

        do {

            System.out.println(
                "\n=========================================="
            );

            System.out.println(
                "       STUDENT GRADE MANAGEMENT SYSTEM"
            );

            System.out.println(
                "=========================================="
            );

            System.out.println("1. Add Student");
            System.out.println("2. Record / Update Marks");
            System.out.println("3. Calculate Class Average");
            System.out.println("4. Calculate Class Median");
            System.out.println("5. Generate All Reports");
            System.out.println("6. Exit");

            System.out.println(
                "=========================================="
            );

            choice = readInt("Enter Your Choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    recordMarks();
                    break;

                case 3:
                    calculateClassAverage();
                    break;

                case 4:
                    calculateClassMedian();
                    break;

                case 5:
                    generateAllReports();
                    break;

                case 6:
                    System.out.println(
                        "Thank you for using Student Grade Management System!"
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice! Please try again."
                    );
            }

        } while (choice != 6);

        sc.close();
    }
}
