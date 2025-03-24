package lab.proj.mostjonajava.model;

import lombok.Data;

import static lab.proj.mostjonajava.utils.Logger.log;

@Data
public class LassitoSpora extends Spora {

    public LassitoSpora() {
        log("Lassito spora letrejotte.");
    }
    /**
     * Kifejti a hatasat a rovarra, ami a lassitas.
     */
    public void hatasKifejtese() {
        log("Lassito spora megtette a hatasat.");
    }

    /**
     * Uj lassito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
