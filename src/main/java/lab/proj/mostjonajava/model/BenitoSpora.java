package lab.proj.mostjonajava.model;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
//@Data
public class BenitoSpora extends Spora {

    /**
     * Benito spora konsturktora.
     */
    public BenitoSpora() {
        super(20);
        log("Benito spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a benitas.
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        rovar.setBenulas(true); 
        log("Benito spora megtette a hatasat.");
    }
}
