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

        TestNelkuliTekton ujTekton = new TestNelkuliTekton();
        getSzomszedosTektonok().add(ujTekton); // minek legyen még a szomszédja?
        
        // gombatest törlése a tektonról és önmagát is töröljük a fonalaival (ezért elpusztul függvény)
        if (this.getGombatest() != null) {
            this.getGombatest().elpusztulas();  
            this.setGombatest(null); 
        }

        // egyéb gombafonal törlése a tektonról
        List<GombaFonal> torlendoFonalak = new ArrayList<>(this.getGombafonalak());
        for (GombaFonal fonal : torlendoFonalak) { this.getGombafonalak().remove(fonal); }

        // spórák törlése a tektonról
        List<Spora> torlendoSporak = new ArrayList<>(this.getSporak());
        for (Spora spora : torlendoSporak) { this.getSporak().remove(spora);  }

        log("A tekton kettetort.");
    }

    //TODO
    //fonal törlésnél alap esetbe nem tűnik el a fonál viszont ha ezt a fonalat direkt elvágják akkor igen
    // + ha fonalat törlünk akkor ez esetben a másik tektonról még mindig törlődik a fonál kivéve ha az is ilyen típusú
    /**
     * Fonal törlése esetén itt nem törlődik a fonál
     * @param fonal
     */
    @Override
    public void fonalTorlese(GombaFonal fonal) {
        hivasLog("fonalTorlese(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        log("Fonal torles sikerleten.");
    }
}
