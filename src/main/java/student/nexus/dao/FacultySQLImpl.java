package student.nexus.dao;

import student.nexus.model.Faculty;
import student.nexus.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultySQLImpl implements FacultyDAO {

    @Override
    public List<Faculty> findAll() throws SQLException {

        String sql = "SELECT * FROM faculties";

        List<Faculty> faculties = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                faculties.add(map(rs));
            }
        }

        return faculties;
    }

    @Override
    public Faculty findById(int id) throws SQLException {

        String sql = "SELECT * FROM faculties WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return map(rs);
        }
    }

    private Faculty map(ResultSet rs) throws SQLException {

        return new Faculty(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
