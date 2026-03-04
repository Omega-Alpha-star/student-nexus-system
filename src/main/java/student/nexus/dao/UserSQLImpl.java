package student.nexus.dao;

import student.nexus.model.User;
import student.nexus.util.DatabaseConnection;

import java.sql.*;

public class UserSQLImpl implements UserDAO {

    @Override
    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void save(String username,
                     String password,
                     String role)
            throws SQLException {

        String sql = """
        INSERT INTO users (username, password, role)
        VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ps.executeUpdate();
        }
    }
}
