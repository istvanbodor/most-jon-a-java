package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Jatekos {
    private String nev;
    private int pont;
    private boolean aktiv;

    public Jatekos(String nev) {
//        this.nev = nev;
//        this.pont = 0;
//        this.aktiv = true;
    }

    public Tekton tektonKivalasztasa() { return null; }
    public void pontNovelese(int ertek) {}
}
