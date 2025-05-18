package lab.proj.mostjonajava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import lab.proj.mostjonajava.model.Gombatest;
import lab.proj.mostjonajava.model.Tekton;

import java.util.Objects;
import java.util.stream.Collectors;

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
    private ImageView gombatesticon;
    @FXML
    private ImageView rovaricon;
    @FXML
    private ImageView sporaicon;

    @FXML
    private void initialize() {
        if (GrafikusJatekVezerlo.aktivGombasz.getGombatestek().size() > 0) {
            ObservableList<Gombatest> gombatestek =
                    FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivGombasz.getGombatestek());
            gombak.setItems(gombatestek);
            aktivGombatest = gombatestek.get(0);
            aktivTekton = aktivGombatest.getTekton();
            ObservableList<Tekton> tektonok =
                    FXCollections.observableArrayList(aktivGombatest.getTekton().getSzomszedosTektonok());
            szomszedosTektonok.setItems(tektonok);
            updateTektonDetails(aktivTekton);
            aktualisTekton.setFill(GrafikusJatekVezerlo.jatek.getTektonSzinek().get(aktivTekton));
            ikonokMegjelenitese();
        }
       else {
            javafx.application.Platform.runLater(() -> {
                ugrasGomb.getScene().getWindow().hide();
            });
        }
    }

    private void ikonokMegjelenitese() {
        if (aktivTekton.getGombatest() != null) {
            gombatesticon.setImage(new Image(Objects.requireNonNull(getClass().getResource(GrafikusJatekVezerlo.jatek.getGombatestIkonok().get(aktivTekton.getGombatest().getId()))).toExternalForm()));

        }
        else {
            gombatesticon.setImage(null);
        }
        if (aktivTekton.getSporak().size() > 0) {
            sporaicon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/ikonok/Spora.png")).toExternalForm()));
        }
        else {
            sporaicon.setImage(null);
        }
        if (aktivTekton.getRovarok().size()>0) {
            rovaricon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/ikonok/Rovar.png")).toExternalForm()));
        }
       else {
           rovaricon.setImage(null);
        }

    }

    @FXML
    public void onSporaSzorasClick(ActionEvent actionEvent) {
        if (szomszedosTektonok.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válaszdd ki hova szeretnél spórát szórni!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
            return;
        }
        Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
        int sporaSzam = hova.getSporak().size();
        GrafikusJatekVezerlo.sporaSzoras(aktivGombatest,hova, 1);
        listakFrissitese();
        if (sporaSzam < hova.getSporak().size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres spóra szórás!");
            alert.setHeaderText("Yay! :D");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Spóra szórás sikertelen!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
    }

    @FXML
    public void onFonalNovesztesClick(ActionEvent actionEvent) {
        if (szomszedosTektonok.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válaszdd ki hova szeretnél fonalat növeszteni!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
            return;
        }
        int fonalakSzama = aktivGombatest.getGombaFonalak().size();
        GrafikusJatekVezerlo.fonalNovesztes(aktivGombatest, aktivTekton, szomszedosTektonok.getSelectionModel().getSelectedItem());
        listakFrissitese();
        if (fonalakSzama < aktivGombatest.getGombaFonalak().size()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres fonal növesztés!");
            alert.setHeaderText("Yay! :D");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fonal növesztés nem lehetséges!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
        if (aktivGombatest.getNoveszthetoFonalakSzama() == 0) {
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }

    @FXML
    public void onGombaTestNovesztesClick(ActionEvent actionEvent) {
        Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
            if (hova != null) {
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
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Válaszdd ki hova szeretnél növeszteni!");
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
                alert.setHeaderText("Ügyi vagy :D");
                alert.showAndWait();
                ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fejlesztés sikertelen!");
                alert.setHeaderText("Hiba :C");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Az aktív tektonon nincs gombatest!");
            alert.setHeaderText("Hiba :C");
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
            alert.setHeaderText("Hiba :C");
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
        updateTektonDetails(aktivTekton);
        aktualisTekton.setFill(GrafikusJatekVezerlo.jatek.getTektonSzinek().get(aktivTekton));
        ikonokMegjelenitese();

    }

    @FXML
    public void onAktivGombatestValtasClick(ActionEvent actionEvent) {
        if (gombak.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Válassz ki egy gombatestet a váltáshoz!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
        else {
            aktivGombatest = gombak.getSelectionModel().getSelectedItem();
            aktivTekton = aktivGombatest.getTekton();
            listakFrissitese();
        }
    }

    @Deprecated
    private void updateTektonDetails(Tekton tekton) {
        ObservableList<String> details = FXCollections.observableArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Tekton[").append(tekton.getId()).append("]\n");

        sb.append("  Gombafonal-osszekottetesek: ");
        if (tekton.getGombafonalak().isEmpty()) {
            sb.append("nincs");
        } else {
            String kapcsolatok = tekton.getGombafonalak().stream()
                    .map(f -> {
                        Tekton masik = f.getHonnan().equals(tekton) ? f.getHova() : f.getHonnan();
                        return String.valueOf(masik.getId());
                    })
                    .distinct()
                    .collect(Collectors.joining(", "));
            sb.append(kapcsolatok);
        }
        sb.append("\n");

        sb.append("  Gombatest: ");
        if (tekton.getGombatest() != null) {
            sb.append("Gombatest[").append(tekton.getGombatest().getId()).append("]");
        } else {
            sb.append("nincs");
        }
        sb.append("\n");

        sb.append("  Sporak: ");
        if (tekton.getSporak().isEmpty()) {
            sb.append("nincs");
        } else {
            sb.append(tekton.getSporak().stream()
                    .map(sp -> sp.getClass().getSimpleName())
                    .collect(Collectors.joining(", ")));
        }
        sb.append("\n");

        sb.append("  Rovarok: ");
        if (tekton.getRovarok().isEmpty()) {
            sb.append("nincsenek");
        } else {
            sb.append(tekton.getRovarok().stream()
                    .map(r -> String.format("ID=%d, lepSz=%d, benult=%b, vago=%b",
                            r.getId(), r.getLepesSzam(), r.getBenulas(), r.getVagoKepesseg()))
                    .collect(Collectors.joining(" | ")));
        }
        sb.append("\n");

        details.add(sb.toString());
        informaciok.setItems(details);
    }
}
