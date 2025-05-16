package lab.proj.mostjonajava.model;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
//@Data
public class GyorsitoSpora extends Spora {

    /**
     * Gyorsito spora konsturktora.
     */
    public GyorsitoSpora() {
        super(5);
        log("Gyorsito spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a gyorsitas.
     * @param rovar
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        rovar.setLepesSzam(rovar.getLepesSzam()+1);
        log("Gyorsito spora megtette a hatasat.");
    }
}
