package seedu.address.model.person;

/**
 * Represents an empty phone number.
 * Guarantees: immutable;
 */
public class EmptyPhone extends Phone {

    public final String value;

    /**
     * Constructs an empty {@code Phone}.
     */
    public EmptyPhone() {
        this.value = "";
    }

    /**
     * Returns false.
     */
    public static boolean isValidPhone(String test) {
        return false;
    }
}
