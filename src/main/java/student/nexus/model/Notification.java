package student.nexus.model;

public class Notification {

    private String studentNumber;
    private String message;
    private boolean isRead;

    public Notification(String studentNumber,
                        String message,
                        boolean isRead) {

        this.studentNumber = studentNumber;
        this.message = message;
        this.isRead = isRead;
    }

    public String getStudentNumber() { return studentNumber; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
}
