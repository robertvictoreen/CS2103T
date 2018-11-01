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
     * Returns true so that storage won't see EmptyPhone objects as errors.
     */
    public static boolean isValidPhone(String test) {
        if (test.equals("")) {
            return true;
        } else {
            return Phone.isValidPhone(test);
        }
    }
}
