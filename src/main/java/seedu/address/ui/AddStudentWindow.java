package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Window to add students.
 */
public class AddStudentWindow extends UiPart {

    private static final String FXML = "AddStudentWindow.fxml";
    private static final String MESSAGE_ADD_FAILED = "Add Student Failed!";

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField tagOneField;
    @FXML
    private TextField tagTwoField;
    @FXML
    private TextField tagThreeField;
    @FXML
    private Button okButton;
    @FXML
    private AnchorPane anchorPane;

    // Implement logger in the future
    // private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private AnchorPane rootPane;

    public AddStudentWindow() {
        super(FXML);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }

    /**
     * Configures window to be shown.
     */
    @FXML
    public void initialize() {
        this.rootPane = anchorPane;

        Scene scene = new Scene(rootPane, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Student");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/address_book_32.png"));

        this.primaryStage = stage;
    }

    // // Test empty tags
    /**
     * Adds student with entered details into student list if valid, closes window after.
     * Alert shown if invalid details entered.
     */
    @FXML
    private void handleOk() {

        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String tagsCombined = "";
        if (isNotNull(tagOneField)) {
            tagsCombined += " t/" + tagOneField.getText();
        }
        if (isNotNull(tagTwoField)) {
            tagsCombined += " t/" + tagTwoField.getText();
        }
        if (isNotNull(tagThreeField)) {
            tagsCombined += " t/" + tagThreeField.getText();
        }

        String commandText;
        CommandResult commandResult;
        if (tagsCombined.equals("")) {
            commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address;
        } else {
            commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address + tagsCombined;
        }

        try {
            commandResult = logic.execute(commandText);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            primaryStage.close();
        } catch (CommandException e) {
            // Log info here
            Alert alert = new Alert(Alert.AlertType.ERROR);
            configAlert(alert, MESSAGE_ADD_FAILED, e.getMessage(), null, Region.USE_PREF_SIZE);

            // setting icon on top left of alert window to be same as it's graphic icon
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/error.png"));

            alert.showAndWait();
        } catch (ParseException e) {
            // Log info here
            Alert alert = new Alert(Alert.AlertType.ERROR);
            configAlert(alert, MESSAGE_ADD_FAILED, Messages.MESSAGE_INVALID_COMMAND, e.getMessage(),
                    Region.USE_PREF_SIZE);

            // setting icon on top left of alert window to be same as it's graphic icon
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/error.png"));

            alert.showAndWait();
        }
    }

    /**
     * Closes window.
     */
    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Checks if TextField is an empty String.
     */
    private boolean isNotNull(TextField field) {
        return !field.getText().equals("");
    }

    /**
     * Sets alert window style, size and text
     */
    private void configAlert(Alert alert, String m1, String m2, String m3, double height) {
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.setTitle(m1);
        alert.setHeaderText(m2);
        alert.setContentText(m3);
        alert.getDialogPane().setMinHeight(height);
    }

    public void setNameField(String name) {
        nameField = new TextField(name);
    }

    public void setPhoneField(String phone) {
        this.phoneField = new TextField(phone);
    }

    public void setAddressField(String address) {
        this.addressField = new TextField(address);
    }

    public void setEmailField(String email) {
        this.emailField = new TextField(email);
    }

    public void setTagOneField(String tagOne) {
        this.tagOneField = new TextField(tagOne);
    }

    public void setTagTwoField(String tagTwo) {
        this.tagTwoField = new TextField(tagTwo);
    }

    public void setTagThreeField(String tagThree) {
        this.tagThreeField = new TextField(tagThree);
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

    public void show() {
        primaryStage.showAndWait();
    }
}
