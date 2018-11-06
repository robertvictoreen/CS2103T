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
 * Display the statistics for an assignment identified by the index number in the assignment list.
 */
public class AssignmentStatsCommand extends Command {

    public static final String COMMAND_WORD = "assignmentStats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the statistics for an assignment identified by the"
            + " index number in the assignment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

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
        DoubleStream markStream;

        markStream = filteredPersonList.stream()
                                        .map(Person::getMarks)
                                        .filter((marks) -> marks.containsKey(uniqueId))
                                        .mapToDouble((marks) -> marks.get(uniqueId).getValue());
        DoubleSummaryStatistics summaryStatistics = markStream.summaryStatistics();

        StringBuilder summary = new StringBuilder(assignment.getName().getValue());
        summary.append(" Statistics\nSubmissions: ");
        summary.append(summaryStatistics.getCount());

        if (summaryStatistics.getCount() > 0) {

            markStream = filteredPersonList.stream()
                                            .map(Person::getMarks)
                                            .filter((marks) -> marks.containsKey(uniqueId))
                                            .mapToDouble((marks) -> marks.get(uniqueId).getValue());
            double[] marks = markStream.sorted().toArray();

            double[] quartiles = {0.25, 0.50, 0.75};

            if (marks.length == 1) {
                quartiles[0] = quartiles[1] = quartiles[2] = marks[0];
            } else {
                double percentile;
                double position;
                int index;

                for (int i = 0; i < 3; i++) {
                    position = quartiles[i] * marks.length;
                    index = (int) position;
                    percentile = marks[index];

                    if (position - index == 0) {
                        percentile = (percentile + marks[index - 1]) / 2;
                    }

                    quartiles[i] = percentile;
                }
            }
            summary.append("\nHighest: ").append(String.format("%.1f", summaryStatistics.getMax()));
            summary.append(", Lowest: ").append(String.format("%.1f", summaryStatistics.getMin()));
            summary.append("\n25th: ").append(String.format("%.1f", quartiles[0]));
            summary.append(", 75th: ").append(String.format("%.1f", quartiles[2]));
            summary.append("\nAverage: ").append(String.format("%.1f", summaryStatistics.getAverage()));
            summary.append(", Median: ").append(String.format("%.1f", quartiles[1]));
        }

        return new CommandResult(summary.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentStatsCommand // instanceof handles nulls
                && targetIndex.equals(((AssignmentStatsCommand) other).targetIndex)); // state check
    }
}
