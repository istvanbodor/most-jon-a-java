package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class GyorsitoSpora extends Spora {

    /**
     * Gyorsito spora konsturktora.
     */
    public GyorsitoSpora() {
        super(5);
        hivasLog("GyorsitoSpora()", List.of(), 0);
        log("Gyorsito spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a gyorsitas.
     */
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        if (rovar != null) {
            rovar.setLepesSzam(rovar.getLepesSzam()+1);
            log("Gyorsito spora megtette a hatasat.");
        }
    }
}
