package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book lexicographically.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted all persons in the Address Book";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}