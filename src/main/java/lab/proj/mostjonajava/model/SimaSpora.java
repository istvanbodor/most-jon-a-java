package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimaSpora extends Spora {
    /**
     * Kifejti a hatasat a rovarra, ami jelenleg csak a tapanyagszint noveles.
     */
    public void hatasKifejtese() {}

    /**
     * A sima spora konstruktora.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
