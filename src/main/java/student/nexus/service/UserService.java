package student.nexus.service;

import student.nexus.dao.UserDAO;
import student.nexus.model.User;
import student.nexus.util.SecurityUtil;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createAdmin(String username, String password) throws Exception {

        String hashedPassword = SecurityUtil.hash(password);

        userDAO.save(username, hashedPassword, "ADMIN");
    }

    public User authenticate(String username, String password) throws Exception {

        User user = userDAO.findByUsername(username);

        if (user == null)
            throw new IllegalArgumentException("Invalid credentials.");

        if (!SecurityUtil.verify(password, user.getPassword()))
            throw new IllegalArgumentException("Invalid credentials.");

        return user;
    }
}