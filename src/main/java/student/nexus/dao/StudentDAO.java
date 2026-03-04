package student.nexus.dao;

import student.nexus.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {

    Student register(Student s) throws SQLException;

    void updateProfile(Student s) throws SQLException;

    List<Student> getStudentsByDepartment(
            int facultyId,
            int departmentId
    ) throws SQLException;

    boolean existsByStudentNumber(String studentNumber)
            throws SQLException;

    void deactivate(String studentNumber)
            throws SQLException;

    void updateFacultyAndDepartment(Connection conn, Student s)
            throws SQLException;

    Student findByStudentNumber(String studentNumber);

    void updateStatus(String studentNumber, String status)
            throws SQLException;

    List<Student> findByStatus(String status)
            throws SQLException;
}