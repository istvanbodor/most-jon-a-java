package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GyorsitoSpora extends Spora {
    /**
     * Kifejti a hatasat a rovarra, ami a gyorsitas.
     */
    public void hatasKifejtese() {}

    /**
     * A gyorsito spora konstruktora.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
