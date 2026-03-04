package student.nexus.dao;

import student.nexus.model.User;

import java.sql.SQLException;

public interface UserDAO {
    User findByUsername(String username);
    void save(String username,
              String password,
              String role)
            throws SQLException;
}
