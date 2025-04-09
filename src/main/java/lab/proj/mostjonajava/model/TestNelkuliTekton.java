package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestNelkuliTekton extends Tekton{

    /**
     * A tekton konstruktora.
     */
    public TestNelkuliTekton() {
        super(-1);
        hivasLog("TestNelkuliTekton()", List.of(), 1);
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

    /**
     * Nem helyezhető rá gombatest
     * @param gombatest
     */
    @Override
    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest"), 0);
        log("Gombatest beallitasa sikertelen");
    }
}
