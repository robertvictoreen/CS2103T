package seedu.address.ui;

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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
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

    // Initializing test data
    private AssignmentStub[] assignments = {new AssignmentStub("Finals", 73), new AssignmentStub("Mid-terms", 39),
        new AssignmentStub("Participation", 10), new AssignmentStub("Product Demo", 101)};

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
        Label noComponents = new Label("<No student selected>");
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

    @Subscribe
    private void handleAddressbookChangedEvent(AddressBookChangedEvent event) {
        currentStudent = getCurrentStudent();
        display(currentStudent); // display student's details in details panel
    }

    @Subscribe
    private void handleSelectedStudentNoteChangeEvent(NewResultAvailableEvent event) {
        // displays student again even if note added was to other students
        // prevents further knowledge of event by parsing event message to check for which student changed
        display(currentStudent); // display student's details in details panel
    }

    /**
     * Displays the student's details in the Details Panel on the bottom right.
     */
    public void display(Person student) {
        if (student == null) {
            return;
        }
        if (!isSetUp) {
            // add 2 columns, default has 1
            ColumnConstraints newColumn = new ColumnConstraints();
            newColumn.setPercentWidth(40);
            components.getColumnConstraints().add(newColumn);
            newColumn = new ColumnConstraints();
            newColumn.setPercentWidth(20);
            components.getColumnConstraints().addAll(newColumn, newColumn, newColumn);

            RowConstraints newRow = new RowConstraints();
            components.getRowConstraints().add(newRow);
            isSetUp = true;
        }

        logger.info("Displaying details!\n");

        // remove old labels
        components.getChildren().clear();

        // add no. of rows equal to no. of assignments keyed in
        // Labels set to be label-bright
        Label label;
        String style = "-fx-font-size: 11pt;\n" + "-fx-font-family: \"Segoe UI Semibold\";\n"
                    + "-fx-text-fill: white;\n" + "-fx-opacity: 1;";

        label = new Label("Assignment");
        label.setStyle(style);
        components.add(label, 0, 0);

        label = new Label("Deadline");
        label.setStyle(style);
        components.add(label, 1, 0);

        label = new Label("Weight");
        label.setStyle(style);
        components.add(label, 2, 0);

        label = new Label("Grade");
        label.setStyle(style);
        components.add(label, 3, 0);

        float assignmentWeight;
        float assignmentMark;
        float assignmentMaxMark;
        float totalWeight = 0;
        float weightedMarks = 0;

        int row = 1;
        Assignment assignment;
        for (int i = 0; i < assignmentList.size(); i++) {
            assignment = assignmentList.get(i);
            // adding assignment label
            row = i + 1;

            label = new Label(String.format("%d. %s", row, assignment.getName()));
            label.setStyle(style);
            components.add(label, 0, row);

            label = new Label(String.valueOf(assignment.getDeadline()));
            label.setStyle(style);
            components.add(label, 1, row);

            assignmentWeight = assignment.getWeight().getValue();
            totalWeight += assignmentWeight;
            label = new Label(String.valueOf(assignment.getWeight()));
            label.setStyle(style);
            components.add(label, 2, row);

            // adding marks label
            try {
                assignmentMark = student.getMarks().get(assignment.getUniqueId()).getValue();
                assignmentMaxMark = assignment.getMaxMark().getValue();

                label = new Label(String.format("%s/%s",
                    String.valueOf(assignmentMark),
                    String.valueOf(assignmentMaxMark)));

                assignmentMark /= assignmentMaxMark;
                weightedMarks += assignmentMark * assignmentWeight;
            } catch (Exception e) {
                label = new Label("");
            }
            label.setStyle(style);
            components.add(label, 3, row);
        }

        row++;
        label = new Label("Total");
        label.setStyle(style);
        components.add(label, 0, row);

        label = new Label(String.format("%s/%s",
                    String.valueOf(weightedMarks),
                    String.valueOf(totalWeight)));
        label.setStyle(style);
        components.add(label, 3, row);

        // show student's notes
        notesText.clear();
        notesText.setText(student.getNote().toString());
    }

    public ObservableList<Person> getList() {
        return studentList;
    }

    public Person getCurrentStudent() {
        return currentStudent;
    }
}
