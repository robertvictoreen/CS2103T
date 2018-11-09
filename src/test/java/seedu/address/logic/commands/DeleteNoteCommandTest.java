package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteNoteCommand.MESSAGE_SUCCESS;
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteNoteCommand}.
 */
public class DeleteNoteCommandTest {

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
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteNoteCommand(null);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
            new DeleteNoteCommand(FIRST_INDEX).execute(null, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_deleteNote_success() {
        Person personToDelete = model.getFilteredPersonList().get(FIRST_INDEX.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // add note to person first before deleting
        Person personWithNote = new PersonBuilder(personToDelete).withNote(SAMPLE_TEXT_WITH_FULL_STOP).build();
        model.updatePerson(personToDelete, personWithNote);
        Command command = new DeleteNoteCommand(FIRST_INDEX);

        String expectedMessage = String.format(MESSAGE_SUCCESS, personToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new DeleteNoteCommand(INVALID_INDEX).execute(model, commandHistory);
    }

    @Test
    public void execute_deleteEmptyNote_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new DeleteNoteCommand(INDEX_FIRST_PERSON).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        DeleteNoteCommand sampleDeleteNoteCommand = new DeleteNoteCommand(FIRST_INDEX);
        DeleteNoteCommand secondIndexDeleteNoteCommand = new DeleteNoteCommand(SECOND_INDEX);

        // same object -> returns true
        assertTrue(sampleDeleteNoteCommand.equals(sampleDeleteNoteCommand));

        // same values -> returns true
        DeleteNoteCommand sampleDeleteNoteCommandCopy = new DeleteNoteCommand(FIRST_INDEX);
        assertTrue(sampleDeleteNoteCommand.equals(sampleDeleteNoteCommandCopy));

        // different types -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(1));

        // null -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(null));

        // different index -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(secondIndexDeleteNoteCommand));
    }
}
