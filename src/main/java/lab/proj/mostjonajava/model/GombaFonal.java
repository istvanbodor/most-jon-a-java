package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;


@Data
public class GombaFonal {
    private Tekton honnan;
    private Tekton hova;
    private Gombatest gombatest;


    public GombaFonal()
    {}

    public GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest) {
        hivasLog("GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest)", List.of(), 2);
        log("Fonal letrejott a ket tekton kozott");
    }

    /**
     * Fonalmasiktektonja kitorolve, mert a masik fonal meg van adva
     */

    /**
     * Uj gomba fonalat hoz létre
     */
    public void ujGombaFonalLetrehozasa(Tekton hn, Tekton hv, Gombatest gt) {
    }

    /**
     *Kitörli a paraméternek megadott gombafonalat
     * @param fonal törlendő fonal
     */
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        log("Fonal eltavolitva.");
    }

    /**
     * Ellenőrzi, hogy az adott fonal csatlakozik-e meg a
     *
     * @param fonal a csekkolandó fonal
     * @return
     */
    public boolean vanGombaTestKapcsolat(GombaFonal fonal) {
        log("vanGombaTestKapcsolat ellenorizve.");
        return false;
    }

}
