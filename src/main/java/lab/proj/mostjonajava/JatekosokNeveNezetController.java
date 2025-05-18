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

public class JatekosokNeveNezetController {

    @FXML
    private VBox nevBemenetTarolo;

    private final List<TextField> nevMezok = new ArrayList<>();

    // Called from previous screen
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

    @FXML
    public void onJatekInditasClick(ActionEvent event) throws IOException {
        List<String> nevek = new ArrayList<>();

        // First validate all fields
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
        Stage mezo = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GrafikusJatekVezerlo jatekVezerlo = new GrafikusJatekVezerlo(nevek, mezo);
    }
}
