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

    /**
     * Konstruktor felül írasa, lista van leétre hozva benne a Gombasz gombatestjeit tároljuk.
     * @param nev
     */
    public Gombasz(String nev) {
        super(nev);
//        this.gombatestek = new ArrayList<>();
        hivasLog("Gombasz(String nev)", List.of("nev: String - " + nev), 1);
//        id = nextId++;
        log("Gombasz letrejott");
    }

    /**
     * Kiválaszthatjuk, hogy melyik tektonon szeretnénk műveletet vegre hajtani.
     * @return
     */
    public Tekton tektonKivalasztasa() {
        log("Tekton kivalasztasa megtortent");
        return null;
    }

    /**
     * Növeli a gombász pontszámát.
     * @param ertek
     */
    public void pontNovelese(int ertek) {}

    /**
     * Egy új gombatest létrehozását valósítja meg.
     * @param tekton
     */
    public void gombaTestNovesztes(Tekton tekton) {}

    /**
     * Egy adott gombatest tovább fejlődését valósítja meg.
     * @param gombatest
     */
    public void gombaTestFejlesztes(Gombatest gombatest) {}
}
