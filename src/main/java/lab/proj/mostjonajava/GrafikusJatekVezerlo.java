package lab.proj.mostjonajava;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.Jatek;
import lab.proj.mostjonajava.model.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.log;

public class GrafikusJatekVezerlo {
    public static Jatek jatek;

    public static Gombasz aktivGombasz;
    public static Rovarasz aktivRovarasz;
    private static int jatekosokSzama;

    private static Stage stage;

    public static boolean JATEK_AKTIV = false;

    @SneakyThrows
    public GrafikusJatekVezerlo(List<String> nevek, Stage stage) {
        jatek = new Jatek(nevek.size(), nevek);
        jatekosokSzama = nevek.size();
        GrafikusJatekVezerlo.stage = stage;
        jatekVezerles();
    }

    private void jatekVezerles() throws IOException {


        JATEK_AKTIV = true;
        Random rnd = new Random();
        for (int i = 0; i < Jatek.KOROK_SZAMA; i++) {
            int korSzam = i + 1;
            int gombaszIndex = 0;
            int rovaraszIndex = 0;
            log(korSzam + ". kor");
            for (Tekton tekton: jatek.getTabla()
            ) {
                tekton.korFrissites();
            }
            for (int c = 0; c < jatek.getRovaraszok().size(); c++) {
                for (Rovar rovar: jatek.getRovaraszok().get(c).getRovarok()
                ) {
                    rovar.korFrissites();
                }
            }
            for (int d = 0; d < jatek.getGombaszok().size(); d++) {
                for (Gombatest gombatest : jatek.getGombaszok().get(d).getGombatestek()) {
                    gombatest.korFrissites();
                }
            }
            for (int j = 0; j < jatekosokSzama; j++) {
                int lepesSzam = j + 1;
                if (j % 2 == 0) {
                    aktivGombasz = jatek.getGombaszok().get(gombaszIndex);
                    String lepoJatekosNeve =aktivGombasz.getNev();
                    FXMLLoader gombaszNezet = new FXMLLoader(getClass().getResource("gombasznezet.fxml"));
                    Parent root = gombaszNezet.load();
                    Scene scene = new Scene(root);

                    Stage modalStage = new Stage();
                    modalStage.initOwner(stage);
                    modalStage.initModality(Modality.APPLICATION_MODAL);
                    modalStage.setTitle("Gombasz nezet - " + lepoJatekosNeve);
                    modalStage.setScene(scene);
                    if(JATEK_AKTIV) {
                        modalStage.showAndWait();
                    }

                    gombaszIndex++;
                } else {
                    aktivRovarasz = jatek.getRovaraszok().get(rovaraszIndex);
                    String lepoJatekosNeve = aktivRovarasz.getNev();
                    FXMLLoader rovaraszNezet = new FXMLLoader(getClass().getResource("rovarasznezet.fxml"));
                    Parent root = rovaraszNezet.load();
                    Scene scene = new Scene(root);

                    Stage modalStage = new Stage();
                    modalStage.initOwner(stage);
                    modalStage.initModality(Modality.APPLICATION_MODAL);
                    modalStage.setTitle("Rovarasz nezet - " + lepoJatekosNeve);
                    modalStage.setScene(scene);
                    if(JATEK_AKTIV) {
                        modalStage.showAndWait();
                    }
                    rovaraszIndex++;
                }
                for (int k = 0; k < jatek.getTabla().size(); k++) {
                    int chance = rnd.nextInt(1, 30);
                    if (chance == 1) {
                        Tekton tekton = jatek.getTabla().get(k);
                        List<Tekton> tektons = jatek.getTabla().get(k).ketteTores();
                        jatek.getTabla().remove(tekton);
                        jatek.getTektonSzinek().put(tektons.get(0), jatek.getTektonSzinek().get(tekton));
                        jatek.getTektonSzinek().put(tektons.get(1), jatek.getTektonSzinek().get(tekton));
                        jatek.getTabla().addAll(tektons);
                    }
                }


            }
        }
        JATEK_AKTIV = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Jatek vege!");
        alert.setContentText("Jatek vege! Gyoztesek: \n" + "Rovarasz: " + jatek.getRovaraszok().stream()
                .max(Comparator.comparingInt(Rovarasz::getPont)).get().getNev() + " - " + jatek.getRovaraszok().stream()
                .max(Comparator.comparingInt(Rovarasz::getPont)).get().getPont() + "\nGombasz: " + jatek.getGombaszok().stream()
                .max(Comparator.comparingInt(Gombasz::getPont)).get().getNev() + " - " + jatek.getGombaszok().stream()
                .max(Comparator.comparingInt(Gombasz::getPont)).get().getPont());
        alert.showAndWait();
        FXMLLoader menu = new FXMLLoader(getClass().getResource("jatekmenunezet.fxml"));
        Parent menuStageRoot = menu.load();
        Scene menuScene = new Scene(menuStageRoot);
        stage.setScene(menuScene);
        stage.show();
    }

    public static void fonalNovesztes(Gombatest gombatest, Tekton honnan, Tekton hova) {
        jatek.keresGombatestById(gombatest.getId()).fonalNovesztes(honnan, hova);
    }

    public static void sporaSzoras(Gombatest gombatest, Tekton hova, int mennyiseg) {
        jatek.keresGombatestById(gombatest.getId()).sporaKiloves(hova, mennyiseg);
    }


    public static void gombaTestNovesztes(Gombasz gombasz, Tekton hova) {
        jatek.keresGombatestById(aktivGombasz.getId()).getGombasz().gombaTestNovesztes(hova);
        System.out.println(aktivGombasz.getGombatestek());
    }

    public static void gombaTestFejlesztes(Gombatest gombatest, Tekton hova) {
        jatek.keresGombatestById(aktivGombasz.getId()).getGombasz().gombaTestFejlesztes(gombatest);
    }

    public static void rovarMozgatas(Rovar rovar, Tekton hova) {
        jatek.keresRovarById(rovar.getId()).lepes(hova);
    }

    public static void fonalVagas(Rovar rovar, Tekton hova) {
        jatek.keresRovarById(rovar.getId()).fonalVagas(hova);
    }
}
