package seedu.address.model.person;

public class AssignmentStub {
    //private totalNumber;
    private String name;
    private float marks;

    public AssignmentStub(String title, float mark){
        name = title;
        marks = mark;
    }

    public float getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }
}
