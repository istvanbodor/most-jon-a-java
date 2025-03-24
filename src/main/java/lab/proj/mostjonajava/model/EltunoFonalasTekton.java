package lab.proj.mostjonajava.model;

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
}
