package student.nexus.dao;

import student.nexus.model.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentDAO {

    List<Department> findByFacultyId(int facultyId)
            throws SQLException;

    Department findById(int id)
            throws SQLException;

    boolean belongsToFaculty(int departmentId,
                             int facultyId)
            throws SQLException;
}