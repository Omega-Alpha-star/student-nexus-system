package student.nexus.service;

import student.nexus.dao.DepartmentDAO;
import student.nexus.dao.FacultyDAO;
import student.nexus.model.Department;
import student.nexus.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public class FacultyService {

    private final FacultyDAO facultyDAO;
    private final DepartmentDAO departmentDAO;

    public FacultyService(FacultyDAO facultyDAO,
                          DepartmentDAO departmentDAO) {
        this.facultyDAO = facultyDAO;
        this.departmentDAO = departmentDAO;
    }

    public List<Faculty> getAllFaculties() throws SQLException {
        return facultyDAO.findAll();
    }

    public List<Department> getDepartments(int facultyId)
            throws SQLException {
        return departmentDAO.findByFacultyId(facultyId);
    }

    public Faculty getById(int id) throws SQLException {
        return facultyDAO.findById(id);
    }

    public Department getDepartmentById(int id) throws SQLException {
        return departmentDAO.findById(id);
    }

    // 🔥 THIS is the missing method
    public void validate(int facultyId, int departmentId)
            throws SQLException {

        boolean valid =
                departmentDAO.belongsToFaculty(
                        departmentId, facultyId);

        if (!valid) {
            throw new IllegalArgumentException(
                    "Invalid department for selected faculty."
            );
        }
    }
}