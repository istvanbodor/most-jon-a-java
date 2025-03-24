package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.*;


@EqualsAndHashCode(callSuper = true)
@Data
public class EgyFonalasTekton extends Tekton {
    /**
     * A tekton konstruktora.
     */
    public Tekton ujTektonLetrehozasa() { return null; }

    /**
     * A tekton kettetoresenek megvalositasa.
     */
    public void ketteTores() {
        hivasLog("ketteTores()", List.of(), 0);
        log("A tekton kettetort");
    }
}
