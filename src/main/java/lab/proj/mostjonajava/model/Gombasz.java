package lab.proj.mostjonajava.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;

@Data
public class Gombasz extends Jatekos {
    private List<Gombatest> gombatestek;

    public Gombasz(String nev) {
        super(nev);
        this.gombatestek = new ArrayList<>();
        hivasLog("Gombasz(String nev)", List.of("nev: String - " + nev), 1);
    }

    public Tekton tektonKivalasztasa() { return null; }
    public void pontNovelese(int ertek) {}
    public void gombaTestNovesztes(Tekton tekton) {}
    public void gombaTestFejlesztes(Gombatest gombatest) {}
}
