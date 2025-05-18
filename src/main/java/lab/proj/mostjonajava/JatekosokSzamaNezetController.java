package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;


public class JatekosokSzamaNezetController {

    @FXML
    private Spinner<Integer> jatekosSzamSpinner;

    @FXML
    public void initialize() {
        jatekosSzamSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 2));
    }

    @FXML
    public void onTovabbClick(ActionEvent event) throws IOException {
        int count = jatekosSzamSpinner.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("jatekosneveknezet.fxml"));
        Parent root = loader.load();

        JatekosokNeveNezetController controller = loader.getController();
        controller.setPlayerCount(count);

        Stage stage = (Stage) jatekosSzamSpinner.getScene().getWindow();
        stage.setTitle("JatekosokNeve");
        stage.setScene(new Scene(root));
    }
}

