package student.nexus.dao;

import student.nexus.model.Department;
import student.nexus.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSQLImpl implements DepartmentDAO {

    @Override
    public List<Department> findByFacultyId(int facultyId)
            throws SQLException {

        String sql = """
                SELECT * FROM departments
                WHERE faculty_id = ?
                """;

        List<Department> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facultyId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    @Override
    public Department findById(int id)
            throws SQLException {

        String sql = """
                SELECT * FROM departments
                WHERE id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return map(rs);
        }
    }

    @Override
    public boolean belongsToFaculty(int departmentId,
                                    int facultyId)
            throws SQLException {

        String sql = """
                SELECT 1 FROM departments
                WHERE id = ?
                AND faculty_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, departmentId);
            ps.setInt(2, facultyId);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private Department map(ResultSet rs)
            throws SQLException {

        return new Department(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("faculty_id")
        );
    }
}