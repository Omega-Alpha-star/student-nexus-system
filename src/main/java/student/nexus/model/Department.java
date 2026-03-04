package student.nexus.model;

public class Department {

    private final int id;
    private final String name;
    private final int facultyId;

    public Department(int id, String name, int facultyId) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getFacultyId() { return facultyId; }
}
