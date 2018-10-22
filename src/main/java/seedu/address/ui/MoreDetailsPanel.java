package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.AssignmentStub;
import seedu.address.model.person.Person;

/**
 * The More Details Panel of the App.
 */
public class MoreDetailsPanel extends UiPart<Region> {

    private static final String FXML = "MoreDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    // List of students
    private ObservableList<Person> studentList;
    private ObservableList<Assignment> assignmentList;

    // Current student whose details are being shown
    private Person currentStudent = null;

    private boolean isSetUp = false;

    @FXML
    private TextArea notesText;

    // Left side holds component name, eg. Participation, right side holds mark attained.
    @FXML
    private GridPane components;

    public MoreDetailsPanel(ObservableList<Person> listOfStudents, ObservableList<Assignment> listOfAssignments) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.studentList = listOfStudents;
        this.assignmentList = listOfAssignments;

        // default label
        Label noComponents = new Label("<No assignments entered>");
        noComponents.setFont(new Font("System", (double) 25));
        components.add(noComponents, 0, 0);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        currentStudent = event.getNewSelection();
        display(currentStudent); // display student's details in details panel
    }

    /**
     * Displays the details of the student selected in the Details Panel on the bottom right.
     */
    public void display(Person student) {
        if (!isSetUp) {
            // add 2 columns, default has 1
            ColumnConstraints newColumn = new ColumnConstraints();
            components.getColumnConstraints().add(newColumn);

            RowConstraints newRow = new RowConstraints();
            components.getRowConstraints().add(newRow);
            isSetUp = true;
        }

        logger.info("Displaying details!\n");

        // remove old labels
        components.getChildren().clear();

        // add no. of rows equal to no. of assignments keyed in
        // Labels set to be label-bright
        for (int i = 0; i < assignmentList.size(); i++) {
            // adding assignment label
            Label toAdd = new Label(assignmentList.get(i).getName().getValue());
            toAdd.setStyle("-fx-font-size: 11pt;\n" + "-fx-font-family: \"Segoe UI Semibold\";\n"
                + "-fx-text-fill: white;\n" + "-fx-opacity: 1;");
            components.add(toAdd, 0, i);

            // adding marks label
            String assignmentId = assignmentList.get(i).getUniqueId();
            Label marksLabel = new Label(Float.toString(student.getMarks().get(assignmentId).getValue()));
            marksLabel.setStyle("-fx-font-size: 11pt;\n" + "-fx-font-family: \"Segoe UI Semibold\";\n"
                + "-fx-text-fill: white;\n" + "-fx-opacity: 1;");
            components.add(marksLabel, 1, i);
        }
    }

    public ObservableList<Person> getList() {
        return studentList;
    }

    public Person getCurrentStudent() {
        return currentStudent;
    }
}
