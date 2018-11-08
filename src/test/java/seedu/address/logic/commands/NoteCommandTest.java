package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NoteCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_LARGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code NoteCommand}.
 */
public class NoteCommandTest {

    private static final String SAMPLE_TEXT = "Test";
    private static final String SAMPLE_TEXT_WITH_FULL_STOP = "Test.";
    private static final String INVALID_TEXT_WITH_WHITESPACE_PREFIX = " Test.";
    private static final Index FIRST_INDEX = INDEX_FIRST_PERSON;
    private static final Index SECOND_INDEX = INDEX_SECOND_PERSON;
    private static final Index INVALID_INDEX = INDEX_LARGE;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new NoteCommand(FIRST_INDEX, null);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new NoteCommand(null, SAMPLE_TEXT);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
            new NoteCommand(FIRST_INDEX, SAMPLE_TEXT).execute(null, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_addNote_success() {
        // check add successful to empty note
        Person personToEdit = model.getFilteredPersonList().get(FIRST_INDEX.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Command command = new NoteCommand(FIRST_INDEX, SAMPLE_TEXT);

        String expectedMessage = String.format(MESSAGE_SUCCESS, personToEdit);
        Person newPerson = new PersonBuilder(personToEdit).withNote(SAMPLE_TEXT_WITH_FULL_STOP).build();
        expectedModel.updatePerson(personToEdit, newPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // check add successful to existing note
        Command secondCommand = new NoteCommand(FIRST_INDEX, SAMPLE_TEXT);

        Person finalPerson = new PersonBuilder(newPerson)
                                .withNote(SAMPLE_TEXT + ", " + SAMPLE_TEXT_WITH_FULL_STOP).build();
        expectedModel.updatePerson(newPerson, finalPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(secondCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new NoteCommand(INVALID_INDEX, SAMPLE_TEXT).execute(model, commandHistory);
    }

    @Test
    public void execute_invalidText_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new NoteCommand(INDEX_FIRST_PERSON, INVALID_TEXT_WITH_WHITESPACE_PREFIX).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        NoteCommand sampleNoteCommand = new NoteCommand(FIRST_INDEX, SAMPLE_TEXT_WITH_FULL_STOP);
        NoteCommand secondIndexNoteCommand = new NoteCommand(SECOND_INDEX, SAMPLE_TEXT_WITH_FULL_STOP);
        NoteCommand noFullStopNoteCommand = new NoteCommand(FIRST_INDEX, SAMPLE_TEXT);

        // same object -> returns true
        assertTrue(sampleNoteCommand.equals(sampleNoteCommand));

        // same values -> returns true
        NoteCommand sampleNoteCommandCopy = new NoteCommand(FIRST_INDEX, SAMPLE_TEXT_WITH_FULL_STOP);
        assertTrue(sampleNoteCommand.equals(sampleNoteCommandCopy));

        // different types -> returns false
        assertFalse(sampleNoteCommand.equals(1));

        // null -> returns false
        assertFalse(sampleNoteCommand.equals(null));

        // different index -> returns false
        assertFalse(sampleNoteCommand.equals(secondIndexNoteCommand));

        // different text -> returns false
        assertFalse(sampleNoteCommand.equals(noFullStopNoteCommand));
    }
}
