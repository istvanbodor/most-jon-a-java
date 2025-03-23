package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Gombasz extends Jatekos {
    private List<Gombatest> gombatestek;

    public Gombasz(String nev) {
        super(nev);
        this.gombatestek = new ArrayList<>();
    }

    public Tekton tektonKivalasztasa() { return null; }
    public void pontNovelese(int ertek) {}
    public void gombaTestNovesztes(Tekton tekton) {}
    public void gombaTestFejlesztes(Gombatest gombatest) {}
}
