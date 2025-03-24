package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;


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
        hivasLog("GombaFonal(Tekton honnan), (Tekton hova), (Gombatest gombatest)", parameterek, 3);
        honnan = hn; hova = hv; gombatest = gt;}

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
    }

    /**
     *Ellenőrzi, hogy az adott fonal csatlakozik-e meg a
     * @param fonal a csekkolandó fonal
     */
    public void vanGombaTestKapcsolat(GombaFonal fonal) {}

}
