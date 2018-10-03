package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

public class AddStudentWindow extends UiPart{

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

    private static final String FXML = "AddStudentWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public Stage primaryStage;
    public Logic logic;
    public static boolean OKClicked = false;
    public static String commandText;

    public AddStudentWindow(){
    }

    /*public AddStudentWindow(Stage stage, Logic logic){
        this.logic = logic;
        this.primaryStage = stage;
    }*/

    /*public AddStudentWindow(Stage primaryStage, Logic logic) {
        /*
        super(FXML, primaryStage);


        // Set dependency
        this.primaryStage = primaryStage;
        this.logic = logic;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/view/AddStudentWindow.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            this.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configure the UI
        primaryStage.setTitle("Add Student");
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(primaryStage);
    }*/

    @FXML
    private void initialize() {
        /*
        super(FXML, primaryStage);
        */

        // Set dependency
        //this.primaryStage = primaryStage;
        //this.logic = logic;

        /*try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AddStudentWindow.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            // Configure the UI
            this.primaryStage.setTitle("Add Student");
            this.primaryStage.initModality(Modality.WINDOW_MODAL);
            this.primaryStage.initOwner(primaryStage);
            this.primaryStage.setScene(scene);
            Logger.getLogger("works");
            this.primaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    void show() {
        primaryStage.show();
    }

    @FXML
    private void handleOK() {

        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String tagsCombined = "";
        if (tagOneField.getText() != ""){
            tagsCombined += " t/" + tagOneField.getText();
        }
        if (tagTwoField.getText() == ""){
            tagsCombined += " t/" + tagTwoField.getText();
        }
        if (tagThreeField.getText() != ""){
            tagsCombined += " t/" + tagThreeField.getText();
        }
        OKClicked = true;
        //String tags = tagOneField.getText() + tagTwoField.getText() + tagThreeField.getText();

        if (tagsCombined == "") {
            this.commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address;
            primaryStage.close();

        } else {
            this.commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address + tagsCombined;
            primaryStage.close();

        }

        // OK clicked
        // // Test empty tags
        /*
        if (tagsCombined == ""){
            try {
                logic.execute("add n/" + name + " p/" + phoneNumber + " e/" + email + " a/"
                        + address);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (logic == null){
                    System.out.printf("String = %s\n", tagsCombined);
                }

                logic.execute("add n/" + name + " p/" + phoneNumber + " e/" + email + " a/"
                        + address + " t/" + tagsCombined);
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
        }
        */

        // OR Straight away use AddCommand
    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    public void setLogic(Logic logic){
        this.logic = logic;
    }

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    public boolean isOKClicked() {
        return OKClicked;
    }
}
