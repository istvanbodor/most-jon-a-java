package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class VagasTiltoSpora extends Spora {

    public VagasTiltoSpora() {
        log("Vagasbenito spora letrejotte.");
    }
    /**
     * Kifejti a hatasat a rovarra, ami a rovarok vagas kepessegenek korlatozasa.
     */
    public void hatasKifejtese() {
        log("Vagasbenito spora megtette a hatasat.");
    }

    /**
     * Uj benito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
