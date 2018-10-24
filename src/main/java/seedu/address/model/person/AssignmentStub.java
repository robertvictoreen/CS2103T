package seedu.address.model.person;

/**
 * Assignment stub class for MoreDetailsPanel to test functionality.
 */
public class AssignmentStub implements Comparable<AssignmentStub> {

    private String name;
    private float marks;

    /**
     * Constructor for AssignmentStub.
     */
    public AssignmentStub(String title, float mark) {
        name = title;
        marks = mark;
    }

    public float getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(AssignmentStub assignment2) {
        return this.name.compareTo(assignment2.name);
    }
}
