package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;


@Data
public class GombaFonal {
    private Tekton honnan;
    private Tekton hova;
    private Gombatest gombatest;

    /**
     *
     * @param hn honnan
     * @param hv hova
     * @param gt gombatest
     */
    public GombaFonal(Tekton hn, Tekton hv, Gombatest gt)
    {   List<String> parameterek = new ArrayList<>();
        parameterek.add("honnan: Tekton - " + hn);
        parameterek.add("hova: Tekton - " + hv);
        parameterek.add("gombatest: Tekton - " + gt);
        //hivasLog("GombaFonal(Tekton honnan), (Tekton hova), (Gombatest gombatest)", parameterek, 3);
        honnan = hn; hova = hv; gombatest = gt;
        log ("gombafonal letrejott.");
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
        log("fonalTorlese megtortent");
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
