package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
//@Data
public class EltunoFonalasTekton extends Tekton {

    /**
     * A tekton konstruktora.
     */
    public EltunoFonalasTekton() {
        super(3);
    }
    
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public List<Tekton> ketteTores() {

        // uj tekton letrehozasa
        EltunoFonalasTekton ujTekton = new EltunoFonalasTekton();
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
        return List.of(this, ujTekton);
    }
}
