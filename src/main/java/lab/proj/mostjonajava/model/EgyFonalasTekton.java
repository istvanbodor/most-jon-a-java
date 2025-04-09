package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class EgyFonalasTekton extends Tekton {

    /**
     * A tekton konstruktora.
     */
    public EgyFonalasTekton() {
        super(-1);
        hivasLog("EgyFonalasTekton()", List.of(), 1);
    }

    /**
     * A tekton kettetoresenek megvalositasa.
     */
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
     * Ha már van rajta gombafonál akkor nem ad hozzá újat.
     * @param fonal
     */
    @Override
    public void setGombafonal(GombaFonal fonal) {
        hivasLog("setGombafonal(GombaFonal fonal)", List.of("fonal: Gombafonal"), 1);
        if(this.getGombafonalak().isEmpty()){
            log("Fonal hozzaadasa sikeres volt.");
            getGombafonalak().add(fonal);
        }
        else log("Fonal hozzaadasa sikertelen volt.");

    }
}
