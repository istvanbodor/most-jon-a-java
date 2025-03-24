package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;

@EqualsAndHashCode(callSuper = true)
@Data
public class Gombasz extends Jatekos {
    private static int nextId;
    private int id;
    private List<Gombatest> gombatestek;

    public Gombasz(String nev) {
        super(nev);
//        this.gombatestek = new ArrayList<>();
        hivasLog("Gombasz(String nev)", List.of("nev: String - " + nev), 1);
//        id = nextId++;
        log("Gombasz letrejott");
    }

    public Tekton tektonKivalasztasa() { return null; }
    public void pontNovelese(int ertek) {}
    public void gombaTestNovesztes(Tekton tekton) {}
    public void gombaTestFejlesztes(Gombatest gombatest) {}
}
