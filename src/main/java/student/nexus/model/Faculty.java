package student.nexus.model;

public class Faculty {

    private final int id;
    private final String name;

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
