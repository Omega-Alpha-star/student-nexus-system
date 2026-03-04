package student.nexus.main;

import student.nexus.DTO.RegistrationResult;
import student.nexus.model.*;
import student.nexus.service.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {

    private final StudentService studentService;
    private final UserService userService;
    private final FacultyService facultyService;

    private final Scanner scanner = new Scanner(System.in);

    private Student loggedInStudent = null;
    private User loggedInAdmin = null;

    public StudentManagementSystem(StudentService studentService,
                                   UserService userService,
                                   FacultyService facultyService) {
        this.studentService = studentService;
        this.userService = userService;
        this.facultyService = facultyService;
    }

    public void start() {

        while (true) {

            if (loggedInAdmin != null) {
                showAdminMenu();
            } else if (loggedInStudent != null) {
                showStudentMenu();
            } else {
                showPublicMenu();
            }
        }
    }

    // =========================
    // PUBLIC MENU
    // =========================

    private void showPublicMenu() {

        System.out.println("\n=== PUBLIC MENU ===");
        System.out.println("1. Create Admin");
        System.out.println("2. Admin Login");
        System.out.println("3. Student Application");
        System.out.println("4. Student Login");
        System.out.println("5. Exit");

        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> createAdmin();
            case 2 -> adminLogin();
            case 3 -> studentApply();
            case 4 -> studentLogin();
            case 5 -> System.exit(0);
            default -> System.out.println("Invalid option.");
        }
    }

    // =========================
    // ADMIN SECTION
    // =========================

    private void showAdminMenu() {

        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. View Pending Students");
        System.out.println("2. Approve Student");
        System.out.println("3. Reject Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. View Pending Transfers");
        System.out.println("6. Approve Transfer");
        System.out.println("7. Reject Transfer");
        System.out.println("8. Administration View");
        System.out.println("9. Logout");

        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        try {
            switch (choice) {
                case 1 -> viewPendingStudents();
                case 2 -> approveStudent();
                case 3 -> rejectStudent();
                case 4 -> deactivateStudent();
                case 5 -> viewPendingTransfers();
                case 6 -> approveTransfer();
                case 7 -> rejectTransfer();
                case 8 -> adminView();
                case 9 -> loggedInAdmin = null;
                default -> System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Operation failed.");
        }
    }

    private void createAdmin() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            userService.createAdmin(username, password);
            System.out.println("Admin created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create admin.");
        }
    }

    private void adminLogin() {

        System.out.print("Admin username: ");
        String username = scanner.nextLine();

        System.out.print("Admin password: ");
        String password = scanner.nextLine();

        try {
            loggedInAdmin = userService.authenticate(username, password);
            System.out.println("Admin logged in successfully.");
        } catch (Exception e) {
            System.out.println("Invalid credentials.");
        }
    }

    private void viewPendingStudents() throws SQLException {

        List<Student> pending =
                studentService.getStudentsByStatus("PENDING");

        if (pending.isEmpty()) {
            System.out.println("No pending applications.");
            return;
        }

        for (Student s : pending) {
            System.out.println("----------------------");
            System.out.println("Student No: " + s.getStudentNumber());
            System.out.println("Name: " + s.getName());
        }
    }

    private void approveStudent() throws SQLException {

        System.out.print("Enter Student Number: ");
        String studentNumber = scanner.nextLine();

        studentService.updateStatus(studentNumber, "ACTIVE");
        System.out.println("Student approved.");
    }

    private void rejectStudent() throws SQLException {

        System.out.print("Enter Student Number: ");
        String studentNumber = scanner.nextLine();

        studentService.updateStatus(studentNumber, "REJECTED");
        System.out.println("Student rejected.");
    }

    private void deactivateStudent() throws SQLException {

        System.out.print("Enter Student Number: ");
        String studentNumber = scanner.nextLine();

        studentService.deactivateStudent(studentNumber);
        System.out.println("Student deactivated.");
    }

    private void viewPendingTransfers() throws SQLException {

        List<TransferRequest> list =
                studentService.getPendingTransfers();

        if (list.isEmpty()) {
            System.out.println("No pending transfers.");
            return;
        }

        for (TransferRequest r : list) {
            System.out.println("---------------------");
            System.out.println("Request ID: " + r.getId());
            System.out.println("Student: " + r.getStudentNumber());
            System.out.println("From Faculty ID: " + r.getCurrentFacultyId());
            System.out.println("To Faculty ID: " + r.getRequestedFacultyId());
        }
    }

    private void approveTransfer() throws SQLException {

        System.out.print("Enter Request ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        studentService.approveTransfer(id);
        System.out.println("Transfer approved.");
    }

    private void rejectTransfer() throws SQLException {

        System.out.print("Enter Request ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        studentService.rejectTransfer(id);
        System.out.println("Transfer rejected.");
    }

    private void adminView() throws Exception {

        Faculty faculty = chooseFaculty();
        Department department = chooseDepartment(faculty.getId());

        List<Student> students =
                studentService.getStudentsByDepartment(
                        faculty.getId(),
                        department.getId()
                );

        if (students.isEmpty()) {
            System.out.println("No active students found.");
            return;
        }

        for (Student s : students) {
            System.out.println("-------------------");
            System.out.println("Name: " + s.getName());
            System.out.println("Student No: " + s.getStudentNumber());
        }
    }

    // =========================
    // STUDENT SECTION
    // =========================

    private void showStudentMenu() {

        System.out.println("\n=== STUDENT MENU ===");
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Request Transfer");
        System.out.println("4. Logout");

        int choice = Integer.parseInt(scanner.nextLine());

        try {
            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> updateProfile();
                case 3 -> requestTransfer();
                case 4 -> loggedInStudent = null;
                default -> System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Operation failed.");
        }
    }

    private void studentApply() {

        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Surname: ");
            String surname = scanner.nextLine();

            System.out.print("National ID: ");
            String nationalId = scanner.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            Faculty faculty = chooseFaculty();
            Department department =
                    chooseDepartment(faculty.getId());

            RegistrationResult result =
                    studentService.registerStudent(
                            name, surname, nationalId,
                            age,
                            faculty.getId(),
                            department.getId()
                    );

            System.out.println("\nApplication submitted.");
            System.out.println("Student Number: "
                    + result.getStudent());
            System.out.println("PIN: "
                    + result.getRawPin());

        } catch (Exception e) {
            System.out.println("Application failed.");
        }
    }

    private void studentLogin() {

        System.out.print("Student Number: ");
        String studentNumber = scanner.nextLine();

        System.out.print("PIN: ");
        String pin = scanner.nextLine();

        try {
            loggedInStudent =
                    studentService.login(studentNumber, pin);
            System.out.println("Login successful.");
        } catch (Exception e) {
            System.out.println("Invalid credentials.");
        }
    }

    private void viewProfile() {
        System.out.println("Name: " + loggedInStudent.getName());
        System.out.println("Student No: "
                + loggedInStudent.getStudentNumber());
    }

    private void updateProfile() throws SQLException {

        System.out.print("New Name: ");
        String name = scanner.nextLine();
        System.out.print("New Name: ");
        String surname = scanner.nextLine();

        loggedInStudent.setName(name);
        loggedInStudent.setSurname(surname);
        studentService.updateProfile(loggedInStudent);

        System.out.println("Profile updated.");
    }

    private void requestTransfer() throws Exception {

        Faculty newFaculty = chooseFaculty();
        Department newDepartment =
                chooseDepartment(newFaculty.getId());

        studentService.requestTransfer(
                loggedInStudent,
                newFaculty.getId(),
                newDepartment.getId()
        );

        System.out.println("Transfer requested.");
    }

    // =========================
    // FACULTY SELECTION
    // =========================

    private Faculty chooseFaculty() throws SQLException {

        List<Faculty> faculties =
                facultyService.getAllFaculties();

        for (int i = 0; i < faculties.size(); i++) {
            System.out.println((i + 1) + ". "
                    + faculties.get(i).getName());
        }

        int choice =
                Integer.parseInt(scanner.nextLine());

        return faculties.get(choice - 1);
    }

    private Department chooseDepartment(int facultyId) throws Exception {

        List<Department> departments =
                facultyService.getDepartments(facultyId);

        if (departments.isEmpty()) {
            System.out.println("No departments available for this faculty.");
            throw new Exception("Department list empty.");
        }

        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getName());
        }

        int choice = Integer.parseInt(scanner.nextLine());

        return departments.get(choice - 1);
    }
}