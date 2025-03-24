package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimaSpora extends Spora {

    public SimaSpora() {
    }

    /**
     * Kifejti a hatasat a rovarra, ami jelenleg csak a tapanyagszint noveles.
     */
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        log("Sima spora megtette a hatasat.");
    }

    /**
     * Uj sima spora letrehozasa.
     */
    public Spora ujSporaLetrehozasa() {
        return null;
    }
}
