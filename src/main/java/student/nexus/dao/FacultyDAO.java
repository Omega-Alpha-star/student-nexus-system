package student.nexus.dao;

import student.nexus.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyDAO {
    List<Faculty> findAll() throws SQLException;
    Faculty findById(int id) throws SQLException;
}
