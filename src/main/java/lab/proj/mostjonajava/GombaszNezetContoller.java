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

    private Tekton aktivTekton;
    @FXML
    private Button fejlesztesGomb;
    @FXML
    private Button jatekVegeGomb;
    @FXML
    private Button aktivTektonValtasGomb;
    @FXML
    private Button aktivGombatestValtasGomb;

    @FXML
    private void initialize() {
        ObservableList<Gombatest> gombatestek =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivGombasz.getGombatestek());
        gombak.setItems(gombatestek);
        aktivGombatest = gombatestek.get(0);
        aktivTekton = aktivGombatest.getTekton();
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivGombatest.getTekton().getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
    }


    @FXML
    public void onSporaSzorasClick(ActionEvent actionEvent) {
        Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
        int sporaSzam = hova.getSporak().size();
        GrafikusJatekVezerlo.sporaSzoras(aktivGombatest,hova, 1);
        listakFrissitese();
        if (sporaSzam < hova.getSporak().size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres spóra szórás!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Spóra szórás sikertelen!");
            alert.showAndWait();
        }
    }

    @FXML
    public void onFonalNovesztesClick(ActionEvent actionEvent) {
        int fonalakSzama = aktivGombatest.getGombaFonalak().size();
        GrafikusJatekVezerlo.fonalNovesztes(aktivGombatest, aktivTekton, szomszedosTektonok.getSelectionModel().getSelectedItem());
        listakFrissitese();
        if (fonalakSzama < aktivGombatest.getGombaFonalak().size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres fonal növesztés!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fonal növesztés nem lehetséges!");
            alert.showAndWait();
        }
        if (aktivGombatest.getNoveszthetoFonalakSzama() == 0) {
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }

    @FXML
    public void onGombaTestNovesztesClick(ActionEvent actionEvent) {
            Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
            Gombatest gombatest = hova.getGombatest();
            GrafikusJatekVezerlo.gombaTestNovesztes(GrafikusJatekVezerlo.aktivGombasz, hova);
            listakFrissitese();

            if (hova.getGombatest() != gombatest) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres gombatest növesztés!");
                alert.setHeaderText("Ügyi vagy :D");
                alert.showAndWait();
                ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Gomba test növesztés sikertelen!");
                alert.setHeaderText("Hiba :C");
                alert.showAndWait();
            }
    }

    @FXML
    public void onUgrasClick(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void onFejlesztesClick(ActionEvent actionEvent) {
        if (aktivTekton.getGombatest() != null) {
            int id = aktivTekton.getGombatest().getId();
            GrafikusJatekVezerlo.gombaTestFejlesztes(aktivTekton.getGombatest(), aktivTekton);
            listakFrissitese();
            if (aktivTekton.getGombatest().getId() != id) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres fejlesztés!");
                alert.showAndWait();
                ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fejlesztés sikertelen!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Az aktív tektonon nincs gombatest!");
            alert.showAndWait();
        }

    }

    @FXML
    public void onJatekVegeClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.JATEK_AKTIV = false;
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }


    @FXML
    public void onAktivTektonValtasClick(ActionEvent actionEvent) {
        if (szomszedosTektonok.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válassz ki egy tektont a váltáshoz!");
            alert.showAndWait();
        }
        else {
            aktivTekton = szomszedosTektonok.getSelectionModel().getSelectedItem();
            listakFrissitese();
        }

    }

    private void listakFrissitese() {
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivTekton.getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
        ObservableList<Gombatest> gombatestek =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivGombasz.getGombatestek());
        gombak.setItems(gombatestek);

    }

    @FXML
    public void onAktivGombatestValtasClick(ActionEvent actionEvent) {
        if (gombak.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válassz ki egy gombatestet a váltáshoz!");
            alert.showAndWait();
        }
        else {
            aktivGombatest = gombak.getSelectionModel().getSelectedItem();
            aktivTekton = aktivGombatest.getTekton();
            listakFrissitese();
        }
    }
}
