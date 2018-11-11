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

    public static final String MESSAGE_SUCCESS = "%s Statistics\nSubmissions: %d, Maximum Mark: %.1f\n"
            + "Highest: %.1f, Lowest: %.1f\n"
            + "25th: %.1f, 75th: %.1f\n"
            + "Average: %.1f, Median: %.1f";

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
        float maxMark = assignment.getMaxMark().getValue();
        String uniqueId = assignment.getUniqueId();
        List<Person> filteredPersonList = model.getFilteredPersonList();
        DoubleStream markStream;

        markStream = filteredPersonList.stream()
                                        .map(Person::getMarks)
                                        .filter((marks) -> marks.containsKey(uniqueId))
                                        .mapToDouble((marks) -> marks.get(uniqueId).getValue());
        DoubleSummaryStatistics summaryStatistics = markStream.summaryStatistics();

        long submissionsCount = summaryStatistics.getCount();

        double[] quartiles = new double[3];
        if (submissionsCount > 0) {

            markStream = filteredPersonList.stream()
                                            .map(Person::getMarks)
                                            .filter((marks) -> marks.containsKey(uniqueId))
                                            .mapToDouble((marks) -> marks.get(uniqueId).getValue());
            double[] marks = markStream.sorted().toArray();

            if (marks.length == 1) {
                quartiles[0] = quartiles[1] = quartiles[2] = marks[0];
            } else {
                double percentile;
                double position;
                int index;

                quartiles[0] = 0.25;
                quartiles[1] = 0.50;
                quartiles[2] = 0.75;

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
        } else {
            summaryStatistics.accept(0);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getName().getValue(),
            submissionsCount, maxMark, assignment.getMaxMark().getValue(),
            summaryStatistics.getMax(), summaryStatistics.getMin(),
            quartiles[0], quartiles[2], summaryStatistics.getAverage(), quartiles[1]));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentStatsCommand // instanceof handles nulls
                && targetIndex.equals(((AssignmentStatsCommand) other).targetIndex)); // state check
    }
}
