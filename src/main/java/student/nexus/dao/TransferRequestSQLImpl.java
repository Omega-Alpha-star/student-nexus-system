package student.nexus.dao;

import student.nexus.model.TransferRequest;
import student.nexus.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferRequestSQLImpl implements TransferRequestDAO {

    @Override
    public void createRequest(TransferRequest request)
            throws SQLException {

        String sql = """
            INSERT INTO transfer_requests
            (student_number,
             current_faculty_id,
             current_department_id,
             requested_faculty_id,
             requested_department_id,
             status)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getStudentNumber());
            ps.setInt(2, request.getCurrentFacultyId());
            ps.setInt(3, request.getCurrentDepartmentId());
            ps.setInt(4, request.getRequestedFacultyId());
            ps.setInt(5, request.getRequestedDepartmentId());
            ps.setString(6, request.getStatus());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsPendingRequest(String studentNumber)
            throws SQLException {

        String sql = """
            SELECT 1 FROM transfer_requests
            WHERE student_number = ?
            AND status = 'PENDING'
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Override
    public List<TransferRequest> findPendingRequests()
            throws SQLException {

        String sql = """
            SELECT * FROM transfer_requests
            WHERE status = 'PENDING'
            """;

        List<TransferRequest> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRequest(rs));
            }
        }

        return list;
    }

    @Override
    public void updateStatus(Connection conn,
                             int requestId,
                             String status)
            throws SQLException {

        String sql = """
            UPDATE transfer_requests
            SET status = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        }
    }

    @Override
    public TransferRequest findById(Connection conn,
                                    int requestId)
            throws SQLException {

        String sql = """
            SELECT * FROM transfer_requests
            WHERE id = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return mapRequest(rs);
        }
    }

    private TransferRequest mapRequest(ResultSet rs)
            throws SQLException {

        return new TransferRequest(
                rs.getInt("id"),
                rs.getString("student_number"),
                rs.getInt("current_faculty_id"),
                rs.getInt("current_department_id"),
                rs.getInt("requested_faculty_id"),
                rs.getInt("requested_department_id"),
                rs.getString("status")
        );
    }
}