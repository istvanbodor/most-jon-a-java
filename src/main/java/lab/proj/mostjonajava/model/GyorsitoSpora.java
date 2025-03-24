package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class GyorsitoSpora extends Spora {

    public GyorsitoSpora() {
        log("Gyorsito spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a gyorsitas.
     */
    public void hatasKifejtese() {
        log("Gyorsito spora megtette a hatasat.");
    }

    /**
     * Uj gyorsito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
