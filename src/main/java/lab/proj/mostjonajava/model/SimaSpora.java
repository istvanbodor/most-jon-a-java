package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimaSpora extends Spora {

    public SimaSpora() {
        log("Sima spora letrejotte.");
    }
    /**
     * Kifejti a hatasat a rovarra, ami jelenleg csak a tapanyagszint noveles.
     */
    public void hatasKifejtese() {
        log("Sima spora megtette a hatasat.");
    }

    /**
     * Uj sima spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
