package student.nexus.main;

import student.nexus.dao.*;
import student.nexus.service.*;

public class Main {

    public static void main(String[] args) {

        // DAO layer
        StudentDAO studentDAO = new StudentSQLImpl();
        TransferRequestDAO transferDAO = new TransferRequestSQLImpl();
        NotificationDAO notificationDAO = new NotificationSQLImpl();
        UserDAO userDAO = new UserSQLImpl();
        FacultyDAO facultyDAO = new FacultySQLImpl();
        DepartmentDAO departmentDAO = new DepartmentSQLImpl();

        // Service layer
        FacultyService facultyService =
                new FacultyService(facultyDAO, departmentDAO);

        StudentService studentService =
                new StudentService(
                        studentDAO,
                        transferDAO,
                        notificationDAO,
                        facultyService
                );

        UserService userService =
                new UserService(userDAO);

        // System UI
        StudentManagementSystem system =
                new StudentManagementSystem(
                        studentService,
                        userService,
                        facultyService
                );

        system.start();
    }
}