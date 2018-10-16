package guitests.guihandles;

import javafx.scene.control.SplitPane;
import seedu.address.model.person.Person;

public class MoreDetailsPanelHandle extends NodeHandle<SplitPane> {

    public static final String DEFAULT = "No student selected.";
    public static final String DETAILS_PANEL_ID = "$detailsPanel";

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
