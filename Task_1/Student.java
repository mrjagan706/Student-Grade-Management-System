import java.util.*;
import java.io.*;

class StudentDetails {

    private int id;
    private String name;
    private HashMap<String, Integer> grades;

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
        if (name == null || name.trim().isEmpty()
                || name.contains("|")) {
            throw new IllegalArgumentException(
                "Invalid student name."
            );
        }

        this.name = name.trim();
    }

    // Add Marks
    public void addMarks(String subject, int marks) {

        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Subject cannot be empty."
            );
        }

        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                "Marks must be between 0 and 100."
            );
        }

        grades.put(subject, marks);
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
        return ((float) total / (grades.size() * 100)) * 100;
    }

    // Calculate GPA
    public double calculateGPA() {
        return calculateAverage()/10;
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
                "Average", calculateAverage()
            )
        );

        System.out.println(
            String.format(
                "%-25s %10.2f",
                "GPA", calculateGPA()
            )
        );

        System.out.println("==============================================");
    }
}


// MAIN CLASS
public class Student {

    static ArrayList<StudentDetails> students =
        new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "students.txt";

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

                System.out.println(
                    "Please enter a valid number!"
                );
            }
        }
    }


    // Read Non-Empty String
    public static String readString(String message) {

        while (true) {

            System.out.print(message);

            String value = sc.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println("Input cannot be empty!");
            }
            else if (value.contains("|")) {
                System.out.println(
                    "The | character is not allowed!"
                );
            }
            else {
                return value;
            }
        }
    }


    // Read Marks with Validation
    public static int readMarks(String subject) {

        while (true) {

            int marks = readInt(
                "Enter " + subject + " Marks (0-100): "
            );

            if (marks >= 0 && marks <= 100) {
                return marks;
            }

            System.out.println(
                "Invalid marks! Enter between 0 and 100."
            );
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


    // SAVE STUDENT DATA INTO TEXT FILE
    public static void saveStudents() {

        try (PrintWriter writer =
                new PrintWriter(new FileWriter(FILE_NAME))) {

            for (StudentDetails s : students) {

                writer.print(s.getId() + "|");
                writer.print(s.getName());

                for (String subject : SUBJECTS) {

                    writer.print("|");

                    // -1 means marks have not been recorded
                    writer.print(
                        getStoredMarks(s, subject)
                    );
                }

                writer.println();
            }

            System.out.println(
                "Student data saved successfully!"
            );

        } catch (IOException e) {

            System.out.println(
                "Error saving data: " + e.getMessage()
            );
        }
    }


    // Get Marks for Saving
    public static int getStoredMarks(
            StudentDetails s, String subject) {

        // The report uses the four fixed subjects.
        // A missing subject is represented by -1.
        try {
            java.lang.reflect.Field field =
                StudentDetails.class.getDeclaredField("grades");

            field.setAccessible(true);

            HashMap<String, Integer> grades =
                (HashMap<String, Integer>) field.get(s);

            return grades.getOrDefault(subject, -1);

        } catch (Exception e) {
            return -1;
        }
    }


    // LOAD STUDENT DATA FROM TEXT FILE
    public static void loadStudents() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            System.out.println(
                "No previous student records found."
            );

            return;
        }

        int loaded = 0;

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {

                    String[] data = line.split("\\|", -1);

                    if (data.length != 6) {
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);

                    String name = data[1];

                    if (id <= 0 || name.trim().isEmpty()
                            || findStudent(id) != null) {
                        continue;
                    }

                    StudentDetails s =
                        new StudentDetails(id, name);

                    for (int i = 0; i < SUBJECTS.length; i++) {

                        int marks = Integer.parseInt(data[i + 2]);

                        if (marks == -1) {
                            continue;
                        }

                        s.addMarks(SUBJECTS[i], marks);
                    }

                    students.add(s);
                    loaded++;

                } catch (Exception e) {

                    System.out.println(
                        "Skipping invalid student record."
                    );
                }
            }

            System.out.println(
                loaded + " student records loaded successfully!"
            );

        } catch (IOException e) {

            System.out.println(
                "Error loading data: " + e.getMessage()
            );
        }
    }


    // ADD 5 STUDENTS
    public static void addStudent() {

        System.out.println("\n===== ADD 5 STUDENTS =====");

        for (int i = 1; i <= 5; i++) {

            System.out.println(
                "\n---------- STUDENT " + i + " ----------"
            );

            int id;

            while (true) {

                id = readInt("Enter Student ID: ");

                if (id <= 0) {
                    System.out.println(
                        "ID must be greater than 0!"
                    );
                }
                else if (findStudent(id) != null) {
                    System.out.println(
                        "Student ID already exists!"
                    );
                }
                else {
                    break;
                }
            }

            String name = readString("Enter Student Name: ");

            StudentDetails s =
                new StudentDetails(id, name);

            System.out.println("\n----- ENTER SUBJECT MARKS -----");

            for (String subject : SUBJECTS) {

                int marks = readMarks(subject);

                s.addMarks(subject, marks);
            }

            students.add(s);

            System.out.println(
                "\nStudent " + i + " added successfully!"
            );

            // Save after every student
            saveStudents();

            System.out.println(
                String.format(
                    "Average: %.2f", s.calculateAverage()
                )
            );

            System.out.println(
                String.format(
                    "GPA: %.2f", s.calculateGPA()
                )
            );
        }

        System.out.println(
            "\n5 Students Added Successfully!"
        );
    }


    // RECORD / UPDATE MARKS
    public static void recordMarks() {

        System.out.println("\n===== RECORD / UPDATE MARKS =====");

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

        saveStudents();

        System.out.println(
            "Marks updated and saved successfully!"
        );
    }


    // CLASS AVERAGE
    public static void calculateClassAverage() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        double total = 0;

        for (StudentDetails s : students) {
            total += s.calculateAverage();
        }

        double average = total / students.size();

        System.out.println(
            String.format(
                "\nClass Average: %.2f", average
            )
        );
    }


    // CLASS MEDIAN
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
                "\nClass Median: %.2f", median
            )
        );
    }


    // GENERATE ALL REPORTS
    public static void generateAllReports() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        for (StudentDetails s : students) {
            s.generateReport();
        }
    }


    // MAIN METHOD
    public static void main(String[] args) {

        // Load saved records when program starts
        System.out.println(
            "\n===== STUDENT GRADE MANAGEMENT SYSTEM ====="
        );

        loadStudents();

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

            System.out.println("1. Add 5 Students");
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
                    saveStudents();

                    System.out.println(
                        "Thank you for using the system!"
                    );

                    break;

                default:
                    System.out.println(
                        "Invalid choice! Try again."
                    );
            }

        } while (choice != 6);

        sc.close();
    }
}