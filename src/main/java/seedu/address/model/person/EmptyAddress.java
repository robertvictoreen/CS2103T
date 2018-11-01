package seedu.address.model.person;

/**
 * Represents an empty address.
 * Guarantees: immutable;
 */
public class EmptyAddress extends Address {

    public final String value;

    /**
     * Constructs an empty {@code Address}.
     */
    public EmptyAddress() {
        this.value = "";
    }

    /**
     * Returns false.
     */
    public static boolean isValidAddress(String test) {
        return false;
    }
}
