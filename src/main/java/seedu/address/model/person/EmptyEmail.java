package seedu.address.model.person;

/**
 * Represents an empty email.
 * Guarantees: immutable;
 */
public class EmptyEmail extends Email {

    public final String value;

    /**
     * Constructs an empty {@code Email}.
     */
    public EmptyEmail() {
        this.value = "";
    }

    /**
     * Returns false.
     */
    public static boolean isValidEmail(String test) {
        return false;
    }
}
