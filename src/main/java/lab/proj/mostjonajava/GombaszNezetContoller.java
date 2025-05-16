package lab.proj.mostjonajava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private Gombatest aktivGombatest;

    @FXML
    private void initialize() {
        ObservableList<Gombatest> gombatestek =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivGombasz.getGombatestek());
        gombak.setItems(gombatestek);
        aktivGombatest = gombatestek.get(0);
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivGombatest.getTekton().getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
    }

    @FXML
    public void onSporaSzorasClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.sporaSzoras(aktivGombatest, szomszedosTektonok.getSelectionModel().getSelectedItem(), 1);
    }

    @FXML
    public void onFonalNovesztesClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.fonalNovesztes(aktivGombatest, aktivGombatest.getTekton(), szomszedosTektonok.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onGombaTestNovesztesClick(ActionEvent actionEvent) {
        if (szomszedosTektonok.getSelectionModel().getSelectedItem().getGombatest() == null) {
            GrafikusJatekVezerlo.gombaTestNovesztes(GrafikusJatekVezerlo.aktivGombasz, szomszedosTektonok.getSelectionModel().getSelectedItem());
        }
        else {

        }

    }

    @FXML
    public void onUgrasClick(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
