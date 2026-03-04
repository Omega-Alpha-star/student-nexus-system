package student.nexus.dao;

import student.nexus.model.Student;
import student.nexus.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSQLImpl implements StudentDAO {

    @Override
    public Student register(Student s) throws SQLException {

        String sql = """
                INSERT INTO students
                (name, surname, national_id, age,
                 faculty_id, department_id,
                 student_number, pin, status, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, true)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getSurname());
            ps.setString(3, s.getNationalId());
            ps.setInt(4, s.getAge());
            ps.setInt(5, s.getFacultyId());
            ps.setInt(6, s.getDepartmentId());
            ps.setString(7, s.getStudentNumber());
            ps.setString(8, s.getPin());
            ps.setString(9, s.getStatus());

            ps.executeUpdate();
            return s;
        }
    }

    @Override
    public void updateProfile(Student s) throws SQLException {

        String sql = """
                UPDATE students
                SET name = ?, surname = ?, age = ?, pin = ?
                WHERE student_number = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getSurname());
            ps.setInt(3, s.getAge());
            ps.setString(4, s.getPin());
            ps.setString(5, s.getStudentNumber());

            if (ps.executeUpdate() == 0)
                throw new SQLException("Student not found.");
        }
    }

    @Override
    public List<Student> getStudentsByDepartment(int facultyId, int departmentId)
            throws SQLException {

        String sql = """
                SELECT * FROM students
                WHERE faculty_id = ?
                AND department_id = ?
                AND status = 'ACTIVE'
                """;

        List<Student> students = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facultyId);
            ps.setInt(2, departmentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                students.add(mapStudent(rs));
            }
        }

        return students;
    }

    @Override
    public boolean existsByStudentNumber(String studentNumber)
            throws SQLException {

        String sql = "SELECT 1 FROM students WHERE student_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Override
    public void deactivate(String studentNumber) throws SQLException {

        String sql = """
                UPDATE students
                SET status = 'INACTIVE',
                    is_active = false
                WHERE student_number = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentNumber);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateFacultyAndDepartment(Connection conn, Student s)
            throws SQLException {

        String sql = """
                UPDATE students
                SET faculty_id = ?, department_id = ?
                WHERE student_number = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getFacultyId());
            ps.setInt(2, s.getDepartmentId());
            ps.setString(3, s.getStudentNumber());

            ps.executeUpdate();
        }
    }

    @Override
    public Student findByStudentNumber(String studentNumber) {

        String sql = "SELECT * FROM students WHERE student_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentNumber);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return mapStudent(rs);

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void updateStatus(String studentNumber, String status)
            throws SQLException {

        String sql = """
                UPDATE students
                SET status = ?
                WHERE student_number = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, studentNumber);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Student> findByStatus(String status)
            throws SQLException {

        String sql = "SELECT * FROM students WHERE status = ?";
        List<Student> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapStudent(rs));
            }
        }

        return list;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {

        return new Student(
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("national_id"),
                rs.getInt("age"),
                rs.getInt("faculty_id"),
                rs.getInt("department_id"),
                rs.getString("student_number"),
                rs.getString("pin"),
                rs.getString("status")
        );
    }
}