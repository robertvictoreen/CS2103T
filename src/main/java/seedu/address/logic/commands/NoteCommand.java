package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a note to the specified student if there is none, adds to end with prefixed whitespace if it already exists.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a note about the student specified by the given index. "
        + "Parameters: "
        + "INDEX TEXT\n"
        + "Example:" + COMMAND_WORD + " 1 " + "Hardworking student.";

    public static final String MESSAGE_SUCCESS = "New note added to %1$s";

    private final Index studentIndex;
    private String textToAdd;

    /**
     * Creates a NoteCommand to add the specified {@code text} as a note about the student.
     */
    public NoteCommand(Index index, String text) {
        requireAllNonNull(index, text);
        studentIndex = index;
        textToAdd = text;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Checks if index is valid
        int zeroBasedIndex = studentIndex.getZeroBased();
        if (zeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person student = lastShownList.get(zeroBasedIndex);

        if (student.hasNote()) {
            textToAdd = " " + textToAdd;
        }
        student.addNote(textToAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, student));
    }

    /**
     * Checks if same object or fields are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteCommand // instanceof handles nulls
                && studentIndex.equals(((NoteCommand) other).studentIndex)
                && textToAdd.equals(((NoteCommand) other).textToAdd));
    }
}
