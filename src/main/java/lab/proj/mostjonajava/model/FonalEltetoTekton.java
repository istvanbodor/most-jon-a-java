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
        hivasLog("FonalEltetoTekton()", List.of(), 1);
    }

    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);
    
        // 1. Új tekton létrehozása
        TobbFonalasTekton ujTekton = new TobbFonalasTekton();
        getSzomszedosTektonok().add(ujTekton);
        
        // 2. Gombatest elpusztítása ha van
        if (this.getGombatest() != null) {
            this.getGombatest().elpusztulas();
            this.setGombatest(null);
        }
    
        // 3. Minden fonal törlése a tektonról
        List<GombaFonal> fonalak = new ArrayList<>(this.getGombafonalak());
        for (GombaFonal fonal : fonalak) {
            fonal.getGombatest().fonalTorles(fonal); // Teljes törlés mindenhonnan
        }
    
        // 4. Spórák törlése
        this.getSporak().clear();
        log("A tekton kettetort.");
    }

    /**
     * Fonal törlése esetén itt nem törlődik a fonál
     * @param fonal
     */
    @Override
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        log("Fonal torles sikertelen.");
    }
}
