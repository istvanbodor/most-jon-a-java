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
    
        // 1. Új tekton létrehozása
        TestNelkuliTekton ujTekton = new TestNelkuliTekton();
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
     * Nem helyezhető rá gombatest
     * @param gombatest
     */
    @Override
    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest"), 0);
        log("Gombatest beallitasa sikertelen");
    }
}
