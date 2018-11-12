package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Mark;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceMark;
import seedu.address.model.common.Mark;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Marks a student as present.
 */
public class AttendanceCommand extends Command {
    public static final String COMMAND_WORD = "attend";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates an attendance entry for the person identified by the index number used in the displayed"
            + " person list.\n"
            + "Parameters: attend INDEX " + PREFIX_ID + "SESSION_INDEX " + PREFIX_ATTENDANCE + "ATTENDANCE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ID + "Tutorial1 " + PREFIX_ATTENDANCE + "1";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Recorded attendance for person: %1$s";
    public static final String MESSAGE_ATTENDANCE_ABSENT = "Recorded absent for person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_ATTENDANCE_NOT_MARKED = "Invalid attendance provided.";

    private final Index index;
    private final Index attendanceIndex;
    private final AttendanceMark attendanceMark;

    /**
     * @param index           of the person in the filtered person list to edit
     * @param attendanceIndex of the attendance shown
     * @param attendanceMark  of the person in class
     */
    public AttendanceCommand(Index index, Index attendanceIndex, AttendanceMark attendanceMark) {
        requireNonNull(index);
        requireNonNull(attendanceIndex);
        requireNonNull(attendanceMark);

        this.index = index;
        this.attendanceIndex = attendanceIndex;
        this.attendanceMark = attendanceMark;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMarkAttendance = lastShownList.get(index.getZeroBased());

        List<Attendance> lastShownAttendanceList = model.getFilteredAttendanceList();
        if (attendanceIndex.getZeroBased() >= lastShownAttendanceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }
        String assignmentUid = lastShownAttendanceList.get(attendanceIndex.getZeroBased()).getUniqueId();

        Person markedPerson = createAttendedPerson(personToMarkAttendance, assignmentUid, attendanceMark);

        if (!personToMarkAttendance.isSamePerson(markedPerson) && model.hasPerson(markedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(personToMarkAttendance, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS, markedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createAttendedPerson(Person personToMarkAttendance, String attendanceUid,
                                               AttendanceMark attendanceMark) {
        assert personToMarkAttendance != null;

        Name updatedName = personToMarkAttendance.getName();
        Phone updatedPhone = personToMarkAttendance.getPhone();
        Email updatedEmail = personToMarkAttendance.getEmail();
        Address updatedAddress = personToMarkAttendance.getAddress();
        ProfilePhoto updatedPhoto = personToMarkAttendance.getProfilePhoto();
        Set<Tag> updatedTags = new HashSet<>();
        Map<String, Mark> updatedAttendance = personToMarkAttendance.getMarks();
        updatedAttendance.put(attendanceUid, attendanceMark);

        return new Person(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPhoto, updatedTags, updatedAttendance);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceCommand)) {
            return false;
        }

        // state check
        AttendanceCommand e = (AttendanceCommand) other;
        return index.equals(e.index)
                && attendanceIndex.equals(e.attendanceIndex)
                && attendanceMark.equals(attendanceMark);
    }
}

