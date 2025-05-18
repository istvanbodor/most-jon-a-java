package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Gombasz extends Jatekos {

    public static int nextId = 1;
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
        log("Gombasz letrejott");
    }

    /**
     * Egy új gombatest létrehozását valósítja meg.
     * @param tekton
     */
    public void gombaTestNovesztes(Tekton tekton) {

        if (tekton.gombatestNoveszthetoE()) {
            // 3 spóra törlése

            Gombatest ujGombatest = new Gombatest(tekton, this);
           if(tekton.setGombatest(ujGombatest)) {
               gombatestek.add(ujGombatest);
               if(tekton.getGombatest() != null)
               {   setPont(getPont() + 1);
                   for (int i = 0; i < 3 && !tekton.getSporak().isEmpty(); i++) {
                       tekton.getSporak().remove(tekton.getSporak().get(0));
                   }
                   log("Gombatest novesztese sikeres.");
               }
           }
           else {
               log("Nem lehet gombatestet noveszteni a megadott tektonon.");
           }
        } else {
            log("Nem lehet gombatestet noveszteni a megadott tektonon.");
        }
    }

    /**
     * Egy adott gombatest tovább fejlődését valósítja meg.
     * @param gombatest
     */
    public void gombaTestFejlesztes(Gombatest gombatest) {

        Tekton tekton = gombatest.getTekton();
        if (tekton.gombatestFejleszthetoE()) {
            // 3 spóra törlése
            for (int i = 0; i < 3 && !tekton.getSporak().isEmpty(); i++) {
                tekton.getSporak().remove(tekton.getSporak().get(0));
            }

            // új fejlett gombatestet hozunk létre a meglévő példány alapján
            FejlettGombatest ujGombatest = new FejlettGombatest(gombatest);
            gombatest.getTekton().setGombatest(ujGombatest);
            gombatest.getGombasz().getGombatestek().add(ujGombatest);
            gombatest.getGombasz().getGombatestek().remove(gombatest);
            // átvesszük a régi gombatest gombafonalait, és átállítjuk az újra
            for (GombaFonal fonal : gombatest.getGombaFonalak()) {
                fonal.setGombatest(ujGombatest);
                ujGombatest.getGombaFonalak().add(fonal);
            }

            // a régi gombatestet elpusztítjuk
          //  gombatest.elpusztulas();
            log("Gombatest fejlesztese sikeres.");
        } else {
            log("Nem lehet fejleszteni a megadott gombatestet.");
        }
    }

    /**
     * Visszatér a gombász id-jával.
     * @return 
     */
    public int getId(){ return id; }

    /**
     * Visszatér a gombász gombatestjeivel.
     * @return 
     */
    public List<Gombatest> getGombatestek() {
        return gombatestek;
    }
}
