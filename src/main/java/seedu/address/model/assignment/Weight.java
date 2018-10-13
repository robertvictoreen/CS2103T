package seedu.address.model.assignment;

import seedu.address.model.common.Validator;

/**
 * Represents an assignment's weight in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Weight extends Validator implements Comparable<Weight> {

    public static final String MESSAGE_WEIGHT_CONSTRAINTS =
            "Weights should only contain a real number from 0 and 100, and it should not be blank";
    public static final String WEIGHT_VALIDATION_REGEX = "^(\\d{0,2}(\\.\\d+)?)|(100)$";

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight string.
     */
    public Weight(String weight) {
        super(weight);
    }

    public int compareTo(Weight other) {
        return this.getValue().compareTo(other.getValue());
    }

    @Override
    public Float getValue() {
        return Float.valueOf(this.internalString);
    }

}
