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

/**
 * A játékosok számának beállítását kezelő kontroller osztály.
 * Felelős a játékosok számának kiválasztásáért és a következő nézet betöltéséért.
 */
public class JatekosokSzamaNezetController {
    /** A játékosok számának kiválasztásához használt spinner */
    @FXML
    private Spinner<Integer> jatekosSzamSpinner;
    /**
     * Inicializáló metódus, amely beállítja a játékosok számának spinnerét.
     * A spinner értékei 2 és 10 között mozognak, kezdőérték 2.
     */
    @FXML
    public void initialize() {
        jatekosSzamSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 2));
    }
    /**
     * Tovább gomb eseménykezelője.
     * Betölti a játékosnevek bevitele nézetet és átadja neki a kiválasztott játékosszámot.
     *
     * @param event az esemény objektum
     * @throws IOException ha hiba történik a következő nézet betöltése közben
     */
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

