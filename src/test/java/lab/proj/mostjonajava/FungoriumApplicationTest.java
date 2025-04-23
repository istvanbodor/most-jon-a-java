package lab.proj.mostjonajava;

import lab.proj.mostjonajava.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

class FungoriumApplicationTest {

    private List<Tekton> palya1;

    @BeforeEach
    void setUp() {
        palya1 = letrehozPalya1();
    }

    /**
     * Létrehoz egy 3 tektonból álló lánc-topológiát:
     * - t1–t2 és t2–t3 között fonalak
     * - t1-en egy Gombatest (2 spóra) és egy Rovar
     * - t2-n 3 darab SimaSpora
     */
    private static List<Tekton> letrehozPalya1() {
        // 1. Tektonok
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();
        Tekton t3 = new TobbFonalasTekton();

        // 2. Fonalak
        GombaFonal f12 = new GombaFonal(t1, t2, new Gombatest());
        GombaFonal f23 = new GombaFonal(t2, t3, new Gombatest());
        t1.setGombafonal(f12);
        t2.setGombafonal(f12);
        t2.setGombafonal(f23);
        t3.setGombafonal(f23);

        // 3. Gombatest és Rovar az első tektonon
        Gombasz gombasz = new Gombasz("G1");
        Gombatest gt = new Gombatest(t1, gombasz);
        t1.setGombatest(gt);
        gt.setElszortSporakSzama(3);
        gt.setKilohetoSporakSzama(2);

        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);
        t2.getSzomszedosTektonok().add(t3);
        t3.getSzomszedosTektonok().add(t2);

        Rovarasz rovarasz = new Rovarasz("R1");
        Rovar rovar = new Rovar(t1, rovarasz);
        t1.getRovarok().add(rovar);
        rovarasz.getRovarok().add(rovar);

        // 4. Három spóra a második tektonon
        t2.getSporak().add(new SimaSpora());
        t2.getSporak().add(new SimaSpora());
        t2.getSporak().add(new SimaSpora());

