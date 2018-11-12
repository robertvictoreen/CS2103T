package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;

/**
 * Adds an assignment to the address book.
 */
public class AddAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "addLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a session to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ATTENDANCE + "PRESENCE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Tutorial1 "
            + PREFIX_ATTENDANCE + "1 ";

    public static final String MESSAGE_SUCCESS = "New Lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This lesson already exists in the address book";

    private final Attendance toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAttendanceCommand(Attendance attendance) {
        requireNonNull(attendance);
        toAdd = attendance;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAttendance(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAttendance(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAttendanceCommand // instanceof handles nulls
                && toAdd.equals(((AddAttendanceCommand) other).toAdd));
    }
}