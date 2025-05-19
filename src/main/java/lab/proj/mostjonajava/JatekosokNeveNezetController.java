package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * A játékosok neveinek bekérését kezelő kontroller osztály.
 * Felelős a játékosnevek beviteleért és a játék indításáért.
 */
public class JatekosokNeveNezetController {
    /** A szövegmezőket tartalmazó vertikális elrendezés */
    @FXML
    private VBox nevBemenetTarolo;
    /** A játékosneveket tároló szövegmezők listája */
    private final List<TextField> nevMezok = new ArrayList<>();
    /**
     * Beállítja a játékosok számának megfelelő szövegmezőket.
     * @param db a játékosok száma
     */
    public void setPlayerCount(int db) {
        nevBemenetTarolo.getChildren().clear();
        nevMezok.clear();

        for (int i = 1; i <= db; i++) {
            TextField szovegMezo = new TextField();
            szovegMezo.setPromptText("Játékos " + i + " neve");
            nevBemenetTarolo.getChildren().add(szovegMezo);
            nevMezok.add(szovegMezo);
        }
    }
    /**
     * Játék indítás gomb eseménykezelője.
     * Ellenőrzi a bevitt neveket és elindítja a játékot.
     *
     * @param event az esemény objektum
     * @throws IOException ha hiba történik a játék inicializálása közben
     */
    @FXML
    public void onJatekInditasClick(ActionEvent event) throws IOException {
        List<String> nevek = new ArrayList<>();
        // Beviteli mezők ellenőrzése
        for (TextField field : nevMezok) {
            String nev = field.getText().trim();
            if (nev.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Kérjük, adjon meg nevet minden játékosnak!");
                alert.showAndWait();
                return;
            }
            nevek.add(nev);
        }
        // Ablak és játékvezérlő inicializálása
        Stage mezo = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GrafikusJatekVezerlo jatekVezerlo = new GrafikusJatekVezerlo(nevek, mezo);
    }
}
