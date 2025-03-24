package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Jatekos {
    private String nev;
    private int pont;
    private boolean aktiv;

    /**
     * A jatekos konstruktora
     * @param nev A jatekos neve
     */
    public Jatekos(String nev) {
        this.nev = nev;
        this.pont = 0;
        this.aktiv = true;
    }

    /**
     * Kivalaszt egy tektont.
     */
    public Tekton tektonKivalasztasa() { return null; }

    /**
     * A jatekosok pontszamat lehet vele novelni a parameterben megadott ertekkel.
     * @param ertek
     */
    public void pontNovelese(int ertek) {}
}
