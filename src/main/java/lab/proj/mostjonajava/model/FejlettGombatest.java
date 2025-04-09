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
    
        log("FejlettGombatest példány létrejött (inicializálva, de még nincs csatolva).");
    }
    
    /**
     * Felulirja a gombatest spora kilovo metodusat,
     * a szomszedos tektonok szomszedaira is tud sporat loni.
     * @param tekton
     */
    @Override
    public void sporaKiloves(Tekton celTekton, int mennyiseg) {
        hivasLog("sporaKiloves(Tekton tekton, int mennyiseg)", List.of("tekton: Tekton", "mennyiseg: int"), 0);

        if (!getTekton().szomszedSzomszedEllenorzese(celTekton)) {
            log("A kiválasztott tekton nem szomszédos a gombatesttel.");
            return;
        }

        if (getKilohetoSporakSzama() < mennyiseg) {
            log("Nincs eleg kiloheto spora. Maradek: " + getKilohetoSporakSzama());
            return;
        }

        setKilohetoSporakSzama(getKilohetoSporakSzama() - mennyiseg);
        setElszortSporakSzama(getElszortSporakSzama() + mennyiseg);

        Random rand = new Random();
        for (int i = 0; i < mennyiseg; i++) {
            int valasztas = rand.nextInt(6);
            Spora spora;
            switch (valasztas) {
                case 0 -> spora = new BenitoSpora();
                case 1 -> spora = new VagasTiltoSpora();
                case 2 -> spora = new OsztodoSpora();
                case 3 -> spora = new LassitoSpora();
                case 4 -> spora = new GyorsitoSpora();
                default -> spora = new SimaSpora();
            }
            celTekton.getSporak().add(spora);
        }
        log("Spora(k) kilovese sikeres volt: " + mennyiseg + " db");
    }
}
