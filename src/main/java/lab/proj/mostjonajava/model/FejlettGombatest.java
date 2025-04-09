package lab.proj.mostjonajava.model;

import java.util.List;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FejlettGombatest extends Gombatest {
    
    /**
     * Fejlett gombatest konstuktora.
     * @param eredeti
     */
    public FejlettGombatest(Gombatest eredeti) {
        super();
        setTekton(eredeti.getTekton());
        setGombasz(eredeti.getGombasz());
        setKilohetoSporakSzama(eredeti.getKilohetoSporakSzama());
        setElszortSporakSzama(eredeti.getElszortSporakSzama());
        setNoveszthetoFonalakSzama(eredeti.getNoveszthetoFonalakSzama());
        log("FejlettGombatest letrejott.");
    }
    
    /**
     * Felulirja a gombatest spora kilovo metodusat,
     * a szomszedos tektonok szomszedaira is tud sporat loni.
     * @param tekton
     */
    @Override
    public void sporaKiloves(Tekton celTekton, int mennyiseg) {
        hivasLog("sporaKiloves(Tekton tekton, int mennyiseg)", List.of("tekton: Tekton", "mennyiseg: int"), 0);

        if (!getTekton().szomszedSzomszedEllenorzese(celTekton) || getKilohetoSporakSzama() < mennyiseg) {
            log("Spora kilovese sikertelen.");
            return;
        }

        setKilohetoSporakSzama(getKilohetoSporakSzama() - mennyiseg);
        setElszortSporakSzama(getElszortSporakSzama() + mennyiseg);

        Random rand = new Random();
        for (int i = 0; i < mennyiseg; i++) {
            Spora spora = switch (rand.nextInt(6)) {
                case 0 -> new BenitoSpora();
                case 1 -> new VagasTiltoSpora();
                case 2 -> new OsztodoSpora();
                case 3 -> new LassitoSpora();
                case 4 -> new GyorsitoSpora();
                default -> new SimaSpora();
            };
            celTekton.getSporak().add(spora);
        }
        log("Spora(k) kilovese sikeres volt: " + mennyiseg + " db");
    }
}
