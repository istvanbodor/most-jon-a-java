package lab.proj.mostjonajava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import lab.proj.mostjonajava.model.Gombatest;
import lab.proj.mostjonajava.model.Rovar;
import lab.proj.mostjonajava.model.Tekton;

public class RovaraszNezetController {
    @FXML
    private ListView<Rovar> rovarok;
    @FXML
    private ListView<Tekton> szomszedosTektonok;
    @FXML
    private ListView<String> informaciok;
    @FXML
    private Rectangle tekton;
    @FXML
    private Button lepesGomb;
    @FXML
    private Button vagasGomb;
    @FXML
    private Button ugrasGomb;

    private Rovar aktivRovar;
    @FXML
    private Button jatekVegeGomb;


    @FXML
    void initialize() {
        ObservableList<Rovar> obsRovarok =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivRovarasz.getRovarok());
        rovarok.setItems(obsRovarok);
        aktivRovar = rovarok.getItems().get(0);
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivRovar.getTekton().getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
    }
    private void listakFrissitese() {
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivRovar.getTekton().getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
        ObservableList<Rovar> obsRovarok =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivRovarasz.getRovarok());
        rovarok.setItems(obsRovarok);
    }

    @FXML
    public void onRovarLepesClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.rovarMozgatas(aktivRovar, szomszedosTektonok.getSelectionModel().getSelectedItem());
        listakFrissitese();
        if (aktivRovar.getTekton() == szomszedosTektonok.getSelectionModel().getSelectedItem()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sikeresen lépett a rovar!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lépés sikertelen!");
            alert.showAndWait();
        }
    }

    @FXML
    public void onFonalVagasClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.fonalVagas(aktivRovar, szomszedosTektonok.getSelectionModel().getSelectedItem());
        listakFrissitese();
    }

    @FXML
    public void onUgrasClick(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void onJatekVegeClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.JATEK_AKTIV = false;
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
