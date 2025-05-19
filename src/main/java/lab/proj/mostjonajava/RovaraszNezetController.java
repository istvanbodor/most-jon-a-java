package lab.proj.mostjonajava;

import javafx.application.Platform;
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
import lab.proj.mostjonajava.model.Rovar;
import lab.proj.mostjonajava.model.Tekton;

import java.util.Objects;
import java.util.stream.Collectors;

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

    private int rovarokOsszLepesSzama;
    @FXML
    private ImageView gombatesticon;
    @FXML
    private ImageView rovaricon;
    @FXML
    private ImageView sporaicon;

    /**
     * Inicializáló metódus, amely beállítja a kezdeti állapotot.
     * Betölti a rovarász rovarlistáját és a szomszédos tektönöket.
     */
    @FXML
    void initialize() {
        setControlsDisabled(true);
        if (GrafikusJatekVezerlo.aktivRovarasz.getRovarok().size() > 0) {
            rovarOsszLepesBeallitas();
            ObservableList<Rovar> obsRovarok =
                    FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivRovarasz.getRovarok());
            rovarok.setItems(obsRovarok);

            aktivRovar = rovarok.getItems().get(0);
            aktivTekton = aktivRovar.getTekton();
            ObservableList<Tekton> tektonok =
                    FXCollections.observableArrayList(aktivRovar.getTekton().getSzomszedosTektonok());
            szomszedosTektonok.setItems(tektonok);
            tekton.setFill(GrafikusJatekVezerlo.jatek.getTektonSzinek().get(aktivTekton));
            ikonokMegjelenitese();
        } else {
            Platform.runLater(() -> {
                if (ugrasGomb.getScene() != null) {
                    ugrasGomb.getScene().getWindow().hide();
                }
            });
        }
        Platform.runLater(() -> setControlsDisabled(false));
    }
    /**
     * A felhasználói felület elemek engedélyezését/letiltását vezérli.
     * @param disabled true esetén letiltja a UI elemeket, false esetén engedélyezi
     */
    private void setControlsDisabled(boolean disabled) {
        lepesGomb.setDisable(disabled);
        vagasGomb.setDisable(disabled);
        ugrasGomb.setDisable(disabled);
        jatekVegeGomb.setDisable(disabled);
        aktivRovarVatlasGomb.setDisable(disabled);
        rovarok.setDisable(disabled);
        szomszedosTektonok.setDisable(disabled);
    }
    /**
     * Megjeleníti a tektönhöz tartozó ikonokat (gombatest, rovar, spóra).
     */
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
    /**
     * Kiszámolja a rovarok összes lépésszámát.
     */
    private void rovarOsszLepesBeallitas() {
        rovarokOsszLepesSzama = 0;
        for (Rovar rovar : GrafikusJatekVezerlo.aktivRovarasz.getRovarok()) {
            rovarokOsszLepesSzama += rovar.getLepesSzam();
        }
    }
    /**
     * Frissíti a listákat és a tektön részleteket.
     */
    private void listakFrissitese() {
        aktivTekton = aktivRovar.getTekton();
        ObservableList<Tekton> tektonok =
                FXCollections.observableArrayList(aktivRovar.getTekton().getSzomszedosTektonok());
        szomszedosTektonok.setItems(tektonok);
        ObservableList<Rovar> obsRovarok =
                FXCollections.observableArrayList(GrafikusJatekVezerlo.aktivRovarasz.getRovarok());
        rovarok.setItems(obsRovarok);
        rovarOsszLepesBeallitas();
        tekton.setFill(GrafikusJatekVezerlo.jatek.getTektonSzinek().get(aktivTekton));
        ikonokMegjelenitese();
    }
    /**
     * Rovar lépése gomb eseménykezelője.
     * @param actionEvent az esemény objektum
     */
    @FXML
    public void onRovarLepesClick(ActionEvent actionEvent) {
        Tekton hova = szomszedosTektonok.getSelectionModel().getSelectedItem();
        if (aktivRovar.getLepesSzam() > 0) {
            GrafikusJatekVezerlo.rovarMozgatas(aktivRovar, hova);
            if (aktivRovar.getTekton() == hova) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeresen lépett a rovar!");
                alert.setHeaderText("Yay! :D");
                alert.showAndWait();
                listakFrissitese();
                if (rovarokOsszLepesSzama <= 0) {
                    ((Button) actionEvent.getSource()).getScene().getWindow().hide();
                }
            } else {
                System.out.println(aktivRovar.getTekton());
                Alert alert = new Alert(Alert.AlertType.ERROR, "Lépés sikertelen!");
                alert.setHeaderText("Hiba :C");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Az aktív rovar már nem léphet!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }

    }
    /**
     * Fonal vágás gomb eseménykezelője.
     * @param actionEvent az esemény objektum
     */
    @FXML
    public void onFonalVagasClick(ActionEvent actionEvent) {
        int meret = aktivRovar.getTekton().getGombafonalak().size();
        if (aktivRovar.getLepesSzam() > 0) {
            GrafikusJatekVezerlo.fonalVagas(aktivRovar, szomszedosTektonok.getSelectionModel().getSelectedItem());
            if (meret > aktivRovar.getTekton().getGombafonalak().size()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres fonal vágás!");
                alert.setHeaderText("Yay! :C");
                alert.showAndWait();
                listakFrissitese();
                if (rovarokOsszLepesSzama == 0) {
                    ((Button) actionEvent.getSource()).getScene().getWindow().hide();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fonal vágás sikertelen!");
                alert.setHeaderText("Hiba :C");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Az aktív rovar már nem léphet!");
            alert.setHeaderText("Hiba :C");
            alert.showAndWait();
        }
    }
    /**
     * Ugrás (kör vége) gomb eseménykezelője.
     * @param actionEvent az esemény objektum
     */
    @FXML
    public void onUgrasClick(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
    /**
     * Játék vége gomb eseménykezelője.
     * @param actionEvent az esemény objektum
     */
    @FXML
    public void onJatekVegeClick(ActionEvent actionEvent) {
        GrafikusJatekVezerlo.JATEK_AKTIV = false;
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
    /**
     * Aktív rovar váltása gomb eseménykezelője.
     * @param actionEvent az esemény objektum
     */
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
    /**
     * Frissíti a tektön részleteit megjelenítő listát.
     * @param tekton a megjelenítendő tekton
     * @deprecated helyette használj más megjelenítési módszert
     */
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
                    .map(r -> String.format("ID=%d",
                            r.getId()))
                    .collect(Collectors.joining(" | ")));
        }
        sb.append("\n");

        details.add(sb.toString());
        informaciok.setItems(details);
    }
}
