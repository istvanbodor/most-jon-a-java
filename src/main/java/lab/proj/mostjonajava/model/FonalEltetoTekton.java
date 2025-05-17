package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;

public class FonalEltetoTekton extends Tekton {
    
    /**
     * A tekton konstruktora.
     */
    public FonalEltetoTekton() {
        super(-1);
    }

    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public List<Tekton> ketteTores() {
    
        // uj tekton letrehozasa
        FonalEltetoTekton ujTekton = new FonalEltetoTekton();
        getSzomszedosTektonok().add(ujTekton);
        ujTekton.getSzomszedosTektonok().add(this);
        // gombatest elpusztitasa, ha van rajta
        if (this.getGombatest() != null) {
            this.getGombatest().elpusztulas();
            this.setGombatest(null);
        }
    
        // minden fonal torlese a tektonrol
        List<GombaFonal> fonalak = new ArrayList<>(this.getGombafonalak());
        for (GombaFonal fonal : fonalak) {
            fonal.getGombatest().fonalTorles(fonal);
        }
    
        // minden spora torlese a tektonbol
        this.getSporak().clear();
        log("A tekton kettetort.");
        return List.of(this, ujTekton);
    }

    /**
     * Fonal törlése esetén itt nem törlődik a fonál
     * @param fonal
     */
    @Override
    public void fonalTorlese(GombaFonal fonal) {
        log("Fonal torles sikertelen.");
    }
}
