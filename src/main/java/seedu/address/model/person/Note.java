package seedu.address.model.person;

/**
 * Represents a Person's note in the address book.
 */
public class Note {

    public static final String MESSAGE_NOTE_CONSTRAINTS = "Note should not be blank"
        + " and have only one whitespace between it and the index.";
    public static final String MESSAGE_NOTE_EMPTY = "Note is empty!";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOTE_INVALIDATION_REGEX = "[\\p{Space}](.*)";

    // Edit this to change what a text checks for before deciding to add a full stop.
    private static final String END_OF_SENTENCE_REGEX = "[.!?]";

    /**
     * Checks if note ends with a character from {@code END_OF_SENTENCE_REGEX}.
     */
    private static final String NOTE_PUNCTUATION_REGEX = "(.*)" + END_OF_SENTENCE_REGEX;

    // Text in an unedited or reset note
    private static final String DEFAULT_NOTE = "<No note added>";

    private String text;

    /**
     * Constructs a {@code Note} with default text.
     */
    public Note() {
        this.text = DEFAULT_NOTE;
    }

    /**
     * Constructs a {@code Note} with input text.
     */
    public Note(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && text.equals(((Note) other).text)); // state check
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    /**
     * Returns a new Note object with added text in its text.
     * @param text to be added
     */
    public Note add(String text) {
        // Empty text if default
        String editedText = this.text;
        if (!this.hasChanged()) {
            editedText = "";
        // if current text ends with one of the characters in {@code END_OF_SENTENCE_REGEX}, change to comma
        } else if (this.text.matches(NOTE_PUNCTUATION_REGEX)) {
            editedText = this.text.split(END_OF_SENTENCE_REGEX)[0];
            editedText += ",";
        }
        /*
         * If added text does not end with one of the characters in {@code END_OF_SENTENCE_REGEX},
         * add a full stop to end.
         */
        if (!text.matches(NOTE_PUNCTUATION_REGEX)) {
            text += ".";
        }
        editedText += text;
        return new Note(editedText);
    }

    /**
     * Returns a Note object with text reset to {@code DEFAULT_NOTE}. Method name keeps internals hidden from users.
     */
    public Note delete() {
        return new Note(DEFAULT_NOTE);
    }

    /**
     * Returns false if this note has the default text. Method name keeps internals hidden from users.
     */
    public boolean hasChanged() {
        return !this.text.equals(DEFAULT_NOTE);
    }
}
