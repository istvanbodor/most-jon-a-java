package lab.proj.mostjonajava.model;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
//@Data
public class OsztodoSpora extends Spora {

    /**
     * Osztodo spora konstuktora.
     */
    public OsztodoSpora() {
        super(5);
        log("Osztodo spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami az osztodas.
     * @param rovar
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        Rovar ujRovar = new Rovar(rovar.getTekton(), rovar.getRovarasz());
        rovar.getTekton().getRovarok().add(ujRovar);
        rovar.getRovarasz().getRovarok().add(ujRovar);
        log("Osztodo spora megtette a hatasat.");
    }
}

