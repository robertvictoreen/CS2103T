package guitests.guihandles;

import javafx.scene.control.SplitPane;
import seedu.address.model.person.Person;

/**
 * Provides a handle for {@code MoreDetailsPanel}.
 */
public class MoreDetailsPanelHandle extends NodeHandle<SplitPane> {

    public static final String DEFAULT = "No student selected.";
<<<<<<< HEAD
    public static final String DETAILS_PANEL_ID = "$detailsPanel";
=======
    public static final String DETAILS_PANEL_ID = "#detailsPanel";
>>>>>>> 624d0fc8d71c333726c78d6c418e037c65c95c24

    private Person lastShownStudent = null;

    public MoreDetailsPanelHandle(SplitPane moreDetailsPanelNode) {
        super(moreDetailsPanelNode);
    }

    /**
     * Remembers the current details shown.
     */
    public void rememberDetails() {
        // remember current student?
    }

    /**
     * Returns true if the details being shown have not changed since the most recent call.
     */
    public boolean isDetailsChanged() {
        // check if current student shown = prev
        return false;
    }

    /**
     * Returns the student whose details are being shown
     */
    public String getOwner() {
        // return current student shown
<<<<<<< HEAD
        /*
        if (currentStudent == null) {
            return "No student selected.";
        }
        return currentStudent.getName() + "'s details:";
        */
        return "";
=======
        if (lastShownStudent == null) {
            return "No student selected.";
        }
        return lastShownStudent.getName() + "'s details:";
>>>>>>> 624d0fc8d71c333726c78d6c418e037c65c95c24
    }

    /**
     * Returns the header at top of details panel that states which student's details is being shown.
     */
    public String getHeader() {
        return getOwner() + "'s details:";
    }
}
