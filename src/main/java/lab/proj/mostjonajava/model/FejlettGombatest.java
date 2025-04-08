package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static lab.proj.mostjonajava.utils.Logger.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class FejlettGombatest extends Gombatest {
    
    public FejlettGombatest(Gombatest eredeti) {
        super();
        this.setTekton(eredeti.getTekton());
        this.setGombasz(eredeti.getGombasz());
        this.setKilohetoSporakSzama(eredeti.getKilohetoSporakSzama());
        this.setElszortSporakSzama(eredeti.getElszortSporakSzama());
        this.setNoveszthetoFonalakSzama(eredeti.getNoveszthetoFonalakSzama());
        this.setGombaFonalak(eredeti.getGombaFonalak());

        // Tektonban kicseréljük
        if (eredeti.getTekton() != null) {
            eredeti.getTekton().setGombatest(this);
        }

        // Gombásznál lecseréljük a listában
        if (eredeti.getGombasz() != null) {
            eredeti.getGombasz().gombatestTorles(eredeti);
            eredeti.getGombasz().setGombatest(this);
        }

        // GombaFonalak frissítése
        for (GombaFonal fonal : this.getGombaFonalak()) {
            fonal.setGombatest(this);
        }

        // Az eredeti példányt "kiürítjük"
        eredeti.elpusztulas(false);

        log("FejlettGombatest letrejott egy Gombatest alapjan.");
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
            celTekton.setSpora(spora);
        }
        log("Spora(k) kilovese sikeres volt: " + mennyiseg + " db");
    }
}
