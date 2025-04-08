package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
        this.id = nextId++;
        this.gombatestek = new ArrayList<>();
        hivasLog("Gombasz(String nev)", List.of("nev: String - " + nev), 1);
        log("Gombasz letrejott");
    }

    /**
     * Egy új gombatest létrehozását valósítja meg.
     * @param tekton
     */
    public void gombaTestNovesztes(Tekton tekton) {
        hivasLog("gombaTestNovesztes(Tekton tekton)", List.of("tekton: Tekton"), 1);

        if (tekton.gombatestNoveszthetoE()) {
            // 3 spóra törlése
            for (int i = 0; i < 3 && tekton.getSporaSzam() > 0; i++) {
                tekton.sporaTorlese(tekton.getSporak().get(0));
            }

            // új gombatest létrehozása
            Gombatest gombatest = new Gombatest();
            gombatest.setTekton(tekton);
            gombatest.setGombasz(this);
            tekton.setGombatest(gombatest);
            this.gombatestek.add(gombatest);

            // pont növelése
            setPont(getPont() + 1);
            log("Gombatest novesztese sikeres volt. Gombasz pontja novelve.");
        } else {
            log("Nem lehet gombatestet noveszteni a megadott tektonon.");
        }
    }
    /**
     * Egy adott gombatest tovább fejlődését valósítja meg.
     * @param gombatest
     */
    public void gombaTestFejlesztes(Gombatest gombatest) {
        hivasLog("gombaTestFejlesztes(Gombatest gombatest)", List.of("gombatest: Gombatest"), 1);
    
        Tekton tekton = gombatest.getTekton();
    
        if (tekton != null && tekton.gombatestFejleszthetoE()) {
            // 3 spóra törlése
            for (int i = 0; i < 3 && tekton.getSporaSzam() > 0; i++) {
                tekton.sporaTorlese(tekton.getSporak().get(0));
            }
    
            FejlettGombatest fejlett = new FejlettGombatest(gombatest);
            log("Gombatest fejlesztese sikeres volt.");
        } else {
            log("Nem lehet fejleszteni a megadott gombatestet.");
        }
    }

    public List<Gombatest> getGombatestek() {
        return gombatestek;
    }

    public void setGombatest(Gombatest gombatest) {
        this.gombatestek.add(gombatest);
    }

    public void gombatestTorles(Gombatest gombatest) {
        this.gombatestek.remove(gombatest);
    }
}
