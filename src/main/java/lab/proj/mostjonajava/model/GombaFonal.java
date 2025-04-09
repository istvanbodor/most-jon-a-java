package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
@Data
public class GombaFonal {

    private Tekton honnan;
    private Tekton hova;
    private Gombatest gombatest;

    /**
     * GombaFonal konsturktora.
     * @param honnan
     * @param hova
     * @param gombatest
     */
    public GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest) {
        hivasLog("GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest)", List.of(), 2);
        this.honnan = honnan;
        this.hova = hova;
        this.gombatest = gombatest; 
        log("Fonal letrejott a ket tekton kozott");
    }

    //TODO
    public void rovarElfogyasztas(Rovar rovar){

    }

    //TODO
    public void TestNovesztes(Tekton tekton){

    }

    /**
     * Visszatér a gombafonal egyik tektonjával. 
     * @return
     */
    public Tekton getHonnan(){ return honnan; }

    /**
     * Visszatér a gombafonal másik tektonjával. 
     * @return
     */
    public Tekton getHova(){ return hova; }

    /**
     * Visszatér a gombafonal gombatestével. 
     * @return
     */
    public Gombatest getGombatest(){ return gombatest; }

    /**
     * Beállíthatjuk vele a gombafonál gombatestjét.
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest){ this.gombatest = gombatest; }

}
