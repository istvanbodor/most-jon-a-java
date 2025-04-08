package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;

@Data
public abstract class Tekton {

    private static int nextId = 1;
    private int id;
    private int fonalakElettartama = -1;

    private List<Tekton> szomszedosTektonok;
    private List<Rovar> rovarok;
    private List<GombaFonal> gombafonalak;
    private List<Spora> sporak;

    private Gombatest gombatest;

    /**
     * Alapértelmezett konstruktor.
     */
    public Tekton(int eletTartam) {
        fonalakElettartama = eletTartam;
        this.id = nextId++;
        this.szomszedosTektonok = new ArrayList<>();
        this.rovarok = new ArrayList<>();
        this.sporak = new ArrayList<>();
        this.gombafonalak = new ArrayList<>();
        log("Tekton letrejott.");
    }

    /**
     * Egy tekton ketté törik.
     */
    public abstract void ketteTores();

     /**
     * Ellenőrzi, hogy növeszthető-e gombatest az adott tekton.
     *
     * @return
     */
    public boolean gombatestNoveszthetoE() {
        hivasLog("gombatestNoveszthetoE()", List.of(), 0);
        if (sporak.size() < 3 || gombafonalak.isEmpty()) return false;
        for (GombaFonal fonal : gombafonalak) {
            if (fonal.getHonnan() != this || fonal.getHova() != this) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy fejleszthető e a tektonon található gombatest
     * @return
     */
    public boolean gombatestFejleszthetoE() {
        hivasLog("gombatestFejleszthetoE()", List.of(), 0);
        return sporak.size() >= 3 && gombatest != null;
    }

    public boolean szomszedossagEllenorzese(Tekton tekton) {
        hivasLog("szomszedossagEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        return szomszedosTektonok.contains(tekton);
    }

    public boolean szomszedSzomszedEllenorzese(Tekton tekton) {
        hivasLog("szomszedSzomszedEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        for (Tekton szomszed : szomszedosTektonok) {
            if (szomszed.getSzomszedosTektonok().contains(tekton)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Jelzi, hogy van-e fonal a két tekton között (amin áll és ami a paraméter).
     *
     * @param tekton
     * @return
     */
    public boolean vanFonalKozottuk(Tekton tekton) {
        hivasLog("vanFonalKozottuk(Tekton tekton)", List.of("tekton: Tekton"), 1);
        if (this.equals(tekton)) return false;
        for (GombaFonal fonal : gombafonalak) {
            if ((fonal.getHonnan().equals(this) && fonal.getHova().equals(tekton)) || (fonal.getHova().equals(this) && fonal.getHonnan().equals(tekton))) {
                return true;
            }
        }
        return false;
    }

    public int getSporaSzam(){ return sporak.size(); }

    public void rovarTorlese(Rovar rovar) {
        hivasLog("rovarTorlese(Rovar rovar)", List.of("rovar: Rovar"), 1);
        rovarok.remove(rovar);
    }

    public void sporaTorlese(Spora spora) {
        hivasLog("sporaTorlese(Spora spora)", List.of("spora: Spora"), 1);
        sporak.remove(spora);
    }

    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest - " + gombatest.toString()), 0);
        this.gombatest = gombatest;
        log("Gombatest beallitasra kerult");
    }

    public void setRovar(Rovar rovar) {
        hivasLog("setRovar(Rovar rovar)", List.of("rovar: Rovar"), 1);
        rovarok.add(rovar);
        log("Rovar beallitasra kerult a tektonon");
    }

    /**
     * Törlődik a megadott fonal.
     *
     * @param fonal
     */
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        new Gombatest().fonalTorles(fonal);
        log("Fonal torlese sikeres volt.");
    }

    public List<Tekton> getSzomszedosTektonok() {
        return szomszedosTektonok;
    }

    public void setSzomszedosTekton(Tekton tekton) {
        this.szomszedosTektonok.add(tekton);
    }

    public List<Rovar> getRovarok() {
        return rovarok;
    }

    public void setRovarok(Rovar rovar) {
        this.rovarok.add(rovar);
    }

    public List<GombaFonal> getGombafonalak() {
        return gombafonalak;
    }

    /**
     * Hozzáad egy fonalat a tektonhoz.
     *
     * @param fonal
     */
    public void setGombafonal(GombaFonal fonal) {
        this.gombafonalak.add(fonal);
    }

    public List<Spora> getSporak() {
        return sporak;
    }

    /**
     * Hozzáad egy spórát.
     *
     * @param spora
     */
    public void setSpora(Spora spora) {
        this.sporak.add(spora);
        log("Spora hozzaadasa sikeres volt.");
    }

}
