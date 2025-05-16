package lab.proj.mostjonajava.game;

import lab.proj.mostjonajava.FungoriumApplication;
import lab.proj.mostjonajava.model.*;
import lombok.Data;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.hibaLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import static lab.proj.mostjonajava.utils.Parancsok.*;

@Data
public class JatekVezerlo {
    private static Jatek jatek;
    public static boolean JATEKMENET_AKTIV = false;
    private static int JATEKOSOK_SZAMA = 0;

    private static BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Konzolos fo menurendszer, ahol a felhasznalo valaszthat jatek inditas, teszt futtatas vagy kilepes kozott.
     * @throws IOException ha a bemenet olvasasa soran hiba lep fel
     */
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

    /**
     * A jatek inditasat es a korok lebonyolitasat vezerlo metodus. Kezeli a jatekosok lepeseit es a veletlenszeru torteneseket is.
     * @throws IOException ha bemeneti hiba tortenik
     */
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
        Random rnd = new Random();
        log("Sikeres jatek inditas - a jatek kezdo allapota:");
        for (int i = 0; i < Jatek.KOROK_SZAMA; i++) {
            allapot();
            elvalasztas();
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
                } else {
                    boolean lepesMegtortent = false;
                    String lepoJatekosNeve = jatek.getRovaraszok().get(rovaraszIndex).getNev();
                    while (!lepesMegtortent) {
                        log(korSzam + ". kor " + lepesSzam + ". lepes - " + lepoJatekosNeve + ":");
                        bemenet = streamReader.readLine();
                        lepesMegtortent = rovaraszParancsErtelmezo(bemenet);
                    }
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
            log("Jatek vege! Gyoztesek: ");
            log("Rovarasz: " +  jatek.getRovaraszok().stream()
                    .max(Comparator.comparingInt(Rovarasz::getPont)).get().getNev());
            log("Gombasz: " + jatek.getGombaszok().stream()
                    .max(Comparator.comparingInt(Gombasz::getPont)).get().getNev());
        }

    }

    /**
     * Elvalaszto vonal kiiratasa a konzolra a megjelenites segitesehez.
     */
    private static void elvalasztas() {
        log("--------------------------------------------");
    }

    /**
     * Tesztparancsok futtatasat vezerlo metodus. Tesztfajlokbol olvassa be a parancsokat.
     * @throws IOException ha fajl megnyitasa vagy olvasasa kozben hiba tortenik
     */
    private static void tesztVezerlo() throws IOException {
        while (true) {
            log("Melyik tesztesetet szeretned futtatni? (1-31) : ");
            String bemenet = streamReader.readLine();
            if (Objects.equals(bemenet, VISSZA)) {
                return;
            }
            log("bemenet:" + bemenet);
            try (InputStream is = FungoriumApplication.class.getClassLoader().getResourceAsStream("tests/test-" + bemenet + ".txt");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                   tesztParancsErtelmezo(line);
                }
                reader.close();
                JATEKMENET_AKTIV = false;
            } catch (Exception e) {
                hibaLog(e.toString());
                hibaLog("Nincs ilyet teszteset (lehetosegek: (1-31))");
            }
        }
    }

    /**
     * A megadott parancs alapjan vezerli a tesztparancs vegrehajtasat.
     * @param bemenet a parancs szoveges formaja
     */
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

    /**
     * Osztodo spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean osztodoSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(4).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Palya epitese valasztott palyaszam alapjan.
     * @param parameterek a palya epites parancs parameterei
     */
    private static void jatekEpites(String[] parameterek) {
        parameterVizsgalat(parameterek, 2);
        String palyaSzam = parameterek[1];
        switch (palyaSzam) {
            case "1" -> jatek = PalyaEpito.palya1();
            case "2" -> jatek = PalyaEpito.palya2();
            case "3" -> jatek = PalyaEpito.palya3();
            case "4" -> jatek = PalyaEpito.palya4();
            case "5" -> jatek = PalyaEpito.palya5();
            case "6" -> jatek = PalyaEpito.palya6();
            case "7" -> jatek = PalyaEpito.palya7();
            case "8" -> jatek = PalyaEpito.palya8();
            case "9" -> jatek = PalyaEpito.palya9();
            case "10" -> jatek = PalyaEpito.palya10();
            default -> hibaLog("Nem letezo palyat probalsz hasznalni");
        }
        if (jatek!= null){
            JATEKMENET_AKTIV = true;
        }
    }

    /**
     * Gombasz jatekos parancsanak ertelmezese.
     * @param bemenet a parancs szoveges formaja
     * @return igaz, ha a lepes sikeres volt
     */
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

    /**
     * Rovarasz jatekos parancsanak ertelmezese.
     * @param bemenet a parancs szoveges formaja
     * @return igaz, ha a lepes sikeres volt
     */
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

    /**
     * Kiirja az aktualis jatek allapotat.
     */
    private static void allapot() {
        if (JATEKMENET_AKTIV) {
            elvalasztas();
            log(jatek.toString());
            elvalasztas();
        }
        else {
            elvalasztas();
            log("Nincs aktiv jatekmenet");
            elvalasztas();
        }
    }

    /**
     * Elinditja a jatekot a megadott parameterlista alapjan.
     * @param parameterek parancs es parameterei
     */
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
        JATEKOSOK_SZAMA = jatekosokSzama;
    }

    private static boolean parameterVizsgalat(String[] parameterek, int elvartMeret) {
        if (parameterek.length < elvartMeret) {
            hibaLog("Nem adtal meg elegendo parametert!");
            return true;
        }
        return false;
    }

    /**
     * Gombafonal novesztes parancs feldolgozasa.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a parancs sikeresen vegrehajtodott
     */
    private static boolean fonalNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int honnanTektonId = Integer.parseInt(parameterek[2]);
        int hovaTektonId = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).fonalNovesztes(jatek.keresTektonById(honnanTektonId), jatek.keresTektonById(hovaTektonId));
        return true;
    }

    /**
     * Sima spora szoras feldolgozasa.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a parancs sikeres volt
     */
    private static boolean simaSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        int mennyieg = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).sporaKiloves(jatek.keresTektonById(tektonId), mennyieg);
        return true;
    }

    /**
     * Fejlett spora szoras feldolgozasa.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a parancs sikeresen vegrehajtodott
     */
    private static boolean fejlettSporaSzoras(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;
        int gombatestId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        int mennyieg = Integer.parseInt(parameterek[3]);
        jatek.keresGombatestById(gombatestId).sporaKiloves(jatek.keresTektonById(tektonId), mennyieg);
        return true;
    }

    /**
     * Gombatest novesztes egy adott tektonra.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a parancs sikeres volt
     */
    //!!!valtozas:GOMBATESTNOVESZTES <gombaszId><tektonId>
    private static boolean gombaTestNovesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek,3)) return false;
        int gombaszId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        jatek.getGombaszok().get(gombaszId-1).gombaTestNovesztes(jatek.keresTektonById(tektonId));
        return true;
    }

    /**
     * Gombatest fejlesztese adott tektonon.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a parancs sikeres volt
     */
    //!!!valtozas: GOMBATESTFEJLESZTES <gombaszId><tektonId>
    private static boolean gombaTestFejlesztes(String[] parameterek) {
        if (parameterVizsgalat(parameterek,3)) return false;
        int gombaszId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);
        Gombatest gombatest = jatek.keresTektonById(tektonId).getGombatest();
        jatek.getGombaszok().get(gombaszId-1).gombaTestFejlesztes(gombatest);
        return true;
    }

    /**
     * Egy gombatest elpusztitasa.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha sikerult elpusztitani
     */
    private static boolean gombaTestElpusztul(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 2)) return false;

        int gombatestId = Integer.parseInt(parameterek[1]);

        jatek.keresGombatestById(gombatestId).elpusztulas();

        return true;
    }

    /**
     * Rovar mozgatasa egy adott tektonra.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a mozgas sikeres volt
     */
    private static boolean rovarMozgatas(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).lepes(jatek.keresTektonById(tektonId));
        return true;
    }

    /**
     * Rovar sporat fogyaszt adott tektonon.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a fogyasztas sikeres volt
     */
    private static boolean sporaFogyasztas(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).sporaElfogyasztas(jatek.keresTektonById(tektonId));

        return true;
    }

    /**
     * Gombafonal vagasa adott rovar altal.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a vagas sikeres volt
     */
    private static boolean fonalVagas(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 3)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int tektonId = Integer.parseInt(parameterek[2]);

        jatek.keresRovarById(rovarId).fonalVagas(jatek.keresTektonById(tektonId));
        return true;
    }

    /**
     * Tekton kettetoresenek feldolgozasa.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha sikeres volt a kettetores
     */
    private static boolean tektonTores(String[] parameterek) {
        if(parameterVizsgalat(parameterek, 2)) return false;

        int tektonId = Integer.parseInt(parameterek[1]);

        List<Tekton> tektons = jatek.keresTektonById(tektonId).ketteTores();
        jatek.getTabla().remove(jatek.keresTektonById(tektonId));
        jatek.getTabla().addAll(tektons);
        return true;
    }

    /**
     * Benito spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean benitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(1).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Lassito spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean lassitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(3).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Gyorsito spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean gyorsitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(2).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Vagasbenito spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean vagasBenitoSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(5).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Sima spora hatasat fejti ki.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha a hatas sikeres volt
     */
    private static boolean simaSporaHatasKifejtese(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int rovarId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(rovarId).getSporak().get(0).hatasKifejtese(jatek.keresRovarById(rovarId));
        return true;
    }

    /**
     * Fonal felszivodasa adott tektonon.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha sikeres volt a felszivodas
     */
    private static boolean fonalFelszivodas(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 2)) return false;
        int honnanTektonId = Integer.parseInt(parameterek[1]);
        jatek.keresTektonById(honnanTektonId).korFrissites();
        return true;
    }

    /**
     * Rovar elfogyasztasa egy gombafonal altal.
     * @param parameterek a parancs parameterlistaja
     * @return true, ha sikeresen megtortent az elfogyasztas
     */
    private static boolean rovarElfogyasztasa(String[] parameterek) {
        if (parameterVizsgalat(parameterek, 4)) return false;

        int rovarId = Integer.parseInt(parameterek[1]);
        int honnanId = Integer.parseInt(parameterek[2]);
        int hovaId = Integer.parseInt(parameterek[3]);

        if(!jatek.keresTektonById(honnanId).vanFonalKozottuk(jatek.keresTektonById(hovaId))){
            return false;
        } else{
            for(int i = 0; i < jatek.keresTektonById(honnanId).getGombafonalak().size(); i++) {
                if(jatek.keresTektonById(honnanId).getGombafonalak().get(i).getHova().getId() == hovaId) {
                    jatek.keresTektonById(honnanId).getGombafonalak().get(i).rovarElfogyasztas(jatek.keresRovarById(rovarId));
                    return true;
                }
            }
        }
        return false;
    }

}
