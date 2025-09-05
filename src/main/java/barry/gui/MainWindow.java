package barry.gui;

import barry.chatbot.Barry;
import barry.chatbot.Ui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Barry barry;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image BarryImage = new Image(this.getClass().getResourceAsStream("/images/DaBarry.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Barry instance */
    public void setBarry(Barry b) {
        barry = b;
        setInitialDialog();
    }

    public void setInitialDialog() {
        DialogBox intro = DialogBox.getBarryDialog(Ui.getWelcome(), BarryImage);
        dialogContainer.getChildren().add(intro);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Barry's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }

        String response = barry.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBarryDialog(response, BarryImage)
        );
        userInput.clear();
    }
}

