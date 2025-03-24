package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BenitoSpora extends Spora {
    /**
     * Kifejti a hatasat a rovarra, ami a benitas.
     */
    public void hatasKifejtese() {}

    /**
     * A benito spora konstruktora.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
