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
import java.util.*;

import static lab.proj.mostjonajava.utils.Logger.log;

public class GrafikusJatekVezerlo {
    public static Jatek jatek;

    public static Gombasz aktivGombasz;
    public static Rovarasz aktivRovarasz;
    private static int jatekosokSzama;

    private static Stage stage;

    public static boolean JATEK_AKTIV = false;

    /**
     * A grafikus játékvezérlő konstruktora
     * @param nevek
     * @param stage
     */
    @SneakyThrows
    public GrafikusJatekVezerlo(List<String> nevek, Stage stage) {
        jatek = new Jatek(nevek.size(), nevek);
        jatekosokSzama = nevek.size();
        GrafikusJatekVezerlo.stage = stage;
        jatekVezerles();
    }

    /**
     * A játék köreinek a kezeléséért és a felhasználói nézetek frissítéséért felel
     * @throws IOException
     */
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
                List<Gombatest> gombatestek = jatek.getGombaszok().get(d).getGombatestek();
                Iterator<Gombatest> it = gombatestek.iterator();
                while (it.hasNext()) {
                    Gombatest gombatest = it.next();
                    if (gombatest.getElszortSporakSzama() >= 10) {
                        gombatest.getGombaFonalak().clear();
                        Tekton t = gombatest.getTekton();
                        if (t != null) {
                            t.setGombatest(null);
                        }
                        it.remove();
                    } else {
                        gombatest.korFrissites();
                    }
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

                for(int g = 0; g < jatek.getGombaszok().size(); g++) {
                    for (int gt = 0; gt < jatek.getGombaszok().get(g).getGombatestek().size(); gt++) {
                        for (int gf = 0; gf < jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().size(); gf++) {
                            for (int r = 0; r < jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHonnan().getRovarok().size(); r++) {
                                if (jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHonnan().getRovarok().get(r).getBenulas()) {
                                    jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).rovarElfogyasztas(jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHonnan().getRovarok().get(r));
                                    jatek.getGombatestIkonok().put(jatek.getGombaszok().get(g).getGombatestek().get(jatek.getGombaszok().get(g).getGombatestek().size()-1).getId(), "/ikonok/SimaGombaTest.png");
                                }
                            }
                            for (int r = 0; r < jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHova().getRovarok().size(); r++) {
                                if (jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHova().getRovarok().get(r).getBenulas()) {
                                    jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).rovarElfogyasztas(jatek.getGombaszok().get(g).getGombatestek().get(gt).getGombaFonalak().get(gf).getHova().getRovarok().get(r));
                                    jatek.getGombatestIkonok().put(jatek.getGombaszok().get(g).getGombatestek().get(jatek.getGombaszok().get(g).getGombatestek().size()-1).getId(), "/ikonok/SimaGombaTest.png");
                                }
                            }
                        }
                    }
                }
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

    /**
     * A fonal növesztéséért felel két tekton között
     * @param gombatest A gombatest amihez tartozik a növesztendő fonal
     * @param honnan A tekton ahonnan nő a fonal
     * @param hova A tekton ahova nő a fonal
     */
    public static void fonalNovesztes(Gombatest gombatest, Tekton honnan, Tekton hova) {
        jatek.keresGombatestById(gombatest.getId()).fonalNovesztes(honnan, hova);
    }

    /**
     * Spórát szór egy tektonra
     * @param gombatest A gombatest ami elszórja a spórát
     * @param hova A tekton ahova szórja a spórát
     * @param mennyiseg Az elszórt spórák száma
     */
    public static void sporaSzoras(Gombatest gombatest, Tekton hova, int mennyiseg) {
        jatek.keresGombatestById(gombatest.getId()).sporaKiloves(hova, mennyiseg);
    }
    /**
     *Gombatestet növeszt egy tektonra
     * @param gombasz A gombász akihez tartozik a gombatest
     * @param hova A tekton ahova nő az új gombatest
     */
    public static void gombaTestNovesztes(Gombasz gombasz, Tekton hova) {
        jatek.keresGombatestById(aktivGombasz.getId()).getGombasz().gombaTestNovesztes(hova);
        jatek.getGombatestIkonok().put(aktivGombasz.getGombatestek().get(aktivGombasz.getGombatestek().size()-1).getId(), "/ikonok/SimaGombaTest.png");
    }

    /**
     * Egy már létező gombatestet fejleszt fejlettGombatesté
     * @param gombatest A fejlesztendő gombatest
     * @param hova A tekton amin a gombatest van
     */
    public static void gombaTestFejlesztes(Gombatest gombatest, Tekton hova) {
        jatek.keresGombatestById(aktivGombasz.getId()).getGombasz().gombaTestFejlesztes(gombatest);
        if (jatek.keresGombatestById(gombatest.getId()) == null) {
            jatek.getGombatestIkonok().put(aktivGombasz.getGombatestek().get(aktivGombasz.getGombatestek().size()-1).getId(), "/ikonok/FejlettGombaTest.png");
        }
        else {
            log("gombatestIkonNoNo");
        }
    }

    /**
     * A rovart mozgatja az egyik tektonról a másikra
     * @param rovar A rovar ami lép
     * @param hova A tekton amire lép a rovar
     */
    public static void rovarMozgatas(Rovar rovar, Tekton hova) {
        jatek.keresRovarById(rovar.getId()).lepes(hova);
    }

    /**
     * Elvágja a fonalat a rovar és a kiválasztott tekton között
     * @param rovar A rovar ami elvágja a fonalat
     * @param hova A tekton ami és maga között a rovar elvágja a fonalat
     */
    public static void fonalVagas(Rovar rovar, Tekton hova) {
        jatek.keresRovarById(rovar.getId()).fonalVagas(hova);
    }
}
