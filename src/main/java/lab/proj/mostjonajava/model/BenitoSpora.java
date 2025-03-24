package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class BenitoSpora extends Spora {

    public BenitoSpora() {
        log("Benito spora letrejotte.");
    }
    /**
     * Kifejti a hatasat a rovarra, ami a benitas.
     */
    public void hatasKifejtese() {
        log("Benito spora megtette a hatasat.");
    }

    /**
     * Uj benito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
