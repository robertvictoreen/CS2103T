package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Note.MESSAGE_NOTE_EMPTY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Edits a note about the specified student, uses {@code DeleteNoteCommand} and {@code NoteCommand} functionality.
 */
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Edits the note of the student specified by the given index. "
        + "Parameters: INDEX (must be a positive integer) "
        + "TEXT\n"
        + "Example: " + COMMAND_WORD + " 1" + " motivated student";

    public static final String MESSAGE_SUCCESS = "Edited note of %1$s";

    /**
     * Guaranteed to be a positive integer, {@code EditNoteCommandParser}.
     */
    private final Index studentIndex;
    private String textToAdd;

    /**
     * Creates an EditNoteCommand to add the specified {@code text} as a note about the student.
     */
    public EditNoteCommand(Index index, String text) {
        requireAllNonNull(index, text);
        studentIndex = index;
        textToAdd = text;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        int zeroBasedIndex = studentIndex.getZeroBased();
        assert(zeroBasedIndex >= 0);

        if (zeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToReplace = lastShownList.get(zeroBasedIndex);
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        Note note = studentToReplace.getNote();
        // check if note is unchanged
        if (!note.hasChanged()) {
            throw new CommandException(MESSAGE_NOTE_EMPTY);
        }
        Note updatedNote = note.delete();
        descriptor.setNote(updatedNote);
        Person newStudent = descriptor.createEditedPerson(studentToReplace);
        model.updatePerson(studentToReplace, newStudent);

        new NoteCommand(studentIndex, textToAdd).execute(model, history);
        Person student = lastShownList.get(zeroBasedIndex);

        return new CommandResult(String.format(MESSAGE_SUCCESS, student));
    }

    /**
     * Checks if same object or fields are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditNoteCommand // instanceof handles nulls
                && studentIndex.equals(((EditNoteCommand) other).studentIndex)
                && textToAdd.equals(((EditNoteCommand) other).textToAdd));
    }
}