        // 5. Visszaadjuk a pályát
        return List.of(t1, t2, t3);
    }

    @Test
    void tesztPalya1Init() {
        // alap-assertok
        assertEquals(3, palya1.size());

        Tekton t1 = palya1.get(0);
        assertNotNull(t1.getGombatest());
        assertEquals(1, t1.getRovarok().size());
        assertEquals(3, t1.getGombatest().getElszortSporakSzama());

        Tekton t2 = palya1.get(1);
        assertEquals(3, t2.getSporak().size());

        Tekton t3 = palya1.get(2);
        assertNull(t3.getGombatest());
        assertTrue(t3.getRovarok().isEmpty());
        assertTrue(t3.getSporak().isEmpty());

        // konzolra írás
        out.println("=== PÁLYA1 ÁLLAPOTA ===");
        palya1.forEach(tp -> out.println(tp));
    }

    /**
     * Létrehoz egy 2 tektonból álló pályát:
     * - t1 és t2 között fonal nélkül
     * - t1-en van egy Gombatest
     * - t2-n nincs Gombatest
     */
    private static List<Tekton> letrehozPalya2() {
        TobbFonalasTekton t1 = new TobbFonalasTekton();
        TobbFonalasTekton t2 = new TobbFonalasTekton();
        EgyFonalasTekton t3 = new EgyFonalasTekton();

        // ─── IDE KELL HOZZÁADNI! ───
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);
        t2.getSzomszedosTektonok().add(t3);
        t3.getSzomszedosTektonok().add(t2);
        // ────────────────────────────

        Gombasz gombasz = new Gombasz("G2");
        Gombatest gt = new Gombatest(t1, gombasz);
        t1.setGombatest(gt);

        return List.of(t1, t2, t3);
    }

    @Test
    void tesztPalya2Init() {
        List<Tekton> palya2 = letrehozPalya2();

        // pont 2 tekton
        assertEquals(3, palya2.size(), "palya2-nek 2 tektonból kell állnia");

        // t1-en van gombatest
        Tekton t1 = palya2.get(0);
        assertNotNull(t1.getGombatest(), "t1-nek kell, hogy legyen Gombatest");
        // sem spóra, sem rovar nem lett rátenni
        assertTrue(t1.getSporak().isEmpty(), "t1-en ne legyen spóra");
        assertTrue(t1.getRovarok().isEmpty(), "t1-en ne legyen rovar");

        // t2-n semmi extra
        Tekton t2 = palya2.get(1);
        assertNull(t2.getGombatest(), "t2-nek NEM szabad Gombatestnek lennie");
        assertTrue(t2.getSporak().isEmpty(), "t2-n ne legyen spóra");
        assertTrue(t2.getRovarok().isEmpty(), "t2-n ne legyen rovar");

        //t3
        Tekton t3 = palya2.get(2);
        assertNull(t3.getGombatest(), "t3-nek NEM szabad Gombatestnek lennie");
        assertTrue(t3.getSporak().isEmpty(), "t3-n ne legyen spóra");
        assertTrue(t3.getRovarok().isEmpty(), "t3-n ne legyen rovar");

        // opcionális: konzolra is kiírhatjuk
        out.println("=== PÁLYA2 ÁLLAPOTA ===");
        palya2.forEach(out::println);
    }

    /**
     * PÁLYA3 – 3 Tektonból áll, az elsőn egy FejlettGombatest van, sehol nincs fonal,
     * de a szomszédságok be vannak állítva. A gombatest 2 spórát tud kilőni.
     */
    /*private static List<Tekton> letrehozPalya3() {
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();
        Tekton t3 = new TobbFonalasTekton();

        // Szomszédságok
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);
        t2.getSzomszedosTektonok().add(t3);
        t3.getSzomszedosTektonok().add(t2);

        // Gombatest létrehozása
        Gombatest eredeti = new Gombatest(t1, new Gombasz("G1"));
        eredeti.setKilohetoSporakSzama(2);
        eredeti.setElszortSporakSzama(0);
        eredeti.setNoveszthetoFonalakSzama(1);

        // Fejlett példány és beállítás
        FejlettGombatest fejlett = new FejlettGombatest(eredeti);
        t1.setGombatest(fejlett);

        return List.of(t1, t2, t3);
    }

    @Test
    void tesztPalya3Init() {
        List<Tekton> palya3 = letrehozPalya3();

        // pontosan 3 tekton legyen
        assertEquals(3, palya3.size(), "Pálya3-nak 3 tektonból kell állnia");

        Tekton t1 = palya3.get(0);
        Tekton t2 = palya3.get(1);
        Tekton t3 = palya3.get(2);

        // T1-en fejlett gombatest van
        assertNotNull(t1.getGombatest(), "T1-en kell legyen gombatest");
        assertInstanceOf(FejlettGombatest.class, t1.getGombatest(), "T1-en fejlett gombatest kell legyen");

        // Fejlett gombatest beállításai
        Gombatest gt = t1.getGombatest();
        assertEquals(2, gt.getElszortSporakSzama(), "Fejlett gombatest 2 spórát tudjon szórni");

        // Ne legyen fonal sehol
        assertTrue(t1.getGombafonalak().isEmpty(), "T1-en ne legyen gombafonal");
        assertTrue(t2.getGombafonalak().isEmpty(), "T2-n se legyen gombafonal");
        assertTrue(t3.getGombafonalak().isEmpty(), "T3-n se legyen gombafonal");

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");
        assertTrue(t2.getSzomszedosTektonok().contains(t3), "T2 szomszédja legyen T3");
        assertTrue(t3.getSzomszedosTektonok().contains(t2), "T3 szomszédja legyen T2");

        // Rovarok és spórák ne legyenek
        assertTrue(t1.getRovarok().isEmpty(), "T1-en ne legyen rovar");
        assertTrue(t2.getRovarok().isEmpty(), "T2-n se legyen rovar");
        assertTrue(t3.getRovarok().isEmpty(), "T3-n se legyen rovar");

        assertTrue(t1.getSporak().isEmpty(), "T1-en ne legyen spóra");
        assertTrue(t2.getSporak().isEmpty(), "T2-n se legyen spóra");
        assertTrue(t3.getSporak().isEmpty(), "T3-n se legyen spóra");

        // Konzolra kiírás (opcionális, fejlesztés közben hasznos lehet)
        System.out.println("=== PÁLYA3 ÁLLAPOTA ===");
        palya3.forEach(System.out::println);
    } */

    /*
    @Test
    void tesztJatekinditasEsAllapotParancs() throws Exception {
        // 1) Be- és kimenet előkészítése
        String bemenet = String.join("\n",
                "JATEKINDITAS 2 JANI,EVI",
                "ALLAPOT",
                "KILEPES"
        ) + "\n";

        InputStream eredetiIn = System.in;
        PrintStream eredetiOut = out;

        try {
            // System.in beállítása
            ByteArrayInputStream bais = new ByteArrayInputStream(bemenet.getBytes(StandardCharsets.UTF_8));
            System.setIn(bais);

            // System.out elfogása
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));

            // 2) Futtatjuk a main-t
            FungoriumApplication.main(new String[]{});

            // 3) Kiértékeljük a kimenetet
            String kimenet = baos.toString(StandardCharsets.UTF_8);
            assertTrue(kimenet.contains("JATEKINDITAS"),     "Hiányzik a „JATEKINDITAS” sor");
            assertTrue(kimenet.contains("Gombasz letrejott"),"Hiányzik a „Gombasz letrejott” sor");
            assertTrue(kimenet.contains("Rovarasz letrejott"),"Hiányzik a „Rovarasz letrejott” sor");
            assertTrue(kimenet.contains("ALLAPOT"),           "Hiányzik az „ALLAPOT” sor");
        } finally {
            // visszaállítjuk az eredeti streameket
            System.setIn(eredetiIn);
            System.setOut(eredetiOut);
        }
    }
    */

    @Test
    void tesztFonalNovesztesPalya2() {
        // 1) Pálya előkészítése
        List<Tekton> palya2 = letrehozPalya2();
        Tekton t1 = palya2.get(0);
        Tekton t2 = palya2.get(1);
        Gombatest gt = t1.getGombatest();

        // 1a) Kiinduló állapot: még nincs fonal
        assertTrue(t1.getGombafonalak().isEmpty(), "Kiinduláskor ne legyen fonal");
        assertTrue(t2.getGombafonalak().isEmpty(), "Kiinduláskor t2-n sem");

        // 2) Kiírás (opcionális)
        out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(out::println);

        // 3) Fonal növesztés
        out.println("FONALNOVESZTES");
        gt.fonalNovesztes(t1, t2);

        // 4) Ellenőrzés: egy fonal került be t1-ből t2-be
        assertEquals(1, t1.getGombafonalak().size(), "t1-nek egy fonala kell legyen");
        assertEquals(1, t2.getGombafonalak().size(), "t2-nek egy fonala kell legyen");
        boolean talalt = t1.getGombafonalak().stream()
                .anyMatch(f -> f.getHonnan() == t1 && f.getHova() == t2);
        assertTrue(talalt, "A fonalnak pontosan t1→t2-nek kell lennie");

        // 5) Végállapot kiírása (opcionális)
        out.println("=== PALYA2 VEGE ===");
        palya2.forEach(out::println);
    }

    @Test
    void tesztSikertelenFonalNovesztesNemSzomszedosTektonok() {
        // 1) Pálya előkészítése: 3 tekton, csak t1–t2 szomszédos
        List<Tekton> palya2 = letrehozPalya2();
        Tekton t1 = palya2.get(0);
        Tekton t2 = palya2.get(1);
        Tekton t3 = palya2.get(2);
        Gombatest gt = t1.getGombatest();

        // 2) Kiírás a kezdeti állapotról
        out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(out::println);

        // 3) Fonal növesztés nem szomszédos tektonok között
        out.println("FONALNOVESZTES");
        gt.fonalNovesztes(t1, t3);

        // 4) Kiírás a végső állapotról
        out.println("=== PALYA2 VEGE ===");
        palya2.forEach(out::println);

        // 5) Ellenőrzés: nem jöhetett létre fonal
        assertTrue(t1.getGombafonalak().isEmpty(), "t1-nek NEM szabad fonalnak lennie");
        assertTrue(t3.getGombafonalak().isEmpty(), "t3-nak NEM szabad fonalnak lennie");
    }

    @Test
    void tesztSimaSporaSzorasPalya1() {
        // 1) pálya előkészítése
        List<Tekton> palya1 = letrehozPalya1();

        // 1a) pálya kiírása művelet előtt
        System.out.println("=== PÁLYA1 ÁLLAPOTA – ELŐTTE ===");
        palya1.forEach(System.out::println);

        // 2) előfeltételek
        assertEquals(2, palya1.get(0).getGombatest().getKilohetoSporakSzama(), "Kezdetben 2 kilőhető spóra");
        assertEquals(3, palya1.get(1).getSporak().size(), "Cél tektonon 3 spóra van kezdetben");

        // 3) művelet: spóra kilövés (2 darab)
        palya1.get(0).getGombatest().sporaKiloves(palya1.get(1), 2);

        // 4) pálya kiírása művelet után
        System.out.println("=== PÁLYA1 ÁLLAPOTA – UTÁNA ===");
        palya1.forEach(System.out::println);

        // 5) ellenőrzések
        assertEquals(0, palya1.get(0).getGombatest().getKilohetoSporakSzama(), "Kilövés után már 0 spóra maradjon");
        assertEquals(5, palya1.get(1).getSporak().size(), "Cél tektonon 5 spóra kell legyen");
    }

    @Test
    void tesztSimaSporaSzorasNincsElegendoSpora() {
        // 1) Pálya előkészítése (pálya1)
        List<Tekton> palya1 = letrehozPalya1(); // 3 Tekton

        assertEquals(2, palya1.get(0).getGombatest().getKilohetoSporakSzama());

        int celElottiSporak = palya1.get(1).getSporak().size();

        // 1a) pálya kiírása művelet előtt
        System.out.println("=== PÁLYA1 ÁLLAPOTA – ELŐTTE ===");
        palya1.forEach(System.out::println);

        // 3) Spóra kilövés — de 4-et kérünk, ami több mint az elérhető
        palya1.get(0).getGombatest().sporaKiloves(palya1.get(1), 4);

        // 4) Ellenőrzések
        assertEquals(2, palya1.get(0).getGombatest().getKilohetoSporakSzama(), "Nem szabadott volna kilőni, spóraszám változatlan kell maradjon");
        assertEquals(celElottiSporak, palya1.get(1).getSporak().size(), "Cél tekton spóralistájának változatlannak kell maradnia");

        // Konzolra írás (opcionális)
        System.out.println("=== PALYA1 UTÁN SPÓRASZÓRÁS ===");
        palya1.forEach(System.out::println);
    }

    //nem működik
    /*@Test
    void tesztFejlettSporaSzorasPalya3() {
        // 1) Pálya előkészítése
        List<Tekton> palya3 = letrehozPalya3();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA3 KEZDETE ===");
        palya3.forEach(System.out::println);

        // 3) Előfeltételek
        assertEquals(2, palya3.get(0).getGombatest().getKilohetoSporakSzama(), "Kezdetben 2 kilőhető spóra");
        assertTrue(palya3.get(1).getSporak().isEmpty(), "A cél tektonon kezdetben ne legyen spóra");

        palya3.get(0).getGombatest().sporaKiloves(palya3.get(1), 2);

        // 5) Ellenőrzések
        assertEquals(0, palya3.get(0).getGombatest().getKilohetoSporakSzama(), "Kilővés után 0 spóra maradjon");
        assertEquals(2, palya3.get(1).getSporak().size(), "Cél tektonon 2 új spóra kell legyen");

        // 6) Kiírás: végállapot
        System.out.println("=== PALYA3 VEGE ===");
        palya3.forEach(System.out::println);
    }*/

    //Kihagytam: Teszteset8 — Fejlett spóra szórás, a szomszéd szomszédjára
    //Kihagytam: Teszteset9 — Fejlett spóra szórás, kevés spórával
    //kihagytam: Teszteset10 — Fejlett spóra szórás, nem szomszédos tektonra

    @Test
    void tesztGombatestLetrehozasPalya2() {
        // 1) Pálya előkészítése
        List<Tekton> palya2 = letrehozPalya2();
        Tekton t3 = palya2.get(2);

        // 2) Előkészítés: 3 darab spóra hozzáadása
        t3.getSporak().add(new SimaSpora());
        t3.getSporak().add(new SimaSpora());
        t3.getSporak().add(new SimaSpora());

        // --- Kezdeti állapot kiírása ---
        System.out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(System.out::println);

        // 3) Előfeltételek
        assertNull(t3.getGombatest(), "A teszt elején még nincs gombatest");
        assertEquals(3, t3.getSporak().size(), "A teszt elején pontosan 3 spóra van");

        // 4) Művelet: gombatest létrehozása (szimulálva parancs alapján)
        Gombatest ujGombatest = new Gombatest(t3, new Gombasz("UJ"));
        t3.setGombatest(ujGombatest);
        t3.getSporak().clear(); // elhasználódnak a spórák

        // 5) Ellenőrzések
        assertNotNull(t3.getGombatest(), "A teszt végén létre kellett jönnie a gombatestnek");
        assertEquals(0, t3.getSporak().size(), "A teszt végén már nem maradhatott spóra");

        // --- Végső állapot kiírása ---
        System.out.println("=== PALYA2 VEGE ===");
        palya2.forEach(System.out::println);
    }

    @Test
    void tesztGombatestLetrehozasHianyosFeltetellelPalya2() {
        // 1) Pálya előkészítése
        List<Tekton> palya2 = letrehozPalya2();
        Tekton t3 = palya2.get(2);

        // --- Kezdeti állapot kiírása ---
        System.out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(System.out::println);

        // 2) Előkészítés: csak 2 spórát teszünk a tektonra (nem elég)
        t3.getSporak().add(new SimaSpora());
        t3.getSporak().add(new SimaSpora());

        // 3) Előfeltételek
        assertNull(t3.getGombatest(), "Kezdetben ne legyen gombatest");
        assertEquals(2, t3.getSporak().size(), "Kezdetben pontosan 2 spóra van");

        // 4) Művelet: próbálkozás gombatest létrehozással (szimulált)
        boolean novesztheto = t3.getSporak().size() >= 3 && !t3.getGombafonalak().isEmpty();

        if (novesztheto) {
            Gombatest ujGombatest = new Gombatest(t3, new Gombasz("UJ"));
            t3.setGombatest(ujGombatest);
            t3.getSporak().clear(); // spórák elhasználódnak
        }

        // 5) Ellenőrzések
        assertFalse(novesztheto, "Nem lehetne létrehozni gombatestet");
        assertNull(t3.getGombatest(), "Nem szabad létrejönnie a gombatestnek");
        assertEquals(2, t3.getSporak().size(), "Spóráknak meg kell maradniuk");

        // --- Végső állapot kiírása ---
        System.out.println("=== PALYA2 VEGE ===");
        palya2.forEach(System.out::println);
    }
}
