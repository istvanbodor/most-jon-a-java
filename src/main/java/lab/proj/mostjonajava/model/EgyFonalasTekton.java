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
    @Override
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);
    
        // uj tekton letrehozasa
        EgyFonalasTekton ujTekton = new EgyFonalasTekton();
        getSzomszedosTektonok().add(ujTekton);
        
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
