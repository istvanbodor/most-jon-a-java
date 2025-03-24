package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;

@Data
public abstract class Tekton {

    private static int nextId = 1;

    private int id;
    private List<Tekton> szomszedosTektonok;

    private Rovar rovar;

    private Gombasz gombasz;

    private List<GombaFonal> gombafonalak;

    private Gombatest gombatest;

    private List<Spora> sporak;
    private int sporaSzam;
    private int fonalakElettartama;


    /**
     * Konstruktor felülírása.
     */
    public Tekton() {
        id = nextId++;
        szomszedosTektonok = new ArrayList<>();
        rovar =null;
        gombasz =null;
        gombafonalak = new ArrayList<>();
        gombatest =null;
        sporak = new ArrayList<>();
        sporaSzam = 0;
        fonalakElettartama = 1;
        log("Tekton letrejott.");
    }

    /**
     * Tekton konstruktor.
     * @return
     */
    public abstract Tekton ujTektonLetrehozasa();

    /**
     * Egy tekton ketté törik.
     */
    public abstract void ketteTores();

    /**
     * Jelzi, hogy van-e fonal a két tekton között (amin áll és ami a paraméter).
     * @param tekton
     * @return
     */
    public boolean vanFonalKozottuk(Tekton tekton) {
        log("vanFonalKozottuk ellenorizve.");
        return false; }

    /**
     * Hozzáad egy fonalat a tektonhoz.
     * @param fonal
     */
    public void fonalHozzaadasa(GombaFonal fonal) {
        log("Fonal hozzaadva.");
    }

    /**
     * Törlődik a megadott fonal.
     * @param fonal
     */
    public void fonalTorlese(GombaFonal fonal) {
        log("fonalTorlese sikeres volt.");
    }

    /**
     * Minden fonal elszakad, ami az adott tektonhoz tertozott.
     */
    public void mindenFonalElszakadasa() {}

    /**
     * Hozzáad egy spórát.
     * @param spora
     */
    public void sporaHozzaadasa(Spora spora) {}

    /**
     * Töröl egy spórát.
     * @param spora
     */
    public void sporaTorlese(Spora spora) {
        log("sporaTorlese sikeres volt.");
    }

    /**
     * Bizonyos tektonoknál (pl. Eltűnő fonalas) számolni kell az eltelt időt, ezt valósítja meg ez a fügvény.
     */
    public void eletIdoCsokkentes( GombaFonal gombafonal) {
        fonalakElettartama--;

        if (fonalakElettartama <= 0) {
            gombafonal.fonalTorlese(gombafonal);
            fonalakElettartama = 1;
        }

        log("Eletido csokkentve.");
    }

    /**
     * Ellenörzi, hogy növeszthető-e a tektonon gombafonal.
     * @return
     */
    public boolean gombafonalNoveszthetoE() {
        log("Gombafonal noveszthetosege megvizsgalva.");
        return false; }

    /**
     * Ellenörzi, hogy növeszthető-e gombatest az adott tekton.
     * @return
     */
    public boolean gombatestNoveszthetoE() { return false; }

    /**
     * Áthelyezi a rovart a megadott tektonra.
     * @param tekton
     */
    public void rovarMozgasa(Tekton tekton) {}

    /**
     * Leellenörzi, hogy önmaga-e.
     * @param tekton
     * @return
     */
    public boolean egyenlo(Tekton tekton) {
        log("Egyenloseg megvizsgalva.");
        return false; }
}
