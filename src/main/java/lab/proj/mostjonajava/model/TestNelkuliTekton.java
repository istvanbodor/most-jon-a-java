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
        log("TestNelkuliTekton letrejott.");
    }
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);

        TestNelkuliTekton ujTekton = new TestNelkuliTekton();
        
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
    public void setGombatest(Gombatest gombatest) {
        hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest - " + gombatest.toString()), 0);
        log("Gombatest beallitasa sikertelen");
    }
}
