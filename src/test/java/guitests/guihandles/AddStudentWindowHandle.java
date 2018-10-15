package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;


/**
 * A handle to the {@code AddStudentWindow} in the GUI.
 */
public class AddStudentWindowHandle extends NodeHandle<AnchorPane> {

    private static final String OKBUTTON = "#okButton";

    public AddStudentWindowHandle(AnchorPane rootNode) {
        // .getRootNode() to get anchor pane
        super(rootNode);
    }

    public void click(Node button) {
        guiRobot.clickOn(button);
    }

    public Node getRoot() {
        return getRootNode();
    }

    public String getButtonId() {
        return OKBUTTON;
    }
}
