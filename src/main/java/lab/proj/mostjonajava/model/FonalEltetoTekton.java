package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

public class FonalEltetoTekton extends Tekton {
    
    /**
     * A tekton konstruktora.
     */
    public FonalEltetoTekton() {
        super(-1);
        log("FonalEltetoTekton letrejott.");
    }
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);

        FonalEltetoTekton ujTekton = new FonalEltetoTekton();
        
        this.getGombatest().elpusztulas(true);
        this.setGombatest(null);
        List<GombaFonal> torlendoFonalak = new ArrayList<>(this.getGombafonalak());
        for (GombaFonal fonal : torlendoFonalak) {
            this.fonalTorlese(fonal); // tektonról törlés
            if (this.getGombatest() != null) {
                this.getGombatest().fonalTorles(fonal); // gombatestről törlés
            }
        }

        List<Spora> torlendoSporak = new ArrayList<>(this.getSporak());
        for (Spora spora : torlendoSporak) {
            this.sporaTorlese(spora);
        }

        log("Kettetoeres befejezodott: sporak es fonalak torlodtek, gombatest leválasztva.");
    }

    @Override
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        log("Fonal torlese sikeres volt.");
    }
}
