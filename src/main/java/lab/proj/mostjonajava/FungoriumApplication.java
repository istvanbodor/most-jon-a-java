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
        if (parameterek.length < 2) {
            hibaLog("Nem adtal meg elegendo parametert a fonal novesztesehez!");
            return;
        }
        Tekton honnan = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };
        Tekton hova = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        hova.setSzomszedosTektonok(new ArrayList<>());
        honnan.setSzomszedosTektonok(new ArrayList<>());
        //List<Tekton> szomszedokHova = new ArrayList<>();
        //List<Tekton> szomszedokHonnan = new ArrayList<>();
        //szomszedokHova.add(honnan);

        //hova.setSzomszedosTektonok(szomszedokHova);
        //honnan.setSzomszedosTektonok(szomszedokHonnan);


        Gombatest gombatest = new Gombatest();
        Gombasz gombasz= new Gombasz("nev");
        Tekton valasztott = gombasz.tektonKivalasztasa();
        hova.getSzomszedosTektonok();
        hova.egyenlo(honnan);
        hova.vanFonalKozottuk(hova);
        hova.gombafonalNoveszthetoE();

        /*if (valasztott == null ||
                hova == null ||
                honnan == null ||
                hova.getSzomszedosTektonok() == null ||
                !hova.egyenlo(honnan) ||
                !hova.vanFonalKozottuk(honnan) ||
                !hova.gombafonalNoveszthetoE()) {
            return;
        } else {*/
            GombaFonal gombafonal = new GombaFonal(honnan, hova, gombatest);
            hova.fonalHozzaadasa(gombafonal);
        //}
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
        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };
        SimaSpora sspora = new SimaSpora();
        tekton.sporaHozzaadasa(sspora);
    }

    private static void testFejlettSporaSzoras(String[] parameterek) {
        Tekton tekton1 = new Tekton() {@Override public Tekton ujTektonLetrehozasa() {
                return null;
            }@Override public void ketteTores() {}
        };
        Tekton tekton2 = new Tekton() {@Override public Tekton ujTektonLetrehozasa() {
                return null;
            }@Override public void ketteTores() { }
        };
        Tekton tekton3 = new Tekton() {@Override public Tekton ujTektonLetrehozasa() {
                return null;
            } @Override public void ketteTores() {}
        };
        List<Tekton> tektonok1 = new ArrayList<>();
        tektonok1.add(tekton2);
        List<Tekton> tektonok2 = new ArrayList<>();
        tektonok2.add(tekton1);
        tektonok2.add(tekton3);
        List<Tekton> tektonok3 = new ArrayList<>();
        tektonok3.add(tekton2);
        tekton1.setSzomszedosTektonok(tektonok1);
        tekton2.setSzomszedosTektonok(tektonok2);
        tekton3.setSzomszedosTektonok(tektonok3);
        Gombatest gombatest = new Gombatest();
        gombatest.setKilohetoSporakSzama(10);
        tekton1.setGombatest(gombatest);
        gombatest.sporaKiloves(tekton3);
        log("FejlettSporaSzoras");
    }

    private static void testGombaTestNovesztes(String[] parameterek) {
        if (parameterek.length < 1) {
            hibaLog("Nem adtal meg elegendo parametert a gombatest novesztesehez!");
            return;
        }

        // Gombasz létrehozása és tekton kiválasztása szimuláltan
        Gombasz gombasz = new Gombasz("tesztgombasz");
        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };
        gombasz.tektonKivalasztasa();
        int sporaSzam = tekton.getSporaSzam();
        boolean vanFonal = !tekton.getGombafonalak().isEmpty(); // feltételezve, hogy ez nem null
        boolean novesztheto = tekton.gombatestNoveszthetoE();
        boolean vanMarGombatest = tekton.getGombatest() != null;

        Spora spora = new Spora() {
            @Override
            public void hatasKifejtese() {

            }

            @Override
            public Spora ujSporaLetrehozasa() {
                return null;
            }
        };
        Tekton hn = tekton;
        Tekton hv = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };
        Gombatest gt = new Gombatest();
        GombaFonal fonal = new GombaFonal(hn, hv, gt);
        List<Spora> sporak = new ArrayList<>();
        sporak.add(spora);
        List<GombaFonal> fonalak = new ArrayList<>();
        fonalak.add(fonal);

        Spora elsoSpora = sporak.get(0);
        tekton.sporaTorlese(elsoSpora);

        GombaFonal elsoFonal = fonalak.get(0);
        tekton.fonalTorlese(elsoFonal);

        gombasz.gombaTestNovesztes(tekton);

        Gombatest uj = new Gombatest();
        tekton.setGombatest(uj);

        gombasz.pontNovelese(10);

        log("Gombatest novesztese sikeres volt.");
    }

    private static void testGombaTestFejlesztes(String[] parameterek) {
        if (parameterek.length < 1) {
            hibaLog("Nem adtal meg elegendo parametert a gombatest fejleszteshez!");
            return;
        }

        // Gombasz és Tekton beállítása
        Gombasz gombasz = new Gombasz("tesztgombasz");
        Tekton tekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        Tekton szomszedtekton = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        Spora spora1 = new Spora() {
            @Override
            public void hatasKifejtese() {

            }

            @Override
            public Spora ujSporaLetrehozasa() {
                return null;
            }
        };
        Spora spora2 = new Spora() {
            @Override
            public void hatasKifejtese() {

            }

            @Override
            public Spora ujSporaLetrehozasa() {
                return null;
            }
        };
        Spora spora3 = new Spora() {
            @Override
            public void hatasKifejtese() {

            }

            @Override
            public Spora ujSporaLetrehozasa() {
                return null;
            }
        };
        int sporaSzam = tekton.getSporaSzam();
        List<Spora> sporak = new ArrayList<>();
        sporak.add(spora1);
        sporak.add(spora2);
        sporak.add(spora3);

        Gombatest gombatest= new Gombatest();
        tekton.setGombatest(gombatest);
        GombaFonal gombaFonal = new GombaFonal(tekton, szomszedtekton, gombatest);
        List<GombaFonal> fonalak = new ArrayList<>();
        fonalak.add(gombaFonal);

        // Feltétel: legalább 3 spóra kell
        if (sporak == null || sporak.size() < 3) {
            log("Nem tortent fejlesztes, mert nincs elegendo spora (szam: " + sporaSzam + ")");
            return;
        }

        // Feltétel: legalább 1 gombafonal kell
        if (fonalak == null ) {
            log("Nem tortent fejlesztes, mert nincs elegendo fonal ");
            return;
        }

        // Gombatest lekérése
        //Gombatest gombatest = tekton.getGombatest();
        if (gombatest == null) {
            log("Nincs gombatest ezen a tektonon!");
            return;
        }

        // Spóra törlése és fejlesztés
        for (int i = 0; i < sporak.size(); i++) {
            tekton.sporaTorlese(sporak.get(i));
        }

        for (int i = 0; i < fonalak.size(); i++) {
            tekton.fonalTorlese(fonalak.get(i));
        }

        gombasz.gombaTestFejlesztes(gombatest);

        log("Gombatest fejlesztes sikeres volt.");
    }

    private static void testGombaTestElpusztul(String[] parameterek) {
        if (parameterek.length < 1) {
            hibaLog("Nem adtal meg elegendo parametert a gombatest elpusztulashoz!");
            return;
        }

        Tekton tekton1 = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        Tekton tekton2 = new Tekton() {
            @Override
            public Tekton ujTektonLetrehozasa() {
                return null;
            }

            @Override
            public void ketteTores() {

            }
        };

        Gombatest gombatest = new Gombatest() {};
        tekton1.setGombatest(gombatest);
        GombaFonal gombaFonal = new GombaFonal(tekton1, tekton2, gombatest);

        if (gombatest == null) {
            hibaLog("Nincs gombatest ezen a Tektonon!");
            return;
        }

        int sporaSzam = gombatest.getKilohetoSporakSzama();

        log("Kiloheto sporak szama: " + sporaSzam);

        if (sporaSzam == 0) {
            if (!gombaFonal.vanGombaTestKapcsolat(gombaFonal)){
               gombaFonal.fonalTorlese(gombaFonal);
            };
            gombatest.elpusztulas();

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
        //todo teszteset
    }

}