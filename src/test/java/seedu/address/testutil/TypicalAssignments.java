package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT_A = new AssignmentBuilder().withName("Assignment A")
            .withDeadline("01/01/2019")
            .withWeight("10")
            .withMaxMark("10").build();
    public static final Assignment ASSIGNMENT_B = new AssignmentBuilder().withName("Assignment B")
            .withDeadline("02/02/2019")
            .withWeight("20")
            .withMaxMark("20").build();
    public static final Assignment ASSIGNMENT_C = new AssignmentBuilder().withName("Assignment C")
            .withDeadline("03/03/2019")
            .withWeight("30")
            .withMaxMark("30").build();

    private TypicalAssignments() {} // prevents instantiation

    public static List<Assignment> getTypicalAssignment() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT_A, ASSIGNMENT_B, ASSIGNMENT_C));
    }
}
