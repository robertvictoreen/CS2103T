package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.UniqueIdUtil.createUniqueId;

import java.util.Objects;

import seedu.address.model.assignment.Deadline;
/**
 * Represents an Attendance in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance implements Comparable<Attendance> {

    // Identity fields
    private final Session name;
    private final AttendanceMark presence;
    private final String uniqueId;

    /**
     * Every field must be present and not null.
     */
    public Attendance(Session name, AttendanceMark presence, String uniqueId) {
        requireAllNonNull(name, uniqueId);
        this.name = name;
        this.presence = presence;
        this.uniqueId = uniqueId;
    }

    public Attendance(Session name, AttendanceMark presence) {
        this(name, presence,
                createUniqueId(name.hashCode() + presence.hashCode())
        );
    }

    public Session getSession() { return name; }

    public AttendanceMark getAttendanceMark() {
        return presence;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns true if both attendance have the same name.
     * This defines a weaker notion of equality between two attendance.
     */
    public boolean isSameAttendance(Attendance otherAttendance) {
        if (otherAttendance == this) {
            return true;
        }

        return otherAttendance != null
                && otherAttendance.getSession().equals(getSession());
    }

    @Override
    public int compareTo(Attendance other) {
        int sessionComparison = this.getSession().compareTo(other.getSession());
        if (sessionComparison != 0) {
            return sessionComparison;
        }
        return 0;
    }

    /**
     * Returns true if both attendance have the same name and data fields.
     * This defines a stronger notion of equality between two attendance.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return otherAttendance.getSession().equals(getSession());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Attendance Session: ")
                .append(getSession())
                .append(getAttendanceMark());
        return builder.toString();
    }

}
