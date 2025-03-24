package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestNelkuliTekton extends Tekton{
    /**
     * A tekton konstruktora.
     */
    public Tekton ujTektonLetrehozasa() { return null; }
    /**
     * A tekton kettetoresenek megvalositasa.
     */
    public void ketteTores() {}
}
