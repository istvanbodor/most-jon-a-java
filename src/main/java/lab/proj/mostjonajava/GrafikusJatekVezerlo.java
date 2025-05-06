package lab.proj.mostjonajava;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.Jatek;
import lab.proj.mostjonajava.model.Gombasz;
import lab.proj.mostjonajava.model.Rovarasz;
import lab.proj.mostjonajava.model.Tekton;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.log;

public class GrafikusJatekVezerlo {
    public static Jatek jatek;

    public static boolean lepett;
    private static int jatekosokSzama;

    private static Stage stage;
    @SneakyThrows
    public GrafikusJatekVezerlo(List<String> nevek, Stage stage) {
        jatek = new Jatek(nevek.size(), nevek);
        jatekosokSzama = nevek.size();
        GrafikusJatekVezerlo.stage = stage;
        jatekVezerlo();
    }

    private void jatekVezerlo() throws IOException {
        FXMLLoader gombaszNezet = new FXMLLoader(getClass().getResource("gombasznezet.fxml"));
        FXMLLoader rovaraszNezet = new FXMLLoader(getClass().getResource("rovarasznezet.fxml"));

        Random rnd = new Random();
        for (int i = 0; i < Jatek.KOROK_SZAMA; i++) {
            int korSzam = i + 1;
            int gombaszIndex = 0;
            int rovaraszIndex = 0;
            log(korSzam + ". kor");
            for (int j = 0; j < jatekosokSzama; j++) {
                int lepesSzam = j + 1;
                if (j % 2 == 0) {
                    String lepoJatekosNeve = jatek.getGombaszok().get(gombaszIndex).getNev();
                    Parent root = gombaszNezet.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Gombasz nezet");
                    stage.setScene(scene);
                    stage.show();
                    gombaszIndex++;
                } else {
                    String lepoJatekosNeve = jatek.getRovaraszok().get(rovaraszIndex).getNev();
                    Parent root = rovaraszNezet.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Gombasz nezet");
                    stage.setScene(scene);
                    stage.show();
                    rovaraszIndex++;
                }
                for (int k = 0; k < jatek.getTabla().size(); k++) {
                    int chance = rnd.nextInt(1,10);
                    if (chance == 1) {
                        List<Tekton> tektons = jatek.getTabla().get(k).ketteTores();
                        jatek.getTabla().remove(k);
                        jatek.getTabla().addAll(tektons);
                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Jatek vege!");
            alert.setContentText("Jatek vege! Gyoztesek: \n" + "Rovarasz: " +  jatek.getRovaraszok().stream()
                    .max(Comparator.comparingInt(Rovarasz::getPont)).get().getNev() + "\n \"Gombasz: \" + jatek.getGombaszok().stream()\n" +
                    "                    .max(Comparator.comparingInt(Gombasz::getPont)).get().getNev()");
            alert.showAndWait();
        }

    }

}
