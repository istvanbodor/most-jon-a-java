package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class LassitoSpora extends Spora {
    
    /**
     * Lassito spora konsturktora.
     */
    public LassitoSpora() {
        super(15);
        log("Lassito spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a lassitas.
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        rovar.setLepesSzam(rovar.getLepesSzam()-1);
        log("Lassito spora megtette a hatasat.");
    }
}
