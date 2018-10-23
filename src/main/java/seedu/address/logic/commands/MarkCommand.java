package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Records a mark for an existing person in the address book.
 * TODO: Currently MarkCommand records marks as Tags in the storage model,
 * this will be changed to a new Mark storage model.
 */
public class MarkCommand extends EditCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a mark entry for the person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
             + PREFIX_MARK + "MARK \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MARK + "75 ";

    public static final String MESSAGE_MARK_SUCCESS = "Recorded mark.";
    public static final String MESSAGE_NOT_MARKED = "Invalid mark provided.";

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public MarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        super.execute(model, history);
        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    @Override
    protected Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personToEdit.getTags());
        editPersonDescriptor.getTags().ifPresent(updatedTags::addAll);
        editPersonDescriptor.setTags(updatedTags);

        return super.createEditedPerson(personToEdit, editPersonDescriptor);
    }
}
