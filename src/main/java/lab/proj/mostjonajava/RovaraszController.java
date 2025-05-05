package lab.proj.mostjonajava;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RovaraszController {

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label roundLabel;

    @FXML
    private ListView<String> rovarListView;

    @FXML
    private Pane jatekter;

    // Esetleg egy belső modell a játékosról és körszámról
    private String currentPlayerName = "Anna";
    private String role = "Rovarász";
    private int currentRound = 1;
    private int maxRounds = 20;

    @FXML
    public void initialize() {
        // Címkék frissítése
        currentPlayerLabel.setText("Aktuális játékos: " + currentPlayerName + " (" + role + ")");
        roundLabel.setText("Kör: " + currentRound + "/" + maxRounds);

        // Például két rovar hozzáadása
        rovarListView.getItems().addAll("Rovar #1", "Rovar #2");

        // TODO: Tektonok és rovar pozíciók kirajzolása a játéktéren
    }

    @FXML
    public void onRovarSelected(MouseEvent event) {
        String selectedRovar = rovarListView.getSelectionModel().getSelectedItem();
        if (selectedRovar != null) {
            System.out.println("Kiválasztott rovar: " + selectedRovar);
            // TODO: Kirajzolás frissítése a kiválasztott rovartól függően
        }
    }

    @FXML
    public void onLepesClicked() {
        System.out.println("Lépés mód bekapcsolva.");
        // TODO: következő kattintás hatására elmozgatjuk a rovart egy tektonra
    }

    @FXML
    public void onFonalvagasClicked() {
        System.out.println("Fonalvágás mód bekapcsolva.");
        // TODO: kattintással választható ki a fonalvágás célpontja
    }
}
