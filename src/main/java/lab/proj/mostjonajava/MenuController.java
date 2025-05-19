package lab.proj.mostjonajava;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
/**
 * A főmenüt kezelő kontroller osztály.
 * Felelős a játék indításáért és a programból való kilépésért.
 */
public class MenuController {
    /**
     * Játék indítása gomb eseménykezelője.
     * Betölti a játékosok számának beállítására szolgáló nézetet.
     *
     * @param event az esemény objektum, ami tartalmazza a forrást (gombot)
     * @throws IOException ha hiba történik a nézet betöltése közben
     */
    @FXML
    public void onJatekInditasClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("jatekosokszamanezet.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 300, 200);

        // Get the current stage from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("JatekosokSzama");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Kilépés gomb eseménykezelője.
     * Bezárja az alkalmazás ablakát.
     *
     * @param event az esemény objektum, ami tartalmazza a forrást (gombot)
     */
    @FXML
    public void onKilepesClick(ActionEvent event) {
        // Optional: gracefully exit
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // or use Platform.exit();
    }
}
