package barry.gui;

import java.io.IOException;

import barry.chatbot.Barry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Barry using FXML.
 */
public class Main extends Application {

    private Barry barry = new Barry();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("BARRY"); // Set the app name at the top
            fxmlLoader.<MainWindow>getController().setBarry(barry);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

