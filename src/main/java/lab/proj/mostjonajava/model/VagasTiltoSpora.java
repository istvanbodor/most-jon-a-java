package lab.proj.mostjonajava.model;


import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
//@Data
public class VagasTiltoSpora extends Spora {

    /**
     * Vagas tilto spora konstuktora.
     */
    public VagasTiltoSpora() {
        super(15);
        log("Vagastilto spora letrejott.");
    }

    /**
     * Kifejti a hatasat a rovarra, ami a rovarok vagas kepessegenek korlatozasa.
     * @param rovar
     */
    @Override
    public void hatasKifejtese(Rovar rovar) {
        rovar.setVagoKepesseg(false);
        log("Vagas tilto spora megtette a hatasat.");
    }
}
