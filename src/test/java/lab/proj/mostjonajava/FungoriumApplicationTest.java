package lab.proj.mostjonajava;

import lab.proj.mostjonajava.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        gt.setElszortSporakSzama(2);

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
        assertEquals(2, t1.getGombatest().getElszortSporakSzama());

        Tekton t2 = palya1.get(1);
        assertEquals(3, t2.getSporak().size());

        Tekton t3 = palya1.get(2);
        assertNull(t3.getGombatest());
        assertTrue(t3.getRovarok().isEmpty());
        assertTrue(t3.getSporak().isEmpty());

        // konzolra írás
        System.out.println("=== PÁLYA1 ÁLLAPOTA ===");
        palya1.forEach(tp -> System.out.println(tp));
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

        // ─── IDE KELL HOZZÁADNI! ───
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);
        // ────────────────────────────

        Gombasz gombasz = new Gombasz("G2");
        Gombatest gt = new Gombatest(t1, gombasz);
        t1.setGombatest(gt);

        return List.of(t1, t2);
    }

    @Test
    void tesztPalya2Init() {
        List<Tekton> palya2 = letrehozPalya2();

        // pont 2 tekton
        assertEquals(2, palya2.size(), "palya2-nek 2 tektonból kell állnia");

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

        // opcionális: konzolra is kiírhatjuk
        System.out.println("=== PÁLYA2 ÁLLAPOTA ===");
        palya2.forEach(System.out::println);
    }

    @Test
    void tesztJatekinditasEsAllapotParancs() throws Exception {
        // 1) Be- és kimenet előkészítése
        String bemenet = String.join("\n",
                "JATEKINDITAS 2 JANI,EVI",
                "ALLAPOT",
                "KILEPES"
        ) + "\n";

        InputStream eredetiIn = System.in;
        PrintStream eredetiOut = System.out;

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
        System.out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(System.out::println);

        // 3) Fonal növesztés
        System.out.println("FONALNOVESZTES");
        gt.fonalNovesztes(t1, t2);

        // 4) Ellenőrzés: egy fonal került be t1-ből t2-be
        assertEquals(1, t1.getGombafonalak().size(), "t1-nek egy fonala kell legyen");
        assertEquals(1, t2.getGombafonalak().size(), "t2-nek egy fonala kell legyen");
        boolean talalt = t1.getGombafonalak().stream()
                .anyMatch(f -> f.getHonnan() == t1 && f.getHova() == t2);
        assertTrue(talalt, "A fonalnak pontosan t1→t2-nek kell lennie");

        // 5) Végállapot kiírása (opcionális)
        System.out.println("=== PALYA2 VEGE ===");
        palya2.forEach(System.out::println);
    }
}
