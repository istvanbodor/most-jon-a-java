package lab.proj.mostjonajava;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class MenuController {

    @FXML
    public void gameStart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("player-number-view.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 250, 200);

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Player Number");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void kilepes(ActionEvent event) {
        // Optional: gracefully exit
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // or use Platform.exit();
    }
}
