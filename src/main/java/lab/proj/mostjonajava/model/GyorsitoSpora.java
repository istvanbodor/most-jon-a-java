package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
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
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        rovar.lepesSzamNoveles();
        log("Gyorsito spora megtette a hatasat.");
    }

    /**
     * Uj gyorsito spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() { return null; }
}
