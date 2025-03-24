package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
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
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        hivasLog("benulas()", List.of(), 1);
        log("A rovar megbenult");
        log("Benito spora megtette a hatasat.");
    }

    /**
     * Uj benito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
