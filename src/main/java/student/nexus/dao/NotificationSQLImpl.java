package student.nexus.dao;

import student.nexus.model.Notification;
import student.nexus.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class NotificationSQLImpl implements NotificationDAO {

        @Override
        public void createNotification(String studentNumber,
                                       String message)
                throws SQLException {

            String sql = """
            INSERT INTO notifications
            (student_number, message, is_read)
            VALUES (?, ?, false)
            """;

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, studentNumber);
                ps.setString(2, message);
                ps.executeUpdate();
            }
        }

        @Override
        public List<Notification> getNotifications(String studentNumber)
                throws SQLException {

            String sql = """
            SELECT * FROM notifications
            WHERE student_number = ?
            """;

            List<Notification> list = new ArrayList<>();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, studentNumber);

                var rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Notification(
                            rs.getString("student_number"),
                            rs.getString("message"),
                            rs.getBoolean("is_read")
                    ));
                }
            }

            return list;
        }
    }
