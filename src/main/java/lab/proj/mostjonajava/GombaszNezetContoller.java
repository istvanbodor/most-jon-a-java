package lab.proj.mostjonajava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import lab.proj.mostjonajava.model.Gombatest;
import lab.proj.mostjonajava.model.Tekton;

public class GombaszNezetContoller {
    @FXML
    private ListView<Gombatest> gombak;
    @FXML
    private ListView<Tekton> szomszedosTektonok;
    @FXML
    private ListView<String> informaciok;
    @FXML
    private Rectangle aktualisTekton;
    @FXML
    private Button sporaSzorasGomb;
    @FXML
    private Button fonalNovesztesGomb;
    @FXML
    private Button gombatestNovesztesGomb;
    @FXML
    private Button ugrasGomb;

    @FXML
    public void onSporaSzorasClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onFonalNovesztesClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onGombaTestNovesztesClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onUgrasClick(ActionEvent actionEvent) {
    }
}
