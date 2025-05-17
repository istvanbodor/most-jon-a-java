package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;
import lab.proj.mostjonajava.game.Jatek;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
//@Data
public class TestNelkuliTekton extends Tekton{

    /**
     * A tekton konstruktora.
     */
    public TestNelkuliTekton() {
        super(-1);
    }

    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public List<Tekton> ketteTores() {
    
        // uj tekton letrehozasa
        TestNelkuliTekton ujTekton = new TestNelkuliTekton();
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
     * Nem helyezhető rá gombatest
     * @param gombatest
     */
    @Override
    public void setGombatest(Gombatest gombatest) {
        log("Gombatest beallitasa sikertelen");
    }
}
