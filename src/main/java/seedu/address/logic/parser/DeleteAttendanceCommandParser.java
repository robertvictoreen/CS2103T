package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteAttendanceCommand object
 */
public class DeleteAttendanceCommandParser implements  Parser<DeleteAttendanceCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAttendanceCommand
     * and returns an DeleteAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAttendanceCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAttendanceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttendanceCommand.MESSAGE_USAGE), pe);
        }
    }

}
