package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VagasTiltoSpora extends Spora {
    /**
     * Kifejti a hatasat a rovarra, ami a rovarok vagas kepessegenek korlatozasa.
     */
    public void hatasKifejtese() {}

    /**
     * A benito spora konstruktora.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
