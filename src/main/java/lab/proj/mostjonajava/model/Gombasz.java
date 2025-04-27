package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

//import static lab.proj.mostjonajava.utils.Logger.hivasLog;
//import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
        //hivasLog("Gombasz(String nev)", List.of("nev: String - " + nev), 1);
        this.id = nextId++;
        this.gombatestek = new ArrayList<>();
        //log("Gombasz letrejott");
    }

    /**
     * Egy új gombatest létrehozását valósítja meg.
     * @param tekton
     */
    public void gombaTestNovesztes(Tekton tekton) {
        //hivasLog("gombaTestNovesztes(Tekton tekton)", List.of("tekton: Tekton"), 1);

        if (tekton.gombatestNoveszthetoE()) {
            // 3 spóra törlése
            for (int i = 0; i < 3 && !tekton.getSporak().isEmpty(); i++) {
                tekton.getSporak().remove(tekton.getSporak().get(0));
            }
            Gombatest ujGombatest = new Gombatest(tekton, this);
            tekton.setGombatest(ujGombatest);
            if(tekton.getGombatest() != null) 
            {   setPont(getPont() + 1);
                //log("Gombatest novesztese sikeres.");
            }
        } else {
            //log("Nem lehet gombatestet noveszteni a megadott tektonon.");
        }
    }

    /**
     * Egy adott gombatest tovább fejlődését valósítja meg.
     * @param gombatest
     */
    public void gombaTestFejlesztes(Gombatest gombatest) {
        //hivasLog("gombaTestFejlesztes(Gombatest gombatest)", List.of("gombatest: Gombatest"), 1);
    
        Tekton tekton = gombatest.getTekton();
        if (tekton.gombatestFejleszthetoE()) {
            // 3 spóra törlése
            for (int i = 0; i < 3 && !tekton.getSporak().isEmpty(); i++) {
                tekton.getSporak().remove(tekton.getSporak().get(0));
            }
    
            FejlettGombatest ujGombatest = new FejlettGombatest(gombatest);
            gombatest.getTekton().setGombatest(ujGombatest);
            gombatest.getGombasz().getGombatestek().add(ujGombatest);

            for (GombaFonal fonal : gombatest.getGombaFonalak()) {
                fonal.setGombatest(ujGombatest);
                ujGombatest.getGombaFonalak().add(fonal);
            }

            gombatest.elpusztulas();
            //log("Gombatest fejlesztese sikeres.");
        } else {
            //log("Nem lehet fejleszteni a megadott gombatestet.");
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
        //hivasLog("getGombatestek()", List.of(), 1);
        return gombatestek;
    }
}
