package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.model.*;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import static lab.proj.mostjonajava.utils.Parancsok.*;

@Data
public class JatekVezerlo {
    private static Jatek jatek;
    public static boolean JATEKMENET_AKTIV = false;
    private static int JATEKOSOK_SZAMA = 0;

    private static BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));

    public JatekVezerlo() throws IOException {
        String bemenet;
        while (true) {
            log("Jatekhoz ird be: 1");
            log("Tesztek futtatasahoz ird be: 2");
            log("Kilepeshez ird be: KILEPES");
            bemenet = streamReader.readLine();
            switch (bemenet) {
                case "1" -> jatekVezerlo();
                case "2" -> tesztVezerlo();
                case KILEPES -> System.exit(0);
                default -> hibaLog("Nincs ilyen menupont, valassz az alabbiak kozul: (1, 2, KILEPES)");
            }
        }
    }

    private static void jatekVezerlo() throws IOException {
        String bemenet;
        while (!JATEKMENET_AKTIV) {
            log("Inditsd el a jatekot a JATEKINDITAS parancs segitsegevel, megfeleloen parameterezve azt");
            bemenet = streamReader.readLine();
            String[] parameterek = bemenet.split(" ");
            String parancs = parameterek[0];
            if (parancs.equals(JATEKINDITAS)) {
                jatekInditasa(parameterek);
            } else {
                hibaLog("Rossz parancsot adtal meg - add meg: (JATEKINDITAS <jatekosokSzama> <jatekosnevekVesszovelElvalasztva>)");
            }
        }

        log("Sikeres jatek inditas - a jatek kezdo allapota:");
        allapot();
        for (int i = 0; i < Jatek.KOROK_SZAMA; i++) {
            int korSzam = i + 1;
            int gombaszIndex = 0;
            int rovaraszIndex = 0;
            log(korSzam + ". kor");
            elvalasztas();
            for (int j = 0; j < JATEKOSOK_SZAMA; j++) {
                int lepesSzam = j + 1;
                if (j % 2 == 0) {
                    boolean lepesMegtortent = false;
                    String lepoJatekosNeve = jatek.getGombaszok().get(gombaszIndex).getNev();
                    while (!lepesMegtortent) {
                        log(korSzam + ". kor " + lepesSzam + ". lepes - " + lepoJatekosNeve + ":");
                        bemenet = streamReader.readLine();
                        lepesMegtortent = gombaszParancsErtelmezo(bemenet);
                    }
                    gombaszIndex++;
                    elvalasztas();
                } else {
                    boolean lepesMegtortent = false;
                    String lepoJatekosNeve = jatek.getRovaraszok().get(rovaraszIndex).getNev();
                    while (!lepesMegtortent) {
                        log(korSzam + ". kor " + lepesSzam + ". lepes - " + lepoJatekosNeve + ":");
                        bemenet = streamReader.readLine();
                        lepesMegtortent = rovaraszParancsErtelmezo(bemenet);
                    }
                    rovaraszIndex++;
                    elvalasztas();
                }
            }
        }

    }

    private static void elvalasztas() {
        log("--------------------------------------------");
    }

    private static void tesztVezerlo() {

    }

    private static boolean gombaszParancsErtelmezo(String bemenet) {
        boolean lepesMegtortent = false;
        String[] parameterek = bemenet.split(" ");
        String parancs = parameterek[0];
        switch (parancs) {
            case FONALNOVESZTES -> lepesMegtortent = testFonalNovesztes(parameterek);
            case SIMASPORASZORAS -> lepesMegtortent = testSimaSporaSzoras(parameterek);
            case FEJLETTSPORASZORAS -> lepesMegtortent = testFejlettSporaSzoras(parameterek);
            case GOMBATESTNOVESZTES -> lepesMegtortent = testGombaTestNovesztes(parameterek);
            case GOMBATESTFEJLESZTES -> lepesMegtortent = testGombaTestFejlesztes(parameterek);
            case UGRAS -> lepesMegtortent = true;
            default -> hibaLog("Csak gombasz parancsok engedelyezettek, probald ujra");
        }
        return lepesMegtortent;
    }

    private static boolean rovaraszParancsErtelmezo(String bemenet) {
        boolean lepesMegtortent = false;
        String[] parameterek = bemenet.split(" ");
        String parancs = parameterek[0];
        switch (parancs) {
            case ROVARMOZGATAS -> lepesMegtortent = testRovarMozgatas(parameterek);
            case FONALVAGAS -> lepesMegtortent = testFonalVagas(parameterek);
            case UGRAS -> lepesMegtortent = true;
            default -> hibaLog("Csak rovarasz parancsok engedelyezettek, probald ujra");
        }
        return lepesMegtortent;
    }

    public static void parancsErtelmezo(String bemenet) {
        String[] parameterek = bemenet.split(" ");
        String parancs = parameterek[0];
        boolean jatekVezerloParancsAktivJatekmenetHianyaban = !JATEKMENET_AKTIV && !Objects.equals(parancs, JATEKINDITAS) && !Objects.equals(parancs, BETOLTES);

        if (jatekVezerloParancsAktivJatekmenetHianyaban) {
            hibaLog("Nincs aktiv jatekmenet - Inditsd el eloszor a jatekot vagy tolts be egy tesztesetet");
        }

        if (Objects.equals(parancs, JATEKINDITAS)) {
            jatekInditasa(parameterek);
        }

        switch (parancs) {
            case MENTES -> mentes(parameterek);
            case BETOLTES -> betoltes(parameterek);
            case MAKE -> make(parameterek);
            case ALLAPOT -> allapot();
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

    private static void mentes(String[] parameterek) {
    }

    private static void betoltes(String[] parameterek) {
    }

    private static void make(String[] parameterek) {
    }

    private static void allapot() {
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
        JATEKMENET_AKTIV = true;
        JATEKOSOK_SZAMA = 2;
    }

    private static boolean parameterVizsgalat(String[] parameterek, int elvartMeret) {
        if (parameterek.length < elvartMeret) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return true;
        }
        return false;
    }

    private static boolean testFonalNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        return true;
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

    private static boolean testSimaSporaSzoras(String[] parameterek) {
        // várunk három elemet: 0=parancs, 1=gombatestId, 2=celTektonId
        if (parameterVizsgalat(parameterek, 3)) return false;

        int gtId;
        int celId;
        try {
            gtId = Integer.parseInt(parameterek[1]);
            celId = Integer.parseInt(parameterek[2]);
        } catch (NumberFormatException e) {
            hibaLog("Érvénytelen ID: " + parameterek[1] + " vagy " + parameterek[2]);
            return false;
        }

        // 2) Gombatest és cél‐tekton lekérdezése a Játékból
        Gombatest gt = jatek.keresGombatestById(gtId);
        Tekton cel = jatek.keresTektonById(celId);
        if (gt == null || cel == null) {
            hibaLog("Nincs ilyen gombatest vagy tekton ID-val: " + gtId + ", " + celId);
            return false;
        }

        // 3) Hívjuk meg a sporaKiloves metódust
        gt.sporaKiloves(cel, 1);  // az 1 itt a kilövendő spórák száma
        return false;
    }

    private static boolean testFejlettSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return false;

        // pl. parse-olod a gombatest-id-t
        int gtId = Integer.parseInt(parameterek[1]);
        Gombatest eredeti = jatek.keresGombatestById(gtId);
        if (eredeti == null) {
            hibaLog("Nincs ilyen Gombatest ID-val: " + gtId);
            return false;
        }

        // csak így hívható meg a konstruktor
        FejlettGombatest fejlett = new FejlettGombatest(eredeti);

        // meg a sporaKiloves metódus is így
        int mennyiseg = Integer.parseInt(parameterek[2]);
        Tekton cel = jatek.keresTektonById(Integer.parseInt(parameterek[2]));
        fejlett.sporaKiloves(cel, mennyiseg);
        return false;
    }

    private static boolean testGombaTestNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        EgyFonalasTekton tekton = new EgyFonalasTekton();
        if (tekton.gombatestNoveszthetoE()) {
            tekton.setGombatest(new Gombatest());
        } else {
            hibaLog("Ezen a tektonon nem novesztheto gombatest!");
        }
        return false;
    }

    private static boolean testGombaTestFejlesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;

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
        return false;
    }

    private static void testGombaTestElpusztul(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);

        Gombatest gombatest = new Gombatest();
        gombatest.setKilohetoSporakSzama(0);

        if (gombatest.getKilohetoSporakSzama() == 0) {
            gombatest.elpusztulas();
        } else {
            log("A gombatest meg nem all keszen a halalra");
        }

    }

    private static boolean testRovarMozgatas(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return false;

        // 1) Létrehozunk egy tekton-t és rovaraszt
        Tekton t = new EgyFonalasTekton();
        Rovarasz rz = new Rovarasz("teszt");

        // 2) Így kell példányosítani a rovart
        Rovar rovar = new Rovar(t, rz);
        // és regisztrálni a modelben:
        t.getRovarok().add(rovar);
        rz.getRovarok().add(rovar);

        // 3) Mozgatjuk
        rovar.lepes( /* ide a cél-Tekton példány */ t);
        return false;
    }

    private static void testSporaFogyasztas(String[] parameterek) {
        parameterVizsgalat(parameterek, 3);
        Rovar rovar = new Rovar();
        rovar.sporaElfogyasztas(new EgyFonalasTekton());
    }

    private static boolean testFonalVagas(String[] parameterek) {
        parameterVizsgalat(parameterek, 3);
        Rovar rovar = new Rovar();
        rovar.fonalVagas(new EgyFonalasTekton());
        return false;
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
