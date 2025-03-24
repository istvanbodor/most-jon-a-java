package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.proj.mostjonajava.game.Jatek;
import lab.proj.mostjonajava.model.*;
import lab.proj.mostjonajava.model.Gombatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import static lab.proj.mostjonajava.utils.Parancsok.*;

public class FungoriumApplication extends Application {
    public static Jatek jatek;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FungoriumApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        String bemenet;
        String[] parameterek;
        String parancs;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Parancsolj: ");
            bemenet = streamReader.readLine();
            parameterek = bemenet.split(" ");
            parancs = parameterek[0];

            switch (parancs) {
                case JATEKINDITAS -> jatekInditasa(parameterek);
                case FONALNOVESZTES -> testFonalNovesztes(parameterek);
                case SPORATERMELES -> testSporaTermeles(parameterek);
                case SIMASPORASZORAS -> testSimaSporaSzoras(parameterek);
                case FEJLETTSPORASZORAS -> testFejlettSporaSzoras(parameterek);
                case GOMBATESTNOVESZTES -> testGombaTestNovesztes(parameterek);
                case GOMBATESTFEJLESZTES -> testGombaTestFejlesztes(parameterek);
                case GOMBATESTELPUSZTUL -> testGombaTestElpusztul(parameterek);
                case ROVARMOZGATAS -> testRovarMozgatas(parameterek);
                case ROVARMOZGATASBLOKKOLAS -> testRovarMozgatasBlokkolas(parameterek);
                case SPORAFOGYASZTAS -> testSporaFogyasztas(parameterek);
                case FONALVAGAS -> testFonalVagas(parameterek);
                case TEKTONTORES -> testTektonTores(parameterek);
                case BENITOSPORAHATASKIFEJTESE -> testBenitoSporaHatasKifejtese(parameterek);
                case LASSITOSPORAHATASKIFEJTESE -> testLassitoSporaHatasKifejtese(parameterek);
                case GYORSITOSPORAHATASKIFEJTESE -> testGyorsitoSporaHatasKifejtese(parameterek);
                case VAGASBENITOSPORAHATASKIFEJTESE -> testVagasBenitoSporaHatasKifejtese(parameterek);
                case SIMASPORAHATASKIFEJTESE -> testSimaSporaHatasKifejtese(parameterek);
                case FONALFELSZIVODAS -> testFonalFelszivodas(parameterek);
                case KILEPES -> System.exit(0);
                default -> hibaLog("Nem letezo parancsot adtal meg!");
            }
        }
    }


    private static void jatekInditasa(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return;

        int jatekosokSzama = Integer.parseInt(parameterek[1]);
        List<String> nevek = List.of(parameterek[2].split(","));

        if (jatekosokSzama < 2 || jatekosokSzama > 10) {
            hibaLog("Tul sok vagy tul keves a jatekosok szama!");
            return;
        }

        if (nevek.size() != jatekosokSzama) {
            hibaLog("Nem egyezik a nevek es a jatekosok szama!");
            return;
        }

        jatek = new Jatek(jatekosokSzama, nevek);
    }

    private static boolean parameterVizsgalat(String[] parameterek, int elvartMeret) {
        if (parameterek.length < elvartMeret) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return true;
        }
        return false;
    }

    private static void testFonalNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return;
        Gombatest gombatest = new Gombatest();
        gombatest.fonalNovesztes(new EgyFonalasTekton(), new EgyFonalasTekton());

    }

    private static void testSporaTermeles(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return;


//        int gombaId = Integer.parseInt(parameterek[1]);
//
//        Gombatest gombatest = Jatek.gombaszok.stream()
//                .flatMap(it -> it.getGombatestek().stream())
//                .filter(it -> it.getId() == gombaId)
//                .findFirst()
//                .orElse(null);
//
//        if (gombatest == null) {
//            hibaLog("Nincs gombatest ilyen ID-val!");
//            return;
//        }
        Gombatest gombatest = new Gombatest();
        gombatest.sporaTermeles();

    }

    private static void testSimaSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return;
        Gombatest gombatest = new Gombatest();
        gombatest.sporaKiloves(new EgyFonalasTekton());
    }

    private static void testFejlettSporaSzoras(String[] parameterek) {
        parameterVizsgalat(parameterek, 3);
        FejlettGombatest gombatest = new FejlettGombatest();
        gombatest.sporaKiloves(new EgyFonalasTekton());
    }

    private static void testGombaTestNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return;
        EgyFonalasTekton tekton = new EgyFonalasTekton();
        if (tekton.gombatestNoveszthetoE()) {
            tekton.setGombatest(new Gombatest());
        } else {
            hibaLog("Ezen a tektonon nem novesztheto gombatest!");
        }
    }

    private static void testGombaTestFejlesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return;

        EgyFonalasTekton tekton = new EgyFonalasTekton();
        if (tekton.gombatestFejleszthetoE()) {
            tekton.setGombatest(new FejlettGombatest());
        } else {
            hibaLog("A tekton gombateste nem fejleszthető!");
        }
    }

    private static void testGombaTestElpusztul(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);

        Gombatest gombatest = new Gombatest();
        gombatest.setKilohetoSporakSzama(0);

        if (gombatest.getKilohetoSporakSzama() == 0) {
            gombatest.elpusztulas();
        }
        else {
            log("A gombatest meg nem all keszen a halalra");
        }

    }

    private static void testRovarMozgatas(String[] parameterek) {
        //todo teszteset
    }

    private static void testRovarMozgatasBlokkolas(String[] parameterek) {
        //todo teszteset
    }

    private static void testSporaFogyasztas(String[] parameterek) {
        //todo teszteset
    }

    private static void testFonalVagas(String[] parameterek) {
        //todo teszteset
    }

    private static void testTektonTores(String[] parameterek) {
        if (parameterek.length < 1) {
            hibaLog("Nem adtal meg elegendo parametert a tekton tores teszthez!");
            return;
        }

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        // Meghívjuk a tores logikát (maga dönti el, megtörténik-e)
        tekton.ketteTores();
    }


    private static void testBenitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int rovarId;
        try {
            rovarId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Ervenytelen rovar ID!");
            return;
        }

        Rovar rovar = Jatek.rovaraszok.stream()
                .filter(r -> r.getId() == rovarId)
                .findFirst()
                .orElse(null);

        if (rovar == null) {
            hibaLog("Nincs ilyen rovar azonositoval: " + rovarId);
            return;
        }

        BenitoSpora benitoSpora = new BenitoSpora();

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        rovar.sporaElfogyasztas(tekton);

        benitoSpora.hatasKifejtese();

        rovar.benulas();
    }

    private static void testLassitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int rovarId;
        try {
            rovarId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Ervenytelen rovar ID!");
            return;
        }

        Rovar rovar = Jatek.rovaraszok.stream()
                .filter(r -> r.getId() == rovarId)
                .findFirst()
                .orElse(null);

        if (rovar == null) {
            hibaLog("Nincs ilyen rovar azonositoval: " + rovarId);
            return;
        }

        LassitoSpora lassitoSpora = new LassitoSpora();

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        rovar.sporaElfogyasztas(tekton);

        lassitoSpora.hatasKifejtese();

        rovar.lepesSzamCsokkentes();
    }

    private static void testGyorsitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int rovarId;
        try {
            rovarId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Ervenytelen rovar ID!");
            return;
        }

        Rovar rovar = Jatek.rovaraszok.stream()
                .filter(r -> r.getId() == rovarId)
                .findFirst()
                .orElse(null);

        if (rovar == null) {
            hibaLog("Nincs ilyen rovar azonositoval: " + rovarId);
            return;
        }

        GyorsitoSpora gyorsitoSpora = new GyorsitoSpora();

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        rovar.sporaElfogyasztas(tekton);

        gyorsitoSpora.hatasKifejtese();

        rovar.lepesSzamNoveles();
    }

    private static void testVagasBenitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int rovarId;
        try {
            rovarId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Ervenytelen rovar ID!");
            return;
        }

        Rovar rovar = Jatek.rovaraszok.stream()
                .filter(r -> r.getId() == rovarId)
                .findFirst()
                .orElse(null);

        if (rovar == null) {
            hibaLog("Nincs ilyen rovar azonositoval: " + rovarId);
            return;
        }

        VagasTiltoSpora vagasTiltoSpora = new VagasTiltoSpora();

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        rovar.sporaElfogyasztas(tekton);

        vagasTiltoSpora.hatasKifejtese();

        rovar.vagoKepessegTiltas();
    }

    private static void testSimaSporaHatasKifejtese(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int rovarId;
        try {
            rovarId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Ervenytelen rovar ID!");
            return;
        }

        Rovar rovar = Jatek.rovaraszok.stream()
                .filter(r -> r.getId() == rovarId)
                .findFirst()
                .orElse(null);

        if (rovar == null) {
            hibaLog("Nincs ilyen rovar azonositoval: " + rovarId);
            return;
        }

        SimaSpora simaSpora = new SimaSpora();

        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        rovar.sporaElfogyasztas(tekton);

        simaSpora.hatasKifejtese();

        rovar.pontNovelese(10);
    }

    private static void testFonalFelszivodas(String[] parameterek) {
        Gombatest gombatest = new Gombatest();
        EltunoFonalasTekton tekton1 = new EltunoFonalasTekton();
        TobbFonalasTekton tekton2 = new TobbFonalasTekton();
        GombaFonal gombafonal = new GombaFonal(tekton1, tekton2, gombatest);

        tekton1.eletIdoCsokkentes(gombafonal);
    }

}