package lab.proj.mostjonajava.model;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EltunoFonalasTekton extends Tekton {

    public EltunoFonalasTekton() {
        super();
    }
    /**
     * A tekton konstruktora.
     */
    public Tekton ujTektonLetrehozasa() { return null; }
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    public void ketteTores() {}

    /**
     * Bizonyos tektonoknál (pl. Eltűnő fonalas) számolni kell az eltelt időt, ezt valósítja meg ez a fügvény.
     */
    public void eletIdoCsokkentes(GombaFonal gombafonal) {
        hivasLog("eletIdoCsokkentes(GombaFonal gombafonal)", List.of("gombafonal: Gombafonal"), 0);
        fonalakElettartama--;
        log("Eletido csokkentve.");
        if (fonalakElettartama <= 0) {
            gombafonal.fonalTorlese(gombafonal);
            fonalakElettartama = 1;
        }

    }
}
