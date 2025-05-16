package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Jatekos {

    private String nev;
    private int pont = 0;
    private boolean aktiv = true;

    /**
     * A jatekos konstruktora
     * @param nev A jatekos neve
     */
    Jatekos(String nev) { this.nev = nev; }

    /**
     * A jatekosok pontszamat lehet lekérdezni vele.
     */
    public int getPont() {
        return pont;
    }

    /**
     * A jatekosok pontszamat lehet vele novelni a parameterben megadott ertekkel.
     * @param ertek
     */
    public void setPont(int ertek) {
        pont = ertek;
    }
}
