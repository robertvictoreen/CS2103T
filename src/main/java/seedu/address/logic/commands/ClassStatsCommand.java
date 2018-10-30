package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * Display the statistics for the overall grades of students.
 */
public class ClassStatsCommand extends Command {

    public static final String COMMAND_WORD = "classStats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the statistics for the overall grades of students.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> filteredAssignmentList = model.getFilteredAssignmentList();
        List<Person> filteredPersonList = model.getFilteredPersonList();

        double totalWeight = filteredAssignmentList.stream()
            .mapToDouble(assignment -> assignment.getWeight().getValue()).sum();
        Map<String, Assignment> assignmentMap = filteredAssignmentList.stream()
                                                    .collect(Collectors.toMap(
                                                        assignment -> assignment.getUniqueId(),
                                                        assignment -> assignment));


        double overallMark;
        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();
        List<Double> overallMarkList = new LinkedList<>();
        Person person;
        for (int i = 0; i < filteredPersonList.size(); i++) {
            person = filteredPersonList.get(i);
            overallMark = person.getMarks().entrySet().stream()
                                            .filter(entry -> assignmentMap.containsKey(entry.getKey()))
                                            .mapToDouble(entry -> {
                                                Assignment assignment = assignmentMap.get(entry.getKey());
                                                double mark = entry.getValue().getValue();
                                                mark /= assignment.getMaxMark().getValue();
                                                mark *= assignment.getWeight().getValue();
                                                return mark;
                                            })
                                            .sum();
            summaryStatistics.accept(overallMark);
            overallMarkList.add(overallMark);
        }

        DoubleStream markStream;

        StringBuilder summary = new StringBuilder("Class Statistics\nStudents: ");
        summary.append(summaryStatistics.getCount());
        summary.append(", Maximum Grade: ").append(totalWeight);

        if (summaryStatistics.getCount() > 0) {

            markStream = overallMarkList.stream().mapToDouble(Double::doubleValue);
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
            summary.append("\nHighest: ").append(summaryStatistics.getMax());
            summary.append(", Lowest: ").append(summaryStatistics.getMin());
            summary.append("\n25th: ").append(quartiles[0]);
            summary.append(", 75th: ").append(quartiles[2]);
            summary.append("\nAverage: ").append(summaryStatistics.getAverage());
            summary.append(", Median: ").append(quartiles[1]);
        }

        return new CommandResult(summary.toString());
    }
}
