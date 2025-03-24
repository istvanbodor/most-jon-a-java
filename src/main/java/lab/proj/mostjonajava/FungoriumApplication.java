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
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
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


    //itt kell megírni a teszteket
    //a parameterek[1] től kezdődnek a paraméterek, paraméter ellenőrzés először, ha rosszakat adott meg vagy nem eleget
    //akkor hibaüzenet, ha jók akkor továbbhívni ezekkel a paraméterekkel
    //kelleni fog egy játék osztály példány globálisan, ebben kell az állapotokat változtatni
    //todo a két játék init teszteset még hiányzik a parancsok közül - azokat megfogalmazni stb.
    //./mvnw clean javafx:run ezzel a paranccsal futtatható az app zöld gomb nélkül is


    private static void jatekInditasa(String[] parameterek) {
        if (parameterek.length < 3) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return;
        }

        int jatekosokSzama = Integer.parseInt(parameterek[1]);
        List<String> nevek = List.of(parameterek[2].split(","));

        if (jatekosokSzama < 2 || jatekosokSzama > 10) {
            hibaLog("Tul sok vagy tul keves a jatekosok szama!");
            return;
        }

        if(nevek.size() != jatekosokSzama) {
            hibaLog("Nem egyezik a nevek es a jatekosok szama!");
            return;
        }

        jatek = new Jatek(jatekosokSzama, nevek);
    }

    private static void testFonalNovesztes(String[] parameterek) {

    }

    private static void testSporaTermeles(String[] parameterek) {
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert!");
        }

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
        //todo teszteset
    }

    private static void testFejlettSporaSzoras(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestNovesztes(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestFejlesztes(String[] parameterek) {
        //todo teszteset
    }

    private static void testGombaTestElpusztul(String[] parameterek) {
        //todo teszteset
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
        //todo teszteset
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
        //todo teszteset
    }

}