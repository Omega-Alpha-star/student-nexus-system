package student.nexus.service;

import student.nexus.DTO.RegistrationResult;
import student.nexus.dao.*;
import student.nexus.exceptions.InvalidCredentialsException;
import student.nexus.model.*;
import student.nexus.util.DatabaseConnection;
import student.nexus.util.SecurityUtil;
import student.nexus.util.StudentGenerator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;
    private final TransferRequestDAO transferRequestDAO;
    private final NotificationDAO notificationDAO;
    private final FacultyService facultyService;

    public StudentService(StudentDAO studentDAO,
                          TransferRequestDAO transferRequestDAO,
                          NotificationDAO notificationDAO,
                          FacultyService facultyService) {

        this.studentDAO = studentDAO;
        this.transferRequestDAO = transferRequestDAO;
        this.notificationDAO = notificationDAO;
        this.facultyService = facultyService;
    }

    // =========================
    // REGISTRATION
    // =========================

    public RegistrationResult registerStudent(
            String name,
            String surname,
            String nationalId,
            int age,
            int facultyId,
            int departmentId
    ) throws SQLException {

        facultyService.validate(facultyId, departmentId);

        String studentNumber;
        do {
            studentNumber = StudentGenerator.generateStudentNumber();
        } while (studentDAO.existsByStudentNumber(studentNumber));

        String rawPin = StudentGenerator.generatePin();
        String hashedPin = SecurityUtil.hash(rawPin);

        Student student = new Student(
                name,
                surname,
                nationalId,
                age,
                facultyId,
                departmentId,
                studentNumber,
                hashedPin,
                "PENDING"
        );

        studentDAO.register(student);

        return new RegistrationResult(studentNumber, rawPin);
    }

    // =========================
    // LOGIN
    // =========================

    public Student login(String studentNumber, String rawPin)
            throws InvalidCredentialsException {

        Student student =
                studentDAO.findByStudentNumber(studentNumber);

        if (student == null)
            throw new InvalidCredentialsException("Invalid credentials.");

        if (!SecurityUtil.verify(rawPin, student.getPin()))
            throw new InvalidCredentialsException("Invalid credentials.");

        if (!"ACTIVE".equals(student.getStatus()))
            throw new InvalidCredentialsException("Account not active.");

        return student;
    }

    // =========================
    // PROFILE
    // =========================

    public void updateProfile(Student student)
            throws SQLException {

        studentDAO.updateProfile(student);
    }

    public void deactivateStudent(String studentNumber)
            throws SQLException {

        studentDAO.deactivate(studentNumber);
    }

    // =========================
    // ADMIN STATUS MANAGEMENT
    // =========================

    public void updateStatus(String studentNumber,
                             String status)
            throws SQLException {

        studentDAO.updateStatus(studentNumber, status);

        notificationDAO.createNotification(
                studentNumber,
                "Your application status is now: " + status
        );
    }

    public List<Student> getStudentsByStatus(String status)
            throws SQLException {

        return studentDAO.findByStatus(status);
    }

    public List<Student> getStudentsByDepartment(
            int facultyId,
            int departmentId
    ) throws SQLException {

        return studentDAO.getStudentsByDepartment(
                facultyId,
                departmentId
        );
    }

    // =========================
    // TRANSFER REQUEST
    // =========================

    public void requestTransfer(Student student,
                                int newFacultyId,
                                int newDepartmentId)
            throws SQLException {

        facultyService.validate(newFacultyId, newDepartmentId);

        if (transferRequestDAO
                .existsPendingRequest(student.getStudentNumber())) {

            throw new IllegalStateException(
                    "You already have a pending transfer request."
            );
        }

        if (student.getFacultyId() == newFacultyId
                && student.getDepartmentId() == newDepartmentId) {

            throw new IllegalArgumentException(
                    "Cannot transfer to the same department."
            );
        }

        TransferRequest request = new TransferRequest(
                0,
                student.getStudentNumber(),
                student.getFacultyId(),
                student.getDepartmentId(),
                newFacultyId,
                newDepartmentId,
                "PENDING"
        );

        transferRequestDAO.createRequest(request);
    }

    public List<TransferRequest> getPendingTransfers()
            throws SQLException {

        return transferRequestDAO.findPendingRequests();
    }

    // =========================
    // APPROVE / REJECT TRANSFER
    // =========================

    public void approveTransfer(int requestId)
            throws SQLException {

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            TransferRequest request =
                    transferRequestDAO.findById(conn, requestId);

            if (request == null)
                throw new SQLException("Request not found.");

            Student student =
                    studentDAO.findByStudentNumber(
                            request.getStudentNumber());

            Student updated = new Student(
                    student.getName(),
                    student.getSurname(),
                    student.getNationalId(),
                    student.getAge(),
                    request.getRequestedFacultyId(),
                    request.getRequestedDepartmentId(),
                    student.getStudentNumber(),
                    student.getPin(),
                    student.getStatus()
            );

            studentDAO.updateFacultyAndDepartment(conn, updated);

            transferRequestDAO.updateStatus(conn,
                    requestId, "APPROVED");

            notificationDAO.createNotification(
                    student.getStudentNumber(),
                    "Your transfer has been approved."
            );

            conn.commit();
        }
    }

    public void rejectTransfer(int requestId)
            throws SQLException {

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            TransferRequest request =
                    transferRequestDAO.findById(conn, requestId);

            if (request == null)
                throw new SQLException("Request not found.");

            transferRequestDAO.updateStatus(conn,
                    requestId, "REJECTED");

            notificationDAO.createNotification(
                    request.getStudentNumber(),
                    "Your transfer has been rejected."
            );

            conn.commit();
        }
    }
}