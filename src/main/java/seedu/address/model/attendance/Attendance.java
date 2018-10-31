package seedu.address.model.attendance;

import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Mark;
import seedu.address.model.assignment.Weight;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.UniqueIdUtil.createUniqueId;

import java.util.Objects;
/**
 * Represents an Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance  {

    // Identity fields
    private final LessonName name;
    private final AttendanceMark mark;
    private final String uniqueId;

    /**
     * Every field must be present and not null.
     */
    public Attendance(LessonName name, AttendanceMark mark, String uniqueId) {
        requireAllNonNull(name, mark, uniqueId);
        this.name = name;
        this.mark = mark;
        this.uniqueId = uniqueId;
    }

    public LessonName getName() {
        return name;
    }

    public AttendanceMark getMark() {
        return mark;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns true if both assignment have the same name.
     * This defines a weaker notion of equality between two assignment.
     */
    public boolean isSameAssignment(seedu.address.model.assignment.Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.getName().equals(getName());
    }

    @Override
    public int compareTo(seedu.address.model.assignment.Assignment other) {
        int deadlineComparison = this.getDeadline().compareTo(other.getDeadline());
        if (deadlineComparison != 0) {
            return deadlineComparison;
        }
        return 0;
    }

    /**
     * Returns true if both assignments have the same name and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.assignment.Assignment)) {
            return false;
        }

        seedu.address.model.assignment.Assignment otherAssignment = (seedu.address.model.assignment.Assignment) other;
        return otherAssignment.getName().equals(getName())
                && otherAssignment.getWeight().equals(getWeight())
                && otherAssignment.getDeadline().equals(getDeadline())
                && otherAssignment.getMaxMark().equals(getMaxMark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Max Mark: ")
                .append(getMaxMark());
        return builder.toString();
    }

}
