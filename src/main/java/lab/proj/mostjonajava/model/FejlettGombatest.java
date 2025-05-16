package lab.proj.mostjonajava.model;

import java.util.Random;

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

        // FONTOS! A tektonnak vissza kell mutatnia erre a példányra
        if (getTekton() != null) {
            getTekton().setGombatest(this);
        }
    }

    /**
     * Felulirja a gombatest spora kilovo metodusat,
     * a szomszedos tektonok szomszedaira is tud sporat loni.
     * //@param celTekton
     * //@param mennyiseg
     */
    @Override
    public void sporaKiloves(Tekton celTekton, int mennyiseg) {
        // ellenőrizzük,hogy a cél tekton közvetlen szomszéd-e
        boolean direktSzomszed = getTekton().szomszedossagEllenorzese(celTekton);
        // ellenőrizzük, hogy a cél tekton másodfokú szomszéd-e
        boolean masodikFoku = false;
        for (Tekton tekton : getTekton().getSzomszedosTektonok()) {
            if (tekton.szomszedossagEllenorzese(celTekton))  {
                masodikFoku = true;
            }
        }
        // most már engedi a közvetlen és a másodikfokú szomszédot is, de elegendő spóra szükséges hozzá
        if (!(direktSzomszed || masodikFoku) || getKilohetoSporakSzama() < mennyiseg) {
            log("Spora kiloves sikertelen.");
            return;
        }

        // kilőhető spórák csökkentése, elszórt spórák számának növelése
        setKilohetoSporakSzama(getKilohetoSporakSzama() - mennyiseg);
        setElszortSporakSzama(getElszortSporakSzama() + mennyiseg);

        Random rand = new Random();
        // a megadott számú spórát véletlenszerűen legeneráljuk és hozzárendeljük a céltektonhoz
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
            log("Spora kiloves sikeres.");
        }
    }
}
