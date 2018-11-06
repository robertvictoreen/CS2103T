package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_SPECIALCHAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT;
import static seedu.address.logic.commands.CommandTestUtil.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.createIndex;

import org.junit.Test;

import seedu.address.logic.commands.NoteCommand;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parseValidInput() {
        // making use of Each Valid Input At Least Once In A Positive Test Case heuristic
        // correct format
        // single digit index, characters text
        assertParseSuccess(parser, WHITESPACE + "1" + WHITESPACE + VALID_NOTE_TEXT,
            new NoteCommand(createIndex(1), VALID_NOTE_TEXT));
        // multiple digit index, numbers text
        assertParseSuccess(parser, WHITESPACE + "111" + WHITESPACE + VALID_NOTE_NUMBERS,
            new NoteCommand(createIndex(111), VALID_NOTE_NUMBERS));
        // special characters text
        assertParseSuccess(parser, WHITESPACE + "1" + WHITESPACE + VALID_NOTE_SPECIALCHAR,
            new NoteCommand(createIndex(1), VALID_NOTE_SPECIALCHAR));
    }

    @Test
    public void parseInvalidInput() {
        // no prefix whitespace
        assertParseFailure(parser, "1" + WHITESPACE + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // no whitespace between
        assertParseFailure(parser, WHITESPACE + "1" + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // letter index
        assertParseFailure(parser, WHITESPACE + "a" + WHITESPACE + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // special char index
        assertParseFailure(parser, WHITESPACE + "/" + WHITESPACE + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // int + char index
        assertParseFailure(parser, WHITESPACE + "1a" + WHITESPACE + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + WHITESPACE + VALID_NOTE_TEXT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }
}
