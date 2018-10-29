package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditNoteCommand object
 */
public class EditNoteCommandParser implements Parser<EditNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditNoteCommand
     * and returns an EditNoteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditNoteCommand parse(String args) throws ParseException {

        // split first integer from rest, throws ParseException if first is not just an integer
        String regex = "(\\s)(\\d+)(\\s)(.*)";
        Pattern formatter = Pattern.compile(regex);
        Matcher matcher = formatter.matcher(args);

        // has to be "editnote %d " followed by anything
        boolean inputMatches = matcher.matches();
        if (!inputMatches) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNoteCommand.MESSAGE_USAGE));
        }

        // TODO: Add tests (test: invalid command word? index not int, out of range)

        // Takes care of nulls
        Index index = Index.fromOneBased(Integer.parseInt(matcher.group(2)));
        String text = matcher.group(4);

        return new EditNoteCommand(index, text);
    }
}
