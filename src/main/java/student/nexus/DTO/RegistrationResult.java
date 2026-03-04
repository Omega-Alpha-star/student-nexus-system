package student.nexus.DTO;

public class RegistrationResult {

    private final String studentNumber;
    private final String rawPin;

    public RegistrationResult(String studentNumber, String rawPin) {
        this.studentNumber = studentNumber;
        this.rawPin = rawPin;
    }

    public String getStudent() { return studentNumber; }
    public String getRawPin() { return rawPin; }

}
