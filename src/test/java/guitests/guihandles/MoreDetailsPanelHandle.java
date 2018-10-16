package guitests.guihandles;

import seedu.address.model.person.Person;

public class MoreDetailsPanelHandle {

    public static final String DEFAULT = "No student selected.";

    private Person lastShownStudent = null;

    /**
     * Remembers the current details shown.
     */
    public void rememberDetails() {
        // remember current student?
    }

    public boolean isDetailsChanged() {
        // check if current student shown = prev
        return false;
    }

    public String getOwner() {
        // return current student shown
        /*
        if (currentStudent == null) {
            return "No student selected.";
        }
        return currentStudent.getName() + "'s details:";
        */
        return "";
    }
}
