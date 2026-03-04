package student.nexus.model;

public class Student {

    private String name;
    private String surname;
    private final String nationalId;
    private int age;

    private final int facultyId;
    private final int departmentId;

    private final String studentNumber;
    private String pin;

    private final String status;

    public Student(
            String name,
            String surname,
            String nationalId,
            int age,
            int facultyId,
            int departmentId,
            String studentNumber,
            String pin,
            String status
    ) {
        this.name = name;
        this.surname = surname;
        this.nationalId = nationalId;
        this.age = age;
        this.facultyId = facultyId;
        this.departmentId = departmentId;
        this.studentNumber = studentNumber;
        this.pin = pin;
        this.status = status;
    }

    // Getters
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getNationalId() { return nationalId; }
    public int getAge() { return age; }
    public int getFacultyId() { return facultyId; }
    public int getDepartmentId() { return departmentId; }
    public String getStudentNumber() { return studentNumber; }
    public String getPin() { return pin; }
    public String getStatus() { return status; }

    //public boolean isActive() { return isActive; }

    // Setters (only for allowed fields to update if needed)
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setAge(int age) { this.age = age; }
    public void setPin(String pin) { this.pin = pin; }

}
