package student.nexus.model;

public class TransferRequest {

    private int id;
    private final String studentNumber;

    private final int currentFacultyId;
    private final int currentDepartmentId;

    private final int requestedFacultyId;
    private final int requestedDepartmentId;

    private final String status;

    public TransferRequest(
            int id,
            String studentNumber,
            int currentFacultyId,
            int currentDepartmentId,
            int requestedFacultyId,
            int requestedDepartmentId,
            String status
    ) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.currentFacultyId = currentFacultyId;
        this.currentDepartmentId = currentDepartmentId;
        this.requestedFacultyId = requestedFacultyId;
        this.requestedDepartmentId = requestedDepartmentId;
        this.status = status;
    }

    public int getId() { return id; }
    public String getStudentNumber() { return studentNumber; }

    public int getCurrentFacultyId() { return currentFacultyId; }
    public int getCurrentDepartmentId() { return currentDepartmentId; }

    public int getRequestedFacultyId() { return requestedFacultyId; }
    public int getRequestedDepartmentId() { return requestedDepartmentId; }

    public String getStatus() { return status; }
}