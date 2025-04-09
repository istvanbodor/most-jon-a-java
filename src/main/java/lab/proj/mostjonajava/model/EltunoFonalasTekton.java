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
        hivasLog("EltunoFonalasTekton()", List.of(), 1);
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
    //ezzel mi legyen? inkább nem a controllerben kéne?
    // + valami függvény overrideja nem kell ide? mert csak egy idő után tünnek el, inkább azt is a controllerben?
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
