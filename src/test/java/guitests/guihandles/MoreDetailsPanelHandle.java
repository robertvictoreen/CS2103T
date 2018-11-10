package guitests.guihandles;

import javafx.scene.control.SplitPane;
import seedu.address.model.person.Person;

/**
 * Provides a handle for {@code MoreDetailsPanel}.
 */
public class MoreDetailsPanelHandle extends NodeHandle<SplitPane> {

    public static final String DEFAULT = "No student selected.";
    public static final String DETAILS_PANEL_ID = "#detailsPanel";

    private Person lastShownStudent = null;
    private Person currentStudent = null;

    public MoreDetailsPanelHandle(SplitPane moreDetailsPanelNode) {
        super(moreDetailsPanelNode);
    }

    /**
     * Remembers the current details shown.
     */
    public void rememberDetails() {
        assert(currentStudent != null);
        lastShownStudent = currentStudent;
    }

    /**
     * Returns true if the details being shown have not changed since the most recent call.
     */
    public boolean isDetailsChanged() {
        // check if current student shown = prev
        return !lastShownStudent.equals(currentStudent);
    }

    /**
     * Returns the student whose details are being shown
     */
    public String getOwner() {
        // return last shown student shown
        if (currentStudent == null) {
            return DEFAULT;
        }
        return currentStudent.getName().fullName;
    }

    /**
     * Update identity of student currently shown.
     * @param person that was just selected in {@code PersonListPanelHandle}.
     */
    public void update(Person person) {
        currentStudent = person;
    }
}
