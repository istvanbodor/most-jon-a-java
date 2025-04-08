package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EltunoFonalasTekton extends Tekton {

    /**
     * A tekton konstruktora.
     */
    public EltunoFonalasTekton() {
        super(3);
        log("EltunoFonalasTekton letrejott.");
    }
    
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    @Override
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);

        EltunoFonalasTekton ujTekton = new EltunoFonalasTekton();
        
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

    /**
     * Bizonyos tektonoknál (pl. Eltűnő fonalas) számolni kell az eltelt időt, ezt valósítja meg ez a fügvény.
     */
    /*public void eletIdoCsokkentes(GombaFonal gombafonal) {
        hivasLog("eletIdoCsokkentes(GombaFonal gombafonal)", List.of("gombafonal: Gombafonal"), 0);
        fonalakElettartama--;
        log("Eletido csokkentve.");
        if (fonalakElettartama <= 0) {
            gombafonal.fonalTorlese(gombafonal);
            fonalakElettartama = 1;
        }

    }*/
}
