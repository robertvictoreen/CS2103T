package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.DoubleStream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * Deletes an assignment identified using it's displayed index from the address book.
 */
public class AssignmentStatsCommand extends Command {

    public static final String COMMAND_WORD = "assignmentStats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displayes the statistics for an assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Assignment %1$s Summary:\n%2$s";

    private final Index targetIndex;

    public AssignmentStatsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignment = lastShownList.get(targetIndex.getZeroBased());
        String uniqueId = assignment.getUniqueId();
        List<Person> filteredPersonList = model.getFilteredPersonList();

        DoubleStream markStream = filteredPersonList.stream().map(Person::getMarks).filter((marks) -> marks.containsKey(uniqueId)).mapToDouble((marks) -> marks.get(uniqueId).getValue());
        DoubleSummaryStatistics summaryStatistics = markStream.summaryStatistics();
        //DoubleSummaryStatistics{count=2, sum=581.000000, min=51.000000, average=290.500000, max=530.000000}
        String summary = String.format("%s Statistics\nCount: %d\nHighest: %.1f\nLowest: %.1f\nAverage: %.1f\n", assignment.getName().getValue(), summaryStatistics.getCount(), summaryStatistics.getMax(), summaryStatistics.getMin(), summaryStatistics.getAverage());
        return new CommandResult(summary);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentStatsCommand // instanceof handles nulls
                && targetIndex.equals(((AssignmentStatsCommand) other).targetIndex)); // state check
    }
}
