package lab.proj.mostjonajava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.proj.mostjonajava.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import static lab.proj.mostjonajava.utils.Parancsok.*;

import lab.proj.mostjonajava.game.Jatek;

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
                case MENTES -> mentes(parameterek);
                case BETOLTES -> betoltes(parameterek);
                case MAKE -> make(parameterek);
                case ALLAPOT -> allapot(parameterek);
                case JATEKINDITAS -> jatekInditasa(parameterek);
                case FONALNOVESZTES -> testFonalNovesztes(parameterek);
                case SIMASPORASZORAS -> testSimaSporaSzoras(parameterek);
                case FEJLETTSPORASZORAS -> testFejlettSporaSzoras(parameterek);
                case GOMBATESTNOVESZTES -> testGombaTestNovesztes(parameterek);
                case GOMBATESTFEJLESZTES -> testGombaTestFejlesztes(parameterek);
                case GOMBATESTELPUSZTUL -> testGombaTestElpusztul(parameterek);
                case ROVARMOZGATAS -> testRovarMozgatas(parameterek);
                case SPORAFOGYASZTAS -> testSporaFogyasztas(parameterek);
                case FONALVAGAS -> testFonalVagas(parameterek);
                case TEKTONTORES -> testTektonTores(parameterek);
                case BENITOSPORAHATASKIFEJTESE -> testBenitoSporaHatasKifejtese(parameterek);
                case LASSITOSPORAHATASKIFEJTESE -> testLassitoSporaHatasKifejtese(parameterek);
                case GYORSITOSPORAHATASKIFEJTESE -> testGyorsitoSporaHatasKifejtese(parameterek);
                case VAGASBENITOSPORAHATASKIFEJTESE -> testVagasBenitoSporaHatasKifejtese(parameterek);
                //case OSZTODOSPORAHATASKIFEJTESE -> testOsztodoSporaHatasKiejtese(parameterek);
                case SIMASPORAHATASKIFEJTESE -> testSimaSporaHatasKifejtese(parameterek);
                //case ROVARELFOGYASZTASA -> rovarelfogyasztasa(parameterek);
                case FONALFELSZIVODAS -> testFonalFelszivodas(parameterek);
                case KILEPES -> System.exit(0);
                default -> hibaLog("Nem letezo parancsot adtal meg!");
            }
        }
    }

    private static void mentes(String[] parameterek) { }

    private static void betoltes(String[] parameterek) { }

    private static void make(String[] parameterek) { }

    private static void allapot(String[] parameterek) { }

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
       // parameterek[0].fonalNovesztes(parameterek[1], parameterek[2]);
    }

    private static void testSporaTermeles(String[] parameterek) {
        // legalább 2 paraméter: a parancs és a gombatest ID
        if (parameterVizsgalat(parameterek, 2)) return;

        // 1) ID parse
        int gtId;
        try {
            gtId = Integer.parseInt(parameterek[1]);
        } catch (NumberFormatException e) {
            hibaLog("Érvénytelen Gombatest ID: " + parameterek[1]);
            return;
        }

        // 2) Gombatest keresése a Játékból
        Gombatest gt = jatek.keresGombatestById(gtId);
        if (gt == null) {
            hibaLog("Nincs gombatest ilyen ID-val: " + gtId);
            return;
        }

        // 3) Előtte–utána számlálás
        int elotteKilotheto = gt.getKilohetoSporakSzama();
        gt.korFrissites();  // ez termel +1 spórát (kilőhetőként) ha <10
        int utanaKilotheto = gt.getKilohetoSporakSzama();

        // 4) Eredmény kiírása
        log("Spora termeles teszt: elotte=" + elotteKilotheto + ", utan=" + utanaKilotheto);
    }

    private static void testSimaSporaSzoras(String[] parameterek) {
        // várunk három elemet: 0=parancs, 1=gombatestId, 2=celTektonId
        if (parameterVizsgalat(parameterek, 3)) return;

        int gtId;
        int celId;
        try {
            gtId  = Integer.parseInt(parameterek[1]);
            celId = Integer.parseInt(parameterek[2]);
        } catch (NumberFormatException e) {
            hibaLog("Érvénytelen ID: " + parameterek[1] + " vagy " + parameterek[2]);
            return;
        }

        // 2) Gombatest és cél‐tekton lekérdezése a Játékból
        Gombatest gt  = jatek.keresGombatestById(gtId);
        Tekton    cel = jatek.keresTektonById(celId);
        if (gt == null || cel == null) {
            hibaLog("Nincs ilyen gombatest vagy tekton ID-val: " + gtId + ", " + celId);
            return;
        }

        // 3) Hívjuk meg a sporaKiloves metódust
        gt.sporaKiloves(cel, 1);  // az 1 itt a kilövendő spórák száma
    }

    private static void testFejlettSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return;

        // pl. parse-olod a gombatest-id-t
        int gtId = Integer.parseInt(parameterek[1]);
        Gombatest eredeti = jatek.keresGombatestById(gtId);
        if (eredeti == null) {
            hibaLog("Nincs ilyen Gombatest ID-val: " + gtId);
            return;
        }

        // csak így hívható meg a konstruktor
        FejlettGombatest fejlett = new FejlettGombatest(eredeti);

        // meg a sporaKiloves metódus is így
        int mennyiseg = Integer.parseInt(parameterek[2]);
        Tekton cel = jatek.keresTektonById( Integer.parseInt(parameterek[2]) );
        fejlett.sporaKiloves(cel, mennyiseg);
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

        // 1) Létrehozunk egy tekton-t és ráteszünk egy sima Gombatestet
        EgyFonalasTekton tekton = new EgyFonalasTekton();
        Gombasz gombasz = new Gombasz("teszt");
        Gombatest alapGt = new Gombatest(tekton, gombasz);
        tekton.setGombatest(alapGt);

        // 2) Ellenőrizzük, fejleszthető-e
        if (tekton.gombatestFejleszthetoE()) {
            // 3) Ha igen, ebből a gombatestből építjük a FejlettGombatestet
            FejlettGombatest fejlettGt = new FejlettGombatest(alapGt);
            tekton.setGombatest(fejlettGt);
            log("Gombatest fejlesztese sikeres.");
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
        if (parameterVizsgalat(parameterek, 3)) return;

        // 1) Létrehozunk egy tekton-t és rovaraszt
        Tekton t = new EgyFonalasTekton();
        Rovarasz rz = new Rovarasz("teszt");

        // 2) Így kell példányosítani a rovart
        Rovar rovar = new Rovar(t, rz);
        // és regisztrálni a modelben:
        t.getRovarok().add(rovar);
        rz.getRovarok().add(rovar);

        // 3) Mozgatjuk
        rovar.lepes( /* ide a cél-Tekton példány */ t );
    }

    private static void testSporaFogyasztas(String[] parameterek) {
        parameterVizsgalat(parameterek, 3);
        Rovar rovar = new Rovar();
        rovar.sporaElfogyasztas(new EgyFonalasTekton());
    }

    private static void testFonalVagas(String[] parameterek) {
        parameterVizsgalat(parameterek, 3);
        Rovar rovar = new Rovar();
        rovar.fonalVagas(new EgyFonalasTekton());
    }

    private static void testTektonTores(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        EgyFonalasTekton tekton = new EgyFonalasTekton();
        tekton.ketteTores();
    }

    private static void testBenitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        BenitoSpora benitoSpora = new BenitoSpora();
        benitoSpora.hatasKifejtese(rovar);
    }

    private static void testLassitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        LassitoSpora lassitoSpora = new LassitoSpora();
        lassitoSpora.hatasKifejtese(rovar);
    }

    private static void testGyorsitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        GyorsitoSpora gyorsitoSpora = new GyorsitoSpora();
        gyorsitoSpora.hatasKifejtese(rovar);
    }

    private static void testVagasBenitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        VagasTiltoSpora vagasTiltoSpora = new VagasTiltoSpora();
        vagasTiltoSpora.hatasKifejtese(rovar);

    }

    private static void testSimaSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        SimaSpora simaSpora = new SimaSpora();
        simaSpora.hatasKifejtese(rovar);
    }

    private static void testFonalFelszivodas(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 1)) return;

        EltunoFonalasTekton tekton = new EltunoFonalasTekton();
        // kérheted, hogy legyen rajta egy fonal is, ha az a cél
        Tekton masik = new EltunoFonalasTekton();
        Gombatest gt = new Gombatest(tekton, new Gombasz("dummy"));
        GombaFonal gf = new GombaFonal(tekton, masik, gt);
        tekton.setGombafonal(gf);

        // élettartam csökkentése, így eltűnnek a fonalak
        tekton.korFrissites();
        // esetleg többször, ha a fonalakElettartama >1

        log("Fonal felszívódás teszt lefutott");
    }


}