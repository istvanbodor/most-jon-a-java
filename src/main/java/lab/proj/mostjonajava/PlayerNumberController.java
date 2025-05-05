package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;


public class PlayerNumberController {

    @FXML
    private Spinner<Integer> playerCountSpinner;

    @FXML
    public void initialize() {
        playerCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 2));
    }

    @FXML
    public void onNextClicked(ActionEvent event) throws IOException {
        int count = playerCountSpinner.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("player-name-view.fxml"));
        Parent root = loader.load();

        PlayerNameController controller = loader.getController();
        controller.setPlayerCount(count);

        Stage stage = (Stage) playerCountSpinner.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}

