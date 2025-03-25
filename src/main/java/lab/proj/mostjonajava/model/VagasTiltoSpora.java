package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
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
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        hivasLog("setVagoKepesseg(boolean vagoKepesseg)", List.of("vagoKepesseg: boolean"), 1);
        log("A rovar vagokepessege megbenult");
        log("Vagasbenito spora megtette a hatasat.");
    }

    /**
     * Uj benito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
