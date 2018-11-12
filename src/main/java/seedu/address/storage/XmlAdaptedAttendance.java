package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceMark;
import seedu.address.model.attendance.Session;

/**
 * JAXB-friendly version of the Attendance.
 */
public class XmlAdaptedAttendance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    @XmlElement(required = true)
    private String session;
    @XmlElement(required = true)
    private String uniqueId;
    @XmlElement(required = true)
    private String attendanceMark;

    /**
     * Constructs an XmlAdaptedAttendance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAttendance() {}

    /**
     * Constructs an {@code XmlAdaptedAttendance} with the given attendance details.
     */
    public XmlAdaptedAttendance(String session, String attendanceMark, String uniqueId) {
        this.session = session;
        this.attendanceMark = attendanceMark;
        this.uniqueId = uniqueId;
    }

    /**
     * Converts a given Attendance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAttendance
     */
    public XmlAdaptedAttendance(Attendance source) {
        session = source.getSession().internalString;
        attendanceMark = source.getAttendanceMark().internalString;
        uniqueId = source.getUniqueId();
    }

    /**
     * Converts this jaxb-friendly adapted attendance object into the model's Attendance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance
     */
    public Attendance toModelType() throws IllegalValueException {
        if (session == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Session.class.getSimpleName())
            );
        }
        if (!Session.isValid(session)) {
            throw new IllegalValueException(Session.MESSAGE_CONSTRAINTS);
        }
        final Session modelSession = new Session(session);

        if (attendanceMark == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AttendanceMark.class.getSimpleName())
            );
        }
        if (!AttendanceMark.isValid(attendanceMark)) {
            throw new IllegalValueException(AttendanceMark.MESSAGE_CONSTRAINTS);
        }
        final AttendanceMark modelAttendanceMark = new AttendanceMark(attendanceMark);

        return new Attendance(modelSession, modelAttendanceMark, uniqueId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAttendance)) {
            return false;
        }

        XmlAdaptedAttendance otherAttendance = (XmlAdaptedAttendance) other;
        return Objects.equals(session, otherAttendance.session)
                && uniqueId.equals(otherAttendance.uniqueId);
    }
}
