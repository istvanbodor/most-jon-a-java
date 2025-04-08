package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class BenitoSpora extends Spora {

    /**
     * Benito spora konsturktora.
     */
    public BenitoSpora() {
        super(20);
        hivasLog("BenitoSpora()", List.of(), 0);
        log("Benito spora letrejotte.");
    }
    /**
     * Kifejti a hatasat a rovarra, ami a benitas.
     */
    public void hatasKifejtese(Rovar rovar) {
        hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        if (rovar != null) {
            rovar.setBenulas(true);
            log("Benito spora megtette a hatasat.");
        }
    }

}
