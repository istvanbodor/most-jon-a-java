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
     * Gombatest konstruktora.
     * @param eletTartam
     */
    Tekton(int eletTartam) {
        fonalakElettartama = eletTartam;
        this.id = nextId++;
        this.szomszedosTektonok = new ArrayList<>();
        this.rovarok = new ArrayList<>();
        this.sporak = new ArrayList<>();
        this.gombafonalak = new ArrayList<>();
        log("Tekton letrejott.");
    }

    /**
     * Egy tekton ketté törik. Minden származtatott osztályban külön van implementálva.
     */
    public abstract void ketteTores();

     /**
     * Ellenőrzi, hogy növeszthető-e gombatest az adott tekton.
     * Feltételek: legyen legalább 3 spóra a tektonon és legyen összekötve fonallal.
     * @return
     */
    public boolean gombatestNoveszthetoE() {
        hivasLog("gombatestNoveszthetoE()", List.of(), 0);
        for (GombaFonal fonal : gombafonalak) {
            if ((fonal.getHonnan() == this || fonal.getHova() == this) && sporak.size() >= 3)
                return true;
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy fejleszthető e a tektonon található gombatest
     * Feltételek: legyen legalább 3 spóra a tektonon és gombatest, amit fejleszthetsz.
     * @return
     */
    public boolean gombatestFejleszthetoE() {
        hivasLog("gombatestFejleszthetoE()", List.of(), 0);
        return sporak.size() >= 3 && gombatest != null;
    }

    /**
     * Ellenőrzi, hogy a paraméterben kapott tekton szomszédja-e az adott tektonnak.
     * @param tekton
     * @return
     */
    public boolean szomszedossagEllenorzese(Tekton tekton) {
        hivasLog("szomszedossagEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        return szomszedosTektonok.contains(tekton);
    }

    /**
     * Ellenőrzi, hogy a paraméterben kapott tekton szomszédja vagy a szomszédainak szomszédja-e az adott tektonnak.
     * @param tekton
     * @return
     */
    public boolean szomszedSzomszedEllenorzese(Tekton tekton) {
        hivasLog("szomszedSzomszedEllenorzese(Tekton tekton)", List.of("tekton: Tekton"), 1);
        for (Tekton szomszed : szomszedosTektonok) {
            if (szomszed.getSzomszedosTektonok().contains(tekton)) 
                return true;
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy van-e fonal a két tekton között (amin áll és ami a paraméter).
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

    /**
     * Visszaadja a szomszédos tektonokat.
     * @return
     */
    public List<Tekton> getSzomszedosTektonok() { return szomszedosTektonok; }

    /**
     * Visszaadja a tektonon lévő rovarokat.
     * @return
     */
    public List<Rovar> getRovarok() { return rovarok; }

    /**
     * Visszaadja a tektonont összekötő gombafonalakat.
     * @return
     */
    public List<GombaFonal> getGombafonal() { return gombafonalak; }

    /**
     * Törlődik a megadott fonal - a FonalElteto (ebben fix) és lehet az EltunoFonalas tektonba override-ol
     * @param fonal
     */
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        gombafonalak.remove(fonal);
        log("Fonal torlese sikeres volt.");
    }

    /**
     * Hozzáad egy gombafonalat a tektonhoz. - az Egyfonalas tektonban override-ol
     * @param fonal
     */
    public void setGombafonal(GombaFonal fonal) { this.gombafonalak.add(fonal); }

    /**
     * Visszaadja a tektonon lévő spórákat.
     * @return
     */
    public List<Spora> getSporak() { return sporak; }

    /**
     * Visszaadja a tektonon lévő gombatestet.
     * @return
     */
    public Gombatest getGombatest() { return gombatest; }

    /**
     * Hozzáad egy gombatestet a tektonhoz. - a TestNelkuli tektonban override-ol
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest - " + gombatest.toString()), 0);
        this.gombatest = gombatest;
        log("Gombatest beallitasra kerult");
    }
}
