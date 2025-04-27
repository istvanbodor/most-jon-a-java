package lab.proj.mostjonajava.model;


//import static lab.proj.mostjonajava.utils.Logger.hivasLog;
//import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
//@Data
public class SimaSpora extends Spora {

    /**
     * Sima spora konstuktora.
     */
    public SimaSpora() {
        super(10);
        //hivasLog("SimaSpora()", List.of(), 1);
        //log("Sima spora letrejotte.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami jelenleg csak a tapanyagszint noveles.
     * @param rovar
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        //hivasLog("hatasKifejtese(Rovar rovar)", List.of("rovar: Rovar"), 0);
        //log("Sima spora megtette a hatasat.");
    }

    
}
