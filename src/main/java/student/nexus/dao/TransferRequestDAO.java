package student.nexus.dao;

import student.nexus.model.TransferRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TransferRequestDAO {

    void createRequest(TransferRequest request) throws SQLException;

    List<TransferRequest> findPendingRequests() throws SQLException;

    void updateStatus(Connection conn, int requestId, String status) throws SQLException;
    TransferRequest findById(Connection conn, int requestId) throws SQLException;
    boolean existsPendingRequest(String studentNumber) throws SQLException;
}
