package student.nexus.dao;

import student.nexus.model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {

    void createNotification(String studentNumber,
                            String message)
            throws SQLException;

    List<Notification> getNotifications(String studentNumber)
            throws SQLException;
}
