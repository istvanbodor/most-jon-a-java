package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.FungoriumApplication;
import lab.proj.mostjonajava.model.*;
import lombok.Data;

import java.io.*;
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
            }
            else if (parancs.equals(VISSZA))
            {
                return;
            }
            else {
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
                    allapot();
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
                    allapot();
                    elvalasztas();
                }
            }
        }

    }

    private static void elvalasztas() {
        log("--------------------------------------------");
    }

    private static void tesztVezerlo() throws IOException {
        while (true) {
            log("Melyik tesztesetet szeretned futtatni? (1-31) : ");
            String bemenet = streamReader.readLine();
            if (Objects.equals(bemenet, VISSZA)) {
                return;
            }
            try (InputStream is = FungoriumApplication.class.getClassLoader().getResourceAsStream("tests/test-" + bemenet + ".txt");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                   tesztParancsErtelmezo(line);
                }
            } catch (Exception e) {
                hibaLog("Nincs ilyet teszteset (lehetosegek: (1-31))");
            }
        }
    }

    private static void tesztParancsErtelmezo(String bemenet) {
        String[] parameterek = bemenet.split(" ");
        String parancs = parameterek[0];
        switch (parancs) {
            case JATEKINDITAS -> jatekInditasa(parameterek);
            case JATEKEPITES -> jatekEpites(parameterek);
            //kesz
            case ALLAPOT -> allapot();
            //kesz
            case FONALNOVESZTES -> fonalNovesztes(parameterek);
            case SIMASPORASZORAS -> simaSporaSzoras(parameterek);
            case FEJLETTSPORASZORAS -> fejlettSporaSzoras(parameterek);
            case GOMBATESTNOVESZTES -> gombaTestNovesztes(parameterek);
            case GOMBATESTFEJLESZTES -> gombaTestFejlesztes(parameterek);
            case GOMBATESTELPUSZTUL -> gombaTestElpusztul(parameterek);
            //kesz
            case ROVARMOZGATAS -> rovarMozgatas(parameterek);
            //kesz
            case SPORAFOGYASZTAS -> sporaFogyasztas(parameterek);
            //kesz
            case FONALVAGAS -> fonalVagas(parameterek);
            //kesz
            case TEKTONTORES -> tektonTores(parameterek);
            //kesz
            case BENITOSPORAHATASKIFEJTESE -> benitoSporaHatasKifejtese(parameterek);
            case LASSITOSPORAHATASKIFEJTESE -> lassitoSporaHatasKifejtese(parameterek);
            case GYORSITOSPORAHATASKIFEJTESE -> gyorsitoSporaHatasKifejtese(parameterek);
            case VAGASBENITOSPORAHATASKIFEJTESE -> vagasBenitoSporaHatasKifejtese(parameterek);
            case SIMASPORAHATASKIFEJTESE -> simaSporaHatasKifejtese(parameterek);
            case FONALFELSZIVODAS -> fonalFelszivodas(parameterek);
            case OSZTODOSPORAHATASKIFEJTESE -> osztodoSporaHatasKifejtese(parameterek);
            case ROVARELFOGYASZTASA -> rovarElfogyasztasa(parameterek);
            default -> hibaLog("hiba tortent a " + parancs + " parancs feldolgozasakor");
        }
    }

    private static void rovarElfogyasztasa(String[] parameterek) {
    }

    private static void osztodoSporaHatasKifejtese(String[] parameterek) {
    }

    private static void jatekEpites(String[] parameterek) {
    }

    private static boolean gombaszParancsErtelmezo(String bemenet) {
        boolean lepesMegtortent = false;
        String[] parameterek = bemenet.split(" ");
        String parancs = parameterek[0];
        switch (parancs) {
            case FONALNOVESZTES -> lepesMegtortent = fonalNovesztes(parameterek);
            case SIMASPORASZORAS -> lepesMegtortent = simaSporaSzoras(parameterek);
            case FEJLETTSPORASZORAS -> lepesMegtortent = fejlettSporaSzoras(parameterek);
            case GOMBATESTNOVESZTES -> lepesMegtortent = gombaTestNovesztes(parameterek);
            case GOMBATESTFEJLESZTES -> lepesMegtortent = gombaTestFejlesztes(parameterek);
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
            case ROVARMOZGATAS -> lepesMegtortent = rovarMozgatas(parameterek);
            case FONALVAGAS -> lepesMegtortent = fonalVagas(parameterek);
            case UGRAS -> lepesMegtortent = true;
            default -> hibaLog("Csak rovarasz parancsok engedelyezettek, probald ujra");
        }
        return lepesMegtortent;
    }


    private static void allapot() {
        if (JATEKMENET_AKTIV) {
            log(jatek.toString());
        }
        else {
            log("Nincs aktiv jatekmenet");
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

    private static boolean fonalNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int honnanTektonId = Integer.parseInt(parameterek[2]);
        int hovaTektonId = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).fonalNovesztes(jatek.keresTektonById(honnanTektonId), jatek.keresTektonById(hovaTektonId));
        return true;
    }
    private static boolean simaSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        int mennyieg = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).sporaKiloves(jatek.keresTektonById(tektonId), mennyieg);
        return true;
    }

    private static boolean fejlettSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        int mennyieg = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).sporaKiloves(jatek.keresTektonById(tektonId), mennyieg);
        return true;
    }

    //!!!valtozas:GOMBATESTNOVESZTES <gombaszId><tektonId>
    private static boolean gombaTestNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek,3)) return false;
        int gombaszId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[1]);
        jatek.getGombaszok().get(gombaszId-1).gombaTestNovesztes(jatek.keresTektonById(tektonId));
        return true;
    }

    //!!!valtozas: GOMBATESTFEJLESZTES <gombaszId><tektonId>
    private static boolean gombaTestFejlesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek,3)) return false;
        int gombaszId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        jatek.getGombaszok().get(gombaszId-1).gombaTestFejlesztes(jatek.keresTektonById(tektonId).getGombatest());
        return true;
    }

    private static boolean gombaTestElpusztul(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 2)) return false;

        int gombatestId = Integer.parseInt(parameterek[1]);

        jatek.keresGombatestById(gombatestId).setKilohetoSporakSzama(0);

        jatek.keresGombatestById(gombatestId).elpusztulas();

        return true;
    }

    private static boolean rovarMozgatas(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).lepes(jatek.keresTektonById(tektonId));
        return true;
    }

    private static boolean sporaFogyasztas(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).sporaElfogyasztas(jatek.keresTektonById(tektonId));

        return true;
    }

    private static boolean fonalVagas(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).fonalVagas(jatek.keresTektonById(tektonId));
        return true;
    }

    private static boolean tektonTores(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 2)) return false;

        int tektonId = Integer.parseInt(parameterek[1]);

        jatek.keresTektonById(tektonId).ketteTores();
        return true;
    }

    private static void benitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        BenitoSpora benitoSpora = new BenitoSpora();
        benitoSpora.hatasKifejtese(rovar);
    }

    private static void lassitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        LassitoSpora lassitoSpora = new LassitoSpora();
        lassitoSpora.hatasKifejtese(rovar);
    }

    private static void gyorsitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        GyorsitoSpora gyorsitoSpora = new GyorsitoSpora();
        gyorsitoSpora.hatasKifejtese(rovar);
    }

    private static void vagasBenitoSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        VagasTiltoSpora vagasTiltoSpora = new VagasTiltoSpora();
        vagasTiltoSpora.hatasKifejtese(rovar);

    }

    private static void simaSporaHatasKifejtese(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        Rovar rovar = new Rovar();
        SimaSpora simaSpora = new SimaSpora();
        simaSpora.hatasKifejtese(rovar);
    }

    private static void fonalFelszivodas(String[] parameterek) {
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
