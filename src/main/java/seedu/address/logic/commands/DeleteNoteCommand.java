package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
 * Deletes the note of the student identified using it's displayed index from the student list.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "delnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note of the student identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Note of Student: %1$s";

    /**
     * Guaranteed to be a positive integer, {@code DeleteNoteCommandParser}.
     */
    private final Index targetIndex;

    public DeleteNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int index = targetIndex.getZeroBased();
        // check if index is valid, not more than list size
        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToReplace = lastShownList.get(index);
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
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, newStudent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNoteCommand) other).targetIndex)); // state check
    }
}
