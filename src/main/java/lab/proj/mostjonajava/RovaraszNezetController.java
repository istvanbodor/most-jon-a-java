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

    private Tekton aktivTekton;
    @FXML
    private Button jatekVegeGomb;
    @FXML
    private Button aktivRovarVatlasGomb;


    @FXML
    void initialize() {
        if (GrafikusJatekVezerlo.aktivRovarasz.getRovarok().size() > 0) {
            ObservableList<Rovar> obsRovarok =
                    FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivRovarasz.getRovarok());
            rovarok.setItems(obsRovarok);

            aktivRovar = rovarok.getItems().get(0);
            aktivTekton = aktivRovar.getTekton();
            ObservableList<Tekton> tektonok =
                    FXCollections.observableArrayList(aktivRovar.getTekton().getSzomszedosTektonok());
            szomszedosTektonok.setItems(tektonok);
        } else {
            javafx.application.Platform.runLater(() -> {
                lepesGomb.getScene().getWindow().hide();
            });
        }
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
        Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
        GrafikusJatekVezerlo.rovarMozgatas(aktivRovar, hova);
        listakFrissitese();
        if (aktivRovar.getTekton() == hova) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeresen lépett a rovar!");
            alert.setHeaderText("Yay! :D");
            alert.showAndWait();
            if (aktivRovar.getLepesSzam() == 0) {
                ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            }
        } else {
            System.out.println(aktivRovar.getTekton());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lépés sikertelen!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
    }

    @FXML
    public void onFonalVagasClick(ActionEvent actionEvent) {
        int meret = aktivRovar.getTekton().getGombafonalak().size();
        GrafikusJatekVezerlo.fonalVagas(aktivRovar, szomszedosTektonok.getSelectionModel().getSelectedItem());
        if (meret > aktivRovar.getTekton().getGombafonalak().size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres fonal vágás!");
            alert.setHeaderText("Yay! :C");
            alert.showAndWait();
            if (aktivRovar.getLepesSzam() == 0) {
                ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fonal vágás sikertelen!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
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

    @FXML
    public void onAktivRovarValtas(ActionEvent actionEvent) {
        if (rovarok.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válassz ki egy rovart a váltáshoz!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        } else {
            aktivRovar = rovarok.getSelectionModel().getSelectedItem();
            aktivTekton = aktivRovar.getTekton();
            listakFrissitese();
        }
    }
}
