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
     * - van t3 is
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
        gt.setElszortSporakSzama(10);

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
        assertEquals(10, t1.getGombatest().getElszortSporakSzama(), "Fejlett gombatest 10 spórát szort el");
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
    private static List<Tekton> letrehozPalya3() {
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
        assertEquals(2, gt.getKilohetoSporakSzama(), "Fejlett gombatest 2 spórát tudjon szórni");

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
    }

    /**
     * 4 tektonból álló pálya:
     * - t1–t2 között fonal
     * - t1-en FEJLETT gombatest (1 kilőhető spóra)
     * - t2-on1 spóra
     * - t3-on 3 spóra
     * - t4-en sima gombatest (3 elszórt spóra)
     */
    private static List<Tekton> letrehozPalya4() {
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();
        Tekton t3 = new TobbFonalasTekton();
        Tekton t4 = new TobbFonalasTekton();

        // szomszédságok: t1–t2–t3–t4
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);
        t2.getSzomszedosTektonok().add(t3);
        t3.getSzomszedosTektonok().add(t2);
        t3.getSzomszedosTektonok().add(t4);
        t4.getSzomszedosTektonok().add(t3);

        // 1) FEJLETT gombatest T1-en, 1 kilőhető spóra
        Gombasz g1 = new Gombasz("G1");
        Gombatest eredetiGt1 = new Gombatest(t1, g1);
        eredetiGt1.setKilohetoSporakSzama(1);
        FejlettGombatest fgt1 = new FejlettGombatest(eredetiGt1);
        // közben a konstruktora beállítja: t1.setGombatest(this)

        // gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(t1, t2, fgt1);
        t1.getGombafonalak().add(f12);
        t2.getGombafonalak().add(f12);

        // 2) t3-ra 3 spóra
        t2.getSporak().add(new BenitoSpora());
        t3.getSporak().add(new GyorsitoSpora());
        t3.getSporak().add(new LassitoSpora());
        t3.getSporak().add(new OsztodoSpora());
        t4.getSporak().add(new GyorsitoSpora());
        t4.getSporak().add(new LassitoSpora());
        t4.getSporak().add(new OsztodoSpora());

        // 3) t4-re sima gombatest, 3 elszórt spóra
        Gombasz g4 = new Gombasz("G4");
        Gombatest gt4 = new Gombatest(t4, g4);
        t4.setGombatest(gt4);
        gt4.setElszortSporakSzama(3);

        return List.of(t1, t2, t3, t4);
    }

    @Test
    void tesztPalya4Init() {
        List<Tekton> palya4 = letrehozPalya4();
        assertEquals(4, palya4.size(), "Pálya4-nek 4 tektonból kell állnia");

        Tekton t1 = palya4.get(0), t2 = palya4.get(1), t3 = palya4.get(2), t4 = palya4.get(3);

        // 1) Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");
        assertTrue(t2.getSzomszedosTektonok().contains(t3), "T2 szomszédja legyen T3");
        assertTrue(t3.getSzomszedosTektonok().contains(t2), "T3 szomszédja legyen T2");
        assertTrue(t3.getSzomszedosTektonok().contains(t4), "T3 szomszédja legyen T4");
        assertTrue(t4.getSzomszedosTektonok().contains(t3), "T4 szomszédja legyen T3");

        // 2) Fonal-összeköttetések: csak t1–t2 között
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek 1 gombafonal legyen");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek 1 gombafonal legyen");
        assertTrue(t3.getGombafonalak().isEmpty(), "T3-on ne legyen gombafonal");
        assertTrue(t4.getGombafonalak().isEmpty(), "T4-en ne legyen gombafonal");

        // 3) Spórák: t2-en 1, t3-on 3, máshol nincs
        assertEquals(0, t1.getSporak().size(), "T1-en ne legyen spóra");
        assertEquals(1, t2.getSporak().size(), "T2-nek 1 spórája legyen");
        assertEquals(3, t3.getSporak().size(), "T3-nak 3 spórája legyen");
        assertEquals(3, t4.getSporak().size(), "T4-en 3 spóra");

        // 4) Gombatestek
        assertTrue(t1.getGombatest() instanceof FejlettGombatest, "T1-en fejlett gombatest legyen");
        FejlettGombatest fgt1 = (FejlettGombatest) t1.getGombatest();
        assertEquals(1, fgt1.getKilohetoSporakSzama(), "Fejlett gombatestnek 1 kilőhető spórája legyen");

        assertNotNull(t4.getGombatest(), "T4-en sima gombatest legyen");
        assertEquals(Gombatest.class, t4.getGombatest().getClass(), "T4-en tényleg sima Gombatest legyen");
        assertEquals(3, t4.getGombatest().getElszortSporakSzama(), "T4 gombatestének 3 elszórt spórája legyen");

        // 5) Konzolra íratás (opcionális)
        System.out.println("=== PÁLYA4 ÁLLAPOTA ===");
        palya4.forEach(System.out::println);
    }

    /**
     * 2 tektonból álló pálya:
     * - t1–t2 között fonal
     * - t1-en sima gombatest
     * - t2-n 1 sima spóra
     * - t1-en egy rovar, a vágóképessége nem aktív
     */
    private static List<Tekton> letrehozPalya5(){
        // Tektonok
        Tekton t1 = new EgyFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        // Szomszédságok: t1–t2
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombasz g1 = new Gombasz("G1");
        Gombatest Gt1 = new Gombatest(t1, g1);
        t1.setGombatest(Gt1);

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(t1, t2, Gt1);
        t1.getGombafonalak().add(f12);
        t2.getGombafonalak().add(f12);

        // t2-re 1 sima spóra
        t2.getSporak().add(new SimaSpora());

        // t1-re egy rovar
        Rovarasz r1 = new Rovarasz("R1");
        Rovar R1 = new Rovar(t1, r1);

        //Rovar vágóképessége nem aktív
        R1.setVagoKepesseg(false);

        // Pálya visszaadása
        return List.of(t1, t2);
    }

    @Test
    void tesztPalya5Init() {
        List<Tekton> palya5 = letrehozPalya5();
        assertEquals(2, palya5.size(), "Pálya5-nek 2 tektonból kell állnia");

        Tekton t1 = palya5.get(0), t2 = palya5.get(1);

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");

        // Gombafonal
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek 1 gombafonal legyen");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek 1 gombafonal legyen");

        // Gombatest
        assertNotNull(t1.getGombatest(), "T1-en legyen gombatest");
        assertEquals(Gombatest.class, t1.getGombatest().getClass(), "T1-en sima gombatest legyen");

        // Spórák
        assertEquals(0, t1.getSporak().size(), "T1-en ne legyen spóra");
        assertEquals(1, t2.getSporak().size(), "T2-nek 1 spórája legyen");
        assertTrue(t2.getSporak().get(0) instanceof SimaSpora, "T2-n sima spóra legyen");

        // Rovar
        assertEquals(1, t1.getRovarok().size(), "T1-en 1 rovar legyen");
        assertEquals("R1", t1.getRovarok().get(0).getRovarasz().getNev(), "T1-en a rovar neve 'R1' legyen");

        // Konzolra íratás
        System.out.println("=== PÁLYA5 ÁLLAPOTA ===");
        palya5.forEach(System.out::println);
    }

    /**
     * 2 tektonból álló pálya:
     * - t1–t2 között fonal
     * - t1-en sima gombatest
     * - t1-en egy rovar
     */
    private static List<Tekton> letrehozPalya6(){
        // Tektonok
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        // Szomszédságok: t1–t2
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombasz g1 = new Gombasz("G1");
        Gombatest Gt1 = new Gombatest(t1, g1);
        t1.setGombatest(Gt1);

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(t1, t2, Gt1);
        t1.getGombafonalak().add(f12);
        t2.getGombafonalak().add(f12);

        // t1-re egy rovar
        Rovarasz r1 = new Rovarasz("R1");
        Rovar R1 = new Rovar(t1, r1);

        // Pálya visszaadása
        return List.of(t1, t2);
    }

    @Test
    void tesztPalya6Init() {
        List<Tekton> palya6 = letrehozPalya6();
        assertEquals(2, palya6.size(), "Pálya6-nek 2 tektonból kell állnia");

        Tekton t1 = palya6.get(0), t2 = palya6.get(1);

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");

        // Gombafonal
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek 1 gombafonal legyen");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek 1 gombafonal legyen");

        // Gombatest
        assertNotNull(t1.getGombatest(), "T1-en legyen gombatest");
        assertEquals(Gombatest.class, t1.getGombatest().getClass(), "T1-en sima gombatest legyen");

        // Spórák
        assertEquals(0, t1.getSporak().size(), "T1-en ne legyen spóra");
        assertEquals(0, t2.getSporak().size(), "T2-nek ne legyen spóra");

        // Rovar
        assertEquals(1, t1.getRovarok().size(), "T1-en 1 rovar legyen");
        assertEquals("R1", t1.getRovarok().get(0).getRovarasz().getNev(), "T1-en a rovar neve 'R1' legyen");

        // Konzolra íratás
        System.out.println("=== PÁLYA6 ÁLLAPOTA ===");
        palya6.forEach(System.out::println);
    }

    /**
     * 2 tektonból álló pálya:
     * - t1-en sima gombatest
     * - t2-n 1 sima spóra
     * - t1-en egy rovar
     */
    private static List<Tekton> letrehozPalya7(){
        // Tektonok
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        // Szomszédságok: t1–t2
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombasz g1 = new Gombasz("G1");
        Gombatest Gt1 = new Gombatest(t1, g1);
        t1.setGombatest(Gt1);

        // t2-re 1 sima spóra
        t2.getSporak().add(new SimaSpora());

        // t1-re egy rovar
        Rovarasz r1 = new Rovarasz("R1");
        Rovar R1 = new Rovar(t1, r1);

        // Pálya visszaadása
        return List.of(t1, t2);
    }

    @Test
    void tesztPalya7Init() {
        List<Tekton> palya5 = letrehozPalya5();
        assertEquals(2, palya5.size(), "Pálya7-nek 2 tektonból kell állnia");

        Tekton t1 = palya5.get(0), t2 = palya5.get(1);

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");

        // Gombatest
        assertNotNull(t1.getGombatest(), "T1-en legyen gombatest");
        assertEquals(Gombatest.class, t1.getGombatest().getClass(), "T1-en sima gombatest legyen");

        // Spórák
        assertEquals(0, t1.getSporak().size(), "T1-en ne legyen spóra");
        assertEquals(1, t2.getSporak().size(), "T2-nek 1 spórája legyen");
        assertTrue(t2.getSporak().get(0) instanceof SimaSpora, "T2-n sima spóra legyen");

        // Rovar
        assertEquals(1, t1.getRovarok().size(), "T1-en 1 rovar legyen");
        assertEquals("R1", t1.getRovarok().get(0).getRovarasz().getNev(), "T1-en a rovar neve 'R1' legyen");

        // Konzolra íratás
        System.out.println("=== PÁLYA7 ÁLLAPOTA ===");
        palya5.forEach(System.out::println);
    }

    //ELTUNOFONALTEKTONHOZ
    /**
     * 2 tektonból álló pálya:
     * - t1: EltunoFonalasTekton
     * - t2: TobbFonalasTekton
     * - t1–t2 között gombafonal
     * - t1-en sima gombatest (nincs fejlettség, nincs spóra)
     */
    private static List<Tekton> letrehozPalya8() {
        // Tektonok
        Tekton t1 = new EltunoFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        // Szomszédságok: t1–t2
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombasz g1 = new Gombasz("G1");
        Gombatest Gt1 = new Gombatest(t1, g1);
        t1.setGombatest(Gt1);

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(t1, t2, Gt1);
        t1.getGombafonalak().add(f12);
        t2.getGombafonalak().add(f12);

        // Pálya visszaadása
        return List.of(t1, t2);
    }

    @Test
    void tesztPalya8Init() {
        List<Tekton> palya8 = letrehozPalya8();
        assertEquals(2, palya8.size(), "Pálya8-nak 2 tektonból kell állnia");

        Tekton t1 = palya8.get(0);
        Tekton t2 = palya8.get(1);

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");

        // Gombafonalak
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek 1 gombafonala legyen");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek 1 gombafonala legyen");

        // Gombatest
        assertNotNull(t1.getGombatest(), "T1-en legyen gombatest");
        assertEquals(Gombatest.class, t1.getGombatest().getClass(), "T1-en sima gombatest legyen");

        // Spórák
        assertTrue(t1.getSporak().isEmpty(), "T1-en ne legyen spóra");
        assertTrue(t2.getSporak().isEmpty(), "T2-en ne legyen spóra");

        // Rovarok
        assertTrue(t1.getRovarok().isEmpty(), "T1-en ne legyen rovar");
        assertTrue(t2.getRovarok().isEmpty(), "T2-en ne legyen rovar");

        // Konzolra íratás
        System.out.println("=== PÁLYA8 ÁLLAPOTA ===");
        palya8.forEach(System.out::println);
    }

    /**
     * 1 tektonból álló pálya:
     * - t1: EltunoFonalasTekton
     * - 6 különböző spóra van rajta:
     *   - SimaSpora, BenitoSpora, GyorsitoSpora, LassitoSpora, OsztodoSpora, VagasTiltoSpora
     * - t1-en egy rovar található ("R1")
     * - nincs gombafonal vagy szomszédos tekton
     * - nincs gombatest
     */
    private static List<Tekton> letrehozPalya9() {
        // Tektonok
        Tekton t1 = new EltunoFonalasTekton();

        // t1-re spórák
        t1.getSporak().add(new SimaSpora());
        t1.getSporak().add(new BenitoSpora());
        t1.getSporak().add(new GyorsitoSpora());
        t1.getSporak().add(new LassitoSpora());
        t1.getSporak().add(new OsztodoSpora());
        t1.getSporak().add(new VagasTiltoSpora());

        // t1-re egy rovar
        Rovarasz r1 = new Rovarasz("R1");
        Rovar R1 = new Rovar(t1, r1);

        // Pálya visszaadása
        return List.of(t1);
    }

    @Test
    void tesztPalya9Init() {
        List<Tekton> palya9 = letrehozPalya9();
        assertEquals(1, palya9.size(), "Pálya9-nek 1 tektonból kell állnia");

        Tekton t1 = palya9.get(0);

        // Szomszédok
        assertTrue(t1.getSzomszedosTektonok().isEmpty(), "T1-nek ne legyen szomszédja");

        // Gombafonalak
        assertTrue(t1.getGombafonalak().isEmpty(), "T1-nek ne legyen gombafonala");

        // Gombatest
        assertNull(t1.getGombatest(), "T1-en ne legyen gombatest");

        // Spórák
        List<Spora> sporak = t1.getSporak();
        assertEquals(6, sporak.size(), "T1-en 6 spórának kell lennie");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof SimaSpora), "T1-en legyen SimaSpora");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof BenitoSpora), "T1-en legyen BenitoSpora");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof GyorsitoSpora), "T1-en legyen GyorsitoSpora");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof LassitoSpora), "T1-en legyen LassitoSpora");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof OsztodoSpora), "T1-en legyen OsztodoSpora");
        assertTrue(sporak.stream().anyMatch(s -> s instanceof VagasTiltoSpora), "T1-en legyen VagasTiltoSpora");

        // Rovar
        assertEquals(1, t1.getRovarok().size(), "T1-en 1 rovarnak kell lennie");
        assertEquals("R1", t1.getRovarok().get(0).getRovarasz().getNev(), "A rovar neve 'R1' legyen");

        // Konzolra íratás
        System.out.println("=== PÁLYA9 ÁLLAPOTA ===");
        palya9.forEach(System.out::println);
    }

    /**
     * 2 tektonból álló pálya:
     * - t1–t2 között fonal
     * - t1-en sima gombatest
     * - t2-en egy rovar
     */
    private static List<Tekton> letrehozPalya10(){
        // Tektonok
        Tekton t1 = new TobbFonalasTekton();
        Tekton t2 = new TobbFonalasTekton();

        // Szomszédságok: t1–t2
        t1.getSzomszedosTektonok().add(t2);
        t2.getSzomszedosTektonok().add(t1);

        // Gombatest t1-en
        Gombasz g1 = new Gombasz("G1");
        Gombatest Gt1 = new Gombatest(t1, g1);
        t1.setGombatest(Gt1);

        // Gombafonal t1–t2
        GombaFonal f12 = new GombaFonal(t1, t2, Gt1);
        t1.getGombafonalak().add(f12);
        t2.getGombafonalak().add(f12);

        // t1-re egy rovar
        Rovarasz r1 = new Rovarasz("R1");
        Rovar R1 = new Rovar(t2, r1);

        // Pálya visszaadása
        return List.of(t1, t2);
    }

    @Test
    void tesztPalya10Init() {
        List<Tekton> palya10 = letrehozPalya6();
        assertEquals(2, palya10.size(), "Pálya6-nek 2 tektonból kell állnia");

        Tekton t1 = palya10.get(0), t2 = palya10.get(1);

        // Szomszédságok
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");
        assertTrue(t2.getSzomszedosTektonok().contains(t1), "T2 szomszédja legyen T1");

        // Gombafonal
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek 1 gombafonal legyen");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek 1 gombafonal legyen");

        // Gombatest
        assertNotNull(t1.getGombatest(), "T1-en legyen gombatest");
        assertEquals(Gombatest.class, t1.getGombatest().getClass(), "T1-en sima gombatest legyen");

        // Spórák
        assertEquals(0, t1.getSporak().size(), "T1-en ne legyen spóra");
        assertEquals(0, t2.getSporak().size(), "T2-nek ne legyen spóra");

        // Rovar
        assertEquals(1, t2.getRovarok().size(), "T2-en 1 rovar legyen");
        assertEquals("R1", t2.getRovarok().get(0).getRovarasz().getNev(), "T2-en a rovar neve 'R1' legyen");

        // Konzolra íratás
        System.out.println("=== PÁLYA10 ÁLLAPOTA ===");
        palya10.forEach(System.out::println);
    }

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
        List<Tekton> palya3 = letrehozPalya3();
        Tekton t1 = palya3.get(0);
        Tekton t2 = palya3.get(1);
        Tekton t3 = palya3.get(2);
        Gombatest gt = t1.getGombatest();

        // 2) Kiírás a kezdeti állapotról
        out.println("=== PALYA3 KEZDETE ===");
        palya3.forEach(out::println);

        // 3) Fonal növesztés nem szomszédos tektonok között
        out.println("FONALNOVESZTES");
        gt.fonalNovesztes(t1, t3);

        // 4) Kiírás a végső állapotról
        out.println("=== PALYA3 VEGE ===");
        palya3.forEach(out::println);

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

    @Test
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
    }

    @Test
    void tesztFejlettSporaSzorasMasodfokuPalya3() {
        // 1) Pálya előkészítése
        List<Tekton> palya3 = letrehozPalya3();
        Tekton t1 = palya3.get(0);
        Tekton t3 = palya3.get(2);
        Gombatest gt = t1.getGombatest();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA3 KEZDETE ===");
        palya3.forEach(System.out::println);

        // 3) Előfeltételek ellenőrzése
        //   t1 és t3 másodfokú szomszédok legyenek
        assertTrue(t1.szomszedSzomszedEllenorzese(t3),
                "T1-nek és T3-nak másodfokú szomszédnak kell lenniük");
        //   legyen elegendő kilőhető spóra
        assertEquals(2, gt.getKilohetoSporakSzama(),
                "Kezdetben 2 kilőhető spóra");
        //   és a cél tektonon (T3) ne legyen spóra
        assertTrue(t3.getSporak().isEmpty(),
                "T3-on kezdetben ne legyen spóra");

        // 4) Művelet: spóra kilövés T3-ra (2 darab)
        gt.sporaKiloves(t3, 2);

        // 5) Eredmény ellenőrzése
        assertEquals(0, gt.getKilohetoSporakSzama(),
                "Kilövés után 0 kilőhető spóra maradjon");
        assertEquals(2, t3.getSporak().size(),
                "T3-on 2 új spórának kell lennie");

        // 6) Kiírás: végállapot
        System.out.println("=== PALYA3 VEGE ===");
        palya3.forEach(System.out::println);
    }

    @Test
    void tesztFejlettSporaSzorasKevessporavalPalya3() {
        // 1) Pálya előkészítése
        List<Tekton> palya3 = letrehozPalya3();
        Tekton t1 = palya3.get(0);
        Tekton t3 = palya3.get(2);
        Gombatest gt = t1.getGombatest();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA3 KEZDETE ===");
        palya3.forEach(System.out::println);

        // 3) Előfeltételek ellenőrzése
        assertTrue(t1.szomszedSzomszedEllenorzese(t3),
                "T1 és T3 legyenek másodfokú szomszédok");
        assertEquals(2, gt.getKilohetoSporakSzama(),
                "Kezdetben 2 kilőhető spóra");
        assertTrue(t3.getSporak().isEmpty(),
                "T3-on kezdetben ne legyen spóra");

        // 4) Művelet: spóra kilövés (3 darab, ennél több nincs)
        gt.sporaKiloves(t3, 3);

        // 5) Eredmény ellenőrzése
        // — mivel nincs elég spóra, nem csökken a kilőhető spórák száma
        assertEquals(2, gt.getKilohetoSporakSzama(),
                "Kevés spóra miatt maradjon 2 kilőhető spóra");
        // — és T3-on továbbra sem jelenik meg új spóra
        assertTrue(t3.getSporak().isEmpty(),
                "Kevés spóra miatt ne kerüljön spóra T3-ra");

        // 6) Kiírás: végállapot
        System.out.println("=== PALYA3 VEGE ===");
        palya3.forEach(System.out::println);
    }

    @Test
    void tesztFejlettSporaSzorasNemSzomszedPalya4() {
        // 1) Pálya előkészítése
        List<Tekton> palya4 = letrehozPalya4();
        Tekton t1 = palya4.get(0);
        Tekton t4 = palya4.get(3);
        FejlettGombatest fejlettGt = (FejlettGombatest) t1.getGombatest();

        // 2) Kezdeti állapot kiírás
        System.out.println("=== PALYA4 KEZDETE ===");
        palya4.forEach(System.out::println);

        // 3) Előfeltételek
        assertEquals(1, fejlettGt.getKilohetoSporakSzama(), "Kezdetben 1 kilőhető spóra");

        // 4) Próbálkozunk t1 → t4 (nem elég közel)
        fejlettGt.sporaKiloves(t4, 1);

        // 5) Utófeltételek ellenőrzése
        // mert t4 túl távoli, nem szórunk semmit, a készlet nem változik
        assertEquals(1, fejlettGt.getKilohetoSporakSzama(), "Nem szomszédosra ne csökkenjen a készlet");
        assertEquals(3, t4.getSporak().size(), "3 spóra volt itt alapból, ne legyen több");

        // 6) Végállapot kiírás
        System.out.println("=== PALYA4 VEGE ===");
        palya4.forEach(System.out::println);
    }

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

    @Test
    void tesztGombatestNovesztesHianyzoFeltetelekkelPalya4() {
        // 1) Pálya előkészítése
        List<Tekton> palya4 = letrehozPalya4();
        Tekton t2 = palya4.get(1);  // 2. tektonon próbáljuk növeszteni

        // 2) Kezdő állapot kiírása
        System.out.println("=== PALYA4 KEZDETE ===");
        palya4.forEach(System.out::println);

        // 3) Fejléc a parancshoz
        System.out.println("GOMBATESTNOVESZTES");

        // 4) Próbáljuk meg növeszteni a gombatestet T2-n, amihez nincs elegendő spóra
        boolean novesztheto = t2.gombatestNoveszthetoE();
        assertFalse(novesztheto, "Ennél a tektonnál NEM szabad gombatestet növeszteni");

        // 5) Ellenőrizzük, hogy nem jött létre új gombatest, de a fonal továbbra is megvan
        assertNull(t2.getGombatest(), "T2-n továbbra sem lehet gombatest");
        assertEquals(1, t2.getGombafonalak().size(),
                "T2-n csak az eredeti fonal maradjon meg");

        // 6) Végállapot kiírása
        System.out.println("=== PALYA4 VEGE ===");
        palya4.forEach(System.out::println);
    }

    @Test
    void tesztGombatestNovesztesNincsFonalPalya4() {
        // 1) Pálya előkészítése
        List<Tekton> palya4 = letrehozPalya4();
        Tekton t3 = palya4.get(2);  // 3. tektonon próbáljuk növeszteni

        // 2) Kezdő állapot kiírása
        System.out.println("=== PALYA4 KEZDETE ===");
        palya4.forEach(System.out::println);

        // 3) Fejléc a parancshoz
        System.out.println("GOMBATESTNOVESZTES");

        // 4) Ellenőrzés: van-e elegendő spóra, de nincs fonal
        assertEquals(3, t3.getSporak().size(), "T3-on legyen pontosan 3 spóra");
        assertTrue(t3.getGombafonalak().isEmpty(), "T3-on ne legyen fonal");

        // 5) Próbáljuk meg növeszteni a gombatestet T3-on
        boolean novesztheto = t3.gombatestNoveszthetoE();
        assertFalse(novesztheto, "T3-on NEM szabad gombatestet növeszteni");

        // 6) Ellenőrizzük, hogy valóban nem jött létre gombatest
        assertNull(t3.getGombatest(), "T3-on továbbra sincs gombatest");

        // 7) Végállapot kiírása
        System.out.println("=== PALYA4 VEGE ===");
        palya4.forEach(System.out::println);
    }

    @Test
    void tesztGombatestFejlesztes3SporavalPalya4() {
        // 1) Pálya előkészítése és kiírás
        List<Tekton> palya4 = letrehozPalya4();
        System.out.println("=== PALYA4 KEZDETE ===");
        palya4.forEach(System.out::println);

        // 2) A sima gombatestet tartalmazó tekton kiválasztása (t4)
        Tekton t4 = palya4.get(3);
        Gombatest eredetiGt4 = t4.getGombatest();
        assertNotNull(eredetiGt4, "T4-nek kell, hogy legyen Gombatest");

        // 3) Előfeltétel: legalább 3 spóra a tektonon
        assertTrue(t4.getSporak().size() >= 3 ||
                        eredetiGt4.getElszortSporakSzama() >= 3,
                "T4-en kell lennie legalább 3 spórának");

        // 4) Gombatest fejleszthetőségének ellenőrzése
        assertTrue(t4.gombatestFejleszthetoE(), "Gombatestet fejleszthetnünk kell");

        // 5) Fejlesztés
        t4.setGombatest(new FejlettGombatest(eredetiGt4));

        // 6) Ellenőrzés: most fejlett Gombatest
        Gombatest ujGt4 = t4.getGombatest();
        assertNotNull(ujGt4, "Fejlesztés után is legyen Gombatest");
        assertTrue(ujGt4 instanceof FejlettGombatest, "Gombatestnek fejlett példánynak kell lennie");
        assertSame(t4, ujGt4.getTekton(), "A fejlett Gombatest a megfelelő tektonhoz kell tartozzon");

        // 7) Pálya végállapot kiírása
        System.out.println("=== PALYA4 VEGE ===");
        palya4.forEach(System.out::println);
    }

    //it amugy lkehet hogy ugy kéne, hogy megpróbáljuk fejleszteni, de false az eredmény
    @Test
    void tesztGombatestFejlesztesSikertelenPalya1() {
        // 1) Előkészítés: pálya1
        List<Tekton> palya1 = letrehozPalya1();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PÁLYA1 KEZDETE ===");
        palya1.forEach(System.out::println);

        // 3) Megkíséreljük a gombatest fejlesztését a 3. tektonon (index 2)
        Tekton t3 = palya1.get(2);
        boolean fejlesztheto = t3.gombatestFejleszthetoE();

        // 4) Elvárt: NEM fejleszthető
        assertFalse(fejlesztheto, "T3-on nem szabad gombatestet fejleszteni");

        System.out.println("GOMBATESTFEJLESZTES");

        if (fejlesztheto) {
            // csak ha mégis true lenne (soha nem lesz), de itt nem fut
            FejlettGombatest fgt = new FejlettGombatest(t3.getGombatest());
            t3.setGombatest(fgt);
        } else {
            System.out.println("A tekton gombateste nem fejlesztheto!");
        }

        // 5) Kiírás: végállapot
        System.out.println("=== PÁLYA1 VEGE ===");
        palya1.forEach(System.out::println);

        // 6) Utóellenőrzés: továbbra se legyen gombatest a 3. tektonon
        assertNull(t3.getGombatest(), "T3-hoz nem szabad új gombatestet állítani");
    }

    @Test
    void tesztGombatestElpusztulKapacitasAlapjanPalya2() {
        // 1) Pálya előkészítése
        List<Tekton> palya2 = letrehozPalya2();
        Tekton t1 = palya2.get(0);
        Gombatest gt = t1.getGombatest();
        Gombasz gs = gt.getGombasz();

        // 2) Előfeltételek
        assertNotNull(gt, "T1-en kell legyen Gombatest");
        // beállítjuk, hogy a gombatestnél elfogyott a kilőhető spóra
        gt.setKilohetoSporakSzama(0);

        // 3) Kiírás: kezdeti állapot
        System.out.println("=== PALYA2 KEZDETE ===");
        palya2.forEach(System.out::println);

        // 4) GOMBATESTELPUSZTUL parancs
        System.out.println("GOMBATESTELPUSZTUL");
        gt.elpusztulas();

        // 5) Kiírás: végállapot
        System.out.println("=== PALYA2 VEGE ===");
        palya2.forEach(System.out::println);

        // 6) Utóellenőrzések
        assertNull(t1.getGombatest(), "A gombatestnek el kellett pusztulnia és eltűnnie a tektonról");
        assertFalse(gs.getGombatestek().contains(gt),
                "A gombasz nyilvántartásából is el kell távolítani a gombatestet");
    }

    @Test
    void tesztSikertelenGombatestElpusztulasPalya1() {
        // 1) Pálya előkészítése
        List<Tekton> palya1 = letrehozPalya1();
        Gombatest gt =  palya1.get(0).getGombatest();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA1 KEZDETE ===");
        palya1.forEach(System.out::println);

        // 3) Parancs
        System.out.println("GOMBATESTELPUSZTUL");

        // 4) Előfeltétel: még van kilőhető spóra
        int kezdoKapacitas = gt.getKilohetoSporakSzama();
        assertTrue(kezdoKapacitas > 0, "Előfeltétel: van még kilőhető spóra");

        // 5) Meghívjuk az elpusztulást
        gt.elpusztulas();

        // 6) Ellenőrzések
        // – a kapacitás nem változott
        assertEquals(kezdoKapacitas, gt.getKilohetoSporakSzama(),
                "A kilőhető spórák száma ne változzon");
        // – a gombatest nem pusztult el
        assertNotNull( palya1.get(0).getGombatest(), "A gombatestnak nem szabad elpusztulnia");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA1 VEGE ===");
        palya1.forEach(System.out::println);
    }

    @Test
    void tesztRovarMozgasaSporaEvesselPalya5() {
        // 1) Pálya előkészítése
        List<Tekton> palya5 = letrehozPalya5();
        Tekton t1 = palya5.get(0);
        Tekton t2 = palya5.get(1);
        Rovar r = palya5.get(0).getRovarok().get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA5 KEZDETE ===");
        palya5.forEach(System.out::println);

        // 3) Parancs
        System.out.println("ROVARMOZGATAS");

        // 4) Előfeltétel: t2-n van sima spóra
        List<Spora> sS = t2.getSporak();
        assertEquals(1, sS.size(), "Előfeltétel: T2-n pontosan 1 spóra legyen");
        assertTrue(sS.get(0) instanceof SimaSpora, "T2-n sima spóra legyen");

        // 5) Meghívjuk a lepést
        r.lepes(t2);

        // 6) Ellenőrzések
        // – a rovar már ne legyen t1-en
        assertFalse(t1.getRovarok().contains(r), "A rovarnak el kellett hagynia T1-et");
        // – a rovar legyen t2-n
        assertTrue(t2.getRovarok().contains(r), "A rovarnak T2-re kellett lépnie");
        // – a spóra tűnjön el, mert megette a rovar
        assertEquals(0, t2.getSporak().size(), "A spórát meg kellett ennie a rovarnak");
        assertEquals(0, t1.getSporak().size(), "A spórát nem helyezte át másik tektonra");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA5 VEGE ===");
        palya5.forEach(System.out::println);
    }

    @Test
    void tesztRovarMozgasaSporaEvesNelkulPalya6() {
        // 1) Pálya előkészítése
        List<Tekton> palya6 = letrehozPalya6();
        Tekton t1 = palya6.get(0);
        Tekton t2 = palya6.get(1);
        Rovar r = palya6.get(0).getRovarok().get(0);
        int eredetiLSz = r.getLepesSzam();
        boolean eredetiVK = r.getVagoKepesseg();
        boolean eredetiB = r.getBenulas();

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA6 KEZDETE ===");
        palya6.forEach(System.out::println);

        // 3) Parancs
        System.out.println("ROVARMOZGATAS");

        // 4) Előfeltétel: t2-n nincs spóra
        assertEquals(0, t2.getSporak().size(), "Előfeltétel: T2-n ne legyen spóra");

        // 5) Meghívjuk a lepést
        r.lepes(t2);

        // 6) Ellenőrzések
        // – a rovar már ne legyen t1-en
        assertFalse(t1.getRovarok().contains(r), "A rovarnak el kellett hagynia T1-et");
        // – a rovar legyen t2-n
        assertTrue(t2.getRovarok().contains(r), "A rovarnak T2-re kellett lépnie");
        // – a spórát nem evett a rovar
        assertEquals(eredetiLSz, r.getLepesSzam(), "A rovar nem evett olyan spórát, ami változtatná az állapotát");
        assertEquals(eredetiVK, r.getVagoKepesseg(), "A rovar nem evett olyan spórát, ami változtatná az állapotát");
        assertEquals(eredetiB, r.getBenulas(), "A rovar nem evett olyan spórát, ami változtatná az állapotát");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA6 VEGE ===");
        palya6.forEach(System.out::println);
    }

    @Test
    void tesztRovarMozgasaSikertelenPalya7() {
        // 1) Pálya előkészítése
        List<Tekton> palya7 = letrehozPalya7();
        Tekton t1 = palya7.get(0);
        Tekton t2 = palya7.get(1);
        Rovar r = palya7.get(0).getRovarok().get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA7 KEZDETE ===");
        palya7.forEach(System.out::println);

        // 3) Parancs
        System.out.println("ROVARMOZGATAS");

        // 4) Előfeltétel: t2-n van sima spóra
        List<Spora> sS = t2.getSporak();
        assertEquals(1, sS.size(), "Előfeltétel: T2-n pontosan 1 spóra legyen");
        assertTrue(sS.get(0) instanceof SimaSpora, "T2-n sima spóra legyen");

        // 5) Meghívjuk a lepést
        r.lepes(t2);

        // 6) Ellenőrzések
        // – a rovar legyen t1-en
        assertTrue(t1.getRovarok().contains(r), "A rovarnak T1-en kellett maradnia");
        // – a rovar ne legyen t2-n
        assertFalse(t2.getRovarok().contains(r), "A rovarnak nem léphetett T2-re");
        // – a spórának nem tűnhet el, mert nem ette meg a rovar
        assertEquals(1, t2.getSporak().size(), "A spórát meg kellett ennie a rovarnak");
        assertEquals(0, t1.getSporak().size(), "A spórát nem helyezte át másik tektonra");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA7 VEGE ===");
        palya7.forEach(System.out::println);
    }

    @Test
    void tesztGombaFonalElvagasaPalya6() {
        // 1) Pálya előkészítése
        List<Tekton> palya6 = letrehozPalya6();
        Tekton t1 = palya6.get(0);
        Tekton t2 = palya6.get(1);
        Rovar r = palya6.get(0).getRovarok().get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA6 KEZDETE ===");
        palya6.forEach(System.out::println);

        // 3) Parancs
        System.out.println("FONALVAGAS");

        // 4) Előfeltétel: a rovar vágóképessége legyen aktív
        assertTrue(r.getVagoKepesseg(), "A rovarnak képesnek kell lennie fonalat vágni");

        // 5) Meghívjuk a fonal vágást
        r.fonalVagas(t2);

        // 6) Ellenőrzések
        // – a rovar még t1-en van
        assertTrue(t1.getRovarok().contains(r), "A rovarnak T1-en kellett maradnia");
        // – a fonal ténylegesen el lett vágva mindkét irányban
        assertFalse(t1.vanFonalKozottuk(t2), "T1 és T2 között nem lehet fonal");
        assertFalse(t2.vanFonalKozottuk(t1), "T2 és T1 között nem lehet fonal");
        // – gombafonalak listája üres mindkét tektonon
        assertEquals(0, t1.getGombafonalak().size(), "T1-nek nem lehet gombafonala");
        assertEquals(0, t2.getGombafonalak().size(), "T2-nek nem lehet gombafonala");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA6 VEGE ===");
        palya6.forEach(System.out::println);
    }

    @Test
    void tesztGombaFonalElvagasaSikertelenPalya5() {
        // 1) Pálya előkészítése
        List<Tekton> palya5 = letrehozPalya5();
        Tekton t1 = palya5.get(0);
        Tekton t2 = palya5.get(1);
        Rovar r = palya5.get(0).getRovarok().get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA5 KEZDETE ===");
        palya5.forEach(System.out::println);

        // 3) Parancs
        System.out.println("FONALVAGAS");

        // 4) Előfeltétel: a rovar vágóképessége nem aktív
        assertFalse(r.getVagoKepesseg(), "A rovar nem lehet képes fonalat vágni");

        // 5) Meghívjuk a fonal vágást
        r.fonalVagas(t2);

        // 6) Ellenőrzések
        // – a rovar még t1-en van
        assertTrue(t1.getRovarok().contains(r), "A rovarnak T1-en kellett maradnia");
        // – a fonal nem lett elvágva mindkét irányban
        assertTrue(t1.vanFonalKozottuk(t2), "T1 és T2 között nem lehet fonal");
        assertTrue(t2.vanFonalKozottuk(t1), "T2 és T1 között nem lehet fonal");
        // – gombafonalak listája nem üres mindkét tektonon
        assertEquals(1, t1.getGombafonalak().size(), "T1-nek nem lehet gombafonala");
        assertEquals(1, t2.getGombafonalak().size(), "T2-nek nem lehet gombafonala");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA5 VEGE ===");
        palya5.forEach(System.out::println);
    }

    @Test
    void tesztEgyFonalasTektonKetteToreseSikeresPalya5() {
        // 1) Pálya előkészítése
        List<Tekton> palya5 = letrehozPalya5();
        Tekton t1 = palya5.get(0);
        Tekton t2 = palya5.get(1);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA5 KEZDETE ===");
        palya5.forEach(System.out::println);

        // 3) Parancs
        System.out.println("KETTETORES");

        // 4) Előfeltétel: T1-en legyen gombatest és fonal, T2-vel összekötve
        assertNotNull(t1.getGombatest(), "T1-en kell legyen gombatest");
        assertFalse(t1.getGombafonalak().isEmpty(), "T1-nek kell legyen gombafonala");
        assertTrue(t1.getSzomszedosTektonok().contains(t2), "T1 szomszédja legyen T2");

        // 5) Meghívjuk a kettétörést
        ((EgyFonalasTekton) t1).ketteTores();

        // 6) Ellenőrzések
        // – a gombatest eltűnt
        assertNull(t1.getGombatest(), "A gombatestnek el kellett tűnnie");
        // – a fonal megszűnt t1 és t2 között
        assertTrue(t1.getGombafonalak().isEmpty(), "T1-nek nem szabad legyen gombafonala");
        assertFalse(t2.getGombafonalak().contains(t1), "T2-nek sem szabad tartalmaznia fonalat T1 felé");
        // – a spórák eltűntek t1-ről
        assertTrue(t1.getSporak().isEmpty(), "T1-en nem maradhatott spóra");

        // – t1 kapott egy új szomszédot
        assertEquals(2, t1.getSzomszedosTektonok().size(), "T1-nek új szomszédot kellett kapnia");
        Tekton uj = t1.getSzomszedosTektonok().stream()
                .filter(sz -> sz != t2)
                .findFirst()
                .orElse(null);
        assertNotNull(uj, "Kettétörés után új tekton kell legyen szomszédként");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA5 VEGE ===");
        palya5.forEach(System.out::println);
        System.out.println("Új tekton: " + uj);

    }

    //BE KELL FEJEZNI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test
    void tesztFonalFelszivodasIdovelPalya8() {
        // 1) Pálya előkészítése
        List<Tekton> palya8 = letrehozPalya8();
        Tekton t1 = palya8.get(0);
        Tekton t2 = palya8.get(1);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA8 KEZDETE ===");
        palya8.forEach(System.out::println);

        // 3) Parancs
        System.out.println("FONALFELSZIVODAS");


    }

    @Test
    void tesztBenitoSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("BENITOSPORAHATASKIFEJTESE");

        // 4) Előkészítés: BenitoSpora és rovar kiválasztása
        Rovar r = t1.getRovarok().get(0);
        int eredetiP = r.getRovarasz().getPont();
        Spora bS = t1.getSporak().stream()
                .filter(s -> s instanceof BenitoSpora)
                .findFirst()
                .orElseThrow(() -> new AssertionError("T1-en nem található BenitoSpora"));

        // 5) Állapot ellenőrzés a hatás előtt
        assertFalse(r.getBenulas(), "A rovar kezdetben ne legyen bénult");

        // 6) Hatás meghívása
        bS.hatasKifejtese(r);

        // 7) Ellenőrzés: a rovar lebénult
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A spóra tápanyagot ad, ezért növeli a pontszámot");
        assertTrue(r.getBenulas(), "A BenitoSpora hatására a rovarnak bénultnak kell lennie");

        // 8) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztLassitoSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("LASSITOSPORAHATASKIFEJTESE");

        // 4) Előkészítés: rovar és lassító spóra
        Rovar r = t1.getRovarok().get(0);
        int eredetiP = r.getRovarasz().getPont();
        int eredetiLSz = r.getLepesSzam();
        Spora lS = t1.getSporak().stream()
                .filter(s -> s instanceof LassitoSpora)
                .findFirst()
                .orElseThrow();

        // 5) Hatás meghívása
        lS.hatasKifejtese(r);

        // 6) Ellenőrzés: 1-gyel csökken a lépésszám
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A spóra tápanyagot ad, ezért növeli a pontszámot");
        assertEquals(eredetiLSz - 1, r.getLepesSzam(), "A lassító spóra csökkentse a lépésszámot");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztGyorsitoSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("GYORSITOSPORAHATASKIFEJTESE");

        // 4) Előkészítés: rovar és gyorsító spóra
        Rovar r = t1.getRovarok().get(0);
        int eredetiLSz = r.getLepesSzam();
        int eredetiP = r.getRovarasz().getPont();
        Spora gyS = t1.getSporak().stream()
                .filter(s -> s instanceof GyorsitoSpora)
                .findFirst()
                .orElseThrow();

        // 5) Hatás meghívása
        gyS.hatasKifejtese(r);

        // 6) Ellenőrzés: 1-gyel nő a lépésszám
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A spóra tápanyagot ad, ezért növeli a pontszámot");
        assertEquals(eredetiLSz + 1, r.getLepesSzam(), "A gyorsító spóra növelje a lépésszámot");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztVagasBenitoSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("VAGASBENITOSPORAHATASKIFEJTESE");

        // 4) Előkészítés: rovar, vágás-tiltó spóra
        Rovar r = t1.getRovarok().get(0);
        int eredetiP = r.getRovarasz().getPont();
        int eredetiLSz = r.getLepesSzam();
        Spora vS = t1.getSporak().stream()
                .filter(s -> s instanceof VagasTiltoSpora)
                .findFirst()
                .orElseThrow();

        // 5) Állapot ellenőrzés a hatás előtt
        assertTrue(r.getVagoKepesseg(), "A rovar kezdetben legyen képes fonalat vágni");

        // 6) Hatások meghívása
        vS.hatasKifejtese(r);

        // 6) Ellenőrzések
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A spóra tápanyagot ad, ezért növeli a pontszámot");
        assertFalse(r.getVagoKepesseg(), "A vágás tiltó spóra tiltsa le a rovar vágóképességét");
        assertEquals(eredetiLSz, r.getLepesSzam(), "A vágás tiltó spóra nem változtatja a lépésszámot");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztSimaSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("SIMASPORAHATASKIFEJTESE");

        // 4) Előkészítés: rovar és sima spóra
        Rovar r = t1.getRovarok().get(0);
        int eredetiP = r.getRovarasz().getPont();
        Spora sS = t1.getSporak().stream()
                .filter(s -> s instanceof SimaSpora)
                .findFirst()
                .orElseThrow();
        int eredetiLSz = r.getLepesSzam();

        // 5) Hatás meghívása
        sS.hatasKifejtese(r);

        // 6) Ellenőrzés: pontszám nő
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A sima spóra tápanyagot ad, ezért növeli a pontszámot");
        assertEquals(eredetiLSz, r.getLepesSzam(), "A sima spóra nem változtatja a lépésszámot");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztOsztodoSporaHatasaPalya9() {
        // 1) Pálya előkészítése
        List<Tekton> palya9 = letrehozPalya9();
        Tekton t1 = palya9.get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA9 KEZDETE ===");
        palya9.forEach(System.out::println);

        // 3) Parancs
        System.out.println("OSZTODOSPORAHATASKIFEJTESE");

        // 4) Előkészítés: rovar és osztódó spóra
        Rovar r = t1.getRovarok().get(0);
        Rovarasz ra = r.getRovarasz();
        int eredetiP = ra.getPont();
        int eredetiLSz = r.getLepesSzam();
        int eredetiTRSz = t1.getRovarok().size();
        int eredetiRSz = ra.getRovarok().size();
        Spora oS = t1.getSporak().stream()
                .filter(s -> s instanceof OsztodoSpora)
                .findFirst()
                .orElseThrow();

        // 5) Hatás meghívása
        oS.hatasKifejtese(r);

        // 6) Ellenőrzés: létrejött egy új rovar
        assertTrue(r.getRovarasz().getPont() > eredetiP,
                "A sima spóra tápanyagot ad, ezért növeli a pontszámot");
        assertEquals(eredetiTRSz + 1, t1.getRovarok().size(),
                "Az osztódó spóra új rovart kellett létrehozzon a tektonon");
        assertEquals(eredetiRSz + 1, ra.getRovarok().size(),
                "A rovarásznak is egy új rovart kellett kapnia");
        assertEquals(eredetiLSz, r.getLepesSzam(), "Az osztódó spóra nem változtatja a lépésszámot");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA9 VÉGE ===");
        palya9.forEach(System.out::println);
    }

    @Test
    void tesztRovarElfogyasztasaPalya10() {
        // 1) Pálya előkészítése
        List<Tekton> palya10 = letrehozPalya10();
        Tekton t1 = palya10.get(0);
        Tekton t2 = palya10.get(1);
        Rovar rovar = t2.getRovarok().get(0);
        GombaFonal fonal = t1.getGombafonalak().get(0);

        // 2) Kiírás: kezdeti állapot
        System.out.println("=== PALYA10 KEZDETE ===");
        palya10.forEach(System.out::println);

        // 3) Parancs
        System.out.println("ROVARELFOGYASZTASA");

        // 4) Előkészítés: rovar bénítása
        rovar.setBenulas(true);
        assertTrue(rovar.getBenulas(), "A rovar legyen bénult állapotban");

        // 5) Hatás meghívása – gombafonal megeszi a rovart
        fonal.rovarElfogyasztas(rovar);

        // 6) Ellenőrzés: rovar eltűnt, gombatest létrejött T2-n
        assertFalse(t2.getRovarok().contains(rovar), "A rovarnak el kellett tűnnie T2-ről");
        assertFalse(rovar.getRovarasz().getRovarok().contains(rovar), "A rovarnak el kellett tűnnie a rovarász alól");
        assertNotNull(t2.getGombatest(), "T2-n új gombatestnek kellett létrejönnie");
        assertEquals(Gombatest.class, t2.getGombatest().getClass(), "Az új gombatest sima Gombatest típusú legyen");

        // 7) Kiírás: végállapot
        System.out.println("=== PALYA10 VÉGE ===");
        palya10.forEach(System.out::println);
    }
}
