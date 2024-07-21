
/*
    Codsoft Java Internship

    Task 5 ( Student Management System  )

    Develop a Student Management System with a Student class for individual student details and a StudentManagementSystem class to manage the student collection.
    The system should provide a user interface for interaction, support data storage, allow various student operations, and include input validation.

    By Mrunali Kamerikar
 
 */

 import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ",     Roll Number: " + rollNumber + ",    Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String filename = "students.ser";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    public void removeStudent(String rollNumber) {
        students.removeIf(student -> student.getRollNumber().equals(rollNumber));
        saveStudents();
    }

    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void editStudent(String rollNumber, String name, String grade) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            if (name != null && !name.isEmpty()) {
                student.setName(name);
            }
            if (grade != null && !grade.isEmpty()) {
                student.setGrade(grade);
            }
            saveStudents();
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No previous data, start with empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    private static final StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Labels and Text Fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel rollLabel = new JLabel("Roll Number:");
        JTextField rollField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton searchButton = new JButton("Search Student");
        JButton displayButton = new JButton("Display All Students");
        JButton editButton = new JButton("Edit Student");
        JButton exitButton = new JButton("Exit");

        // Adding components to the panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(rollLabel);
        panel.add(rollField);
        panel.add(gradeLabel);
        panel.add(gradeField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(displayButton);
        panel.add(editButton);
        panel.add(exitButton);

        // TextArea for displaying students
        JTextArea displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Adding panel and scrollPane to the frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Action listeners for buttons
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String rollNumber = rollField.getText();
            String grade = gradeField.getText();
            if (!name.isEmpty() && !rollNumber.isEmpty() && !grade.isEmpty()) {
                sms.addStudent(new Student(name, rollNumber, grade));
                JOptionPane.showMessageDialog(frame, "Student added successfully!");
                nameField.setText("");
                rollField.setText("");
                gradeField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
            }
        });

        removeButton.addActionListener(e -> {
            String rollNumber = rollField.getText();
            if (!rollNumber.isEmpty()) {
                sms.removeStudent(rollNumber);
                JOptionPane.showMessageDialog(frame, "Student removed successfully!");
                rollField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Roll number is required!");
            }
        });

        searchButton.addActionListener(e -> {
            String rollNumber = rollField.getText();
            Student student = sms.searchStudent(rollNumber);
            if (student != null) {
                JOptionPane.showMessageDialog(frame, student.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Student not found!");
            }
        });

        displayButton.addActionListener(e -> {
            List<Student> students = sms.getAllStudents();
            displayArea.setText(""); // Clear the text area
            StringBuilder studentList = new StringBuilder("All Students:\n");
            for (Student student : students) {
                studentList.append(student).append("\n");
            }
            displayArea.setText(studentList.toString());
        });

        editButton.addActionListener(e -> {
            String rollNumber = rollField.getText();
            String name = nameField.getText();
            String grade = gradeField.getText();
            if (!rollNumber.isEmpty()) {
                sms.editStudent(rollNumber, name.isEmpty() ? null : name, grade.isEmpty() ? null : grade);
                JOptionPane.showMessageDialog(frame, "Student updated successfully!");
                rollField.setText("");
                nameField.setText("");
                gradeField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Roll number is required!");
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Show the frame
        frame.setVisible(true);
    }
}
